import java.util.*;

public class StepTracker {
    HashMap<Integer, ArrayList<Integer>> data = new HashMap<>();
    Converter converter = new Converter();
    Integer goalSteps = 10000;

    public StepTracker() {
        int[] monthsList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int month : monthsList) {
            ArrayList<Integer> draftOfMonth = new ArrayList<Integer>(Collections.nCopies(30, 0));
            data.put(month, draftOfMonth);
        }
    }


    public void saveStepDay(HashMap<Integer, ArrayList<Integer>> data, Scanner scanner) {
        Integer userMonth = insertMonth(scanner);
        Integer userDay = insertDay(scanner);
        Integer userSteps = insertSteps(scanner);
        data.get(userMonth).add(userDay - 1, userSteps);
    }

    public void statisticSteps(HashMap<Integer, ArrayList<Integer>> data, Scanner scanner){
        Integer userMonth = insertMonth(scanner);
        Integer maxStepsInMonth =  Collections.max(data.get(userMonth));
        OptionalDouble avgStepsInMonth = data.get(userMonth).stream().mapToInt(Integer::intValue).average();
        double kilometers = converter.stepsToKm(data.get(userMonth).stream().mapToInt(Integer::intValue).sum());
        double kcal = converter.stepsToKcal(data.get(userMonth).stream().mapToInt(Integer::intValue).sum());
        Integer seria = bestSeria(userMonth, data, this.goalSteps);
        System.out.println("Statistic per month " + userMonth);
        for (int i = 0; i < data.get(userMonth).size(); i++) {
            System.out.println((i+1) + " day: " + data.get(userMonth).get(i));
        }
        System.out.println("Max steps in month " + maxStepsInMonth);
        System.out.println("Average steps in month " + avgStepsInMonth.getAsDouble());
        System.out.println("Distance " + kilometers + " km");
        System.out.println("Burned kcal " + kcal + " kcal");
        System.out.println("Best series of steps " + seria + " days");
    }

    public Integer bestSeria(Integer userMonth, HashMap<Integer, ArrayList<Integer>> data, Integer goalSteps){
        ArrayList<Integer> stepsInMonth = data.get(userMonth);
        int maxDaysMoreGoal = 0;
        int currDaysMoreGoal = 0;
        for (Integer steps : stepsInMonth){
            if (steps > goalSteps){
                currDaysMoreGoal++;
            }
            else{
                if (currDaysMoreGoal > maxDaysMoreGoal){
                    maxDaysMoreGoal = currDaysMoreGoal;
                }
                currDaysMoreGoal = 0;
            }
        }
        return maxDaysMoreGoal;
    }

    public void changeGoal(Scanner scanner){
        this.goalSteps = insertNewGoal(scanner);
    }

    public Integer insertDay(Scanner scanner){
        System.out.println("Insert number of day");
        int result = scanner.nextInt();
        while (result < 1 || result > 30) {
            System.out.println("Wrong day. Insert again");
            result = scanner.nextInt();
        }
        return result;
    }

    public Integer insertNewGoal(Scanner scanner){
        System.out.println("Insert new goal");
        int result = scanner.nextInt();
        while (result < 0) {
            System.out.println("Wrong goal. Insert again");
            result = scanner.nextInt();
        }
        return result;
    }

    public Integer insertMonth(Scanner scanner){
        System.out.println("Insert number of month");
        int result = scanner.nextInt();
        while (result < 1 || result > 12) {
            System.out.println("Wrong number. Insert number of month again");
            result = scanner.nextInt();
        }
        return result;
    }

    public Integer insertSteps(Scanner scanner){
        System.out.println("Insert count of steps");
        int result = scanner.nextInt();
        while (result < 0) {
            System.out.println("Wrong steps. Insert again");
            result = scanner.nextInt();
        }
        return result;
    }
}