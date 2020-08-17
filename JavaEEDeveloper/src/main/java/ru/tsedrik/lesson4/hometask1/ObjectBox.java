package ru.tsedrik.lesson4.hometask1;

import java.util.ArrayList;
import java.util.Collection;

public class ObjectBox<T> {
    Collection<T> objects;

    public ObjectBox() {
        this.objects = new ArrayList<>();
    }

    public void addObject(T o){
        objects.add(o);
    }

    public void deleteObject(T o){
        if (objects.contains(o)){
            objects.remove(o);
        }
    }

    public void dump(){
        System.out.println(objects.toString());
    }
}
