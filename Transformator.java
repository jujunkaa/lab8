import java.util.List;
import java.util.stream.Collectors;

public class Transformator {
    
    @DataProcessor
    public List<String> toUpper (List<String> data) {
        return data.parallelStream().map(s -> s.toUpperCase()).collect(Collectors.toList());
    }
}
