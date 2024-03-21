package container;

import model.Task;

import static utils.Constants.INITIAL_QUEUE_SIZE;

public abstract class SuperclassContainer implements Container {
    protected Task[] tasks;
    protected int size;
//    public SuperclassContainer(Task[] tasks, int size) {
//        tasks=new Task[INITIAL_QUEUE_SIZE];
//        size=0;
//    }

    @Override
    public void add(Task task){
        if(tasks.length==size)
        {
            Task[] t= new Task[2* tasks.length];
            System.arraycopy(tasks,0,t,0, tasks.length);
            tasks=t;
        }
        tasks[size]=task; //adaugam simplu la final
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
