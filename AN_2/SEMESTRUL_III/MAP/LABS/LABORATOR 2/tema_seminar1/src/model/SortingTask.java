package model;

import sort.AbstractSorter;
import sort.BubbleSorter;
import sort.QuickSorter;

public class SortingTask extends Task{
    private int[] numbers;
    private AbstractSorter sorter;

    //mai trevbuie sa adug un camp cu tipul de sort pe care vr sa il folosesc
    public SortingTask(String taskID, String descriere, int[] numbers)
    {
        super(taskID,descriere);
        this.numbers=numbers;

        //am ales eu un criteriu dupa care sa decid ce sortare folosesc
        if(descriere.length()%2==0) sorter= new BubbleSorter();
        else sorter=new QuickSorter();
    }

    //@Override
    public void execute(){
        sorter.sort(numbers);
    }

    //mic test
    public static void main(String[] args){
        int[] sir=new int[]{ 1,3,2,4,5};
        SortingTask s=new SortingTask("1","asta",sir);
        s.execute();
        for(int el:sir){
            System.out.println(el);
        }
    }
}
