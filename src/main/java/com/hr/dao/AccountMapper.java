package com.hr.dao;

import com.hr.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by hr on 2017/08/04.
 */
@Mapper
public interface AccountMapper {

    int insert(Account account);

    Account selectByAccountId(@Param("accountId") Long accountId);

    int updateNameByAccountId(@Param("name") String name, @Param("accountId") Long accountId);

}
