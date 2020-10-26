package ru.tsedrik.lesson16.hometask1;

import java.util.Collection;

public interface Storage <I, T extends Identifiable<I>>{
    T add(T t);
    T search(I id);
    T change(T t);
    Collection<T> getAll();
    T delete(T t);
}
