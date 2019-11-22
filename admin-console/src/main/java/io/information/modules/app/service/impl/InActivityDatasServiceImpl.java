package io.information.modules.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityDatasDao;
import io.information.modules.app.entity.InActivityDatas;
import io.information.modules.app.service.IInActivityDatasService;
import io.information.modules.app.vo.PassUserVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class InActivityDatasServiceImpl extends ServiceImpl<InActivityDatasDao, InActivityDatas> implements IInActivityDatasService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InActivityDatas> page = this.page(
                new Query<InActivityDatas>().getPage(params),
                new QueryWrapper<InActivityDatas>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<InActivityDatas> queryByActId(Long actId) {
        LambdaQueryWrapper<InActivityDatas> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InActivityDatas::getActId, actId);
        return this.list(queryWrapper);
    }

    @Override
    public PassUserVo pass(Map<String, Object> map) {
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        if (null != map.get("actId") && StringUtil.isNotBlank(map.get("actId"))) {
            String actId = (String) map.get("actId");
            LambdaQueryWrapper<InActivityDatas> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InActivityDatas::getActId, actId);
            IPage<InActivityDatas> page = this.page(
                    new Query<InActivityDatas>().getPage(map),
                    queryWrapper
            );

            List<InActivityDatas> list = page.getRecords();
            Map<Long, List<InActivityDatas>> m = list.stream().collect(Collectors.groupingBy(InActivityDatas::getuId));
            List list1 = new ArrayList();
            for (Map.Entry<Long, List<InActivityDatas>> longListEntry : m.entrySet()) {
                JSONObject obj = new JSONObject();
                obj.put("id", longListEntry.getKey());
                for (InActivityDatas k : longListEntry.getValue()) {
                    obj.put(k.getfName(),
                            k.getdValue() == null ? "" : k.getdValue());
                }
                list1.add(obj);
            }
            int total = (int) page.getTotal();
            return new PassUserVo(list1, total, pageSize, currPage);
        }
        return null;
    }

}