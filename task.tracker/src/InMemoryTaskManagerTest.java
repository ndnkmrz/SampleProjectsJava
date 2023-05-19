import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    static TaskManager taskManager;
    @BeforeAll
    static void beforeAll(){
        taskManager = Managers.getDefault();
    }
    @Test
    void create() {
        final Task task = new Task("testName", "testDescr",1, Task.Status.NEW);
        final Task taskResult = taskManager.create(task);
        Assertions.assertNotNull(taskResult);
        Assertions.assertEquals(task, taskResult);
    }

    @Test
    void update() {
        final Task task = new Task("testName1", "testDescr",1, Task.Status.NEW);
        taskManager.update(task, 1);
        final Task taskResult = taskManager.getById(1);
        Assertions.assertNotNull(taskResult);
        Assertions.assertEquals(task, taskResult);
    }

    @Test
    void delete() {
        final Task task = new Task("testName1", "testDescr", 1, Task.Status.NEW);
        taskManager.delete("Task", 1);
        Assertions.assertNull(taskManager.getById(1));
    }
}