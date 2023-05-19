import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {
    @ParameterizedTest
    @CsvSource({
            "0, 1, 0",
            "1, 1, 1",
            "2, 1, 2",
            "0, 0, 0",
            "4, 2, 2",
            "3, 2, 1",
            "0, 2, 0"
    })
    void checkState(int subTasksSum, int subTasksSize, int result){
        EpicTask epicTask = new EpicTask("testName", "testDescr", Task.Status.IN_PROGRESS);
        epicTask.checkState(subTasksSum, subTasksSize);
        Assertions.assertEquals(result, epicTask.getState().ordinal());
    }
}