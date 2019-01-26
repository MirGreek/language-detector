
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        List<String> hungarianNgrams = createNGrams(clearedHun);
        List<String> englishNgrams = createNGrams(clearedEn);
        getMostCommonNGrams(hungarianNgrams);

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
        //System.out.println(clearedText);
        return clearedText;
    }
    public static List<String> createNGrams(List<String> inputText){
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
        while (n != inputString.length()) {
            for (int j = b; j < n ; j++) {
                nGram.append(chars[j]);
            }
            nGramList.add(nGram.toString());
            nGram.setLength(0);
            n++;
            b++;
        }
        //System.out.println(nGramList);
        return nGramList;
    }

    public static void getMostCommonNGrams(List<String> nGramList) {
        HashMap<String,Integer> map = new HashMap<>();

        for (int i = 0; i <nGramList.size() ; i++) {
            if( ! map.containsKey(nGramList.get(i))){
                map.put(nGramList.get(i),1);
            } else {
                map.put(nGramList.get(i), map.get(nGramList.get(i)) + 1);
            }
        }


        List<Integer> numberDB = new ArrayList<>(map.values());
        List<String> maxHundred = new ArrayList<>();
        Collections.sort(numberDB);
        List<Integer> theHundredBiggest = new ArrayList<>();

        for (int i = numberDB.size()-5; i <numberDB.size() ; i++) { //100
            theHundredBiggest.add(numberDB.get(i));
        }
        System.out.println(map);

        for (Map.Entry<String, Integer> db : map.entrySet()) {
            for (Integer biggest: theHundredBiggest) {
                if (db.getValue() == biggest) {
                    maxHundred.add(db.getKey());
                }
            }
        }

        System.out.println(maxHundred);
    }
}