public class EpicTask extends Task{

    public EpicTask(String name, String description, Status state) {
        super(name, description, state);
    }

    public EpicTask(String name, String description, int id, Status state) {
        super(name, description, id, state);
    }
}