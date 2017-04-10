
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

//import ParallelQuickSort.SortTask;

import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort 
{
	static int[] list;
	int threshold;
	
   //  int array[];
     static int length;

    
    
  public ParallelQuickSort(int[] a, int threshold)
  {
	 this.list = a; 
	 this.threshold = threshold;
	 
  }
  public static void startMainTask(int[] list, int threshold) 
  {
    RecursiveAction mainTask = new SortTask(list,threshold);
    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(mainTask);
  }
  public static void startMainTask(int[] list, int threshold, int threadSize) 
  {
    RecursiveAction mainTask = new SortTask(list,threshold);
    ForkJoinPool pool = new ForkJoinPool(threadSize);
    pool.invoke(mainTask);
  }

  private static class SortTask extends RecursiveAction 
  {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] list;
    private int threshold;
    
    SortTask(int[] list, int threshold) 
    {
      this.list = list;
      this.threshold = threshold;
    }
//    
//    protected void compute() {
//        if (list.length < threshold)
//        {
//          //Arrays.sort(list);
//          //OR
//          Arrays.parallelSort(list); 
//        }
//        else {
//        	quicksort(0, list.length-1);
//        }
//    }
//    
//    private void quicksort(int low, int high) {
//        int i = low, j = high;
//        // Get the pivot element from the middle of the list
//        int pivot = list[low + (high-low)/2];
//
//        // Divide into two lists
//        while (i <= j) {
//        	invokeAll(); 
//                // If the current value from the left list is smaller then the pivot
//                // element then get the next element from the left list
//                while (list[i] < pivot) {
//                        i++;
//                }
//                // If the current value from the right list is larger then the pivot
//                // element then get the next element from the right list
//                while (list[j] > pivot) {
//                        j--;
//                }
//
//                // If we have found a values in the left list which is larger then
//                // the pivot element and if we have found a value in the right list
//                // which is smaller then the pivot element then we exchange the
//                // values.
//                // As we are done we can increase i and j
//                if (i <= j) {
//                        exchange(i, j);
//                        i++;
//                        j--;
//                }
//        }
//        // Recursion
//        if (low < j)
//                quicksort(low, j);
//        if (i < high)
//                quicksort(i, high);
//}
//
//    private void exchange(int i, int j) {
//        int temp = list[i];
//        list[i] = list[j];
//        list[j] = temp;
//}

    protected void compute() {
      if (list.length < threshold)
      {
    	  //System.out.println("length: " + length + " " + threshold);
        Arrays.parallelSort(list);
      }
      else {
          int k = partition(list);
        //  System.out.println("Length " + k);
          
    
          int[] FirstHalfArray = new int[k];
          System.arraycopy(list, 0, FirstHalfArray, 0, FirstHalfArray.length);
         // System.out.println("Length " + (list.length-k-1));

  
          int[] SecondHalfArray = new int[list.length-k -1];
          System.arraycopy(list, k+1, SecondHalfArray, 0, SecondHalfArray.length);

          
//        // Recursively sort the two halves
        SortTask first  = new SortTask (FirstHalfArray, threshold);
        SortTask second = new SortTask (SecondHalfArray, threshold);
        invokeAll( first,second );

  System.arraycopy(FirstHalfArray, 0, list, 0, FirstHalfArray.length);
  System.arraycopy(SecondHalfArray, 0, list, k+1, SecondHalfArray.length);
//  		for(int i = 0; i< list.length; i++){
//  			System.out.print(list[i] + " ");
//  			System.out.println("");
//  		}
  		
      }

      }
//    }
    
    
    int partition(int arr[])
    {
    	
    	int pivot = list[0];
    	int low = 0; 
    	int first = 0;
    	int high = list.length-1; 
    	int last = list.length-1;
    	while(high >low){
    		while(low <= high && list[low] <=pivot){
    			low ++;
    		}
    		while(low<=high && list[high] > pivot){
    			high--;
    		}
    		if(high>low){
    			int temp = list[high];
    			list[high] = list[low];
    			list[low] = temp; 
    		}
    	}
    	while(high>first && list[high]>= pivot){
    		high --; 
    	}
    	if(pivot>list[high]){
    		list[first] = list[high];
    		list[high] = pivot;
    		return high;
    	}
    	else
    		return first; 
    	
    	
//          int i = left, j = right;
//          int tmp;
//          int pivot = arr[(left + right) / 2];
//          System.out.println("partiiton" + left + " " + right); 
////          tmp= pivot;
////    		arr[(left + right) / 2] = arr[0];
////    		arr[0] = tmp; 
//          while (i <= j) {
//                while (arr[i] < pivot)
//                      i++;
//                while (arr[j] > pivot)
//                      j--;
//                if (i <= j) {
//                      tmp = arr[i];
//                      arr[i] = arr[j];
//                      arr[j] = tmp;
//                      i++;
//                      j--;
//                }
//          };
//          
//          for( i = left; i <=right; i++)
//         System.out.println(arr[i] );
         // return i;
    }
    
    
    
    
  }
  

    
    //keep these 
  
}