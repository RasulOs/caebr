import vector.BasicVector;
import vector.Vector;

public class Main {
    public static void main(String[] args) {

        BasicVector vector = new BasicVector(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        System.out.println(vector.toString());

        vector.add(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        System.out.println(vector.toString());

        vector.subtract(new int[] {2, 5, 0, 2003, 3123, 30, 7, 89, 91, -100});

        System.out.println(vector.toString());
    }
}
