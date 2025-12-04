package advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Day4 {

    static int validCount = 0;
    public static void run() throws Exception {
        File file = new File("src/main/java/data/day4.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = reader.readAllLines();
        int rows = lines.size();
        int cols = lines.getFirst().length();
        final State[][] states = new State[rows][cols];
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            for(int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                states[i][j] = c == '@' ? State.ROLL : State.EMPTY;
            }
        }

        boolean rollRemoved;
        do {
                rollRemoved = false;
            for (int row = 0; row < states.length; row++) {
                for (int col = 0; col < states[0].length; col++) {
                    if (states[row][col] == State.ROLL && isAccessible(states, row, col)) {
                        states[row][col] = State.EMPTY;
                        rollRemoved = true;
                        validCount++;
                    }
                }
            }
        } while (rollRemoved);
        System.out.println(validCount);

    }

    public enum State {
        EMPTY,
        ROLL
    }

    public static boolean isAccessible(State[][] states, int row, int col) {
        int rollsAround = 0;
        for(int i = row-1; i <= row+1; i++) {
            if(i < 0 || i >= states.length) continue;
            for(int j = col-1; j <= col+1; j++) {
                if(j < 0 || j >= states[0].length) continue;
                if(i==row && j == col) continue;
                rollsAround += states[i][j].ordinal();
            }
        }
        return rollsAround < 4;
    }
}
