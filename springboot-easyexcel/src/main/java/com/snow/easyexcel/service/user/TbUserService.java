package com.snow.easyexcel.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.snow.easyexcel.entity.user.TbUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (TbUser)表服务接口
 *
 * @author makejava
 * @since 2022-03-26 16:28:41
 */
public interface TbUserService extends IService<TbUser> {

    /**
     * 分页查询
     *
     * @param tbUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<TbUser> queryByPage(TbUser tbUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    TbUser insert(TbUser tbUser);

    /**
     * 修改数据
     *
     * @param tbUser 实例对象
     * @return 实例对象
     */
    TbUser update(TbUser tbUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<TbUser> getList();

}
