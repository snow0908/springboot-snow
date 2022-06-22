package com.snow.easyexcel.service.impl.user;

import com.snow.easyexcel.entity.user.TbUser;
import com.snow.easyexcel.mapper.user.TbUserMapper;
import com.snow.easyexcel.service.user.TbUserService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TbUser)表服务实现类
 *
 * @author makejava
 * @since 2022-03-26 16:28:42
 */
@Service("tbUserService")
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper,TbUser> implements TbUserService {
    @Resource
    private TbUserMapper mapper;


    /**
     * 分页查询
     *
     * @param tbUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<TbUser> queryByPage(TbUser tbUser, PageRequest pageRequest) {
        return null;
    }

    /**
     * 新增数据
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    @Override
    public TbUser insert(TbUser tbUser) {
		save(tbUser);
        return tbUser;
    }

    /**
     * 修改数据
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    @Override
    public TbUser update(TbUser tbUser) {
		updateById(tbUser);
        return tbUser;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
		return removeById(id);
    }

    @Override
    public List<TbUser> getList() {
        return list();
    }
}
