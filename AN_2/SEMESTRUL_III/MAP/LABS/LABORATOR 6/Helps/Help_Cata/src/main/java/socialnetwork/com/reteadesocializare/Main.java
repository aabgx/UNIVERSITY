package socialnetwork.com.reteadesocializare;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import socialnetwork.com.repo_db.FriendDbRepository;
import socialnetwork.com.repo_db.UserDbRepository;
import socialnetwork.com.service.Service;

public class Main extends Application{

    private Service srv;

    public static void main(String[] args)  {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserDbRepository utilizatori = new UserDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres");
        FriendDbRepository prieteni = new FriendDbRepository("jdbc:postgresql://localhost:5432/retea_de_socializare","postgres","postgres");
        Service srv= new Service(utilizatori,prieteni);
        UiController app = new UiController(primaryStage,srv);
        app.start();
    }
}