package socialnetwork.com.reteadesocializare;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import socialnetwork.com.reteadesocializare.Observers.Observer;

import java.util.List;

public abstract class TableList extends VBox implements Observer {
    private ListView listView = new ListView<>();
    List<Button> btns;

    public ListView getListView(){
        return listView;
    }

    public void setListView(ListView listView){
        this.listView= listView;
    }

    TableList(Button... btns){
        this.btns= List.of(btns);
        getChildren().add(listView);
        Button btn = new Button("Reload");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reload();
            }
        });
        getChildren().add(btn);
        for(Button b:btns){
            getChildren().add(b);
        }
    }
}
