public class Task {
    private String taskTitle;
    private String taskDescription;
    private String[] taskStatusList = {"NEW", "IN_PROGRESS", "DONE"};
    private Integer taskID;
    private String taskStatus;

    public Task(String taskTitle, String taskDescription, Integer taskID) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskID = taskID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskTitle='" + taskTitle + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskID=" + taskID +
                '}';
    }

    public Task() {
    }

    public Task(String taskTitle, String taskDescription, String taskStatus, Integer taskID) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.taskID = taskID;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public String[] getTaskStatusList() {
        return taskStatusList;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }


    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }


    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
