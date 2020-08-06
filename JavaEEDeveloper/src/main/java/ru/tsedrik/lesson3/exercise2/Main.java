package ru.tsedrik.lesson3.exercise2;

public class Main {
    public static void main(String[] args) {
        try{
            String str = null;
            if(str.equals("message")){
                System.out.println(str);
            }
        } catch (NullPointerException npe){
            System.out.println("NPE");
            return;
        } catch (ArithmeticException are){
            System.out.println("ARE");
        } catch (Exception ex){
            System.out.println("EX");
        } finally {
            System.out.println("Finally");
        }
    }
}
