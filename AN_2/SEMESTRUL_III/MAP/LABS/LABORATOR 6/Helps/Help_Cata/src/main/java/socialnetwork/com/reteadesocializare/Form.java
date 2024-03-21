package socialnetwork.com.reteadesocializare;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form extends GridPane {
    private List<String> lst;
    private Button btn;
    private String title;
    private Map<String, TextField> lines = new HashMap<>();


    public Form(String title,Button btn, String... lst) {
        this.lst= List.of(lst);
        this.btn = btn;
        this.title=title;
        init();
        createForm();
    }
    public void init(){
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));
    }
    public void createForm(){
        Text scenetitle = new Text(title);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(scenetitle, 0, 0, 2, 1);
        for(int i=0;i<lst.size();i++) {
            String crt = lst.get(i);
            Label label = new Label(crt);
            add(label, 0, i + 1);

            TextField textField = new TextField();
            add(textField, 1, i + 1);
            lines.put(crt, textField);
        }
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 1, lst.size()+1);
    }

    public Map<String,TextField>getLines(){
        return lines;
    }

    public void clearLines(){
        for (Map.Entry<String,TextField> entry : lines.entrySet())
            entry.getValue().setText("");
    }
}
