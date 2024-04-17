package com.von.api.common.service;

import java.sql.SQLException;

import com.von.api.common.component.MessengerVO;

public interface CommandService<T> {
    MessengerVO save(T t) throws SQLException;
    MessengerVO deleteById(Long id);
    MessengerVO modify(T t);

}