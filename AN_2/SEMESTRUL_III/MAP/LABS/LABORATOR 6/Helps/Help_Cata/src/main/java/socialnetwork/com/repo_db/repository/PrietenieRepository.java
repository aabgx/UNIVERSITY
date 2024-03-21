package socialnetwork.com.repo_db.repository;

import socialnetwork.com.domain.Friend;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrietenieRepository extends FileRepository<Integer, Friend>{
    public PrietenieRepository(String filename) {
        super(filename);
    }

    /***
     * Suprascrie metoda din clasa parinte
     * @param line Linia
     * @return Entitatea
     */
    @Override
    protected Friend LineToEntity(String line) {
        String[] sir=line.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Friend(Integer.parseInt(sir[1]),Integer.parseInt(sir[2]), LocalDateTime.parse(sir[3],formatter));
    }

    /***
     * Suprascrie metoda din parinte
     * @param entity Entitatea
     * @return Entitatea transformata in String
     */
    @Override
    protected String EntityToLine(Friend entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return entity.get_id()+","+entity.getPrieten1()+","+entity.getPrieten2()+","+entity.getData().format(formatter);
    }
}
