package sort;

public class QuickSorter implements AbstractSorter{
    public int partition(int[] numbers, int st, int dr){
        //iau pivotul cel mai din dreapta element, el se pune la locul lui si continuam asa
        //vezi desen
        int i, pivotIndex, pivot, aux;
        pivotIndex=st;
        pivot=numbers[dr];
        for(i=st;i<=dr; i++){
            if(numbers[i]<=pivot){  //swap(numbers[i], numbers[pivotIndex])
                aux= numbers[i];
                numbers[i]=numbers[pivotIndex];
                numbers[pivotIndex]=aux;
                pivotIndex++;
            }
        }
        return pivotIndex-1;
    }
    public void QuickSort(int[]  numbers, int st, int dr){
        if(st<dr){
            int pivotIndex= partition(numbers, st, dr);
            QuickSort(numbers, st, pivotIndex-1);
            QuickSort(numbers, pivotIndex+1, dr);
        }
    }
    @Override
    public void sort(int[] numbers) {
        QuickSort(numbers, 0, numbers.length-1);
    }
}
