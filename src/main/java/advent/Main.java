package advent;


import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static void main() throws Exception {
        Scanner scan = new Scanner(System.in);
       System.out.print("What day to solve: ");
       int day = scan.nextInt();
       System.out.println();
       switch (day) {
            case 1 -> {
                    Day1.run();
            }
            case 2 -> {
                Day2.run();
            }
           case 3 -> {
               Day3.run();
           }
           default -> System.out.println("Day not found");
       }

    }
}
