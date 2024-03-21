package com.controller;

import com.HelloApplication;
import com.domain.Utilizator;
import com.domain.validators.ValidationException;
import com.service.Service;
import com.utils.events.FriendshipEntityChangeEvent;
import com.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorController implements Observer<FriendshipEntityChangeEvent> {
    Service service;
    ObservableList<Utilizator> model= FXCollections.observableArrayList();

    @FXML
    private Button trimiteMesajBtn;
    @FXML
    public TableView<Utilizator> tableView;
    @FXML
    private TableColumn<Utilizator, String> emailColoana;
    @FXML
    public TableColumn<Utilizator, String> numeColoana;
    @FXML
    public TableColumn<Utilizator,String> prenumeColoana;

    public void setService(Service service) {
        this.service = service;
        initModel();
        service.addObserver(this);
    }

    @FXML
    public void initialize() {
        emailColoana.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("email"));
        numeColoana.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("nume"));
        prenumeColoana.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("prenume"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<Utilizator> allUsers = service.obtineUtilizatoriPrieteni();
        List<Utilizator> users = StreamSupport.stream(allUsers.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
    }

    //pt Observers
    @Override
    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent) {
        initModel();
    }

    public void handleMesaj(ActionEvent actionEvent) throws IOException {
        Utilizator selected= tableView.getSelectionModel().getSelectedItem();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/mesajView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("CONVERSATIE PAGE");
        MessageController messageController = fxmlLoader.getController();

        messageController.setService(service,selected);
        stage.show();
    }

    public void handleSterge(ActionEvent actionEvent) {
        try{
            String id1= service.getIdCurent();
            Utilizator selected= tableView.getSelectionModel().getSelectedItem();
            String id2= selected.getId();
            service.stergePrieten(id1, id2);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "INFO", "PRIETENIA A FOST STEARSA!");
            //initModel();
        }catch (ValidationException e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void handleAdauga(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/adaugaView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("ADAUGA PAGE");
        AdaugaController adaugaController = fxmlLoader.getController();

        adaugaController.setService(service);
        stage.show();
    }

    public void handleCereri(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/cereriView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("CERERI TRIMISE PAGE");
        CereriController cereriController = fxmlLoader.getController();

        cereriController.setService(service);
        stage.show();
    }

    public void handleAccepta(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/acceptaView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("ACCEPTA PAGE");
        AcceptaController acceptaController = fxmlLoader.getController();

        acceptaController.setService(service);
        stage.show();
    }

    public void handleLogout(ActionEvent actionEvent) throws IOException {
        service.deconectareUtilizator();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/startView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("START PAGE");
        StartController startController = fxmlLoader.getController();

        startController.setService(service);
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide(); //ca sa ascunda prima fereastra
    }
}
