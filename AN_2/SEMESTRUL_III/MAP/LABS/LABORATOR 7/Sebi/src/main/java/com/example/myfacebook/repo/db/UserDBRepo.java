package com.example.myfacebook.repo.db;

import com.example.myfacebook.domain.User;
import com.example.myfacebook.repo.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDBRepo implements Repository<Integer, User> {

    private String url;
    private String userName;
    private String password;

    public UserDBRepo(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }


    @Override
    public User findOne(Integer id) {
        /*Set<User> users= (Set<User>) findAll();
        for(User u:users){
            if(u.getId().equals(id)){
                return u;
            }
        }*/
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from users where id=" + id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                String city = resultSet.getString("city");
                String password = resultSet.getString("password");
                User new_user = new User(id1, name, age, sex, city, password);
                return new_user;
            } else {
                return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from users");
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String sex = resultSet.getString("sex");
                String city = resultSet.getString("city");
                String password = resultSet.getString("password");
                User new_user = new User(id, name, age, sex, city, password);
                users.add(new_user);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User entity) {

        String sql = "insert into users(id, name, age, sex, city, password) values(?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getUserID());
            ps.setString(2, entity.getUsername());
            ps.setInt(3, entity.getAge());
            ps.setString(4, entity.getSex());
            ps.setString(5, entity.getCity());
            ps.setString(6, entity.getPassword());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public User delete(Integer id) {
        /*Set<User> users= (Set<User>) findAll();
        for(User u:users){
            if(u.getId()==id){
                users.remove(u);
            }
        }*/
        User us = findOne(id);
        if (us != null) {
            try {
                Connection connection = DriverManager.getConnection(url, userName, password);
                PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?");
                st.setInt(1, id);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return us;
        }

        return null;
    }

    @Override
    public User update(User entity) {
        String sql = "update users(id, name, age, sex, city, password) values(?, ?, ?, ?, ?, ?) where id=" + entity.getUserID();

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getUserID());
            ps.setString(2, entity.getUsername());
            ps.setInt(3, entity.getAge());
            ps.setString(4, entity.getSex());
            ps.setString(5, entity.getCity());
            ps.setString(5, entity.getPassword());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }
}
