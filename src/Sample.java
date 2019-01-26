
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

        System.out.println("Írj be egy összetett mondatot, és megmondom, hogy angolul vagy magyarul van :)");
        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();
        String clearedInput = clearText(sentence);
        List<String> inputNGrams = createNGrams(clearedInput);

        System.out.println(inputNGrams);

        String hungarianText = readTextFromFile(hungarian);
        String engList = readTextFromFile(english);

        String clearedHun = clearText(hungarianText);
        String clearedEn = clearText(engList);
        List<String> hungarianNGrams = createNGrams(clearedHun);
        List<String> englishNGrams = createNGrams(clearedEn);
        List<String> mostCommonNGramsHun = getMostCommonNGrams(hungarianNGrams);
        List<String> mostCommonNGramsEng = getMostCommonNGrams(englishNGrams);

        compareInputWithNGrams(inputNGrams,mostCommonNGramsHun, mostCommonNGramsEng);
    }

    public static String readTextFromFile(Path text){
        List<String> textList = new ArrayList<>();
        try {
            textList = Files.readAllLines(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder textAsString = new StringBuilder();
        for (String s:textList) {
            textAsString.append(s);
            textAsString.append(" ");
        }
        return textAsString.toString();
    }

    public static String clearText(String inputText) {
        String clearedString = inputText.trim().replaceAll("\\s{2,}", " ").toLowerCase();
        clearedString = clearedString.replaceAll("[^A-Za-z öüóőúéáűí]","");
        clearedString = clearedString.replaceAll("[\\n]","");
        return clearedString;
    }

    public static List<String> createNGrams(String inputText){
        List<String> nGramList = new ArrayList<>();

        //System.out.println(inputString);

        StringBuilder nGram = new StringBuilder();

        char[] chars = new char[inputText.length()];
        chars = inputText.toCharArray();
        int n = 3;
        int b = 0;
        while (n != inputText.length()) {
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

    public static List<String> getMostCommonNGrams(List<String> nGramList) {
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

        for (int i = numberDB.size()-100; i <numberDB.size() ; i++) {
            theHundredBiggest.add(numberDB.get(i));
        }
        //System.out.println(map);

        for (Map.Entry<String, Integer> db : map.entrySet()) {
            for (Integer biggest: theHundredBiggest) {
                if (db.getValue() == biggest) {
                    maxHundred.add(db.getKey());
                }
            }
        }

        //System.out.println(maxHundred);
        return maxHundred;
    }

    private static void compareInputWithNGrams(List<String> inputNGrams, List<String> hungarian, List<String> english) {
        int countNGramsHun = 0;
        int countNGramsEng = 0;

        for (int i = 0; i <inputNGrams.size() ; i++) {
            for (int j = 0; j <hungarian.size() ; j++) {
                if (inputNGrams.get(i).equals(hungarian.get(j))) {
                    countNGramsHun++;
                } else if (inputNGrams.get(i).equals(english.get(j))) {
                    countNGramsEng++;
                }
            }
        }
        if (countNGramsEng < countNGramsHun) {
            System.out.println("Ez magyarul van");
        } else if (countNGramsHun < countNGramsEng) {
            System.out.println("This is in english");
        } else {
            System.out.println("Nem tudom eldönteni, I cannot decide");
        }
    }

}