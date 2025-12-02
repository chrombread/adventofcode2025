package advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    static long runningSum = 0;
    public static void run() throws IOException {
        File file = new File("src/main/java/data/day2.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = reader.readAllAsString();
        str = str.strip();
        do {
            int endOfPattern = str.indexOf(',');
            if(endOfPattern == -1) endOfPattern = str.length();
            String fullPattern = str.substring(0,endOfPattern);
            int dash = str.indexOf('-');

            String beginningID = fullPattern.substring(0, dash);
            String endID = fullPattern.substring(dash+1, endOfPattern );

            Input input = new Input(beginningID, endID);
            for(long i = input.startID; i <= input.endID; i++) {
                String current = Long.toString(i);
                // scan the ID for all possible repetitions
                for(int currentIndex = 1; currentIndex < current.length(); currentIndex++) {
                    if (current.length() % currentIndex == 0) {
                        String pattern = current.substring(0, currentIndex);
                        int repAmount = current.length() / currentIndex;
                        String duplicatedPattern = pattern.repeat(repAmount);
                        if(duplicatedPattern.equals(current)) {
                            System.out.println(pattern+" was duplicated " + repAmount +" times to make" +duplicatedPattern+", which is the same as "+current);
                            runningSum+=i;
                            break;
                        }
                    }
                }

            }
            if(endOfPattern+1 < str.length()) str = str.substring(endOfPattern+1);
            else {
                str = "";
            }
        } while (!str.isEmpty());

        System.out.println("Value: "+runningSum);
    }

    static class Input{
        public long startID;
        public long endID;
        public Input(String start, String end)
        {
            this.startID = Long.parseLong(start);
            this.endID = Long.parseLong(end);
        }
    }
}
