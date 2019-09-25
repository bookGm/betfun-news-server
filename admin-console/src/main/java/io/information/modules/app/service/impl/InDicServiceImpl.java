package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.modules.app.dao.InDicDao;
import io.information.modules.app.entity.InDic;
import io.information.modules.app.service.IInDicService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资讯字典表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InDicServiceImpl extends ServiceImpl<InDicDao, InDic> implements IInDicService {

    @Override
    public void deleteDic(Long disId) {
        //判断是否为父字典
        InDic dic = this.getById(disId);
        List<InDic> dics = this.findPSDic(dic);
        if(dics != null && !dics.isEmpty()){
            throw new IMException(ExceptionEnum.NODE_PARENT_PATH);
        }else {
            //删除
            this.removeByIds(dics);
        }
    }

    @Override
    public void updateDic(InDic dic) {
        //判断是否为父字典
        List<InDic> dics = this.findPSDic(dic);
        if(dics != null && !dics.isEmpty()){
            //修改子节点中的父节点
            dics.forEach(dicSon->{
                dicSon.setDPcode(dic.getDCode());
                this.updateById(dicSon);
            });
        }
        //更新
        this.updateById(dic);
    }

    @Override
    public List<InDic> queryDicById(Long dicId) {
        List<InDic> dicList = new ArrayList<>();
        InDic dic = this.getById(dicId);
        List<InDic> dics = this.findPSDic(dic);
        if(dics!=null&&!dics.isEmpty()){
            dicList.addAll(dics);
        }
        dicList.add(dic);
        return dicList;
    }

    @Override
    public List<InDic> queryNameDic(String dicName) {
        LambdaQueryWrapper<InDic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(InDic::getDName,dicName);
        List<InDic> dics = this.list(queryWrapper);
        return dics;
    }

    @Override
    public List<InDic> queryAllDic() {
        QueryWrapper<InDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().apply("select * from in_dic");
        List<InDic> dics = this.list(queryWrapper);
        return dics;
    }

    @Override
    public List<InDic> queryDicByCode(String dicCode) {
        QueryWrapper<InDic> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InDic::getDCode,dicCode);
        List<InDic> dicList = this.list(queryWrapper);
        dicList.forEach(dic -> {
            dicList.addAll(this.findPSDic(dic));
        });

        return dicList;
    }


    //查询节点和其下子节点
    private List<InDic> findPSDic(InDic dic){
        LambdaQueryWrapper<InDic> queryWrapper = new LambdaQueryWrapper<InDic>();
        queryWrapper.eq(InDic::getDPcode,dic.getDCode());
        List<InDic> dics = this.list(queryWrapper);
        return dics;
    }

}
