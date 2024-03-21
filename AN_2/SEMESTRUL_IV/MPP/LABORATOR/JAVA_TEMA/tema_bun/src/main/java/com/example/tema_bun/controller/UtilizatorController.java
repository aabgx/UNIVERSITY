package com.example.tema_bun.controller;

import com.example.tema_bun.HelloApplication;
import com.example.tema_bun.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UtilizatorController {
    private Service service;
    @FXML
    private Label d_6_8=new Label();
    @FXML
    private Label d_9_11=new Label();
    @FXML
    private Label d_12_15=new Label();
    @FXML
    private Label c_6_8=new Label();
    @FXML
    private Label c_9_11=new Label();
    @FXML
    private Label c_12_15=new Label();
    @FXML
    private Label p_6_8=new Label();
    @FXML
    private Label p_9_11=new Label();
    @FXML
    private Label p_12_15=new Label();

    @FXML
    private ComboBox probaCombo;
    @FXML
    private ComboBox categorieCombo;
    @FXML
    private ListView cautaListView;

    //PENTRU INSCRIERE
    @FXML
    private Spinner varstaSpinner;
    @FXML
    private RadioButton desenRadio;
    @FXML
    private RadioButton cautareRadio;
    @FXML
    private RadioButton poezieRadio;
    @FXML
    private TextField numeField;
    @FXML
    private Button inscriereBtn;

    ObservableList<String> model= FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        //initModel();
        setCasute();
        setCombo();
        setSpinner();
    }

    @FXML
    public void initialize() {
        cautaListView.setItems(model);
    }
    private void initModel(){
    }


    public void setCasute(){
        List<Integer> list = service.creareVectorCnt();
        d_6_8.setText(list.get(0).toString());
        d_9_11.setText(list.get(1).toString());
        d_12_15.setText(list.get(2).toString());
        c_6_8.setText(list.get(3).toString());
        c_9_11.setText(list.get(4).toString());
        c_12_15.setText(list.get(5).toString());
        p_6_8.setText(list.get(6).toString());
        p_9_11.setText(list.get(7).toString());
        p_12_15.setText(list.get(8).toString());
    }

    public void setCombo(){
        probaCombo.getItems().add("desen");
        probaCombo.getItems().add("cautare de comori");
        probaCombo.getItems().add("poezie");

        categorieCombo.getItems().add("6_8");
        categorieCombo.getItems().add("9_11");
        categorieCombo.getItems().add("12_15");
    }

    public void setSpinner(){
        SpinnerValueFactory<Integer> varstaSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(6,15,6);
        this.varstaSpinner.setValueFactory(varstaSpinner);
        //this.varstaSpinner.setEditable(true);
    }


    public void handleSearch() {
        if(probaCombo.getValue() != null && categorieCombo.getValue()!=null) {
            String proba = probaCombo.getValue().toString();
            String categorie = categorieCombo.getValue().toString();
            model.clear();

            System.out.println(proba + " + " +categorie);

//            for(String nume: service.gasesteNumeVarstaCopii(service.gasesteParticipanti(proba,categorie))){
//                model.add(nume);
//            }
//
//            for(String s: model){
//                System.out.println(s);
//            }
            for(String numeVarsta: service.JoinInscrieriCopii(proba,categorie)){
                model.add(numeVarsta);
            }
            initModel();
        }
    }

    public void handleInscriere(){
        if((desenRadio.isSelected() && cautareRadio.isSelected() && poezieRadio.isSelected()) ||
        !(desenRadio.isSelected() || cautareRadio.isSelected() || poezieRadio.isSelected())){
            MessageAlert.showErrorMessage(null, "SELECTATI MINIM UNA SI MAXIM 2 PROBE!");
        }
        else if(numeField.getText().equals("")){
            MessageAlert.showErrorMessage(null, "NUMELE NU POATE FI NULL!");
        }
        else{
            List<String> probe=new ArrayList<String>();
            if(desenRadio.isSelected()) probe.add("desen");
            if(cautareRadio.isSelected()) probe.add("cautare de comori");
            if(poezieRadio.isSelected()) probe.add("poezie");

            service.adaugaCopilInscriere(numeField.getText().toString(), (Integer) varstaSpinner.getValue(), probe);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"INFO","INSCRIERE EFECTUATA CU SUCCES!");
            setCasute();
        }
    }

    public void handleLogout(ActionEvent actionEvent){
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/startView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            StartController startController = fxmlLoader.getController();
            startController.setService(service);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
