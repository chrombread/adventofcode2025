package advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day5 {

    public static void run() throws Exception {
        File rangeFile = new File("src/main/java/data/day5ranges.txt");
        BufferedReader reader = new BufferedReader(new FileReader(rangeFile));

        List<String> rangeSTRS = reader.readAllLines();
        List<Range> allRanges = new ArrayList<>();
        for(String rangestr : rangeSTRS) {

            int sep = rangestr.indexOf('-');
            long bottom = Long.parseLong(rangestr.substring(0, sep));
            long top = Long.parseLong(rangestr.substring(sep+1));

            Range r = new Range(bottom, top);
            allRanges.add(r);
        }

        allRanges.sort(Comparator.comparingLong(range -> range.low));


        int oldSize = -1;
        while(oldSize != allRanges.size()) {
            oldSize = allRanges.size();
            for (int i = 0; i < allRanges.size() - 1; i++) {
                if (allRanges.get(i) == null) continue;

                Range current = allRanges.get(i);
                Range next = allRanges.get(i + 1);
                if (next.low <= current.high) {
                    current.high = Math.max(current.high, next.high);
                    allRanges.remove(next);
                }
            }
        }


        long l = 0;
        for(Range r : allRanges) {
            System.out.println(r);
            l+=  r.amount();
        }
        System.out.println(l);

    }

    public static class Range {
        Range(long l, long h) {
            this.low = l;
            this.high = h;
        }
        long low;
        long high;

        @Override
        public String toString() {
            return low + " - " + high;
        }

        public long amount() {
            return 1 + this.high - this.low;
        }

        public boolean isSubsetOf(Range other) {
            return this.low >= other.low && this.high <= other.high;
        }

        public boolean equals (Range r) {
           return this.high == r.high && this.low == r.low;
        }
    }
}
