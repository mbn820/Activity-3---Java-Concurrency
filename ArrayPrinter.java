import java.util.Arrays;

public class ArrayPrinter {

    public void printArray(String[] arr) {
        Arrays.stream(arr)
              .forEach(s -> System.out.printf("|%s|", s));
    }

    public void printArray(String[][] table) {
        for(String[] elem : table) {
            printArray(elem);
        }
    }



}//endClass
