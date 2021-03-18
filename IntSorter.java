/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import java.util.LinkedList;

/**
 *
 * @author HP
 */
public class IntSorter {
    public static void swap (int a[], int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    // Selection sort : O(n^2)
    // get index of minimum value from start to end position in a[]
    public static int getMinIndex (int a[], int first, int last) {
        int minIndex = first;
        for (int i = first + 1; i <= last; i++) 
            if (a[minIndex] > a[i]) minIndex = i;
        return minIndex;
    }
    
    public static void selectionSort (int a[], int n) {
        int minIndex;
        for (int i  = 0; i < n-1; i++) {
            minIndex = getMinIndex(a, i, n-1);
            swap(a, minIndex, i);
        }
    }
    
    public static void insertionSort (int a[], int n) {
        int i, j, tmp;
        for (i = 1; i < n; i++) {
            tmp = a[i];
            // Determine the right position of tmp
            j = i - 1;
            while (j>=0 && a[j] > tmp) {
                a [j+1] = a[j]; // shift right all elements > tmp
                j--;
            }
            // put tmp to the right position
            a[j+1] = tmp;
        }
    }
    
    public static void bubbleSort (int a[], int n) {
        int i, j;
        for (i = 0; i < n-1; i++)
            for ( j = n-1; j > i; j--) {
                if (a[j] < a[j-1]) swap(a, i, j-1);
            }
    }
    
    // Quick sort 1 : O(nlogn)
    // Using the mid of array is the pivote, a[(first+last)/2]
    public static void quickSort1 (int a[], int first, int last) {
        int mid = (first+last)/2;
        swap(a, first, mid); // move the pivote to the beginning
        int lower = first + 1, upper = last;
        int pivote = a[first];
        while (lower <= upper) {
            while (lower <= upper && pivote > a[lower]) lower++;
            while (lower <= upper && pivote < a[upper]) upper--;
            if (lower < upper) swap(a, lower++, upper--);
            else lower++;
        }
        //upper is the suitable position for the pivote (a[first])
        // privote must be put to the right position
        swap(a, upper, first);
        //upper is omitted becasue the oivote was put to
        if (first < upper -1) quickSort1(a, first, upper - 1); // sort left subarray
        if (upper + 1 < last) quickSort1(a, upper +1 , last); // sort right subarray
    }
    
    public static void quickSort1 (int a[], int n) {
            quickSort1(a, 0, n -1 );
    }
    
    // quick sort 2: O(nlogn)
    // The pivote is the median of a[first], a[last] and a[mid]
    // get median of 3 integers
    public static int median (int a, int b, int c) {
        if ((a>b && a<c) || (a>c && a<b)) return a; // ORDER: b a c OR c a b
        if ((b>a && b<c) || (b>c && b<a)) return b;
        return c;
    }
    
    //Quick sort 2: Using median of (a[first], a[last], a[mid])
    public static void quickSort2 (int a[], int first, int last) {
        if (first >= last) return; // array contains 1 element
        int mid = (first + last) /2;
        int pivote = median(a[first], a[last], a[mid]);
        int lower= first;
        int upper = last;
        while (lower <= upper) {
            // find a greater or equal value than pivote from the left side
            while (lower <= upper && pivote > a[lower]) lower++;
            // find a smaller value than pivote from the right side
            while (lower <= upper && pivote < a[upper]) upper--;
            if (lower < upper) swap(a, lower++, upper--);
            else lower++; // for left can be greater than right
        }
        quickSort2(a, first, upper);
        quickSort2(a, upper + 1, last);
    }
    
    public static void quickSort2 (int a[], int n) {
        quickSort2(a, 0, n-1);
    }
    
    // Merge sort: O(nlogn)
    static int[] temp; // Temp array for merging 
    // Array contains 2 sorted subarrays: a[first]..a[mid],a[mid+1]..a[last]
    // Merge 2 sorted sub-arrays to a sorted array
    private static void merge (int[] a, int first, int last) {
        int mid = (first + last) / 2;
        int i = first; // index to left subarr
        int j = mid +1; // index to right subarr
        int k = 0; // index to the arr tmp
        //merging
        while (i <= mid && j <= last) // while not compleeted
            if (a[i] < a[j]) temp[k++] = a[i++];
            else temp[k++] = a[j++];
        while ( i <= mid) temp[k++] = a[i++]; // copy remainder in left side
        while (j <= last) temp[k++] = a[j++]; // copy reaminder in right side
        //Re covery data in a[] <-- copy temp[] to a[]
        for (k = 0, i = first; i <= last; a[i++] = temp[k++]);
    }
    
    // merge sort a[] from index first to index last
    private static void mergersort (int a[], int first, int last) {
        int mid = (first + last) / 2;
        if (first < mid) mergersort(a, first, mid);
        if (mid +1 < last) mergersort(a, mid + 1, last);
        merge(a, first, last); // merge 2 subarrays
    }
    
    public static void mergeSort(int a[], int n) {
        if (n < 2) return;
        temp = new int[n]; // allocate mem of the array temp
        mergersort(a, 0, n-1);
    }
    
    // heap sort 1 : O(nlogn)
    public static void moveDown(int[] a, int first, int last) {
        // first is the father node
        // leftChild = 2*father + 1, rightChild = 2*father +2 
        int largest = 2*first+1; // suppose that largest is left child 
        while (largest <= last) {
            // Checking the right child is greater left child?
            if (largest < last && a[largest] < a[largest + 1]) largest++;
            if (a[first] < a[largest]) { // father < child
                swap(a, first, largest); // swap father and largest child
                first = largest;
                largest = 2* first +1;
            }
            
            // to exit the loop: the heap property isn't violated by a[first]
            else largest = last + 1;
        }
    }
    
    public static void heapSort (int a[], int n) {
        // tranfer the initial array to max heap
        for (int i = n/2 - 1; i >= 0; --i) moveDown(a, i, n-1);
        for (int i = n - 1; i >= 1; --i) {
            swap(a, 0, i); // move the largest value to the end of gr
            moveDown(a, 0, i-1); // transfer the remainder to max heap
        }
    }
    
    //Radix sort
    public static final int RADIX = 10;
    public static int countDigits(int n) {
        int count = 0;
        // 12304 -> 1230 -> 123 -> 12 -> 1 -> 0
        while (n/10 != 0){
            count++;
            n /= 10;
        }
        return count + 1;
    }
    
    public static int maxAbsolute (int a[], int n) {
        int result = Integer.MIN_VALUE;
        for (int i : a) {
            if (i < 0) i=-i;
            if (result < i) result = i;
        }
        return result;
    }
    
    public static void radixSort(int a[], int n) {
        int longest = maxAbsolute(a, n);
        int digits = countDigits(longest);
        int d, j, k, factor;
        LinkedList<Integer>[] queues = new LinkedList[RADIX]; // array of queues
        for( d = 0; d < RADIX; d++) queues[d] = new LinkedList<Integer>();
        for ( d = 1, factor = 1; d <= digits; factor *= RADIX, d++) {
            // move elements in arr to suitable queues based on the dth digits
            for ( j = 0; j < n; j++) queues[(a[j]/factor) % RADIX].addLast(a[j]);
            // Move values in queyes to the arr
            for (j = k = 0; j < RADIX; j++)
                while (!queues[j].isEmpty()) a[k++] = queues[j].removeFirst();
        }
    }
}