package org.catc.java.jdbc.service;

import org.catc.java.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface Service {

    List<User> list() throws SQLException;

    User byId(Long id) throws SQLException;

    User save(User user) throws SQLException;

    void delete(Long id) throws SQLException;
}
