package ru.tsedrik.lesson17.hometask1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage<I, T extends Identifiable<I>> implements Storage<I, T> {
    private Map<I, T> map;

    public MapStorage(){
        map = new HashMap<>();
    }

    @Override
    public T add(T t) {
        return map.put(t.getId(), t);
    }

    @Override
    public T search(I id) {
        return map.get(id);
    }

    @Override
    public T change(T t) {
        return map.put(t.getId(), t);
    }

    @Override
    public Collection<T> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public T delete(T t) {
        return map.remove(t.getId());
    }
}
