package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.TagDao;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.service.TagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;


@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String tName = (String) params.get("tName");
        Long tId = (Long) params.get("tId");

        IPage<TagEntity> page = this.page(
                new Query<TagEntity>().getPage(params),
                new QueryWrapper<TagEntity>()
                    .like(StringUtils.isNotBlank(tName),"t_name",tName)
                    .eq(tId!=null,"t_id",tId)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTag(TagEntity tag) {
//        tag.setTCreateTime(new Date());
        this.save(tag);
    }

}