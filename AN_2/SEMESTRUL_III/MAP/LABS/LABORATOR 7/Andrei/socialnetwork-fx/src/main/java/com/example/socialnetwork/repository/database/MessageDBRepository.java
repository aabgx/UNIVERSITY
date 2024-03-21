package com.example.socialnetwork.repository.database;

import com.example.socialnetwork.domain.Message;
import com.example.socialnetwork.exception.RepoException;
import com.example.socialnetwork.repository.Repository0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessageDBRepository implements Repository0<Long, Message> {

    private final String urlDb;
    private final String username;
    private final String password;

    public MessageDBRepository(String urlDb, String username, String password) {
        this.urlDb = urlDb;
        this.username = username;
        this.password = password;
    }

    @Override
    public Message findOne(Long id) {
        if (id == null)
            throw new RepoException("id must be not null");
        Message message = null;
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long recvId = resultSet.getLong("user2");
                Long sentId = resultSet.getLong("user1");
                String messageText = resultSet.getString("text");
                String date = resultSet.getString("date");
                message = new Message(sentId, recvId, messageText, date);
                message.setId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long recvId = resultSet.getLong("user2");
                Long sentId = resultSet.getLong("user1");
                String messageText = resultSet.getString("text");
                String date = resultSet.getString("date");
                Message newMessage = new Message(sentId, recvId, messageText, date);
                newMessage.setId(id);
                messages.add(newMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return messages;
    }

    public List<Message> findAllForChat(Long recvId, Long sentId) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE user1 = ? AND user2 = ? OR user1 = ? AND user2 = ?");
            statement.setLong(1, recvId);
            statement.setLong(2, sentId);
            statement.setLong(3, sentId);
            statement.setLong(4, recvId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String messageText = resultSet.getString("text");
                String date = resultSet.getString("date");
                Long sentIdDB = resultSet.getLong("user1");
                Long recvIdDB = resultSet.getLong("user2");
                Message newMessage = new Message(sentIdDB, recvIdDB, messageText, date);
                newMessage.setId(id);
                messages.add(newMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return messages;
    }

    @Override
    public Message save(Message msg) {
        if (msg == null)
            throw new RepoException("Message must be not null");
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO messages (user1, user2, text) VALUES (?, ?, ?)");
            statement.setLong(1, msg.getSentId());
            statement.setLong(2, msg.getRecvId());
            statement.setString(3, msg.getMessage());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message delete(Long msg) {
        if (msg == null)
            throw new RepoException("Message must be not null");
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM messages WHERE id = ?");
            statement.setLong(1, msg);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message update(Message entity) {
        if (entity == null)
            throw new RepoException("Message must be not null");
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            PreparedStatement statement = connection.prepareStatement("UPDATE messages SET user1 = ?, user2 = ?, text = ? WHERE id = ?");
            statement.setLong(1, entity.getRecvId());
            statement.setLong(2, entity.getSentId());
            statement.setString(3, entity.getMessage());
            statement.setLong(4, entity.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(urlDb, username, password)) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM messages");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
