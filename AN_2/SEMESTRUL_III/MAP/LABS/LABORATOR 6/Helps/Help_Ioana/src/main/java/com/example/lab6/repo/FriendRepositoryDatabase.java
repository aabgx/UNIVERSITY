package com.example.lab6.repo;

import com.example.lab6.domain.Friendship;
import com.example.lab6.domain.Pair;
import com.example.lab6.domain.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendRepositoryDatabase implements Repository<Pair<Integer>, Friendship>
{
    final private String url;
    final private String username;
    final private String password;
    final private UserRepositoryDatabase repo;
    private List<Friendship> lista = new ArrayList<>();

    public FriendRepositoryDatabase(String url, String username, String password,UserRepositoryDatabase repo)
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.repo = repo;
        loadData();
    }
    public void save(Friendship utilizator)
    {
        String comanda="INSERT INTO public.\"Friendship\" (\"Uid1\",\"Uid2\",\"Date\",\"Time\",\"Acceptat\") VALUES (?,?,?,?,?);";
        try (Connection connection = DriverManager.getConnection(url, username, password);PreparedStatement ps = connection.prepareStatement(comanda))
        {
            ps.setInt(1, utilizator.getId().getId1());
            ps.setInt(2, utilizator.getId().getId2());
            String[] list = utilizator.getData().toString().split("T");
            ps.setDate(3, Date.valueOf(list[0]));
            String[] data=list[1].split(":");
            String curr=data[0]+":"+data[1]+":00";
            ps.setTime(4, Time.valueOf(curr));
            ps.setBoolean(5,utilizator.isAcceptat());
            lista.add(utilizator);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(Friendship utilizator)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);Statement ps = connection.createStatement())
        {
            String comanda1="DELETE FROM public.\"Friendship\" WHERE \"Uid1\"="+utilizator.getId().getId1()+" AND \"Uid2\"="+utilizator.getId().getId2()+";";
            String comanda2="DELETE FROM public.\"Friendship\" WHERE \"Uid1\"="+utilizator.getId().getId2()+" AND \"Uid2\"="+utilizator.getId().getId1()+";";
            lista.remove(utilizator);
            ps.executeUpdate(comanda1);
            ps.executeUpdate(comanda2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Friendship find(Pair<Integer> userToBeFound)
    {
        for(Friendship u:lista)
        {
            if(u.getId().equals(userToBeFound))
            {
                return u;
            }
        }
        return null;
        /*try (Connection connection = DriverManager.getConnection(url, username, password);Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"Friendship\" WHERE \"Uid1\"="+userToBeFound.getId1()+" AND \"Uid2\"="+userToBeFound.getId2()+" OR \"Uid1\"="+userToBeFound.getId2()+" AND \"Uid2\"="+userToBeFound.getId1()))
        {
            if(resultSet.next())
            {
                int id1 = resultSet.getInt("Uid1");
                int id2 = resultSet.getInt("Uid2");
                Pair<Integer> pereche = new Pair<>(id1,id2);
                String data = resultSet.getString("Date");
                String time = resultSet.getString("Time");
                User u1 = repo.find(id1);
                User u2 = repo.find(id2);
                Friendship prieteni = new Friendship(u1,u2, LocalDateTime.parse(data+"T"+time));
                prieteni.setId(pereche);
                return prieteni;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;*/
    }
    public void update(Friendship utilizator,Pair<Integer> id)
    {

    }
    public List<Friendship> getList()
    {
        return lista;
    }
    private void loadData()
    {
        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);ResultSet resultSet = statement.executeQuery("SELECT * FROM public.\"Friendship\""))
        {
            while (resultSet.next())
            {
                int id1 = resultSet.getInt("Uid1");
                int id2 = resultSet.getInt("Uid2");
                Pair<Integer> pereche = new Pair<>(id1,id2);
                String data = resultSet.getString("Date");
                String time = resultSet.getString("Time");
                Boolean val = resultSet.getBoolean("Acceptat");
                User u1 = repo.find(id1);
                User u2 = repo.find(id2);
                Friendship prieteni = new Friendship(u1,u2, LocalDateTime.parse(data+"T"+time));
                prieteni.setId(pereche);
                prieteni.setAcceptat(val);
                lista.add(prieteni);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
