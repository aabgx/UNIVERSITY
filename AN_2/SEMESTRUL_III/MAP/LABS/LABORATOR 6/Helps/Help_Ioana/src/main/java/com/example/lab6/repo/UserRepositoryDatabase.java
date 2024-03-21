package com.example.lab6.repo;

import com.example.lab6.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDatabase implements Repository<Integer,User>
{
    final private String url;
    final private String username;
    final private String password;
    private List<User> lista = new ArrayList<>();

    public UserRepositoryDatabase(String url, String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
        loadData();
    }
    public void save(User utilizator)
    {
        String comanda="INSERT INTO public.\"User\" (\"Nume\",\"Parola\") VALUES (?,?);";
        try (Connection connection = DriverManager.getConnection(url, username, password);PreparedStatement ps = connection.prepareStatement(comanda))
        {
            ps.setString(1, utilizator.getNume());
            ps.setString(2, utilizator.getParola());
            lista.add(utilizator);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(User utilizator)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);Statement ps = connection.createStatement())
        {
            String comanda="DELETE FROM public.\"User\" WHERE \"Uid\"="+utilizator.getId();
            lista.remove(utilizator);
            ps.executeUpdate(comanda);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User find(Integer userToBeFound)
    {
        for(User u:lista)
        {
            if(u.getId().equals(userToBeFound))
            {
                return u;
            }
        }
        return null;
    }

    @Override
    public void update(User entity, Integer id) {

    }

    private void loadData()
    {
        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"User\""))
        {
            while (resultSet.next())
            {
                int id = resultSet.getInt("Uid");
                String firstName = resultSet.getString("Nume");
                String parola = resultSet.getString("Parola");

                User utilizator = new User(firstName, parola);
                utilizator.setId(id);
                lista.add(utilizator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getList()
    {
        return this.lista;
    }
}
