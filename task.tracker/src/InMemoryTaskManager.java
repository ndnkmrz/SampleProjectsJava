import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    public int counter;
    public static HashMap<Integer, Task> tasks = new HashMap<>();
    public static HashMap<Integer, SubTask> subTasks = new HashMap<>();
    public static HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    public static ArrayList<Integer> historyOfViews = new ArrayList<>();

    @Override
    public ArrayList<Task> getAllTasks(String className){
        switch (className) {
            case "EpicTask" -> {
                return new ArrayList<>(epicTasks.values());
            }
            case "Task" -> {
                return new ArrayList<>(tasks.values());
            }
            case "SubTask" -> {
                return new ArrayList<>(subTasks.values());
            }
        }
        return new ArrayList<>();
    }
    @Override
    public void deleteAllTasks(String className){
        switch (className) {
            case "EpicTask" -> epicTasks.clear();
            case "Task" -> tasks.clear();
            case "SubTask" -> subTasks.clear();
        }
    }
    @Override
    public Task getById(String className, int id){
        Task task = null;
        if(epicTasks.containsKey(id)){
            task = epicTasks.get(id);
        } else if (tasks.containsKey(id)) {
            task = tasks.get(id);
        }else if(subTasks.containsKey(id)){
            task = subTasks.get(id);
        }
        if(historyOfViews.size() == 10){
            historyOfViews.remove(0);
        }
        historyOfViews.add(id);
        return task;
    }
    @Override
    public Task create(Task task){
        task.setId(counter);
        counter++;
        switch (task.getClass().getName()) {
            case "EpicTask" -> {
                epicTasks.put(task.getId(), (EpicTask) task);
                System.out.println("Added new task to epics");
            }
            case "Task" -> {
                tasks.put(task.getId(), task);
                System.out.println("Added new task to tasks");
            }
            case "SubTask" -> {
                subTasks.put(task.getId(), (SubTask) task);
                System.out.println("Added new task to subtasks");
                checkEpicState(((SubTask) task).getEpicId());
            }
        }
        return task;
    }
    @Override
    public void update(Task task, int id){
        switch (task.getClass().getName()) {
            case "EpicTask" -> {
                epicTasks.put(id, (EpicTask) task);
                System.out.println("Epic updated");
            }
            case "Task" -> {
                tasks.put(id, task);
                System.out.println("Task updated");
            }
            case "SubTask" -> {
                subTasks.put(id, (SubTask) task);
                System.out.println("Subtask updated");
                checkEpicState(((SubTask) task).getEpicId());
            }
        }
    }
    @Override
    public void delete(String className, int id){
        switch (className) {
            case "EpicTask" -> {
                epicTasks.remove(id);
                ArrayList<SubTask> subtasksToDelete = getAllSubTasks(id);
                for (SubTask subTask: subtasksToDelete) {
                    subTasks.remove(subTask.getId());
                }
                System.out.println("Epic deleted");
            }
            case "Task" -> {
                tasks.remove(id);
                System.out.println("Task deleted");
            }
            case "SubTask" -> {
                SubTask task = (SubTask) getById("SubTask",id);
                subTasks.remove(id);
                System.out.println("Subtask deleted");
                if(task != null) {
                    checkEpicState(((SubTask) task).getEpicId());
                }
            }
        }
    }
    @Override
    public ArrayList<SubTask> getAllSubTasks(int epicId){
        return subTasks.values().stream().filter(subtask ->
                epicId == subtask.getEpicId()).collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    public void printAllTasks(){
        ArrayList<Task> tasks = getAllTasks("Task");
        ArrayList<Task> epics = getAllTasks("EpicTask");
        System.out.println("_______");
        for (Task task : tasks) {
            System.out.println(task);
        }
        for (Task task : epics) {
            System.out.println(task);
            ArrayList<SubTask> subTasksToShow = getAllSubTasks(task.getId());
            for (SubTask subtask : subTasksToShow) {
                System.out.println("    " + subtask);
            }
        }
    }
    @Override
    public ArrayList<Task> history(){
        ArrayList<Task> history = new ArrayList<>();
        for (Integer id: historyOfViews) {
            if(epicTasks.containsKey(id)){
                history.add(epicTasks.get(id));
            } else if (tasks.containsKey(id)) {
                history.add(tasks.get(id));
            } else if(subTasks.containsKey(id)){
                history.add(subTasks.get(id));
            }
        }
        return history;
    }
    public void checkEpicState(int id){
        EpicTask epic = (EpicTask) getById("EpicTask", id);
        if(epic == null){
            return;
        }
        int epicState = epic.getState().ordinal();
        ArrayList<Task.Status> subtasks = getAllSubTasks(id).stream().map(Task::getState)
                .collect(Collectors.toCollection(ArrayList::new));
        int subTasksSum = subtasks.stream().mapToInt(Enum::ordinal).sum();
        if(subTasksSum == 0 ){
            epicState = 0;
        } else if (subTasksSum == subtasks.size() * 2) {
            epicState = 2;
        } else{
            epicState = 1;
        }
        epic.setState(Task.Status.values()[epicState]);
        update(epic, id);
    }

}