import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks(String className);
    void deleteAllTasks(String className);
    Task getById(String className, int id);
    Task create(Task task);
    void update(Task task, int id);
    void delete(String className, int id);
    List<SubTask> getAllSubTasks(int epicId);
    void printAllTasks();
    List<Task> history();

}