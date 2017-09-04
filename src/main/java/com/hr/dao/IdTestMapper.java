package com.hr.dao;

import com.hr.model.IdTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 * Created by hr on 2017/08/21.
 */
@Mapper
public interface IdTestMapper {

    IdTest getBySystemName(@Param("systemName") String systemName);

    int updateBySystemName(@Param("systemName") String systemName, @Param("currentNo") long currentNo, @Param("origNo") long origNo);

    List<String> getAllSystemName();

    List<IdTest> getAll();
}
