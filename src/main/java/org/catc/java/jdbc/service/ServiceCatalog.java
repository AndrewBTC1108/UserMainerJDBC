package org.catc.java.jdbc.service;

import org.catc.java.jdbc.model.User;
import org.catc.java.jdbc.repository.Repository;
import org.catc.java.jdbc.repository.UserRepository;
import org.catc.java.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ServiceCatalog implements Service{

    private Repository<User> userRepository;

    //Instance class UserRepository with the Constructor
    public ServiceCatalog() {
        this.userRepository = new UserRepository();
    }

    @Override
    public List<User> list() throws SQLException {
        try(Connection conn = DatabaseConnection.getConnection()){
            //set the Database Connection
            userRepository.setConn(conn);
            return userRepository.list();
        }
    }

    @Override
    public User byId(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            //set the Database Connection
            userRepository.setConn(conn);
            return userRepository.byId(id);
        }
    }

    @Override
    public User save(User user) throws SQLException {
        //enable auto commit
        try (Connection conn = DatabaseConnection.getConnection()){
            //set the Database Connection
            userRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            User newUser = null;

            try{
                newUser = userRepository.save(user);
                //commit
                conn.commit();
                //catch some error
            }catch (SQLException e){
                //if something goes fail make rollback
                conn.rollback();
                e.printStackTrace();
            }
            return newUser;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            //set the Database Connection
            userRepository.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                userRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
