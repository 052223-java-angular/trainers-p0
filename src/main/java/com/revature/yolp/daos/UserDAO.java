package com.revature.yolp.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.yolp.models.User;
import com.revature.yolp.utils.ConnectionFactory;

/**
 * The UserDAO class handles database operations for User objects.
 * It implements the CrudDAO interface.
 */
public class UserDAO implements CrudDAO<User> {

    @Override
    public void save(User obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO users (id, username, password, role_id) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set values for prepared statement parameters
                ps.setString(1, obj.getId());
                ps.setString(2, obj.getUsername());
                ps.setString(3, obj.getPassword());
                ps.setString(4, obj.getRoleId());

                // Execute the SQL statement
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }
    }

    @Override
    public void update(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public User findById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the User object if found, otherwise an empty
     *         Optional
     */
    public Optional<User> findByUsername(String username) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                // Set the username parameter for the prepared statement
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Create a new User object and populate it with data from the result set
                        User user = new User();
                        user.setId(rs.getString("id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setRoleId(rs.getString("role_id"));
                        return Optional.of(user);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }

        return Optional.empty();
    }
}
