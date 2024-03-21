package socialnetwork.com.reteadesocializare.Observers;

public interface Observable {
    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
