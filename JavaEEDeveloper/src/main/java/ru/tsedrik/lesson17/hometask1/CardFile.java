package ru.tsedrik.lesson17.hometask1;

import java.util.Collection;

public abstract class CardFile <I, T extends Identifiable<I>>{
    protected Storage<I, T> storage;

    public CardFile(Storage storage){
        this.storage = storage;
    }

    abstract T add(T o);
    abstract T search(I id);
    abstract T change(T t);
    abstract Collection<T> getAll();
    abstract T delete(T t);
    abstract void print();
}
