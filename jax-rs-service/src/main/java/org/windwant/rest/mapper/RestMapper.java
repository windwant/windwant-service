package org.windwant.rest.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/12/19.
 */
public interface RestMapper {

    String selectStudent(@Param("name") String name);
}
