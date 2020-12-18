package ru.tsedrik.lesson21;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FileStorage{
    private Path path;
    private ObjectMapper objectMapper;
    private Set<UUID> ids;

    public FileStorage(String fileName){
        path = Path.of(fileName + ".json");
        objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        ids = new HashSet<>();
        getAllIds();
    }

    private void getAllIds(){
        if (!Files.exists(path)){
            return;
        }
        try {
            ids = Files.lines(path)
                    .map(s -> {
                            Pet pet = null;
                                try {
                                    pet = objectMapper.readValue(s, Pet.class);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return pet.getId();
                            })
                    .collect(Collectors.toSet());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean add(Pet pet) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toString(), true))){
            bufferedWriter.write(objectMapper.writeValueAsString(pet));
            bufferedWriter.newLine();
            ids.add(pet.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public Pet search(UUID id) {
        Pet pet = null;
        if (ids.contains(id)){
            try {
                pet = Files.lines(path)
                        .map(s -> {
                            Pet p = null;
                            try {
                                p = objectMapper.readValue(s, Pet.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return p;})
                        .filter(p -> p.getId().equals(id))
                        .findFirst()
                        .get();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return pet;
    }

    public Pet change(Pet t) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

    public Collection<Pet> getAll() {
        List<Pet> pets = new ArrayList<>();
        try {
            pets = Files.lines(path).map(s -> {
                Pet p = null;
                try {
                    p = objectMapper.readValue(s, Pet.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return p;
            }).collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
        }

        return pets;
    }

    public boolean delete(Pet pet) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

}
