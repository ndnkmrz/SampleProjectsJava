public class SubTask extends Task{
    private final int EpicId;

    public SubTask(String name, String description, Status state, int epicId) {
        super(name, description, state);
        EpicId = epicId;
    }

    public int getEpicId() {
        return EpicId;
    }

}