package com.example.tema_bun.repo;

import com.example.tema_bun.domain.Copil;
import com.example.tema_bun.domain.Entitate;
import com.example.tema_bun.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CopilRepository implements Repository<Integer, Copil>{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CopilRepository(Properties props) {
        logger.info("Initializing CopilRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void adauga(Copil elem) {
        //to do
        logger.traceEntry("saving task {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Copii(nume, varsta) values (?,?)")){
            preStmt.setString(1,elem.getNume());
            preStmt.setInt(2,elem.getVarsta());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer id, Copil elem) {
        //to do
        logger.traceEntry("updating elem {}",elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update Copii set nume=?, varsta=? where id=?")){
            preStmt.setString(1,elem.getNume());
            preStmt.setInt(2, elem.getVarsta());
            preStmt.setInt(3, id);

            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error"+e);
        }
    }

    @Override
    public List<Copil> getAll() {
        //to do
        logger.traceEntry("gettig find all");
        Connection con=dbUtils.getConnection();
        List<Copil> copii = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Copii"))
        {
            try(ResultSet result = preStmt.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    Copil copil = new Copil(nume,varsta);
                    copil.setId(id);
                    copii.add(copil);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(copii);
        return copii;
    }

    public void sterge(Copil elem){
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from Copii where id=?")){
            preStmt.setInt(1, elem.getId());

            preStmt.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    public Copil cautaId(Integer idCautat){
        //to do
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        Copil copil;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Copii where id=?")){
            preStmt.setInt(1,idCautat);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()) {
                    Integer id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer varsta = result.getInt("varsta");

                    copil = new Copil(id, nume, varsta);
                    return copil;
                }
            }
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }

        return null;
    }

    public List<String> JoinCopilInscrieri(String string_id_proba){
        logger.traceEntry("gettig name+age");
        Connection con= dbUtils.getConnection();
        List<String> copii = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT c.nume as nume,c.varsta as varsta FROM Copii c INNER JOIN Inscrieri i ON c.id = i.id_copil WHERE i.id_proba = ?")){
            preStmt.setString(1,string_id_proba);
            try(ResultSet result = preStmt.executeQuery())
            {
                while(result.next())
                {
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    String str_format = nume + " - " + varsta + " ani";
                    copii.add(str_format);
                }
            }
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(copii);
        return copii;
    }
}
