import java.util.Arrays;

public class Epic extends Task{
    private Subtask[] subList;
    public Subtask[] getSubList() {
        return subList;
    }

    public void setSubList(Subtask[] subList) {
        this.subList = subList;
    }

    public Epic(Subtask[] subList) {
        this.subList = subList;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subList=" + Arrays.toString(subList) +
                "} " + super.toString();
    }

    public Epic(String taskTitle, String taskDescription, Integer taskID, Subtask[] subList) {
        super(taskTitle, taskDescription, taskID);
        this.subList = subList;
        super.setTaskStatus(setStatusToEpic(subList));
    }

    public String setStatusToEpic(Subtask[] subList){
        String result;
        Integer counterNew = 0;
        Integer counterDone = 0;
        for (Subtask subtask : subList) {
            if (subtask.getTaskStatus().equals("NEW")){
                counterNew++;
            } else if (subtask.getTaskStatus().equals("DONE")) {
                counterDone++;
            }
        }
        if (counterNew == subList.length){
            result = "NEW";
        } else if (counterDone == subList.length) {
            result = "DONE";
        }
        else {
            result = "IN_PROGRESS";
        }
        return result;
    }
}
