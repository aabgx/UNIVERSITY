package com.repo;

import com.domain.Prietenie;
import com.domain.Utilizator;
import repo.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PrietenieDBRepository implements Repository<String,Prietenie> {
    private final String url;
    private final String username;
    private final String password;

    public PrietenieDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Prietenie> getAll() {
        List<Prietenie> prietenii = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"Friendships\"");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String idPrieten1 = resultSet.getString("idPrieten1");
                String idPrieten2 = resultSet.getString("idPrieten2");
                String data = resultSet.getString("data");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(data, formatter);

                Boolean pending = resultSet.getBoolean("status");

                Prietenie user = new Prietenie(id, idPrieten1, idPrieten2, dateTime,pending);
                prietenii.add(user);
            }
            return prietenii;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prietenii;
    }

    @Override
    public void adauga(Prietenie prietenie) {

        String sql = "insert into \"Friendships\" (id,idprieten1,idprieten2,data,status) values (?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, prietenie.getId());
            ps.setString(2, prietenie.getIdPrieten1());
            ps.setString(3, prietenie.getIdPrieten2());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String data=prietenie.getData().format(formatter);

            ps.setString(4, data);
            ps.setBoolean(5, prietenie.getPending());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge(Prietenie prietenie) {
        String sql = "delete from \"Friendships\" where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, prietenie.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Prietenie cautaId(String id){
        String sql = "select * from \"Friendships\" where id = ? ";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while(resultSet.next()){
                String idPrieten1 = resultSet.getString("idprieten1");
                String idPrieten2 = resultSet.getString("idprieten2");
                LocalDateTime data =LocalDateTime.parse(resultSet.getString("data"),formatter);
                Boolean pending = resultSet.getBoolean("status");
                return new Prietenie(id, idPrieten1, idPrieten2, data,pending);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Prietenie prietenieVeche,Prietenie prietenieNoua){
        String sql = "update \"Friendships\" set idprieten1 = ?, idprieten2 = ?, data = ?, status = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,prietenieNoua.getIdPrieten1());
            ps.setString(2, prietenieNoua.getIdPrieten2());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String data=prietenieNoua.getData().format(formatter);

            ps.setString(3, data);
            ps.setBoolean(4, prietenieNoua.getPending());
            ps.setString(5, prietenieVeche.getId());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
