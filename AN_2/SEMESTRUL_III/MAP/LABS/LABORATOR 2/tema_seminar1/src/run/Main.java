package run;

import run.TestRunner;

public class Main {
    public static void main(String[] args){
        TestRunner.doar_afisare();
        TestRunner.strategy_task_runner(args[0]);
        TestRunner.printer_runner(args[0]);
        TestRunner.delay_runner(args[0]);
    }
}
