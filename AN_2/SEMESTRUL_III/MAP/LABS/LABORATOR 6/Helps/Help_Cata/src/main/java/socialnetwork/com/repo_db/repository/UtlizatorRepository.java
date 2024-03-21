package socialnetwork.com.repo_db.repository;


import socialnetwork.com.domain.User;

import java.util.List;

public class UtlizatorRepository extends FileRepository<Integer, User>{

    public UtlizatorRepository(String filename) {
        super(filename);
    }

    /***
     * Suprascrie metoda din parinte
     * @param line Linia
     * @return Utilizatorul
     */
    @Override
    protected User LineToEntity(String line) {
        String[] sir=line.split(",");
        return new User(Integer.parseInt(sir[0]),sir[1],sir[2],sir[3],sir[4]);
    }

    /***
     * Suprascrie metoda din parinte
     * @param entity Entitatea
     * @return String-ul
     */
    @Override
    protected String EntityToLine(User entity) {
        return entity.get_id()+","+entity.getFirstname()+","+entity.getLastname()+","+entity.getEmail()+","+entity.getPassword();
    }

    /***
     * Cauta un utilizator dupa email
     * @param email, emailul utilizatorului pe care dorim sa il adaugam
     * @return utilizator, utilizatorul pe care dorim sa il cautam
     */
    public User cautaDupaEmail(String email){
        List<User> lst= getAll();
        for(User e:lst){
            if(e.getEmail().equals(email))return e;
        }
        return null;
    }

    /***
     * Cauta un utilizator dupa prenume
     * @param firstname prenumele utilizatorului
     * @return utilizatorii care au acest prenume
     */
    public List<User> cautaDupaPrenume(String firstname){
        List<User> lst= getAll();
        return lst.stream().filter(u->u.getFirstname().equals(firstname)).toList();
    }
    /***
     * Cauta un utilizator dupa nume
     * @param lastname prenumele utilizatorului
     * @return utilizatorii care au acest prenume
     */
    public List<User> cautaDupaNume(String lastname){
        List<User> lst= getAll();
        return lst.stream().filter(u->u.getLastname().equals(lastname)).toList();
    }
}