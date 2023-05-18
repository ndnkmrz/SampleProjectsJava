import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("Убрать террасу", "Помой все полы и подмети листья", Task.Status.NEW);
        Task task2 = new Task("Развесить белье", "Сними высохшее и развесь новое", Task.Status.NEW);
        task1 = manager.create(task1);
        task2 = manager.create(task2);
        EpicTask epic1 = new EpicTask("Убрать кухню", "Кухня должна блестеть",Task.Status.NEW);
        EpicTask epic2 = new EpicTask("Убрать ванную", "Ванная должна блестеть",Task.Status.NEW);
        epic1 = (EpicTask)manager.create(epic1);
        epic2 = (EpicTask)manager.create(epic2);
        SubTask subTask1 = new SubTask("Помыть посуду", "Загрузить всё в посудомойку",Task.Status.NEW,
                epic1.getId());
        SubTask subTask2 = new SubTask("Помыть полы", "Протереть зону до стола", Task.Status.NEW,
                epic1.getId());
        SubTask subTask3 = new SubTask("Помыть унитаз", "Вычистить вилкой",Task.Status.NEW,
                epic2.getId());
        manager.create(subTask1);
        manager.create(subTask2);
        manager.create(subTask3);
        manager.printAllTasks();
        task2.setState(Task.Status.DONE);
        manager.update(task2, task2.getId());
        subTask2.setState(Task.Status.DONE);
        subTask3.setState(Task.Status.DONE);
        manager.update(subTask2, subTask2.getId());
        manager.update(subTask3, subTask3.getId());
        manager.printAllTasks();
        manager.delete("Task", task1.getId());
        manager.delete("EpicTask", epic2.getId());
        manager.getById(2);
        manager.printAllTasks();
        manager.deleteAllTasks("Task");
        manager.printAllTasks();
        manager.showHistory();
    }
}