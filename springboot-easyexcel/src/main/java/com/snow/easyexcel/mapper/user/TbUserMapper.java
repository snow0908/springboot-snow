package com.snow.easyexcel.mapper.user;

import com.snow.easyexcel.entity.user.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (TbUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-26 16:28:37
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    
}

