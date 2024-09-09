package org.catc.java.jdbc.repository;

import org.catc.java.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User>{

    private Connection conn;

    public UserRepository() {//constructor
    }
    //setter to set Database Connection
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<User> list() throws SQLException{
        List<User> users = new ArrayList<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                while (rs.next()){
                    //This return a  User's list
                    User p = createUser(rs);
                    users.add(p);
                }
        }
        return users;
    }

    @Override
    public User byId(Long id) throws SQLException {
        User user = null;
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")){
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    user = createUser(rs);
                }
            }
        }
        return user;
    }

    @Override
    public User save(User user) throws SQLException {
        String sql;
        if (user.getId() != null && user.getId()>0) {//it means, there is an id, and it would be an update
            sql = "UPDATE users SET username=?, password=?, email=? WHERE id=?";
        } else {
            sql = "INSERT users (username, password, email) VALUES(?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            //if there's an id, it means that is an update
            if (user.getId() != null && user.getId() > 0) {
                stmt.setLong(4, user.getId());
            }

            stmt.executeUpdate();

            return user;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private User createUser(ResultSet rs) throws SQLException {
        //instance User class
        User u = new User();
        //take from rs the id and more
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));
        return u;
    }
}
