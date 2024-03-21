package run;
import container.Strategy;
import model.MessageTask;
import runner.DelayTaskRunner;
import runner.PrinterTaskRunner;
import runner.StrategyTaskRunner;

import java.time.LocalDateTime;

public class TestRunner {
    //aici iau mesaje de la 3 obiecte si le folosesc in restul testelor
    public static MessageTask[] getMessages(){
        MessageTask lab1 = new MessageTask("1","seminar1","tema1","profa","studenti", LocalDateTime.now());
        MessageTask lab2 = new MessageTask("2","seminar2","tema2","profa","studenti", LocalDateTime.now());
        MessageTask lab3 = new MessageTask("3","seminar3","tema3","profa","studenti", LocalDateTime.now());
        return new MessageTask[]{lab1, lab2, lab3};
    }

    //de afisat cele 3 MessageTask-uri
    public static void doar_afisare(){
        MessageTask[] sir=getMessages();
        for(MessageTask m:sir)
            m.execute();
    }

    //de afisat via StrategyTaskRunner cu strategia luata din linia de comanda
    public static void strategy_task_runner(String parametru){
        StrategyTaskRunner program;
        if(parametru.equals("LIFO")) {
            program=new StrategyTaskRunner(Strategy.LIFO);
        }
        else {
            program=new StrategyTaskRunner(Strategy.FIFO);
        }

        MessageTask[] sir= getMessages();
        program.addTask(sir[0]);
        program.addTask(sir[1]);
        program.addTask(sir[2]);

        program.executeAll();
    }

    public static void printer_runner(String parametru){
        StrategyTaskRunner program;
        if(parametru.equals("LIFO")) {
            program=new StrategyTaskRunner(Strategy.LIFO);
        }
        else {
            program=new StrategyTaskRunner(Strategy.FIFO);
        }

        PrinterTaskRunner printer=new PrinterTaskRunner(program);
        MessageTask[] sir= getMessages();
        printer.addTask(sir[0]);
        printer.addTask(sir[1]);
        printer.addTask(sir[2]);

        printer.executeAll();

    }

    public static void delay_runner(String parametru){
        StrategyTaskRunner program;
        if(parametru.equals("LIFO")) {
            program=new StrategyTaskRunner(Strategy.LIFO);
        }
        else {
            program=new StrategyTaskRunner(Strategy.FIFO);
        }

        DelayTaskRunner delay=new DelayTaskRunner(new PrinterTaskRunner (program));
        MessageTask[] sir= getMessages();
        delay.addTask(sir[0]);
        delay.addTask(sir[1]);
        delay.addTask(sir[2]);

        delay.executeAll();
    }





}
