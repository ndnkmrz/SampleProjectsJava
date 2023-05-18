import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileBackedTasksManager extends InMemoryTaskManager{
    public static String fileName = "backed_tasks.csv";
    public FileBackedTasksManager() {
        loadFromFile();
        printAllTasks();
        showHistory();
    }
    private void loadFromFile(){
        // Подгружать всю информацию из файла, если этот файл есть
        // Читает файл до пустой строки. Размещает в словари по типам
        // Читает файл после пустой строки, создает связный список и словарь
        Path path = Paths.get(fileName);
        if(!Files.exists(path)){
            return;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));) {
            ArrayList<Task> tasks = new ArrayList<>();
            String header = br.readLine();
            String line = br.readLine();
            while(!line.isEmpty()){
                tasks.add(fromString(line));
                line = br.readLine();
            }
            for(Task task : tasks){
                create(task);
            }
            String history = br.readLine();
            historyFromString(history);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Task fromString(String task){
        String[] list = task.split(",");
        return switch (list[1]) {
            case "Task" -> new Task(list[2], list[4],Integer.parseInt(list[0]), Task.Status.valueOf(list[3]));
            case "EpicTask" -> new EpicTask(list[2], list[4],Integer.parseInt(list[0]), Task.Status.valueOf(list[3]));
            case "SubTask" -> new SubTask(list[2], list[4],Integer.parseInt(list[0]), Task.Status.valueOf(list[3]), Integer.parseInt(list[5]));
            default -> null;
        };
    }

    private void historyFromString(String history){
        if(history == null){
            return;
        }
        String[] list = history.split(",");
        for (String id: list) {
            getById(Integer.parseInt(id));
        }
    }

    private void save() throws IOException {
        try(FileWriter writer = new FileWriter(fileName)){
            writer.write("id,type,name,status,description,epic\n");
            for (TaskType type: TaskType.values()) {
                ArrayList<Task> taskList = getAllTasks(type.name());
                for(Task task: taskList){
                    String taskResult = taskToString(task, type.name());
                    writer.write(taskResult);
                }
            }
            writer.write("\n");
            writer.write(historyToString());
        }
    }

    private String taskToString(Task task, String typeName){
        String result = String.format("%d,%s,%s,%s,%s,", task.getId(), typeName, task.getName(), task.getState().name(), task.getDescription());
        if(task.getEpicId() != null){
            result = result + task.getEpicId();
        }
        result = result + "\n";
        return result;
    }

    private String historyToString(){
        String[] history = getHistory().stream().map(Task::getStringId).toArray(String[]::new);
        String result = String.join(",", history);
        return result;
    }

    @Override
    public Task create(Task task) {
        Task resultTask = super.create(task);
        try {
            save();
        }
        catch (Exception ex){
            System.out.println("Manager save error");
        }
        return resultTask;
    }

    @Override
    public void update(Task task,int id) {
        super.update(task, id);
        try {
            save();
        }
        catch (Exception ex){
            System.out.println("Manager save error");
        }
    }
    @Override
    public void delete(String className, int id){
        super.delete(className, id);
        try {
            save();
        }
        catch (Exception ex){
            System.out.println("Manager save error");
        }
    }

    @Override
    public Task getById(int id){
        Task task = super.getById(id);
        try {
            save();
        }
        catch (Exception ex){
            System.out.println("Manager save error");
        }
        return task;
    }

}