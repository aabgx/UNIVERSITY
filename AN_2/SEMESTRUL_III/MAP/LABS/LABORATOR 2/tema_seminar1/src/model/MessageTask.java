package model;
import utils.Constants;

import java.time.LocalDateTime;
import utils.Constants;

public class MessageTask extends Task {
    private String message;
    private String from;
    private String to;
    private LocalDateTime date;

    //constructor
    public MessageTask(String taskID, String descriere,String message, String from, String to, LocalDateTime date){
        super(taskID,descriere); //face un obiect Task cu astea 2 atribute, sa nu avem badsmell
        this.message=message;
        this.from=from;
        this.to=to;
        this.date=date;
    }

    public String toString(){
        return super.toString()+" "+ message+ " "+ from+" "+ to+" "+date.format(Constants.DATE_TIME_FORMATTER);
    }
    public void execute() {System.out.println(toString());}

    //gettere si settere pt toate campurile
    public String getMessage() {return message;}
    public String getFrom() {return from;}
    public String getTo() {return to;}
    public LocalDateTime getDate() {return date;}
    public void setMessage(String message) {this.message = message;}
    public void setFrom(String from) {this.from = from;}
    public void setTo(String to) {this.to = to;}
    public void setDate(LocalDateTime date) {this.date = date;}
}
