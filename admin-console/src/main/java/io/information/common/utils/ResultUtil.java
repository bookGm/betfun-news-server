package io.information.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@ApiModel("api接口通用返回")
public class ResultUtil<T> implements Serializable{
 
	private static final long serialVersionUID = -2476335985187907541L;
	
	public ResultUtil(){
	}
	
	public ResultUtil(int code,String msg,T data){
		this.code=code;
        this.msg=msg;
		this.data=data;
	}
	
	@ApiModelProperty(value = "返回码",dataType = "int")
    private int code;//0 成功  1失败
	
	@ApiModelProperty(value = "返回内容",dataType = "String")
    private String msg;//0 成功  1失败
 
    @ApiModelProperty(value = "返回数据",dataType = "Object")
    private T data;
    
    private static <T>ResultUtil<T> getInstance(int code,String msg,T data){
    	ResultUtil<T> instance=null;
    	if(null==instance){
    		instance=new ResultUtil<T>(code,msg,data);
    	}
    	return instance;
    }
    
    public static <T>ResultUtil<T> ERR(BindingResult br){
		List<ObjectError> oes=br.getAllErrors();
		StringBuffer sb=new StringBuffer();
		int index=1;
		for(ObjectError oe:oes){
			sb.append(index).append("").append(oe.getDefaultMessage()).append("\r\n");
			index++;
		}
		return error(sb.toString());
	}
    
    public static <T> ResultUtil<T> ok(){
   	   return getInstance(0,"success",null);
    }
    
    public static <T> ResultUtil<T> ok(T data){
    	   return getInstance(0,"success",data);
    }
    
    public static <T> ResultUtil<T> error(){
 	   return getInstance(HttpStatus.SC_INTERNAL_SERVER_ERROR,"未知异常，请联系管理员",null);
    }
    
    public static <T> ResultUtil<T> error(String msg){
  	   return getInstance(HttpStatus.SC_INTERNAL_SERVER_ERROR,msg,null);
     }
    
    public static <T> ResultUtil<T> error(int code,String msg){
  	   return getInstance(code,msg,null);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
    
}
