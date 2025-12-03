package advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 {
    public static long runningSum = 0;

    public static void run() throws Exception {
        File file = new File("src/main/java/data/day3.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<int[]> bankList = new ArrayList<>();
        reader.readAllLines().forEach(str -> bankList.add(str.chars().map(x -> x - '0').toArray()));


        for(int[] bank : bankList) {
            int[] chosenBank = new int[12];
            int peersLeft = bank.length - chosenBank.length;
            for(int start = 0; start < chosenBank.length; start++) {

                int[] forwardValues = new int[peersLeft+1];
                int offset = bank.length - chosenBank.length - peersLeft;
                int maxIndex = 0;
                for(int i = 0; i < forwardValues.length; i++) {

                    forwardValues[i] = bank[start + offset + i];
                    if(forwardValues[i] > forwardValues[maxIndex]) {
                        maxIndex = i;
                    }
                }
                chosenBank[start] = forwardValues[maxIndex];
                peersLeft-=maxIndex;
            }
            System.out.println(Arrays.toString(chosenBank));
            for(int start = 0; start < chosenBank.length; start++) {
                runningSum += (long) (chosenBank[start] * Math.pow(10, chosenBank.length - start - 1));
            }
        }
        System.out.println(runningSum);


    }


}
