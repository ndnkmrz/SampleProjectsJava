import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task{

    public SubTask(String name, String description, Status state, int epicId) {
        super(name, description, state, epicId);
    }

    public SubTask(String name, String description, int id, Status state, Integer epicId) {
        super(name, description, id, state, epicId);
    }

    public SubTask(String name, String description, int id, Status state, Integer epicId, Duration duration,
                   LocalDateTime startTime) {
        super(name, description, id, state, epicId, duration, startTime);
    }
}