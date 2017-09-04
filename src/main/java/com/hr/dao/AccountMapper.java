package com.hr.dao;

import com.hr.model.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hr on 2017/08/04.
 */
@Mapper
public interface AccountMapper {

    int insert(Account account);

}
