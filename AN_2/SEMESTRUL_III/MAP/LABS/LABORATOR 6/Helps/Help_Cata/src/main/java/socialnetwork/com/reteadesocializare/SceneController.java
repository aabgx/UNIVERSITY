package socialnetwork.com.reteadesocializare;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneController {
    public Map<String,Scene> scenes = new HashMap<>();

    private Stage primaryStage;
    private List<Pair<String,String>> pages = new ArrayList<>();

    public SceneController(Stage primaryStage){
        pages.add(new Pair<String,String>("Login","login"));
        pages.add(new Pair<String,String>("Create an account","createAccount"));
        pages.add(new Pair<String,String>("Add a friend","addFriend"));
        pages.add(new Pair<String,String>("Delete a friend","deleteFriend"));
        pages.add(new Pair<String,String>("Friends request","friendRequest"));
        pages.add(new Pair<String,String>("User List","userList"));
        this.primaryStage=primaryStage;
    }

    public void addNewScene(String name,Scene scene){
        scenes.put(name,scene);
    }
    public Scene getScene(String name){
        return scenes.get(name);
    }

    public Scene genMenuWithScene(String name){
        MenuBar mb = new MenuBar();
        for(Pair<String,String> p:pages){
            Menu m = new Menu(p.getKey());
            MenuItem item = new MenuItem(p.getKey());
            item.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    setScene(p.getValue());
                }
            });
            m.getItems().add(item);
            mb.getMenus().add(m);
        }

        VBox vb = new VBox();
        vb.getChildren().add(mb);
        vb.getChildren().add(getScene(name).getRoot());

        return new Scene(vb);
    }

    public void setScene(String name){
        Scene scene = genMenuWithScene(name);
        primaryStage.setScene(scene);
    }
}
