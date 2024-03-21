package container;

import model.Task;
import java.util.Arrays;
import static utils.Constants.INITIAL_QUEUE_SIZE;
import static utils.Constants.INITIAL_STACK_SIZE;

public class QueueContainer extends SuperclassContainer{
    public QueueContainer() {
        size=0;
        tasks= new Task[INITIAL_QUEUE_SIZE];
    }

    public Task remove(){ //stergem de la inceput
        if(!isEmpty()){
            Task t[]=new Task[tasks.length-1];
            for(int i=1;i<tasks.length;i++)
                t[i-1]=tasks[i];
            return tasks[0];
        }
        return null;
    }
}
