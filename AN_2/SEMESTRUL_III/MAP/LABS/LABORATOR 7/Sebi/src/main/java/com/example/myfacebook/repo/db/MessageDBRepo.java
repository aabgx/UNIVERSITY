package com.example.myfacebook.repo.db;

import com.example.myfacebook.domain.Message;
import com.example.myfacebook.domain.Request;
import com.example.myfacebook.repo.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MessageDBRepo implements Repository<Integer, Message> {

    private String url;
    private String userName;
    private String password;

    public MessageDBRepo(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Message findOne(Integer id) {

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from messages where idMessage=" + id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                int idd = resultSet.getInt("idMessage");
                int id1 = resultSet.getInt("idFromUser");
                int id2 = resultSet.getInt("idToUser");
                String data = resultSet.getString("dataSent");
                Message newMessage = new Message(id1, id2, data);
                newMessage.setId(idd);
                return newMessage;
            } else {
                return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement statement = connection.prepareStatement("select * from messages");
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                int id = resultSet.getInt("idMessage");
                int id_u1 = resultSet.getInt("idFromUser");
                int id_u2 = resultSet.getInt("idToUser");
                String data = resultSet.getString("dataSent");
                Message new_mess = new Message(id_u1, id_u2, data);
                new_mess.setId(id);
                messages.add(new_mess);


            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }


    @Override
    public Message save(Message entity) {

        String sql = "insert into messages values(?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            Random rand = new Random();
            int upperbound = 10000;
            ps.setInt(1, entity.getIdMessage());
            ps.setInt(2, entity.getIdFromUser());
            ps.setInt(3, entity.getIdToUser());
            ps.setString(4, entity.getDataSent());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Message delete(Integer id) {
        Message us = findOne(id);
        if (us != null) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, userName, password);
                PreparedStatement st = connection.prepareStatement("DELETE FROM messages WHERE idMessage = " + id);
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
    public Message update(Message entity) {
        String sql = "update messages(idMessage, idFromUser, idToUser, dataSent) values(?, ?, ?, ?) where idMessage=" + entity.getId();

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.setInt(2, entity.getIdFromUser());
            ps.setInt(3, entity.getIdToUser());
            ps.setString(4, entity.getDataSent());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

}
