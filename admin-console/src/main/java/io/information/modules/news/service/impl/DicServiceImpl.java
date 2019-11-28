package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.common.utils.RedisKeys;
import io.information.modules.news.dao.DicDao;
import io.information.modules.news.entity.DicEntity;
import io.information.modules.news.service.DicService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DicServiceImpl extends ServiceImpl<DicDao, DicEntity> implements DicService {

    @Override
    public List<DicEntity> queryDidAscList() {
        QueryWrapper<DicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(DicEntity::getdId);
        return this.list(queryWrapper);
    }

    @Override
    @Cacheable(value = RedisKeys.CONSTANT, key = "#key")
    public Map<String, List<DicEntity>> getListAll(String key) {
        HashMap<String, List<DicEntity>> map = new HashMap<>();
        QueryWrapper<DicEntity> queryWrapper = new QueryWrapper<>();
        List<DicEntity> dicts = this.list(queryWrapper);
        map.put("dict", dicts);
        return map;
    }

    @Override
    public void deleteDic(Long[] dIds) {
        //判断是否为父字典
        for (Long dId : dIds) {
            DicEntity dic = this.getById(dId);
            List<DicEntity> dics = this.findPSDic(dic);
            if (dics != null && !dics.isEmpty()) {
                throw new IMException(ExceptionEnum.NODE_PARENT_PATH);
            } else {
                //删除
                this.removeByIds(dics);
            }
        }
    }

    @Override
    public void updateDic(DicEntity dic) {
        //判断是否为父字典
        List<DicEntity> dics = this.findPSDic(dic);
        if (dics != null && !dics.isEmpty()) {
            //修改子节点中的父节点
            dics.forEach(dicSon -> {
                dicSon.setdPcode(dic.getdCode());
                this.updateById(dicSon);
            });
        }
        //更新
        this.updateById(dic);
    }


    //查询节点和其下子节点
    private List<DicEntity> findPSDic(DicEntity dic) {
        LambdaQueryWrapper<DicEntity> queryWrapper = new LambdaQueryWrapper<DicEntity>();
        queryWrapper.eq(DicEntity::getdPcode, dic.getdCode());
        return this.list(queryWrapper);
    }
}