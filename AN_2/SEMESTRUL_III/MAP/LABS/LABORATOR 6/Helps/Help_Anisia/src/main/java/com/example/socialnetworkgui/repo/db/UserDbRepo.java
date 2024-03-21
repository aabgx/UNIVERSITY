package com.example.socialnetworkgui.repo.db;

import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repo.memory.InMemoryRepo;

import java.sql.*;

public class UserDbRepo extends InMemoryRepo<Long, User> {
    private String URL;
    private String userName;
    private String password;

    public UserDbRepo(Validator<User> validator, String URL, String userName, String password) {
        super(validator);
        this.URL = URL;
        this.userName = userName;
        this.password = password;
        loadData();
    }

    private void loadData() {
        try (Connection connection = DriverManager.getConnection(URL, userName, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String fName = rs.getString("firstname");
                String lName = rs.getString("lastname");
                String email = rs.getString("email");
                User u = new User(fName, lName, email);
                u.setId(id);
                super.save(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findOne(Long aLong) {
        return super.findOne(aLong);
    }

    @Override
    public Iterable<User> findAll() {
        return super.findAll();
    }

    @Override
    public User save(User entity) {
        User returned= super.save(entity);
        if(returned==null){  //daca nu am mai gasit userul
            String sql = "INSERT INTO users(id, firstname, lastname, email) VALUES (?,?,?,?);";
            try (Connection connection = DriverManager.getConnection(URL, userName, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, entity.getId());
                ps.setString(2, entity.getFirstName());
                ps.setString(3, entity.getLastName());
                ps.setString(4, entity.getEmail());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return returned;
    }

    @Override
    public User delete(Long aLong) {
        User returned= super.delete(aLong);
        if(returned!=null){
            String Sql = "DELETE FROM users WHERE id='" + aLong.toString() + "'";
            try (Connection connection = DriverManager.getConnection(URL, userName, password);
                 PreparedStatement ps = connection.prepareStatement(Sql)) {
                int st = ps.executeUpdate();
                //System.out.println(st);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return returned;
    }

    @Override
    public User update(User entity) {
        User returned= super.update(entity);
        if(returned!=null){
            String Sql = "UPDATE users SET firstname='" + entity.getFirstName() + "'" + "," +
                    "lastname='" + entity.getLastName() + "'" + "," +
                    "email='" + entity.getEmail() + "'" +
                    "WHERE id='" + entity.getId() + "'";

            try (Connection connection = DriverManager.getConnection(URL, userName, password);
                 PreparedStatement ps = connection.prepareStatement(Sql)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return returned;
    }
}
