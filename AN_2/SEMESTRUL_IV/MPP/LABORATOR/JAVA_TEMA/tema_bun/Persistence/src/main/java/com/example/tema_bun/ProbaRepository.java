package com.example.tema_bun;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ProbaRepository implements Repository<String, Proba>{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ProbaRepository(Properties props) {
        logger.info("Initializing ProbaRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void adauga(Proba elem) {
        //to do
        logger.traceEntry("saving task {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Probe(id) values (?)")){
            //preStmt.setInt(1,elem.getId());
            preStmt.setString(1,elem.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(String id, Proba elem) {
        //to do
//        logger.traceEntry("updating elem {}",elem);
//        Connection con = dbUtils.getConnection();
//        try(PreparedStatement preStmt = con.prepareStatement("update Probe set nume=? where id=?")){
//            preStmt.setString(1,elem.getNume());
//            preStmt.setInt(2, id);
//
//            int result=preStmt.executeUpdate();
//            logger.trace("Updated {} instances",result);
//        } catch (SQLException e) {
//            logger.error(e);
//            System.err.println("DB Error"+e);
//        }
    }

    @Override
    public List<Proba> getAll() {
        //to do
        logger.traceEntry("gettig find all");
        Connection con=dbUtils.getConnection();
        List<Proba> probe = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Probe"))
        {
            try(ResultSet result = preStmt.executeQuery())
            {
                while(result.next())
                {
                    String id = result.getString("id");
                    //String nume = result.getString("nume");
                    Proba proba = new Proba(id);
                    //proba.setId(id);
                    probe.add(proba);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(probe);
        return probe;
    }

    public void sterge(Proba elem){
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from Probe where id=?")){
            preStmt.setString(1, elem.getId());

            preStmt.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    public Proba cautaId(String idCautat){
        //to do
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        Proba proba;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Probe where id=?")){
            preStmt.setString(1,idCautat);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()) {
                    String id = result.getString("id");
                    //String nume = result.getString("nume");

                    proba = new Proba(id);
                    return proba;
                }
            }
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        return null;
    }
}

