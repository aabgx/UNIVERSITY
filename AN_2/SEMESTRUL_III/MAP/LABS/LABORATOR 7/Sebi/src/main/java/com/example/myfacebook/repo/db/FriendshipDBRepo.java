package com.example.myfacebook.repo.db;

import com.example.myfacebook.domain.Friendship;
import com.example.myfacebook.repo.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FriendshipDBRepo implements Repository<Integer, Friendship> {

    private String url;
    private String userName;
    private String password;

    public FriendshipDBRepo(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }


    @Override
    public Friendship findOne(Integer id) {

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from friendships where idfr=" + id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                int idd = resultSet.getInt("idfr");
                int id1 = resultSet.getInt("id_user1");
                int id2 = resultSet.getInt("id_user2");
                String date = resultSet.getString("friendship_date");
                Friendship newFriendship = new Friendship(id1, id2, date);
                newFriendship.setId(idd);
                return newFriendship;
            } else {
                return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from friendships");
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idfr");
                int id_u1 = resultSet.getInt("id_user1");
                int id_u2 = resultSet.getInt("id_user2");
                String date = resultSet.getString("friendship_date");
                Friendship new_fr = new Friendship(id_u1, id_u2, date);
                new_fr.setId(id);
                friendships.add(new_fr);


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Friendship save(Friendship entity) {

        String sql = "insert into friendships(idfr, id_user1, id_user2, friendship_date) values(?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            Random rand = new Random();
            int upperbound = 10000;
            ps.setInt(1, rand.nextInt(upperbound));
            ps.setInt(2, entity.getU1());
            ps.setInt(3, entity.getU2());
            ps.setString(4, entity.getFriendsFrom());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Friendship delete(Integer id) {
        Friendship us = findOne(id);
        if (us != null) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, userName, password);
                PreparedStatement st = connection.prepareStatement("DELETE FROM friendships WHERE idfr = " + id);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return us;
        }

        return null;
    }

    @Override
    public Friendship update(Friendship entity) {

        String sql = "update friendships(id, id_user1, id_user2, friendship_date) values(?, ?, ?, ?) where id=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.setInt(2, entity.getU1());
            ps.setInt(3, entity.getU2());
            ps.setString(4, entity.getFriendsFrom());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }
}
