package advent;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {

    public static int maxDepth = 0;
    public static int split = 0;
    public static void run() throws Exception{

        File file = new File("src/main/java/data/day7.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        List<String> inputs = reader.readAllLines();
        List<List<Character>> board = new ArrayList<>();
        List<List<Long>> beamBoard = new ArrayList<>();
        for(String line : inputs) {
            List<Character> list = line.chars().mapToObj(c-> (char) c).collect(Collectors.toList());
            List<Long> beamList = line.chars().mapToObj(c-> 0L).collect(Collectors.toList());
            beamBoard.add(beamList);
            board.add(list);
        }
        for(int rowIndex = 0; rowIndex < board.size(); rowIndex++) {
            List<Character> row = board.get(rowIndex);
            List<Long> beamRow = beamBoard.get(rowIndex);

            for(int colIndex = 0; colIndex< row.size(); colIndex++) {

                char ch = row.get(colIndex);
                if(ch == '.') continue;
                if(ch == 'S') {
                    List<Character> nextRow = board.get(rowIndex+1);
                    nextRow.set(colIndex,'|');
                    List<Long> nextBoardRow = beamBoard.get(rowIndex+1);
                    nextBoardRow.set(colIndex, 1L);

                }
                if(ch == '|') {
                    if(rowIndex+1 == board.size()) continue;
                    List<Character> nextRow = board.get(rowIndex+1);
                    List<Long> nextBeamRow = beamBoard.get(rowIndex+1);
                    Long val = beamBoard.get(rowIndex).get(colIndex);

                    Character nextCh = nextRow.get(colIndex);
                    if(nextCh == '^') {
                        split++;
                        nextBeamRow.set(colIndex+1, nextBeamRow.get(colIndex+1) + val);
                        nextBeamRow.set(colIndex-1, nextBeamRow.get(colIndex-1) + val);

                        nextRow.set(colIndex+1, '|');
                        nextRow.set(colIndex -1 , '|' );
                    } else{
                        nextBeamRow.set(colIndex, nextBeamRow.get(colIndex)+val);
                        nextRow.set(colIndex, '|');
                    }
                }
            }
            System.out.println(row);
        }
        long totalPaths = 0;
        for(Long i : beamBoard.getLast()) {
            totalPaths+= i;
        }
        System.out.println(split);
        System.out.println(totalPaths);

    }

}
