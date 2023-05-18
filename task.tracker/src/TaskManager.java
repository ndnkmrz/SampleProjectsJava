import java.util.List;

public interface TaskManager extends HistoryManager{
    List<Task> getAllTasks(String className);
    void deleteAllTasks(String className);
    Task getById(int id);
    Task create(Task task);
    void update(Task task, int id);
    void delete(String className, int id);
    List<SubTask> getAllSubTasks(int epicId);
    void printAllTasks();
    void showHistory();
}