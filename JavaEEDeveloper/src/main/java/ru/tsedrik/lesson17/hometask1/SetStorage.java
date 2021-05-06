package ru.tsedrik.lesson17.hometask1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetStorage<I, T extends Identifiable<I>> implements Storage<I, T> {
    private Set<T> set;

    public SetStorage(){
        set = new HashSet<>();
    }

    @Override
    public T add(T t) {
        set.add(t);
        return t;
    }

    @Override
    public T search(I id) {
        T foundedEntity = set.stream().filter(e -> e.getId() == id).findFirst().get();
        return foundedEntity;
    }

    @Override
    public T change(T t) {
        T changedEntity = set.stream().filter(e -> e.getId() == t.getId()).findFirst().get();
        set.remove(changedEntity);
        set.add(t);
        return changedEntity;
    }

    @Override
    public Collection<T> getAll() {
        return new HashSet<>(set);
    }

    @Override
    public T delete(T t) {
        set.remove(t);
        return t;
    }
}
