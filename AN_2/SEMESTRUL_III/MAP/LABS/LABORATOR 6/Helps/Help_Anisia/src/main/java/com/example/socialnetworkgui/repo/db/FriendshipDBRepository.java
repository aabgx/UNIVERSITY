package com.example.socialnetworkgui.repo.db;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repo.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.example.socialnetworkgui.utils.Constants.DATE_TIME_FORMATTER;

public class FriendshipDBRepository implements Repository<Pair<Long, Long>, Friendship> {
    private String URL;
    private String userName;
    private String password;
    private Validator<Friendship> validator;

    public FriendshipDBRepository(String URL, String userName, String password, Validator<Friendship> validator) {
        this.URL = URL;
        this.userName = userName;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public Friendship save(Friendship entity) {
        if(entity.getId()==null) throw new IllegalArgumentException("ID cannot be null!");

        Friendship found= findOne(entity.getId());
        validator.validate(entity);

        String Sql= "INSERT INTO friendships(id1, id2, friendsfrom) VALUES(?,?,?);";
        if(found==null){
            try(Connection connection= DriverManager.getConnection(URL, userName, password);
                PreparedStatement ps= connection.prepareStatement(Sql)){
                ps.setLong(1, entity.getId().getFirst());
                ps.setLong(2, entity.getId().getSecond());
                ps.setString(3, entity.getDate().format(DATE_TIME_FORMATTER));
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return found;
    }

    @Override
    public Friendship delete(Pair<Long, Long> longLongPair) {
        if(longLongPair==null) throw new IllegalArgumentException("ID cannot be null!");
        Friendship found= findOne(longLongPair);
        if(found!=null) {
            Long id1 = found.getId().getFirst();
            Long id2 = found.getId().getSecond();

            String Sql = "DELETE FROM friendships WHERE id1='" + id1.toString() + "'" +
                    "AND id2='" + id2.toString() + "'";
            try (Connection connection = DriverManager.getConnection(URL, userName, password);
                 PreparedStatement ps = connection.prepareStatement(Sql)) {
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return found;
    }

    @Override
    public Friendship findOne(Pair<Long, Long> longLongPair) {
        for(Friendship f: findAll()){
            if(f.getId().equals(longLongPair)){
                return f;
            }
        }
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> allF= new HashSet<>();
        try(Connection connection= DriverManager.getConnection(URL, userName, password);
            PreparedStatement ps= connection.prepareStatement("SELECT * FROM friendships");
            ResultSet rs= ps.executeQuery()){
            while(rs.next()){
                Long id1= rs.getLong("id1");
                Long id2= rs.getLong("id2");
                String dateString= rs.getString("friendsfrom");
                LocalDateTime date= LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);

                Friendship f= new Friendship(id1, id2, date);
                allF.add(f);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allF;
    }

    @Override
    public Friendship update(Friendship entity) {
        if(entity.getId()==null) throw new IllegalArgumentException("ID cannot be null!");

        Friendship found= findOne(entity.getId());

        if(found!=null){
            validator.validate(entity);
            String Sql= "UPDATE friendships SET friendsfrom='"+entity.getDate().format(DATE_TIME_FORMATTER)+"'"+
                    "WHERE id1='"+found.getId().getFirst().toString()+"'"+
                    "AND id2='"+found.getId().getSecond().toString()+"'";

            try(Connection connection=DriverManager.getConnection(URL, userName, password);
                PreparedStatement ps= connection.prepareStatement(Sql)){
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return found;
    }
}
