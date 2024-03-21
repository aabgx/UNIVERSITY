package socialnetwork.com.reteadesocializare.Observers;

import java.util.ArrayList;
import java.util.List;

public class ObservableI implements Observable{

    List<Observer> lst = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        lst.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        lst.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o:lst){
            o.reload();
        }
    }
}
