package com.snow.easyexcel.controller.user;

import com.snow.common.pojo.PublicResult;
import com.snow.easyexcel.entity.user.TbUser;
import com.snow.easyexcel.service.user.TbUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TbUser)表控制层
 *
 * @author makejava
 * @since 2022-03-26 16:28:33
 */
@RestController
@RequestMapping("tbUser")
public class TbUserController {
    /**
     * 服务对象
     */
    @Resource
    private TbUserService service;

    /**
     * 分页查询
     *
     * @param tbUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<TbUser>> queryByPage(TbUser tbUser, PageRequest pageRequest) {
        return ResponseEntity.ok(service.queryByPage(tbUser, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public PublicResult<TbUser> queryById(@PathVariable("id") Integer id) {
		return PublicResult.buildQuerySucess(service.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param tbUser 实体
     * @return 新增结果
     */
	@PostMapping("add")
    public PublicResult<TbUser> add(TbUser tbUser) {
        return PublicResult.buildAddSucess(service.insert(tbUser));
    }

    /**
     * 编辑数据
     *
     * @param tbUser 实体
     * @return 编辑结果
     */
    @PutMapping
    public PublicResult<TbUser> edit(TbUser tbUser) {
        return PublicResult.buildUpdateSucess(service.update(tbUser));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public PublicResult<Boolean> deleteById(Integer id) {
        return PublicResult.buildDeleteSucess(service.deleteById(id));
    }

}

