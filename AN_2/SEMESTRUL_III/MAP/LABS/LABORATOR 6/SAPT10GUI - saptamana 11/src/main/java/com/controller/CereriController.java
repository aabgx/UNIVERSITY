package com.controller;

import com.domain.Utilizator;
import com.domain.validators.ValidationException;
import com.service.Service;
import com.utils.events.FriendshipEntityChangeEvent;
import com.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CereriController implements Observer<FriendshipEntityChangeEvent> {
    Service service;
    @FXML
    public TableView<Utilizator> tableView;
    @FXML
    private TableColumn<Utilizator, String> emailColoana;
    @FXML
    public TableColumn<Utilizator, String> numeColoana;
    @FXML
    public TableColumn<Utilizator,String> prenumeColoana;

    ObservableList<Utilizator> model= FXCollections.observableArrayList();

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
        Iterable<Utilizator> allUsers = service.obtineUtilizatoriCereriTrimise();
        List<Utilizator> users = StreamSupport.stream(allUsers.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);
    }

    @Override
    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent) {
        initModel();
    }

    public void handleSterge(ActionEvent actionEvent) {
        try{
            Utilizator selected= tableView.getSelectionModel().getSelectedItem();
            String id= selected.getId();
            service.stergeCerereTrimisa(id);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "INFO", "CEREREA A FOST STEARSA!");
            //initModel();
        }catch (ValidationException e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }
}
