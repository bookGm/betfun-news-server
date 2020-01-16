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
import io.information.modules.news.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DicServiceImpl extends ServiceImpl<DicDao, DicEntity> implements DicService {
    @Autowired
    private NodeService nodeService;

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
        List<DicEntity> dicts = this.list();
        ArrayList<DicEntity> sumList = new ArrayList<>(dicts);
        for (DicEntity dic : dicts) {
            Long noType = dic.getdId();
            //noId,noName  根据字典ID找到节点下一级
            Map<Long, String> search = nodeService.search(noType);
            if (null != search) {
                for (Long noId : search.keySet()) {
                    //创建新的字典对象
                    DicEntity nodeDic = new DicEntity();
                    nodeDic.setdId(noId);
                    nodeDic.setdName(search.get(noId));
                    nodeDic.setpId(noType);
                    sumList.add(nodeDic);
                }
            }
        }
        map.put("dict", sumList);
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