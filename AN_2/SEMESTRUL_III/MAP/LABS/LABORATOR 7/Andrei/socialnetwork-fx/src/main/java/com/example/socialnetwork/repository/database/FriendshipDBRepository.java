package com.example.socialnetwork.repository.database;

import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.exception.RepoException;
import com.example.socialnetwork.repository.Repository0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FriendshipDBRepository implements Repository0<Long, Friendship> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public FriendshipDBRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Friendship findOne(Long id) throws RepoException {
        if (id == null)
            throw new RepoException("id must be not null");
        Friendship friendship = null;
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String status = resultSet.getString("status");
                friendship = new Friendship(id1, id2, status, LocalDateTime.now());
                friendship.setId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendship;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String status = resultSet.getString("status");
                LocalDateTime date = resultSet.getTimestamp("friends_from").toLocalDateTime();
                Friendship newFriendship = new Friendship(id1, id2, status, date);
                newFriendship.setId(id);
                friendships.add(newFriendship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Friendship save(Friendship entity) throws RepoException {
        if (entity == null)
            throw new RepoException("entity must be not null");
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO friendships (id1, id2, status, friends_from) VALUES (?, ?, ?, ?)");
            statement.setLong(1, entity.getLeft());
            statement.setLong(2, entity.getRight());
            statement.setString(3, entity.getStatus());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(entity.getFriendsFrom()));
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship delete(Long id) throws RepoException {
        if (id == null)
            throw new RepoException("id must be not null");
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM friendships WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship update(Friendship entity) throws RepoException {
        if (entity == null)
            throw new RepoException("entity must be not null");
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("UPDATE friendships SET id1 = ?, id2 = ?, status = ?, friends_from = ? WHERE id = ?");
            statement.setLong(1, entity.getLeft());
            statement.setLong(2, entity.getRight());
            statement.setString(3, entity.getStatus());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(entity.getFriendsFrom()));
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb)) {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM friendships");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                size = resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
