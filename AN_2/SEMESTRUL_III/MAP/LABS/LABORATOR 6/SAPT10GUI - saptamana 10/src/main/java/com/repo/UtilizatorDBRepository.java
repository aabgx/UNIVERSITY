package com.repo;

import com.domain.Utilizator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizatorDBRepository implements repo.Repository<String,Utilizator> {

    private final String url;
    private final String username;
    private final String password;

    public UtilizatorDBRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Utilizator> getAll() {
        List<Utilizator> utilizatori = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"Users\"");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String email = resultSet.getString("email");
                String parola = resultSet.getString("parola");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");

                Utilizator user = new Utilizator(id, email, parola, nume, prenume);
                utilizatori.add(user);
            }
            return utilizatori;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilizatori;
    }

    @Override
    public void adauga(Utilizator utilizator) {

        String sql = "insert into \"Users\" (id,email,parola,nume,prenume) values (?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, utilizator.getId());
            ps.setString(2, utilizator.getEmail());
            ps.setString(3, utilizator.getParola());
            ps.setString(4, utilizator.getNume());
            ps.setString(5, utilizator.getPrenume());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge(Utilizator utilizator) {
        String sql = "delete from \"Users\" where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, utilizator.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utilizator cautaId(String id){
        String sql = "select * from \"Users\" where id = ? ";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            String email = resultSet.getString("email");
            String parola = resultSet.getString("parola");
            String nume = resultSet.getString("nume");
            String prenume = resultSet.getString("prenume");
            return new Utilizator(id, email, parola, nume, prenume);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Utilizator utilizatorVechi,Utilizator utilizatorNou){
        String sql = "update \"Users\" set email = ?, parola = ?, nume = ?,prenume = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,utilizatorNou.getEmail());
            ps.setString(2, utilizatorNou.getParola());
            ps.setString(3, utilizatorNou.getNume());
            ps.setString(4, utilizatorNou.getPrenume());
            ps.setString(5, utilizatorVechi.getId());

            ps.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
