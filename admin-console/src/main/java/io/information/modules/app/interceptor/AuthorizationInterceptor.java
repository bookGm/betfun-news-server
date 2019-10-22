

package io.information.modules.app.interceptor;


import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import io.information.common.utils.R;
import io.information.modules.app.service.IInUserService;
import io.jsonwebtoken.Claims;
import io.information.common.exception.RRException;
import io.information.modules.app.utils.JwtUtils;
import io.information.modules.app.annotation.Login;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 权限(Token)验证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private IInUserService inUserService;
    /**
     * app登录用户 attribute key
     */
    public static final String USER_KEY = "AppUserId";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());
        if(StringUtils.isBlank(token)){
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if(StringUtils.isBlank(token)||token.length()<6){
            printJson(request,response,HttpStatus.UNAUTHORIZED.value(),"请先登录");
            return false;
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
            printJson(request,response,HttpStatus.UNAUTHORIZED.value(),"登录超时，请重新登录");
            return false;
        }
        if(StringUtil.isBlank(claims.getSubject())){
            printJson(request,response,HttpStatus.UNAUTHORIZED.value(),"无效的token");
            return false;
        }
        Long userId=0L;
        try {
            userId=Long.parseLong(claims.getSubject());
        } catch (NumberFormatException e) {
            printJson(request,response,HttpStatus.UNAUTHORIZED.value(),"无效的token");
            return false;
        }
        if(null==inUserService.getById(userId)){
            printJson(request,response,HttpStatus.UNAUTHORIZED.value(),"无效的token");
            return false;
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, userId);

        return true;
    }

    private static void printJson(HttpServletRequest request, HttpServletResponse response, int code,String msg) {
        String content = JsonUtil.toJSONString(R.error(code,msg));
        printContent(request, response, content);
    }


    private static void printContent(HttpServletRequest request,HttpServletResponse response, String content) {
        try {
            response.reset();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,Authorization, x-ui-request,lang");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(content);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
