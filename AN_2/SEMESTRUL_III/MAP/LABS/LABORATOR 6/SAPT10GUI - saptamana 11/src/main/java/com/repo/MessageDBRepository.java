package com.repo;

import com.domain.Message;
import com.domain.Utilizator;

import java.sql.*;
import java.util.*;

public class MessageDBRepository implements repo.Repository<String, Message> {

    private final String url;
    private final String username;
    private final String password;

    public MessageDBRepository(String url, String userName, String password) {
        this.url = url;
        this.username = userName;
        this.password = password;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"Message\"");
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String id_u1 = resultSet.getString("idFromUser");
                String id_u2 = resultSet.getString("idToUser");
                String data = resultSet.getString("dataSent");
                Message new_mess = new Message(id, id_u1, id_u2, data);
                messages.add(new_mess);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public void adauga(Message entity) {

        String sql = "insert into \"Message\" values(?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getIdToUser());
            ps.setString(2, entity.getDataSent());
            ps.setString(3, entity.getId());
            ps.setString(4, entity.getIdFromUser());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge(Message message) {
        String sql = "delete from \"Message\" where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, message.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message cautaId(String id) {

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from \"Message\" where id=" + id);
             ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                String idd = resultSet.getString("id");
                String id1 = resultSet.getString("idFromUser");
                String id2 = resultSet.getString("idToUser");
                String data = resultSet.getString("dataSent");
                Message newMessage = new Message(id,id1, id2, data);

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
    public void update(Message mVechi,Message mNou){
        String sql = "update \"Message\"(id, idFromUser, idToUser, dataSent) values(?, ?, ?, ?) where idMessage= " + mVechi.getId();
        //String sql = "update \"Message\" set idFromUser = ?, idToUser = ?, dataSent = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, mVechi.getIdMessage());
            ps.setString(2,mNou.getIdFromUser());
            ps.setString(3, mNou.getIdToUser());
            ps.setString(4, mNou.getDataSent());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


//    @Override
//    public void update(Message entity) {
//        String sql = "update \"Message\"(id, idFromUser, idToUser, dataSent) values(?, ?, ?, ?) where idMessage=" + entity.getId();
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, entity.getId());
//            ps.setString(2, entity.getIdFromUser());
//            ps.setString(3, entity.getIdToUser());
//            ps.setString(4, entity.getDataSent());
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
