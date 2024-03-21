package factory;

import container.Container;
import container.QueueContainer;
import container.StackContainer;
import container.Strategy;

public class TaskContainerFactory implements Factory{
    //constructor
    //daca nu declar unul, se defineste unul default si nu vrem sa il lasa

    private TaskContainerFactory(){}

    //aici fac sa fie singletone, daca nu a mai fost creat niciodata pana atunci, il creaza
    //altfel il refoloseste, nu pit fi create 2 instante ale clasei asteia
    private static TaskContainerFactory single_instance =null;
    public static TaskContainerFactory getInstance(){
        if(single_instance==null) single_instance=new TaskContainerFactory();
        return single_instance;
    }

    @Override
    public Container createContainer(Strategy strategy){
        if(strategy==Strategy.LIFO){
            return new StackContainer();
        }
        else if(strategy==Strategy.FIFO)
            return new QueueContainer();
        return null;
    }
}
