import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println(main.sortCalls(Paths.get(args[0])));
    }

    public String sortCalls(Path path) throws IOException {
        Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8);
        Map<Integer, Integer> results = new TreeMap<>();
        for(String calls: stream.collect(Collectors.toList())){
            String[] line = calls.split(":");
            Integer start = null;
            Integer end = null;
            try{
                start = Integer.parseInt(line[0]);
                end = Integer.parseInt(line[1]);
                for (int i = start; i <= end; i++){
                    results = callsCount(results, i);
                }
            } catch (NumberFormatException ex){
                throw new NumberFormatException("File incorrect, "+start+" or "+end+" isn't integer value.");
            }
        }
        return durationFind(results);
    }

    public Map<Integer,Integer> callsCount(Map<Integer, Integer> results, Integer second) {
        if(results.containsKey(second)){
            Integer val = results.get(second)+1;
            results.put(second, val);
            return results;
        }
        results.put(second, 1);
        return results;
    }

    public String durationFind(Map<Integer, Integer> results) {
        if (results.isEmpty()){
            throw new NoSuchElementException();
        }
        List<Integer> durationCalls = new ArrayList<>();
        int max =results.values().stream().max(Comparator.naturalOrder()).get();
        results.forEach((k,v) ->{
            if (v == max){
                durationCalls.add(k);
            }
        });
        StringBuilder sb = new StringBuilder("The peak for this call log is "+max+" simultaneous calls,");
        if (durationCalls.size()==1){
            sb.append(" only in "+durationCalls.get(0)+".");
            return sb.toString();

        } else {
            sb.append(" that occurred between ");
            int index = 0;
            for (int i = 0; i < durationCalls.size()-1; i++) {
                if (durationCalls.get(i+1) - durationCalls.get(i) > 1){
                    sb.append(durationCalls.get(index) +" and "+ durationCalls.get(i)+" and between ");
                    index = i+1;
                }
                if (i == durationCalls.size()-2){
                    sb.append(durationCalls.get(index) +" and "+ durationCalls.get(durationCalls.size()-1)+".");
                }
            }
            return sb.toString();
        }
    }

}
