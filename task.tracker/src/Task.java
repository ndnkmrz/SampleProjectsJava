import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    private String name;
    private String description;
    private int id;
    private Status state;
    private Integer epicId = null;
    //public static final String[] STATUS = {"NEW", "IN_PROGRESS", "DONE"};
    private Duration duration;
    private LocalDateTime startTime;
    public enum Status {
        NEW,
        IN_PROGRESS,
        DONE
    }

    private LocalDateTime getEndTime(){
        return this.startTime.plus(this.duration);
    }

    public Task(String name, String description, int id, Status state, Integer epicId, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.state = state;
        this.epicId = epicId;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, Status state) {
        this.name = name;
        this.description = description;
        this.state = state;
    }
    public Task(String name, String description, Status state, int epicId) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.epicId = epicId;
    }

    public Task(String name, String description, int id, Status state) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.state = state;
    }

    public Task(String name, String description, int id, Status state, Integer epicId) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.state = state;
        this.epicId = epicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public String getStringId(){
        return Integer.toString(id);
    }
    public void setId(int id) {
        this.id = id;
    }

    public Status getState() {
        return state;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setState(Status state) {
        this.state = state;
    }
    public Integer getEpicId() {
        return epicId;
    }
    @Override
    public String toString(){
        return id + ": " + name + " — " + state.name();
    }

}