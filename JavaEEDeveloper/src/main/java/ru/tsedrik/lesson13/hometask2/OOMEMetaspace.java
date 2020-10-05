package ru.tsedrik.lesson13.hometask2;

import java.util.ArrayList;
import java.util.List;

public class OOMEMetaspace {
    static List<ClassLoader> loaders = new ArrayList<>();

    public static void main(String[] args) throws Exception{
//        while (true) {
            CustomClassLoader cl = new CustomClassLoader();
            loaders.add(cl);
            System.out.println(loaders.size());
            try {
                Class<?> cla = cl.loadClass("ru.tsedrik.lesson13.hometask2.Test");
                Test test = (Test)cla.newInstance();
                System.out.println(cla.getClassLoader());
                test.d++;
                Class<?> cla2 = cl.loadClass("ru.tsedrik.lesson13.hometask2.Test2");
                Test test2 = (Test)cla.newInstance();
                test2.d++;
                System.out.println("One tr done");
            }catch (Exception e){
                e.printStackTrace();
            }
//        }

//        Object.class.getPackage().
//        for (Class c : Object.class.getClasses()){
//            System.out.println(c.getName());
//        }
    }

}
