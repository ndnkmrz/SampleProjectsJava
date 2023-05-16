public class Subtask extends Task{
    private Epic epic;

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public Subtask(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epic=" + epic +
                "} " + super.toString();
    }

    public Subtask(String taskTitle, String taskDescription, String taskStatus, Integer taskID) {
        super(taskTitle, taskDescription, taskStatus, taskID);
    }
}
