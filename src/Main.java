import vector.BasicVector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        BasicVector<Integer> vector = new BasicVector<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        System.out.println(vector.toString());

        vector.add(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        System.out.println(vector.toString());

        vector.subtract(new Integer[] {2, 6, 0, 2003, 3123, 30, 7, 89, 91, -100});

        System.out.println(vector.toString());

        System.out.println(Arrays.toString(vector.toArray()));
        System.out.println(Arrays.toString(vector.toIntegerArray()));
        System.out.println(Arrays.toString(vector.toLongArray()));
        System.out.println(Arrays.toString(vector.toShortArray()));
        System.out.println(Arrays.toString(vector.toByteArray()));
    }
}
