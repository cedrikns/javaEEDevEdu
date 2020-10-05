package ru.tsedrik.lesson13.hometask2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader{
    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassFromFile(name);
        return defineClass(name, b, 0, b.length);
    }



    private byte[] loadClassFromFile(String fileName)  {
        String fileName1 = fileName.replace('.', File.separatorChar) + ".class";
        try {
            return Files.readAllBytes(Paths.get(fileName1));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to read file from disk");
        }
    }

//    protected Class<?> loadClass(String name, boolean resolve)
//            throws ClassNotFoundException {
//
//        synchronized (getClassLoadingLock(name)) {
//            // First, check if the class has already been loaded
//            Class<?> c = findLoadedClass(name);
//            if (c == null) {
//                long t0 = System.nanoTime();
//                try {
//                    if (parent != null) {
//                        c = parent.loadClass(name, false);
//                    } else {
//                        c = findBootstrapClassOrNull(name);
//                    }
//                } catch (ClassNotFoundException e) {
//                    // ClassNotFoundException thrown if class not found
//                    // from the non-null parent class loader
//                }
//
//                if (c == null) {
//                    // If still not found, then invoke findClass in order
//                    // to find the class.
//                    c = findClass(name);
//                }
//            }
//            if (resolve) {
//                resolveClass(c);
//            }
//            return c;
//        }
//    }
//
}
