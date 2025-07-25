package lezgi.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        List<String> letters = new ArrayList<>();
        letters.addAll(readTxtFile("src/main/resources/lezgi-text.txt"));
        System.out.println("Общее количество букв: " + letters.size());

        getStatisticsForEachLetter(letters);
    }

    private static void getStatisticsForEachLetter(List<String> letters) {
        Map<String, Integer> letterCount = new TreeMap<>();
        for (String letter : letters) {
            letterCount.put(letter, letterCount.getOrDefault(letter, 0) + 1);
        }
        // Сортируем по убыванию значений
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(letterCount.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.println(entry.getKey().replace("i", "I") + " : " + entry.getValue());
        }
    }

    /* Чтение файла inputData с текстом */
    private static List<String> readTxtFile(String filePath) {
        final List<String> lines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                lines.addAll(splitStringIntoLetters(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static List<String> splitStringIntoLetters(String fileLine) {
        String regex = "Гъ|Гь|Къ|Кь|КI|ПI|ТI|Уь|Хъ|Хь|ЦI|ЧI|[А-Яа-я]";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(fileLine);

        List<String> letters = new ArrayList<>();
        while (matcher.find()) {
            letters.add(matcher.group().toLowerCase());
        }
        return letters;
    }
}