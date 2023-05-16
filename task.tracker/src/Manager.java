import java.util.Arrays;
import java.util.HashMap;

public class Manager {
    static Integer taskId = 0;
    static HashMap<Integer, Task> taskMap = new HashMap<>();
    static HashMap<Integer, Subtask> subTaskMap = new HashMap<>();
    static HashMap<Integer, Epic> epicMap = new HashMap<>();
    public static void main(String[] args) {
        taskId++;
        Task task1 = new Task("Learn Java Syntax", "Do Java tasks", "NEW", taskId);
        createTask(task1);
        taskId++;
        Task task2 = new Task("Learn java again", "Learn theory", "NEW", taskId);
        createTask(task2);
        printListOfTasks(taskMap);
        deleteTaskByID(taskMap, 2);
        printListOfTasks(taskMap);
        task1.setTaskTitle("Sleep");
        updateTask(task1, 1, taskMap);
        printListOfTasks(taskMap);
        Subtask subtask1 = new Subtask("Learn java", "Learn theory", "NEW", 5);
        Subtask subtask2 = new Subtask("Learn java", "Learn theory", "DONE", 6);
        Subtask[] subList = {subtask1, subtask2};
        Epic epic = new Epic("Prepare to interview", "Learn everything", taskId, subList);
        createTask(epic);
        printListOfTasks(epicMap);
    }
    public static void createTask(Object task){
        switch (task.getClass().getSimpleName()) {
            case "Task" -> taskMap.put(taskId, (Task) task);
            case "Subtask" -> subTaskMap.put(taskId, (Subtask) task);
            case "Epic" -> epicMap.put(taskId, (Epic) task);
        }
    }

    public static void updateTask(Object task, Integer taskId, HashMap taskMap){
        taskMap.replace(taskId, task);
    }

    public static void printListOfTasks(HashMap taskMap){
        System.out.println(taskMap);
    }

    public static void clearAllTasks(HashMap taskMap){
        taskMap.clear();
    }

    public static void getTaskByID(HashMap taskMap, Integer taskId){
        taskMap.get(taskId);
    }

    public static void deleteTaskByID(HashMap taskMap, Integer taskId){
        taskMap.remove(taskId);
    }

    public static void printSubTasksOfEpic(Epic epic){
        System.out.println(Arrays.toString(epic.getSubList()));
    }


}
