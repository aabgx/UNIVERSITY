package com.example.tema_bun.repo;

        import com.example.tema_bun.domain.Copil;
        import com.example.tema_bun.domain.Entitate;
        import com.example.tema_bun.domain.Proba;
        import com.example.tema_bun.domain.Utilizator;
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

public class UtilizatorRepository implements Repository<Integer, Utilizator>{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UtilizatorRepository(Properties props) {
        logger.info("Initializing UtilizatorRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public void adauga(Utilizator elem) {
        //to do
        logger.traceEntry("saving task {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Utilizatori(username,parola) values (?,?)")){
            preStmt.setString(1,elem.getUsername());
            preStmt.setString(2,elem.getParola());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer id, Utilizator elem) {
        //to do
        logger.traceEntry("updating elem {}",elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update Utilizatori set username=?,parola=? where id=?")){
            preStmt.setString(1,elem.getUsername());
            preStmt.setString(1,elem.getParola());
            preStmt.setInt(3, id);

            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances",result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB Error"+e);
        }
    }

    @Override
    public List<Utilizator> getAll() {
        //to do
        logger.traceEntry("gettig find all");
        Connection con=dbUtils.getConnection();
        List<Utilizator> utilizatori = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Utilizatori"))
        {
            try(ResultSet result = preStmt.executeQuery())
            {
                while(result.next())
                {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String parola = result.getString("parola");
                    Utilizator utilizator = new Utilizator(username,parola);
                    utilizator.setId(id);
                    utilizatori.add(utilizator);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(utilizatori);
        return utilizatori;
    }

    public void sterge(Utilizator elem){
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from Utilizatori where id=?")){
            preStmt.setInt(1, elem.getId());

            preStmt.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
            System.err.println("Error DB " + e);
        }
    }

    public Utilizator cautaId(Integer idCautat){
        //to do
        logger.traceEntry();
        Connection con= dbUtils.getConnection();
        Utilizator utilizator;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Utilizatori where id=?")){
            preStmt.setInt(1,idCautat);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()) {
                    Integer id = result.getInt("id");
                    String username = result.getString("username");
                    String parola = result.getString("parola");

                    utilizator = new Utilizator(id, username, parola);
                    return utilizator;
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


