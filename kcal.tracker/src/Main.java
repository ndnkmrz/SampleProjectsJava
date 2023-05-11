import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StepTracker stepTracker = new StepTracker();

        printMenu();
        int userInput = scanner.nextInt();
        while (userInput != 0){
            if (userInput == 1){
                stepTracker.saveStepDay(stepTracker.data, scanner);
            }
            else if (userInput == 2){
                stepTracker.statisticSteps(stepTracker.data, scanner);
            } else if (userInput == 3) {
                stepTracker.changeGoal(scanner);
            }
            printMenu();
            userInput = scanner.nextInt();
        }
        System.out.println("Program completed");

    }
    public static void printMenu(){
        System.out.println("Choose what you want to do");
        System.out.println("1 — input your steps per day");
        System.out.println("2 — print statistics for a certain period");
        System.out.println("3 — change your daily steps goal");
        System.out.println("0 — exit");
    }
}