package com.controller;

import com.domain.Message;
import com.domain.Utilizator;
import com.domain.validators.ValidationException;
import com.service.Service;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.lang.constant.Constable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static javafx.scene.control.TableColumn.*;

public class MessageController {
    Service service;
    Utilizator conversant;

    @FXML
    public TextField mesajField;
    @FXML
    public TableView<String> tableView;
    @FXML
    private TableColumn<String, String> mesajeColumn;

    @FXML
    public ListView<String> listView;
    ObservableList<String> model= FXCollections.observableArrayList();

    public void setService(Service service,Utilizator conversant) {
        this.service = service;
        this.conversant=conversant;
        initModel();
        //service.addObserver(this);
    }

    @FXML
    public void initialize() {
//        mesajeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("dataSent"));
          listView.setItems(model);
//        tableView.setItems(model);
    }
    private void initModel() {
        Iterable<String> allM = service.obtineConv(conversant.getId());
        List<String> m = StreamSupport.stream(allM.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(m);
    }

    @FXML
    void handleTrimite(ActionEvent event) {
        String message=mesajField.getText();
        service.trimiteMesaj(conversant.getId(), message);
        initModel();
    }
}
