package ru.tsedrik.lesson7.hometask1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SearchWordsInTextFile {
    private static String inFileName; //= "F:\\JavaFiles\\InFile.txt";
    private static String outFileName; //= "F:\\JavaFiles\\OutFile.txt";

    public static void main(String[] args) {

        try(BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter the full filename for reading:");
            inFileName = consoleReader.readLine();

            System.out.println("Enter full filename for writing:");
            outFileName = consoleReader.readLine();

       } catch (IOException e){
            e.printStackTrace();
        }

        Path inFile = Paths.get(inFileName);
        Path outFile = Paths.get(outFileName);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile.toFile()))){

            Set<String> uniqueWords = Files.readAllLines(inFile, StandardCharsets.UTF_8).stream()
                    .flatMap(s -> Arrays.stream(s.split("[^a-zA-Zа-яА-Я0-9_]+")))
                    .map(String::toLowerCase)
                    .collect(Collectors.toCollection(TreeSet::new));

            for (String s : uniqueWords){
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
