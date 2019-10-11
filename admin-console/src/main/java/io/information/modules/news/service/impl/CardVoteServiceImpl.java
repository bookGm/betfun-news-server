package io.information.modules.news.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.CardVoteDao;
import io.information.modules.news.entity.CardVoteEntity;
import io.information.modules.news.service.CardBaseService;
import io.information.modules.news.service.CardVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("cardVoteService")
public class CardVoteServiceImpl extends ServiceImpl<CardVoteDao, CardVoteEntity> implements CardVoteService {
    @Autowired
    CardBaseService cardBaseService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CardVoteEntity> page = this.page(
                new Query<CardVoteEntity>().getPage(params),
                new QueryWrapper<CardVoteEntity>()
        );
        for(CardVoteEntity v:page.getRecords()){
            if(StringUtil.isNotBlank(v.getCvInfo())){
                List<String> list = JSONObject.parseArray(v.getCvInfo(),String.class);
                for(int i=0;i<list.size();i++){
                    v.setInfo(i,list.get(i));
                }
            }
            v.setBaseCard(cardBaseService.getById(v.getcId()));
        }

        return new PageUtils(page);
    }
}