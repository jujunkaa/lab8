import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataManager {
    private final List<Object> processors = new ArrayList<>();
    private final List<String> data = new ArrayList<>();
    private List<String> newData = new ArrayList<>();
    public void registerDataProcessor(Object processor){
        processors.add(processor);
    }
    public void loadData(String source) throws FileNotFoundException, IOException{
        try(BufferedReader file = new BufferedReader(new FileReader(source))){
            String line;
            while ((line = file.readLine())!=null){
                data.add(line);
            }
        } 

    }
    public void processData() throws Exception{
        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Future<List<String>>> results = new ArrayList<>();
        for (Object processor: processors) {
            for (Method method: processor.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(DataProcessor.class)){
                    Future<List<String>> result = executor.submit(() -> {
                        return (List<String>) method.invoke(processor, data);
            });
            
                    results.add(result);
                }
            }
        }
        newData = new ArrayList<>();
        for (Future<List<String>> result : results) {
            newData.addAll(result.get());
        }
        executor.shutdown();
    }
    public void saveData(String destination) throws IOException {
        Files.write(Paths.get(destination), newData);
    }
}
