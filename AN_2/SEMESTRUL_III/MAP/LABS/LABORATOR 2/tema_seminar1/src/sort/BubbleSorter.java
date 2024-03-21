package sort;

public class BubbleSorter implements AbstractSorter{
    @Override
    public void sort(int[] numbers){
        int i, ok=1, c;
        do{
            ok=1;
            for(i=0;i< numbers.length-1;i++){
                if(numbers[i]>numbers[i+1]){
                    c=numbers[i];
                    numbers[i]=numbers[i+1];
                    numbers[i+1]=c;
                    ok=0;
                }
            }
        }while(ok==0);
    }
}
