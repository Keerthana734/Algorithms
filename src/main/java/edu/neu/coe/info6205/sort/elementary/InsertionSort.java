/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Source;
import edu.neu.coe.info6205.util.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;


/**
 * Class InsertionSort.
 *
 * @param <X> the underlying comparable type.
 */
public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        for(int i=from+1; i<to; i++) {
            int j = i;
            while(j>=from+1 && helper.swapStableConditional(xs, j)) {
                j--;
            }
        }
        // FIXME
        // END 
    }

    public static Integer[] getPartialArray(Integer[] a,int from,int to) {
        Arrays.sort(a, from, to);
        return a;
    }

    public static Integer[] getRamdomArray(Integer[] a) {
        ArrayList<Integer> mylist = new ArrayList<Integer>();
        for(int i = 0;i<a.length;i++) {
            mylist.add(a[i]);
        }
        Collections.shuffle(mylist);
        for(int i = 0;i<a.length;i++) {
            a[i]=mylist.get(i);
        }
        return a;
    }

    public static Integer[] getReverseArray(Integer[] a) {
        ArrayList<Integer> mylist = new ArrayList<Integer>();
        for(int i = 0;i<a.length;i++) {
            mylist.add(a[i]);
        }
        Collections.reverse(mylist);
        for(int i = 0;i<a.length;i++) {
            a[i]=mylist.get(i);
        }
        return a;
    }
    public void getValuesForReport(int runs, int len) {
        Supplier<Integer[]> supplier= new Source(len, len).intsSupplier(10);
        //Warm-up
        Supplier<Integer[]> supplierW= new Source(5000, 5000).intsSupplier(10);
        double timeWarmup = new Timer().repeat(5*runs, supplierW, (xs) -> new InsertionSort<Integer>().sort(xs), null, null);

        double time1 = new Timer().repeat(runs, supplier, (xs) -> new InsertionSort<Integer>().sort(xs), null, null);
        double time2 = new Timer().repeat(runs, supplier, (xs) -> new InsertionSort<Integer>().sort(xs), (xs)->getRamdomArray(xs), null);
        double time3 = new Timer().repeat(runs, supplier, (xs) -> new InsertionSort<Integer>().sort(xs), (xs)->getReverseArray(xs), null);
        int halfLen = len/2;
        double time4 = new Timer().repeat(runs, supplier, (xs) -> new InsertionSort<Integer>().sort(xs), (xs)->getPartialArray(getRamdomArray(xs),0,halfLen), null);

        System.out.println("--------------------------------------------");
        System.out.println("Array with size of " + len + " run "+ runs+" times:");
        System.out.println("--------------------------------------------");
        System.out.println("Sorted array : "+ (time1));
        System.out.println("--------------------------------------------");
        System.out.println("Random array : "+ (time2));
        System.out.println("--------------------------------------------");
        System.out.println("Revert array : "+ (time3));
        System.out.println("--------------------------------------------");
        System.out.println("Partial array: "+ (time4));
        System.out.println("--------------------------------------------");

    }
    public static void main(String args[])  //static method
    {
        Supplier<Integer[]> supplier= new Source(20, 20).intsSupplier(10);
        Integer[] a = supplier.get();
        Integer[] b = getPartialArray(a,0,a.length/2);
        InsertionSort<Integer> sort = new InsertionSort<Integer>();
        sort.getValuesForReport(100,400);
        sort.getValuesForReport(100,800);
        sort.getValuesForReport(100,1600);
        sort.getValuesForReport(100,3200);
        sort.getValuesForReport(100,6400);
        sort.getValuesForReport(100,12800);
    }
    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }
}
