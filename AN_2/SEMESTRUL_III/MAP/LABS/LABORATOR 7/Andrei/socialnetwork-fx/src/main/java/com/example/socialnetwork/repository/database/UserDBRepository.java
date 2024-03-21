package com.example.socialnetwork.repository.database;

import com.example.socialnetwork.domain.User;
import com.example.socialnetwork.exception.RepoException;
import com.example.socialnetwork.repository.Repository0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class UserDBRepository implements Repository0<Long, User> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public UserDBRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public User findOne(Long id) throws RepoException {
        if (id == null)
            throw new RepoException("id must be not null");
        User user = null;
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                user = new User(first_name, last_name);
                user.setId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                User newUser = new User(first_name, last_name);
                newUser.setId(id);
                users.add(newUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User user) throws RepoException {
        String sql = "INSERT INTO users (first_name, last_name) VALUES (?,?)";
        try {
            Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try(Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User newUser) {
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE id = ?";
        try(Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newUser.getFirstName());
            ps.setString(2, newUser.getLastName());
            ps.setLong(3, newUser.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int size() {
        return 0;
    }


}

