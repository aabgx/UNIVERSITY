package container;

import model.Task;

import java.util.Arrays;

import static utils.Constants.INITIAL_STACK_SIZE;

public class StackContainer extends SuperclassContainer{
    public StackContainer() {
        size=0;
        tasks= new Task[INITIAL_STACK_SIZE];
    }

    public Task remove(){ //stergem de la final
        if(!isEmpty()){
            size--;
            return tasks[size];
        }
        return null;
    }
}
