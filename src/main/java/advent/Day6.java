package advent;

import javax.annotation.processing.SupportedSourceVersion;
import javax.swing.GroupLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Day6 {
    public static void run() throws Exception {
       part1();
       part2();
    }

    public static void part1() throws Exception {
        final long startTime = System.currentTimeMillis();
        File rangeFile = new File("src/main/java/data/day6.txt");
        BufferedReader reader = new BufferedReader(new FileReader(rangeFile));
        List<String> inputs = reader.readAllLines();
        Map<Integer, DayFunction> functionMap = new HashMap<>();
        for(String line : inputs) {

            StringTokenizer tokenizer = new StringTokenizer(line);
            int index = 0;
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                functionMap.computeIfAbsent(index, k -> new DayFunction());
                DayFunction function = functionMap.get(index);
                if (Operator.OPERATORS.contains(token)) {
                    function.operator = Operator.toOperator(token);
                }
                else {
                    function.inputs.add(Integer.parseInt(token));
                }
                index++;
            }
        }

        long total = 0;
        for(Map.Entry<Integer,DayFunction> entry : functionMap.entrySet()) {
            total += entry.getValue().operator.function.apply(entry.getValue().inputs);
        }
        System.out.println("Day 1 total: "+total);
        System.out.println("Time delta (ms): "+ (System.currentTimeMillis() - startTime));
    }


    public static void part2() throws Exception {
        final long startTime = System.currentTimeMillis();
        File rangeFile = new File("src/main/java/data/day6.txt");
        BufferedReader reader = new BufferedReader(new FileReader(rangeFile));
        List<String> inputs = reader.readAllLines();
        Map<Integer, DayFunction> functionMap = new HashMap<>();
        for(String line : inputs) {

            StringTokenizer tokenizer = new StringTokenizer(line);
            int index = 0;
            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                functionMap.computeIfAbsent(index, k -> new DayFunction());
                DayFunction function = functionMap.get(index);
                if (Operator.OPERATORS.contains(token)) {
                    function.operator = Operator.toOperator(token);
                }
                else {
                    function.strInput.add(token);
                }
                index++;
            }
        }

        String snippet = inputs.getLast();
        int oldIndex = -1;
        for(Map.Entry<Integer,DayFunction> entry : functionMap.entrySet()) {
            // aligns the operator to right or DC.
            // DC is treated as left aligned
            DayFunction function = entry.getValue();
            int index = snippet.indexOf(function.operator.stringRep, oldIndex+1);
            oldIndex = index;
            for(int position = 0; position < inputs.size(); position++) {
                String line = inputs.get(position);
                if (line.charAt(index) == ' ') {
                    function.alignment = EquationAlignment.RIGHT;
                    break;
                }
            }

            // now do the math.
            int max = function.strInput.stream().mapToInt(String::length).max().getAsInt();

            // pre/ap-pend _ to values that are missing
            for(int i = 0; i < function.strInput.size(); i++) {
                String input = function.strInput.get(i);
                int lengthComp = max - input.length();
                String append = "_".repeat(lengthComp);
                if(function.alignment == EquationAlignment.DC) {
                    function.strInputProper.add(input+append);
                }
                else {
                    function.strInputProper.add(append+input);
                }
            }

          // read the values downwards and add them to the integer values list of the function
          for(int i = 0; i < function.strInputProper.getFirst().length(); i++) {
              int pointer = 0;
              StringBuilder val = new StringBuilder();
              while(pointer < function.strInputProper.size()) {
                  val.append(function.strInputProper.get(pointer).charAt(i));
                  pointer++;
              }
              String strRep = val.toString().replace("_","");
              function.inputs.add(Integer.parseInt(strRep));
          }

        }
      long total = 0;
      for(Map.Entry<Integer,DayFunction> entry : functionMap.entrySet()) {
          total += entry.getValue().operator.function.apply(entry.getValue().inputs);
      }
      System.out.println("Day 2 total: "+total);
      System.out.println("Time delta (ms): "+ (System.currentTimeMillis() - startTime));

    }

    public static class DayFunction {

        public List<Integer> inputs = new ArrayList<>();
        public List<String> strInput = new ArrayList<>();
        public List<String> strInputProper = new ArrayList<>();
        public Operator operator;
        public EquationAlignment alignment = EquationAlignment.DC;

        @Override
        public String toString() {
            return this.operator + "   "+inputs.toString();
        }
    }

    public enum Operator{
        PLUS(
            "+", new Function<List<Integer>, Long>() {

            @Override
            public Long apply(List<Integer> integers) {
                long retLong = 0;
                for(Integer val : integers) {
                    retLong += val;
                }
                return retLong;
            }
        }),
        MULT(
            "*", new Function<List<Integer>, Long>() {

            @Override
            public Long apply(List<Integer> integers) {
                long retLong = 1L;
                for(Integer val : integers) {
                    retLong*= val;
                }
                return retLong;
            }
        }),
        EMPTY("EMPTY", null);

        public final String stringRep;
        public final Function<List<Integer>, Long> function;
        Operator(String s, Function<List<Integer>, Long> func) {
            this.stringRep = s;
            this.function = func;
        }

        public static Operator toOperator(String in) {
            for (Operator o : Operator.values()) {
                if(in.equals(o.stringRep)) return o;
            }
            return EMPTY;
        }

        public static final List<String> OPERATORS = List.of("+","*");

    }

    public enum EquationAlignment {
        RIGHT("RIGHT"),
        DC("DC");

        final String rep;
        EquationAlignment(String r) {
            this.rep = r;
        }

        @Override
        public String toString() {
            return rep;
        }
    }


}
