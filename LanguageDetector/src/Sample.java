
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Sample {
    public static void main(String[] args) {
        String hun = "hun.txt";
        String en = "en.txt";
        Path hungarian = Paths.get(hun);
        Path english = Paths.get(en);

        List<String> hunList = readHungarianText(hungarian);
        List<String> engList = readEnglishText(english);

        List<String> clearedHun = clearText(hunList);
        List<String> clearedEn = clearText(engList);
        createNGrams(clearedHun);

    }
    public static List<String> readHungarianText(Path hungarian){
        List<String> allHun = new ArrayList<>();
        try {
             allHun = Files.readAllLines(hungarian);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allHun;
    }

    public static List<String> readEnglishText(Path english) {
        List<String> allEng = new ArrayList<>();

        try {
            allEng = Files.readAllLines(english);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allEng;
    }

    public static List<String> clearText(List<String> inputText) {
     List<String> clearedText = new ArrayList();

        for (int i = 0; i <inputText.size() ; i++) {
           String clearedString = inputText.get(i).replaceAll("[^A-Za-z öüóőúéáűí]","");
           clearedString = clearedString.trim().replaceAll("\\s{2,}", " ").toLowerCase();
           clearedText.add(clearedString);
        }
        System.out.println(clearedText);
        return clearedText;
    }
    public static void createNGrams(List<String> inputText){
        List<String> nGramList = new ArrayList<>();
        StringBuilder inputString = new StringBuilder();
        for (String s:inputText) {
            inputString.append(s);
            inputString.append(" ");
        }
        //System.out.println(inputString);

        StringBuilder nGram = new StringBuilder();

        char[] chars = new char[inputString.length()];
        chars = inputString.toString().toCharArray();
        int n = 3;
        int b = 0;
        while (n!=inputString.length()) {
            for (int j = b; j <n ; j++) {
                nGram.append(chars[j]);
            }
            nGramList.add(nGram.toString());
            nGram.setLength(0);
            n++;
            b++;
        }
        System.out.println(nGramList);

    }
}
