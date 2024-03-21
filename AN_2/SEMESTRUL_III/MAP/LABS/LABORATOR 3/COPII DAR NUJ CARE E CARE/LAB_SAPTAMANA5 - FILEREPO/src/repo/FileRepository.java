package repo;

import domain.Entitate;
import domain.Prietenie;
import domain.Utilizator;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//abstracta pt ca avem si Utilizatori si Prietenii de luat, diferit, din fisiere (si de pus in fisiere)
//trebe sa ii spun extends entitate ca sa pot folosi getId mai jos
public abstract class FileRepository<ID,entityType extends Entitate<ID>> implements Repository<ID,entityType> {
    private final String fileName;

    /**
     * constructor*/
    public FileRepository(String fileName) {this.fileName=fileName;}

    //metode de luat si de pus date din si in fisier, diferite pt Utilizator si Prietenie
    /**
     * Transforma o linie din fisierul din care se preiau date intr-o entitate dorita.
     * @param line: linia de transformat
     * @return entitatea obtinuta din acea linie
     */
    protected abstract entityType lineToEntity(String line);
    /**
     * Transforma o entitate intr-o linie de pus in fisier
     * @param entity: entiattea de transformat
     * @return linia obtinuta din acea entitate
     */
    protected abstract String entityToLine(entityType entity);

    //loadFromFile
    /**
    * Incarca entitatile din fisier
     * @return lista entitatilor
     * @throws FileNotFoundException daca nu gaseste fisierul dat
     * @throws IOException daca se incearca accesarea fisierului cu un path gresit
    * */
    //public abstract void updateUtilizator(Utilizator u, String Nume,String Prenume);
    //public abstract void updatePrietenie(Prietenie p, LocalDateTime data);
    protected List<entityType> loadFromFile() {
        List<entityType> lst = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line=reader.readLine())!=null){
                lst.add(lineToEntity(line));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst;
    }

    //saveToFile
    /**
     * Salveaza o lista de entitati data ca parametru in fisier
     * @param lst - lista de salvat
     * @throws IOException daca se incearca accesarea fisierului cu un path gresit
     * */

    //nu e elegant dar am nevoie in teste sa il pot folosi
    public void saveToFile(List<entityType> lst) {
        //System.out.println(entities);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (entityType e : lst) {
                writer.write(entityToLine(e));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adauga o entitate in lista
     * @param entity - entitatea de adaugat*/
    @Override
    public void adauga(entityType entity) {
        List<entityType> entities = loadFromFile();
        entities.add(entity);
        saveToFile(entities);
    }

    /**
     * Sterge o entitate din lista
     * @param entity - entitatea de sters*/
    @Override
    public void sterge(entityType entity) {
        List<entityType> entities= loadFromFile();
        entities.remove(entity);
        saveToFile(entities);
    }

    /**
     * Cauta o entitate dupa un id dat
     * @param id - id-ul de cautat in lista de entitati
     * @return entitatea cautata sau null daca id-ul nu reprezinta o entitate*/
    @Override
    public entityType cautaId(String id) {
        for (entityType entity : loadFromFile()){
            if(entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Getter pentru lista de entitati
     * @return lista de entitati*/
    @Override
    public List<entityType> getAll() {
        return loadFromFile();
    }

    @Override
    public entityType update(entityType entity,entityType new_entity) {
        sterge(entity);
        adauga(new_entity);
        return entity;
    }
}
