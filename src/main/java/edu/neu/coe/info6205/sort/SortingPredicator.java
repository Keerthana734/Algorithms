package edu.neu.coe.info6205.sort;

import edu.neu.coe.info6205.sort.elementary.HeapSort;
import edu.neu.coe.info6205.sort.linearithmic.MergeSortBasic;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort;
import edu.neu.coe.info6205.sort.linearithmic.QuickSort_DualPivot;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.PrivateMethodTester;
import edu.neu.coe.info6205.util.StatPack;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SortingPredicator {
    public static void main(String[] args) {

        int n = 80000;

        final Config config = Config.setupConfig("true", "0", "1", "1", "");
        BaseHelper<Integer> helper = new InstrumentedHelper<>("test", config);

        QuickSort<Integer> quick = new QuickSort_DualPivot<>(helper);

        Consumer<Integer[]> randomFunc = randArr -> quick.sort(randArr);
        Benchmark_Timer<Integer[]> randomTimer = new Benchmark_Timer<>("Sort array of " + n + " elements", randomFunc);
        Supplier<Integer[]> random = () -> {
            Random randI = new Random();
            Integer[] randArr = new Integer[n];
            for(int i=0; i<n; i++) {
                int randInt = randI.nextInt(n);
                randArr[i] = randInt+1;
            }
            return randArr;
        };
        randomFunc.accept(random.get());
        double randTime = randomTimer.run(random.get(), 1);
        System.out.println("Quicksort Time for array of " + n + " elements is: " + randTime);

        helper.postProcess(quick.sort(random.get()));
        PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
        StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");

        int quickCompares = (int) statPack.getStatistics(InstrumentedHelper.COMPARES).mean();
        int quickSwaps = (int) statPack.getStatistics(InstrumentedHelper.SWAPS).mean();
        int quickHits = (int) statPack.getStatistics(InstrumentedHelper.HITS).mean();
        System.out.println("Quicksort Compares for array of " + n + " elements is: " + quickCompares);
        System.out.println("Quicksort Swaps for array  of " + n + " elements is: " + quickSwaps);
        System.out.println("Quicksort Hits for array of " + n + " elements is: " +  quickHits);


        BaseHelper<Integer> helper1 = new InstrumentedHelper<>("test", config);

        MergeSortBasic<Integer> merge = new MergeSortBasic<>(helper1);

        Consumer<Integer[]> randomFunc1 = randArr1 -> merge.sort(randArr1);
        Benchmark_Timer<Integer[]> randomTimer1 = new Benchmark_Timer<>("Sort array of " + n + " elements", randomFunc1);
        Supplier<Integer[]> random1 = () -> {
            Random randI = new Random();
            Integer[] randArr1 = new Integer[n];
            for(int i=0; i<n; i++) {
                int randInt = randI.nextInt(n);
                randArr1[i] = randInt+1;
            }
            return randArr1;
        };
        randomFunc1.accept(random1.get());
        double randTime1 = randomTimer1.run(random1.get(), 1);
        System.out.println("Mergesort Time for array of " + n + " elements is: " + randTime1);

        helper1.postProcess(merge.sort(random1.get()));

        PrivateMethodTester privateMethodTester1 = new PrivateMethodTester(helper1);
        StatPack statPack1 = (StatPack) privateMethodTester1.invokePrivate("getStatPack");

        int mergeCompares = (int) statPack1.getStatistics(InstrumentedHelper.COMPARES).mean();
        int mergeSwaps = (int) statPack1.getStatistics(InstrumentedHelper.SWAPS).mean();
        int mergeHits = (int) statPack1.getStatistics(InstrumentedHelper.HITS).mean();
        System.out.println("Mergesort Compares for array of " + n + " elements is: " + mergeCompares);
        System.out.println("Mergesort Swaps for array of " + n + " elements is: " + mergeSwaps);
        System.out.println("Mergesort Hits for array of " + n + " elements is: " + mergeHits);


        BaseHelper<Integer> helper2 = new InstrumentedHelper<>("test", config);

        HeapSort<Integer> heap = new HeapSort<>(helper2);

        Consumer<Integer[]> randomFunc2 = randArr2 -> merge.sort(randArr2);
        Benchmark_Timer<Integer[]> randomTimer2 = new Benchmark_Timer<>("Sort array of " + n + " elements", randomFunc2);
        Supplier<Integer[]> random2 = () -> {
            Random randI = new Random();
            Integer[] randArr2 = new Integer[n];
            for(int i=0; i<n; i++) {
                int randInt = randI.nextInt(n);
                randArr2[i] = randInt+1;
            }
            return randArr2;
        };
        randomFunc2.accept(random2.get());
        double randTime2 = randomTimer2.run(random2.get(), 1);
        System.out.println("Heapsort Time for array of " + n + " elements is: " + randTime2);

        helper2.postProcess(heap.sort(random2.get()));

        PrivateMethodTester privateMethodTester2 = new PrivateMethodTester(helper2);
        StatPack statPack2 = (StatPack) privateMethodTester2.invokePrivate("getStatPack");

        int heapCompares = (int) statPack2.getStatistics(InstrumentedHelper.COMPARES).mean();
        int heapSwaps = (int) statPack2.getStatistics(InstrumentedHelper.SWAPS).mean();
        int heapHits = (int) statPack2.getStatistics(InstrumentedHelper.HITS).mean();
        System.out.println("Heapsort Compares for array of " + n + " elements is: " + heapCompares);
        System.out.println("Heapsort Swaps for array of " + n + " elements is: " + heapSwaps);
        System.out.println("Heapsort Hits for array of " + n + " elements is: " + heapHits);
    }
}
