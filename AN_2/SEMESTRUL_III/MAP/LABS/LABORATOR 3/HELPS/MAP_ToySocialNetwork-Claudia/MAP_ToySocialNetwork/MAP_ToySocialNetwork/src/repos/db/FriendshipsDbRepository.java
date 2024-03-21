package repos.db;

import constants.Constants;
import domain.Friendship;
import domain.User;
import repos.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FriendshipsDbRepository implements Repository<Friendship> {
    private final Connection connection;

    public FriendshipsDbRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Friendship entity) {
        String sql = "insert into friendships (id, friend_one_id, friend_two_id) values (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getFriendOneId());
            preparedStatement.setString(3, entity.getFriendTwoId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Friendship entity) {
        String sql = "delete from friendships where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Friendship getById(String id) {
        String sql = "select * from friendships where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            String friendOneId = resultSet.getString("friend_one_id");
            String friendTwoId = resultSet.getString("friend_two_id");

            return new Friendship(id, friendOneId, friendTwoId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Friendship> getAll() {
        List<Friendship> friendships = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String friendOneId = resultSet.getString("friend_one_id");
                String friendWtoId = resultSet.getString("friend_two_id");

                friendships.add(new Friendship(id, friendOneId, friendWtoId));
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public void update(Friendship entity) {

    }


}
