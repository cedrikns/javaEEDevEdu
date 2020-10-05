package ru.tsedrik.lesson13.hometask2;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class OOMEMetaspace {
    public static void main(String[] args) throws Exception{
        //package where to compile SomeClass.java
        String workerClassPackage = Worker.class.getPackage().getName();
        System.out.println("workerClassPackage = " + workerClassPackage);

        //create SomeClass.java file
        File file = new File("SomeClass");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file.getName() + ".java"))){

            bw.write("package " + workerClassPackage + ";");
            bw.newLine();
            bw.write("public class SomeClass implements Worker{");
            bw.newLine();
            bw.write("\tstatic{System.out.println(\"In static init section\");}");
            bw.newLine();
            bw.write("\tpublic void doWork(){");
            bw.newLine();
            bw.write("\t\tSystem.out.println(\"In doWork method.\");");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.write("}");
            bw.newLine();
        } catch (IOException e){
            e.printStackTrace();
        }

        //compile SomeClass.java file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, file.getPath() + ".java");

        //copy compiled SomeClass.class into the correct destination
        Path fileForCopy = Paths.get(file.getPath() + ".class");
        System.out.println("fileForCopy = " + fileForCopy);
        Path workerPath = Paths.get(Worker.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        System.out.println("workerPath = " + workerPath);
        String workerPackagePath = workerClassPackage.replace('.', File.separatorChar);
        System.out.println("workerPackagePath = " + workerPackagePath);

        try {
            Files.move(fileForCopy, Paths.get(workerPath.toString(), workerPackagePath, fileForCopy.toString()), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }

        CustomClassLoader cl = new CustomClassLoader();
        try {
            //load SomeClass.class
            Class<?> someClass = cl.loadClass(workerClassPackage + "." + file.getPath());
            //create instance of loaded class and invoke method 'doWork()'
            Worker worker = (Worker) someClass.newInstance();
            worker.doWork();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

