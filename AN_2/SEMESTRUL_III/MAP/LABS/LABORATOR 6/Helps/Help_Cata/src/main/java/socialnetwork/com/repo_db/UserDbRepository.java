package socialnetwork.com.repo_db;

import socialnetwork.com.domain.User;
import socialnetwork.com.repo_db.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDbRepository implements Repository<Integer, User> {
    private String url;
    private String username;
    private String password;

    private String tabel = "users";

    public UserDbRepository(String url, String username, String password){
        this.url=url;
        this.username=username;
        this.password=password;
    }
    public UserDbRepository(String url, String username, String password, String tabel){
        this.url=url;
        this.username=username;
        this.password=password;
        this.tabel=tabel;
    }

    @Override
    public User add(User entity) {
        String firstname = "'" +entity.getFirstname()+"'";
        String lastname = "'" +entity.getLastname()+"'";
        String email = "'" +entity.getEmail()+"'";
        String parola = "'" +entity.getPassword()+"'";
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO "+tabel+" VALUES "+"("+firstname+", "+lastname+", "+email+", "+parola+")");
            statement.execute();
            entity.set_id(getId(entity.getEmail()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }
    @Override
    public User delete(User entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("delete from "+tabel+" where id = "+entity.get_id().toString());
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public User findById(Integer... id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("select * from "+tabel+" where id = "+id[0].toString());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String firstname = resultSet.getString("firstname");
                String lastname= resultSet.getString("lastname");
                String email=resultSet.getString("email");
                String parola=resultSet.getString("parola");
                return new User(id[0],firstname,lastname,email,parola);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User update(User entity, User new_entity) {
        String firstname = "'" +new_entity.getFirstname()+"'";
        String lastname = "'" +new_entity.getLastname()+"'";
        String email = "'" +new_entity.getEmail()+"'";
        String parola = "'" +new_entity.getPassword()+"'";
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("update "+tabel+" set "+"firstname = "+firstname + ", lastname = "+lastname+", email = "+email+", parola = "+parola+" where id = "+entity.get_id().toString());
            statement.execute();
            new_entity.set_id(entity.get_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public List<User> getAll() {
        List<User> lst = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("select * from "+tabel);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname= resultSet.getString("lastname");
                String email=resultSet.getString("email");
                String parola=resultSet.getString("parola");
                lst.add(new User(id,firstname,lastname,email,parola));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lst;
    }
    private Integer getId(String email){
        email="'"+email+"'";
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = connection.prepareStatement("select * from "+tabel+" where email = "+email);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer id = resultSet.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
