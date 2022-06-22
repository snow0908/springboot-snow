package com.snow.es.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snow.es.entity.TbSyJzsj;
import com.snow.es.service.TbSyJzsjService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 就诊事件索引(TbSyJzsj)表控制层
 *
 * @author makejava
 * @since 2022-05-13 14:32:28
 */
@RestController
@RequestMapping("tbSyJzsj")
public class TbSyJzsjController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private TbSyJzsjService tbSyJzsjService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param tbSyJzsj 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<TbSyJzsj> page, TbSyJzsj tbSyJzsj) {
        return success(this.tbSyJzsjService.page(page, new QueryWrapper<>(tbSyJzsj)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.tbSyJzsjService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param tbSyJzsj 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody TbSyJzsj tbSyJzsj) {
        return success(this.tbSyJzsjService.save(tbSyJzsj));
    }

    /**
     * 修改数据
     *
     * @param tbSyJzsj 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody TbSyJzsj tbSyJzsj) {
        return success(this.tbSyJzsjService.updateById(tbSyJzsj));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.tbSyJzsjService.removeByIds(idList));
    }
}

