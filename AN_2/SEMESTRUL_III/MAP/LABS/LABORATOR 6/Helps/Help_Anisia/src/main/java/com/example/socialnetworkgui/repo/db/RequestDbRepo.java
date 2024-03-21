package com.example.socialnetworkgui.repo.db;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Pair;
import com.example.socialnetworkgui.domain.Request;
import com.example.socialnetworkgui.domain.RequestStatus;
import com.example.socialnetworkgui.domain.validators.RequestValidator;
import com.example.socialnetworkgui.domain.validators.Validator;
import com.example.socialnetworkgui.repo.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.example.socialnetworkgui.utils.Constants.DATE_TIME_FORMATTER;

public class RequestDbRepo implements Repository<Pair<Long,Long>, Request> {

    private String URL;
    private String userName;
    private String password;
    private Validator<Request> validator;

    public RequestDbRepo(String URL, String userName, String password, Validator<Request> validator) {
        this.URL = URL;
        this.userName = userName;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Request save(Request entity) {
        if(entity.getId()==null) throw new IllegalArgumentException("ID cannot be null!");
        Request found= findOne(entity.getId());
        validator.validate(entity);

        String sql= "INSERT INTO requests(id1, id2, sentat, status) VALUES(?,?,?,?)";

        if(found == null){
            try(Connection connection= DriverManager.getConnection(URL, userName, password);
                PreparedStatement ps= connection.prepareStatement(sql)){
                ps.setLong(1, entity.getId().getFirst());
                ps.setLong(2, entity.getId().getSecond());
                ps.setString(3, entity.getSentAt().format(DATE_TIME_FORMATTER));
                ps.setString(4, entity.getStatus().toString());
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return found;
    }

    @Override
    public Request delete(Pair<Long, Long> longLongPair) {
        if(longLongPair==null) throw new IllegalArgumentException("ID cannot be null!");
        Request found= findOne(longLongPair);
        if(found!=null) {
            Long id1 = found.getId().getFirst();
            Long id2 = found.getId().getSecond();

            String Sql = "DELETE FROM requests WHERE id1='" + id1.toString() + "'" +
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
    public Request findOne(Pair<Long, Long> longLongPair) {
        for(Request r: findAll()){
            if(r.getId().equals(longLongPair)){
                return r;
            }
        }
        return null;
    }

    @Override
    public Iterable<Request> findAll() {
        Set<Request> allRequests= new HashSet<>();
        try(Connection connection= DriverManager.getConnection(URL, userName, password);
            PreparedStatement ps= connection.prepareStatement("SELECT * FROM requests");
            ResultSet rs= ps.executeQuery()){
            while (rs.next()){
                Long id1= rs.getLong("id1");
                Long id2= rs.getLong("id2");
                String dateString= rs.getString("sentat");
                LocalDateTime date= LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
                String status= rs.getString("status");
                RequestStatus st= null;
                if(status.equals("SENT")){
                    st= RequestStatus.SENT;
                }
                if(status.equals("ACCEPTED")){
                    st= RequestStatus.ACCEPTED;
                }
                if(status.equals("REJECTED")){
                    st= RequestStatus.REJECTED;
                }
                Request r= new Request(id1, id2, date, st);
                allRequests.add(r);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allRequests;
    }

    @Override
    public Request update(Request entity) {
        if(entity.getId()==null) throw new IllegalArgumentException("ID cannot be null!");

        Request found= findOne(entity.getId());

        if(found!=null){
            validator.validate(entity);
            String Sql= "UPDATE requests SET sentat='"+entity.getSentAt().format(DATE_TIME_FORMATTER)+"'" +
                    ", status='"+entity.getStatus().toString()+"'"+
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
