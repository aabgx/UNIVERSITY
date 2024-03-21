package socialnetwork.com.repo_db;

import socialnetwork.com.domain.Friend;
import socialnetwork.com.repo_db.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FriendDbRepository implements Repository<Integer, Friend> {
    private String url;
    private String username;
    private String password;

    private String table ="prietenie";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FriendDbRepository(String url, String username, String password){
        this.url=url;
        this.username=username;
        this.password=password;
    }

    public FriendDbRepository(String url, String username, String password, String table){
        this.url=url;
        this.username=username;
        this.password=password;
        this.table = table;
    }

    @Override
    public Friend add(Friend entity) {
        String prieten1=entity.getPrieten1().toString();
        String prieten2=entity.getPrieten2().toString();
        String data= "'"+entity.getData().format(formatter)+"'";
        String pending = entity.getPending().toString();
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO "+ table +" VALUES "+"("+prieten1+", "+prieten2+", "+data+", "+pending+")");
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }
    @Override
    public Friend delete(Friend entity) {
        String prieten1=entity.getPrieten1().toString();
        String prieten2=entity.getPrieten2().toString();
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("delete from "+ table +" where id_prieten_1 = "+prieten1 + " and id_prieten_2 = "+prieten2);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Friend findById(Integer... id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("select * from "+ table +" where id_prieten_1 = "+id[0].toString()+" and id_prieten_2 = "+id[1].toString());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer prieten1=resultSet.getInt("id_prieten_1");
                Integer prieten2=resultSet.getInt("id_prieten_2");
                LocalDateTime data =LocalDateTime.parse(resultSet.getString("data"),formatter);
                Boolean pending = resultSet.getBoolean("pending");
                return new Friend(prieten1,prieten2,data,pending);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Friend update(Friend entity, Friend new_entity) {
        String prieten1=new_entity.getPrieten1().toString();
        String prieten2=new_entity.getPrieten2().toString();
        String data= "'"+new_entity.getData().format(formatter)+"'";
        String pending = new_entity.getPending().toString();
        String id_1 = entity.getPrieten1().toString();
        String id_2=entity.getPrieten2().toString();
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("update "+ table +" set "+"id_prieten_1 = "+prieten1 + ", id_prieten_2 = "+prieten2+", data = "+data+", pending = "+pending+" where id_prieten_1 = "+id_1+" and id_prieten_2 = "+id_2);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public List<Friend> getAll() {
        List<Friend> lst = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("select * from "+ table +"");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer prieten1=resultSet.getInt("id_prieten_1");
                Integer prieten2=resultSet.getInt("id_prieten_2");
                LocalDateTime data =LocalDateTime.parse(resultSet.getString("data"),formatter);
                Boolean pending = resultSet.getBoolean("pending");
                lst.add(new Friend(prieten1,prieten2,data,pending));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lst;
    }
}
