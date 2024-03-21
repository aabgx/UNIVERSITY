package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.domain.Request;
import com.example.socialnetworkgui.domain.RequestStatus;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.UserRequestDTO;
import com.example.socialnetworkgui.domain.exceptions.EntityAlreadyFound;
import com.example.socialnetworkgui.domain.exceptions.EntityNotFound;
import com.example.socialnetworkgui.domain.exceptions.ValidationException;
import com.example.socialnetworkgui.service.ServiceGUI;
import com.example.socialnetworkgui.service.ServiceRequest;
import com.example.socialnetworkgui.utils.events.RequestEntityChangeEvent;
import com.example.socialnetworkgui.utils.observer.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.socialnetworkgui.utils.Constants.DATE_TIME_FORMATTER;

public class RequestsController implements Observer<RequestEntityChangeEvent> {

    User loggedUser= null;
    ServiceRequest serviceRequest;
    ServiceGUI serviceGUI;

    @FXML
    private Button acceptRequestBtn;

    @FXML
    private Button rejectRequestBtn;

    @FXML
    private TableColumn<UserRequestDTO, String> fromFirstName;

    @FXML
    private TableColumn<UserRequestDTO, String> fromLastName;

    @FXML
    private TableColumn<UserRequestDTO, String> sentAtColumn;

    @FXML
    private TableColumn<UserRequestDTO, String> statusColumn;

    @FXML
    private TableView<UserRequestDTO> tableRequests;

    ObservableList<UserRequestDTO> model= FXCollections.observableArrayList();

    public void setService(ServiceRequest serviceRequest, ServiceGUI serviceGUI){
        this.serviceGUI=serviceGUI;
        this.serviceRequest=serviceRequest;
        serviceRequest.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize(){
        fromFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        fromLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        sentAtColumn.setCellValueFactory(new PropertyValueFactory<>("sentAt"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableRequests.setItems(model);
    }

    private void initModel(){
        Iterable<Request> all= serviceRequest.allRequests();

        //get requests for current user
        Predicate<Request> p= r-> r.getId().getSecond().equals(serviceGUI.getLoggedUser().getId());
        List<Request> allR= StreamSupport.stream(all.spliterator(), false)
                .filter(p).collect(Collectors.toList());

        //convert Request to UserRequestDTO
        List<UserRequestDTO> allUR= allR.stream().map(x-> new UserRequestDTO(serviceRequest.getWithId(x.getId().getFirst()).getFirstName(),
                serviceRequest.getWithId(x.getId().getFirst()).getLastName(), x.getSentAt().format(DATE_TIME_FORMATTER), x.getStatus().toString())).collect(Collectors.toList());
        model.setAll(allUR);
    }


    @Override
    public void update(RequestEntityChangeEvent requestEntityChangeEvent) {
        initModel();
    }

    public void acceptRequest(ActionEvent actionEvent) {
        UserRequestDTO ur= tableRequests.getSelectionModel().getSelectedItem();
        if(!ur.getStatus().equals("SENT")){
            MessageAlert.showErrorMessage(null, "Cererea trebuie sa fie SENT!");
            return;
        }
        Long id1= serviceGUI.getLoggedUser().getId();
        Long id2= serviceRequest.getWithName(ur.getFirstName(), ur.getLastName()).getId();

        Request toUpdate= new Request(id1, id2, LocalDateTime.parse(ur.getSentAt(), DATE_TIME_FORMATTER), RequestStatus.ACCEPTED);
        try{
            serviceGUI.addFriendship(id1, id2);
            serviceRequest.updateRequest(toUpdate);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Accepted friend request!");
        }catch (EntityAlreadyFound| EntityNotFound e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void rejectRequest(ActionEvent actionEvent) {
        UserRequestDTO ur= tableRequests.getSelectionModel().getSelectedItem();
        if(!ur.getStatus().equals("SENT")){
            MessageAlert.showErrorMessage(null, "Cererea trebuie sa fie SENT!");
            return;
        }
        Long id1= serviceGUI.getLoggedUser().getId();
        Long id2= serviceRequest.getWithName(ur.getFirstName(), ur.getLastName()).getId();

        //Request toDelete= new Request(id1, id2, LocalDateTime.parse(ur.getSentAt(), DATE_TIME_FORMATTER), RequestStatus.SENT);
        try{
            serviceRequest.deleteRequest(id1, id2);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Info", "Rejected friend request!");

        }catch (EntityNotFound| IllegalArgumentException| ValidationException e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }
}
