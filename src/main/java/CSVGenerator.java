import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVGenerator {

    private static final Map<String, Integer> wordsMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("No args");
            return;
        }

        //Регексы тут упорно не хотят работать с русским языком
        // var reader = new Scanner(new File(args[0]), StandardCharsets.UTF_8).useDelimiter("[\\W_\\s]");
        var reader = new WordReader(args[0]);

        int count = 0;

        try (reader) {//Юзаем try-with-resources и не думаем о том кто будет файл закрывать
            while (reader.hasNext()) {
                var word = reader.next();

                if (word.isEmpty()) continue; // пустые строки пропускаем
                count++;

                if (wordsMap.containsKey(word)) {
                    wordsMap.replace(word, wordsMap.get(word) + 1);
                } else {
                    wordsMap.put(word, 1);
                }
            }
        }


        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordsMap.entrySet());
        list.sort((a, b) -> (b.getValue() - a.getValue()));


        var writer = new OutputStreamWriter(new FileOutputStream("out.csv"), StandardCharsets.UTF_8);
        try (writer) {
            final int finalCount = count;
            list.forEach(strInt -> {
                try {
                    writer.write(String.format("%s,%d,%d%n", strInt.getKey(), strInt.getValue(), (strInt.getValue() * 100) / finalCount));
                    writer.flush();
                } catch (IOException ignored) {
                }
            });
        }
    }
}
