package ru.tsedrik.lesson16.hometask1;

import java.io.File;
import java.util.Collection;

public class FileStorage<I, T extends Identifiable<I>> implements Storage<I, T>{
    private File file;

    public FileStorage(String fileName){
        file = new File(fileName);
    }

    @Override
    public T add(T t) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

    @Override
    public T search(I id) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

    @Override
    public T change(T t) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

    @Override
    public Collection<T> getAll() {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }

    @Override
    public T delete(T t) {
        throw new UnsupportedOperationException("Not implemented, yet!");
    }
}
