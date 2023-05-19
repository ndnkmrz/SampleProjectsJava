import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    public int counter;
    public static HashMap<Integer, Task> tasks = new HashMap<>();
    public static HashMap<Integer, SubTask> subTasks = new HashMap<>();
    public static HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    public static HashMap<Integer, Node<Task>> historyOfViews = new HashMap<>();
    public static LinkedTaskList<Task> taskList = new LinkedTaskList<Task>();

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
    public Task getById(int id){
        Task task = null;
        if(epicTasks.containsKey(id)){
            task = epicTasks.get(id);
            add(task);
        } else if (tasks.containsKey(id)) {
            task = tasks.get(id);
            add(task);
        }else if(subTasks.containsKey(id)){
            task = subTasks.get(id);
            add(task);
        }
        return task;
    }
    @Override
    public Task create(Task task){
        if(task.getId() == 0) {
            task.setId(counter);
            counter++;
        } else{
            counter = task.getId() + 1;
        }
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
                checkDate(((SubTask) task).getEpicId());
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
                checkDate(((SubTask) task).getEpicId());
            }
        }
    }
    @Override
    public void delete(String className, int id){
        remove(id);
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
                SubTask task = (SubTask) getById(id);
                subTasks.remove(id);
                System.out.println("Subtask deleted");
                if(task != null) {
                    checkEpicState(((SubTask) task).getEpicId());
                    checkDate(((SubTask) task).getEpicId());
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
    public List<Task> getHistory(){
        return taskList.getTasks();
    }
    @Override
    public void showHistory(){
        ArrayList<Task> history = (ArrayList<Task>) getHistory();
        System.out.println("HISTORY: ");
        for (Task task: history) {
            System.out.println(task);
        }
    }
    @Override
    public void add(Task task){
        if(task == null){
            return;
        }
        if(historyOfViews.containsKey(task.getId())){
            taskList.removeNode(task);
        }
        taskList.linkLast(task);
        historyOfViews.put(task.getId(), taskList.getLast());
    }
    @Override
    public void remove(int taskId){
        if(historyOfViews.containsKey(taskId)){
            taskList.removeNode(historyOfViews.get(taskId).data);
            historyOfViews.remove(taskId);
        }
    }
    public void checkEpicState(int id){
        EpicTask epic = epicTasks.get(id);
        if(epic == null){
            return;
        }
        ArrayList<SubTask> subtasks = getAllSubTasks(id);
        int subTasksSum = subtasks.stream().mapToInt(x -> x.getState().ordinal()).sum();
        epic.checkState(subTasksSum, subtasks.size());
    }
    public void checkDate(int id){
        EpicTask epic = epicTasks.get(id);
        if(epic == null){
            return;
        }
        ArrayList<SubTask> subtasks = getAllSubTasks(id);
        LocalDateTime epicStartDate = subtasks.stream().map(Task::getStartTime).min(Comparator.naturalOrder()).orElse(null);
        Duration epicDuration = subtasks.stream().map(Task::getDuration).reduce(Duration.ZERO, Duration::plus);
        epic.setDuration(epicDuration);
        epic.setStartTime(epicStartDate);
    }
    public void getPrioritizedTasks(){
        ArrayList<Task> tasksList = (ArrayList<Task>) tasks.values();
        ArrayList<EpicTask> epicList = (ArrayList<EpicTask>) epicTasks.values();
        ArrayList<SubTask> subList = (ArrayList<SubTask>) subTasks.values();
    }
    static class LinkedTaskList<T>{
        private Node<T> head;
        private Node<T> tail;
        private int size;

        public void linkLast(T element){
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(tail, element, null);
            tail = newNode;
            if(oldTail == null){
                head = newNode;
            }
            else{
                oldTail.next = newNode;
            }
            size++;
        }

        public Node<T> getLast(){
            return tail;
        }
        public List<T> getTasks(){
            ArrayList<T> result = new ArrayList<>();
            if(head == null){
                return result;
            }
            Node<T> checkHead = head;
            while(checkHead != null){
                result.add(checkHead.data);
                checkHead = checkHead.next;
            }
            return result;
        }

        public void removeNode(T element){
            if(head == null){
                return;
            }
            Node<T> checkHead = head;
            while(element != checkHead.data){
                checkHead = checkHead.next;
            }
            if(tail == head){
                tail = null;
                head = null;
                size--;
                return;
            }
            if(checkHead.prev != null){
                if(checkHead.next == null){
                    checkHead.prev.next = null;
                    tail = checkHead.prev;
                    head = checkHead.prev;
                }
                else{
                    checkHead.prev.next = checkHead.next;
                }
            }

            if(checkHead.next != null){
                if(checkHead.prev == null){
                    checkHead.next.prev = null;
                    tail = checkHead.next;
                    head = checkHead.next;
                }
                else{
                    checkHead.next.prev = checkHead.prev;
                }
            }
            size--;
        }
    }
}