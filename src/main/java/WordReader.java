import java.io.*;
import java.util.Iterator;

public class WordReader implements Iterator<String>, Closeable {

    private final InputStreamReader reader;
    private boolean hasNext = false;

    WordReader(String fileName) throws FileNotFoundException {
        File file = new File(fileName);


        reader = new InputStreamReader(new FileInputStream(file));
        if (file.length() > 0) {
            hasNext = true;
        }

    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public String next() {
        if (!hasNext) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        while (true) {
            try {

                var sym = reader.read();
                if (sym == -1) {
                    hasNext = false;
                    return builder.toString();
                }

                if (Character.isLetterOrDigit(sym)) {
                    builder.append((char) sym);
                } else {
                    return builder.toString();
                }

            } catch (IOException e) {
                hasNext = false;
                return builder.toString();//отдаем что успели считать
            }

        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
