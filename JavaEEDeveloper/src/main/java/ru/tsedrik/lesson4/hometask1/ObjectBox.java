package ru.tsedrik.lesson4.hometask1;

import java.util.ArrayList;
import java.util.Collection;

public class ObjectBox {
    Collection objects;

    public ObjectBox() {
        this.objects = new ArrayList<>();
    }

    public void addObject(Object o){
        objects.add(o);
    }

    public void deleteObject(Object o){
        if (objects.contains(o)){
            objects.remove(o);
        }
    }

    public void dump(){
        System.out.println(objects.toString());
    }
}
