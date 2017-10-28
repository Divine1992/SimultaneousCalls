import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void filePresentTest() {
        Main main = new Main();
        try {
            main.sortCalls(FileSystems.getDefault().getPath("src/main/2.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void callsCountTest(){
        Main main = new Main();
        Map<Integer, Integer> inputMap = new TreeMap<>();
        inputMap.put(1385718405, 1);
        inputMap.put(1385718406, 2);
        inputMap.put(1385718407, 3);
        main.callsCount(inputMap, 1385718405);
        main.callsCount(inputMap, 1385718406);
        Map<Integer, Integer> expectedMap = new TreeMap<>();
        expectedMap.put(1385718405, 2);
        expectedMap.put(1385718406, 3);
        expectedMap.put(1385718407, 3);
        assertEquals(expectedMap, inputMap);

    }

    @Test
    public void emptyInputValueTest(){
        Main main = new Main();
        Map<Integer, Integer> emptyMap = new TreeMap<>();
        try{
            main.durationFind(emptyMap);
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    @Test
    public void maxOneSimultaneousCallsTest(){
        Main main = new Main();
        Map<Integer, Integer> inputMap = new TreeMap<>();
        inputMap.put(1385718405, 1);
        inputMap.put(1385718406, 2);
        inputMap.put(1385718407, 3);
        StringBuilder expectedSb = new StringBuilder("The peak for this call log is 3 simultaneous calls, only in 1385718407.");
        assertEquals(expectedSb.toString(), main.durationFind(inputMap));
    }

    @Test
    public void maxManySimultaneousCallsTest(){
        Main main = new Main();
        Map<Integer, Integer> inputMap = new TreeMap<>();
        inputMap.put(1385718405, 1);
        inputMap.put(1385718406, 3);
        inputMap.put(1385718407, 3);
        StringBuilder expectedSb = new StringBuilder("The peak for this call log is 3 simultaneous calls, that occurred between 1385718406 and 1385718407.");
        assertEquals(expectedSb.toString(), main.durationFind(inputMap));
    }

    @Test
    public void periodMaxSimultaneousCallsTest(){
        Main main = new Main();
        Map<Integer, Integer> inputMap = new TreeMap<>();
        inputMap.put(1385718405, 1);
        inputMap.put(1385718406, 3);
        inputMap.put(1385718407, 3);
        inputMap.put(1385718410, 3);
        inputMap.put(1385718411, 3);
        StringBuilder expectedSb = new StringBuilder("The peak for this call log is 3 simultaneous calls, that occurred between 1385718406 and 1385718407 and between 1385718410 and 1385718411.");
        assertEquals(expectedSb.toString(), main.durationFind(inputMap));
    }

}
