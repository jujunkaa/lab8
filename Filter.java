import java.util.List;
import java.util.stream.Collectors;

public class Filter {
    
    @DataProcessor
    public List<String> filter(List<String> data) {
        return data.parallelStream().filter(s -> s.toLowerCase().contains("j")).collect(Collectors.toList());
    }
}
