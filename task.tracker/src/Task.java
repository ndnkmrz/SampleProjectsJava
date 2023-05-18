public class Task {
    private String name;
    private String description;
    private int id;
    private Status state;
    private Integer epicId = null;
    //public static final String[] STATUS = {"NEW", "IN_PROGRESS", "DONE"};
    public enum Status {
        NEW,
        IN_PROGRESS,
        DONE
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