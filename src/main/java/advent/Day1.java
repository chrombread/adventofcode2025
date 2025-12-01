package advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Day1 {

    private static int penis = 0;
    private static int cyclesTally = 0;
    public static void run() throws FileNotFoundException {
        int start = 50;
        try {
            File file = new File("src/main/java/data/day1.txt");
            System.out.println(file.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = reader.readAllLines();
            for( String line : lines) {
                String c = line.substring(0,1);
                int num = Integer.parseInt(line.substring(1));
                int multiplier = 1;
                if(c.equals("L")) {
                    multiplier *= -1;
                }
                start = performSwitch(start, num, multiplier);
            }
            System.out.println("Value "+(penis));
        } catch (Exception e) {
            System.out.println("File not found!");
        }
    }

    private static int performSwitch(int state, int offset, int mult) {
        for(int i = 0; i < offset; i++) {
            state+= mult;
            if(state == 100) state = 0;
            if(state == -1) state = 99;
            if(state == 0) penis++;
        }
       return state;
    }

}
