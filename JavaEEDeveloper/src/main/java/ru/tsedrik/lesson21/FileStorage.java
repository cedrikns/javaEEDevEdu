package ru.tsedrik.lesson21;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileStorage implements PetStorage{
    private Path path;
    private ObjectMapper objectMapper;
    private Set<UUID> ids;

    private Function<String, Pet> deserializePet = s -> {
        try {
            return objectMapper.readValue(s, Pet.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    };

    public FileStorage(String fileName){
        path = Path.of(fileName + ".json");
        objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        ids = getAllIds();
    }

    private Set<UUID> getAllIds(){
        Set<UUID> foundedIds = new HashSet<>();
        if (!Files.exists(path)){
            return foundedIds;
        }
        try {
            foundedIds = Files.lines(path)
                    .map(deserializePet)
                    .map(p -> p.getId())
                    .collect(Collectors.toSet());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return foundedIds;
    }

    @Override
    public boolean add(Pet pet) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toString(), true))){
            bufferedWriter.write(objectMapper.writeValueAsString(pet));
            bufferedWriter.newLine();
            ids.add(pet.getId());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Pet search(UUID id) {
        Pet pet = null;
        if (ids.contains(id)){
            try {
                pet = Files.lines(path)
                        .map(deserializePet)
                        .filter(p -> p.getId().equals(id))
                        .findFirst()
                        .get();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return pet;
    }

    @Override
    public Pet change(Pet pet) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

    @Override
    public Collection<Pet> getAll() {
        List<Pet> pets = new ArrayList<>();
        try {
            pets = Files.lines(path)
                    .map(deserializePet)
                    .collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
        }

        return pets;
    }

    @Override
    public boolean delete(Pet pet) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

}
