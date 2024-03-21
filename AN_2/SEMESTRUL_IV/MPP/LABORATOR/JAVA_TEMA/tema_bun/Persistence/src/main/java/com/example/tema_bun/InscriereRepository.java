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

public class InscriereRepository implements Repository<Integer, Inscriere>{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public InscriereRepository(Properties props) {
        logger.info("Initializing InscriereRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void adauga(Inscriere elem) {
        //to do
        logger.traceEntry("saving task {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Inscrieri(id_copil,id_proba) values (?,?)")){
            //preStmt.setInt(1,elem.getId());
            preStmt.setInt(1,elem.getId_copil());
            preStmt.setString(2,elem.getId_proba());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void sterge(Inscriere entity) {

    }

    @Override
    public Inscriere cautaId(Integer integer) {
        return null;
    }

    @Override
    public List<Inscriere> getAll() {
        //to do
        logger.traceEntry("gettig find all");
        Connection con=dbUtils.getConnection();
        List<Inscriere> probe = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Inscrieri"))
        {
            try(ResultSet result = preStmt.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("id");
                    int id_copil = result.getInt("id_copil");
                    String id_proba = result.getString("id_proba");
                    Inscriere inscriere = new Inscriere(id_copil,id_proba);
                    inscriere.setId(id);
                    probe.add(inscriere);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(probe);
        return probe;
    }

    @Override
    public void update(Integer integer, Inscriere elem) {

    }
}
