package com.example.ydhy;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface tkMapper<T> extends MySqlMapper<T>,Mapper<T> {
}
