package com.example.myfacebook.repo.db;

import com.example.myfacebook.domain.Friendship;
import com.example.myfacebook.domain.Request;
import com.example.myfacebook.repo.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RequestDBRepo implements Repository<Integer, Request> {

    private String url;
    private String userName;
    private String password;

    public RequestDBRepo(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Request findOne(Integer id) {
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from requests where idreq=" + id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                int idd = resultSet.getInt("idreq");
                int id1 = resultSet.getInt("user1");
                int id2 = resultSet.getInt("user2");
                Request newRequest = new Request(id1, id2);
                newRequest.setId(idd);
                return newRequest;
            } else {
                return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Request> findAll() {
        Set<Request> requests = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from requests");
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idreq");
                int id_u1 = resultSet.getInt("user1");
                int id_u2 = resultSet.getInt("user2");
                Request new_fr = new Request(id_u1, id_u2);
                new_fr.setId(id);
                requests.add(new_fr);


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public Request save(Request entity) {
        String sql = "insert into requests(idreq, user1, user2) values(?, ?, ?)";


        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            Random rand = new Random();
            int upperbound = 10000;
            ps.setInt(1, rand.nextInt(upperbound));
            ps.setInt(2, entity.getFrom());
            ps.setInt(3, entity.getTo());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Request delete(Integer id) {
        Request us = findOne(id);
        if (us != null) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, userName, password);
                PreparedStatement st = connection.prepareStatement("DELETE FROM requests WHERE idreq = " + id);
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
    public Request update(Request entity) {
        String sql = "update requests(idreq, user1, user2) values(?, ?, ?) where idreq=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.setInt(2, entity.getFrom());
            ps.setInt(3, entity.getTo());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

}
