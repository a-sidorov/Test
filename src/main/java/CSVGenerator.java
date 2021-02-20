import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVGenerator {

    private static final Map<String, Integer> wordsMap = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 1) {
            System.err.println("No args");
            return;
        }

        //var reader = new Scanner(new File(args[0])).useDelimiter("[^[a-zA-z][0-9]]");
        var reader = new WordReader(args[0]);

        int count = 0;
        while (reader.hasNext()) {
            var word = reader.next();//do smth with word

            if (word.isEmpty()) continue; // пустые строки пропускаем
            count++;

            if (wordsMap.containsKey(word)) {
                wordsMap.replace(word, wordsMap.get(word) + 1);
            } else {
                wordsMap.put(word, 1);
            }
        }


        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordsMap.entrySet());
        list.sort(Map.Entry.comparingByValue());


        final int finalCount = count;
        list.forEach(strInt -> {
            System.out.println(
                    String.format("%s,%d,%d", strInt.getKey(), strInt.getValue(), (strInt.getValue() * 100) / finalCount)
            );
        });
    }
}
