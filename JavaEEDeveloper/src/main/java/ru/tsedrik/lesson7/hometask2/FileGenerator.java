package ru.tsedrik.lesson7.hometask2;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class FileGenerator {
    private static int generateRandomNum(int min, int max){
        return new Random().nextInt(max) + min;
    }

    private static Character generateLetter(){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();

        return alphabet.charAt(random.nextInt(alphabet.length()));
    }

    public static String generateWord(){
        Random random = new Random();
        int wordLength = random.nextInt(15) + 1;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++){
            sb.append(generateLetter());
        }

        return sb.toString();
    }

    public static String generateSentence(){
        StringBuilder sentence = new StringBuilder();
        Random random = new Random();
        int wordsCount = random.nextInt(15) + 1;

        //generate first word in sentence with first letter in uppercase
        String firstWord = generateWord();
        sentence.append(firstWord.substring(0, 1).toUpperCase());
        sentence.append(firstWord.substring(1));

        //generate word for sentence with random ,
        for (int i = 0; i < wordsCount - 1; i++){
            sentence.append(random.nextInt(2) == 0 ? ", " : " ");
            sentence.append(generateWord());
        }

        //generate random end of sentence
        String sentenceEnds = ".!?";
        sentence.append(sentenceEnds.charAt(random.nextInt(sentenceEnds.length())));
        sentence.append(" ");

        return sentence.toString();
    }

    public static String generateParagraph(){
        StringBuilder paragraph = new StringBuilder();
        Random random = new Random();
        int paragraphsCount = random.nextInt(20) + 1;

        for (int i = 0; i < paragraphsCount; i++) {
            paragraph.append(generateSentence());
        }
        paragraph.append("\r\n");

        return paragraph.toString();
    }

    public static Path[] getFiles(String path, int n, int size, String[] words){
        Path[] generatedFiles = new Path[n];

        Path directory = Paths.get(path);
        if (!Files.exists(directory)){
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < n; i++) {
            Path fileName = Paths.get(path, "generatedFile_" + (i + 1) + ".txt");
            try (FileWriter fileWriter = new FileWriter(fileName.toFile())) {

                long curFileSize = 0;
                while (curFileSize < size){
                    String paragraph = generateParagraph();
                    long restSize = size - curFileSize;
                    if (restSize >= paragraph.length()) {
                        fileWriter.write(paragraph);
                        fileWriter.flush();
                    } else {
                        fileWriter.write(paragraph, 0, (int)restSize);
                    }
                    curFileSize += paragraph.length();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("File \"" + fileName.toString() + "\" was generated.");
            generatedFiles[i] = fileName;
        }
        return generatedFiles;
    }

    public static void main(String[] args) {
        getFiles("F:\\JavaFiles\\files", 5, 10000, new String[]{});
    }
}
