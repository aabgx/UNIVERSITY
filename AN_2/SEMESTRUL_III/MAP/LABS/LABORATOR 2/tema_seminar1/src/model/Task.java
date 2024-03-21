package model;

import java.util.Objects;

public abstract class Task {
    private String taskID;
    private String descriere;

    //constructor cu parametrii
    public Task(String taskID, String descriere){
        this.taskID=taskID;
        this.descriere=descriere;
    }

    //gettere si settere pt taskID si descriere
    public String getTaskID() {return taskID;}
    public String getDescriere() {return descriere;}
    public void setTaskID(String taskID) {this.taskID = taskID;}
    public void setDescriere(String descriere) {this.descriere = descriere;}

    //metoda abstracta execute()
    public abstract void execute();

    //override pentru toString equals si hashCode
    @Override
    public String toString(){return taskID+" "+descriere;}
    @Override
    public boolean equals(Object o){
        if(this==o) {return true;} //e egal cu el insusi
        if(!(o instanceof Task)) {return false;} //daca o nu e de tipul Task

        Task task=(Task)o;
        return Objects.equals(this.getTaskID(), task.getTaskID()) && Objects.equals(this.getDescriere(), task.getDescriere());
    }
    @Override
    public int hashCode(){return Objects.hash(this.getTaskID(),getDescriere());}
}
