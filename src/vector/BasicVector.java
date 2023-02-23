package vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BasicVector<T extends Number> extends Vector<T> {

    private Double[] currentVector;
    private final int rows;
    private final int columns;

    private final boolean isVertical;

    public BasicVector(T[] vector) {
        currentVector = transformToDoubleVector(vector);
        rows = vector.length;
        columns = 1;
        isVertical = true;
    }

    public BasicVector(T[] vector, int columns) {
        currentVector = transformToDoubleVector(vector);
        this.columns = columns;
        rows = 1;
        isVertical = false;

        if (vector.length != columns) {
            throw new IllegalArgumentException(String.format("The vector length is not the same as number of columns. " +
                    "Vector length: %d, Number of columns: %d", vector.length, columns));
        }
    }

    private Double[] transformToDoubleVector(T[] tVector) {
        Double[] doubleVector = new Double[tVector.length];
        for (int i = 0; i < tVector.length; i++) {
            doubleVector[i] = tVector[i].doubleValue();
        }
        return doubleVector;
    }

    public Vector<T> add(T[] b, boolean isVertical) {

        Double[] baseVector = currentVector;

        checkVector(b, isVertical);

        Double[] result = new Double[baseVector.length];

        for (int i = 0; i < baseVector.length; i++) {
            result[i] = baseVector[i] + b[i].doubleValue();
        }

        currentVector = result;

        return this;
    }

    private void checkVector(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, "Vector cannot be null");
        checkDimensions(vector, isVertical);
    }

    private void checkDimensions(T[] b, boolean isVertical) {

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format("The vectors are not the same orientation. " +
                            "First vector orientation: %s, Second vector orientation: %s", this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.length > b.length) {
            throw new IllegalArgumentException(String
                    .format("The first vector is longer than the second vector. First vector length: %d, " +
                            "Second vector length: %d", currentVector.length, b.length));
        }

        if (currentVector.length < b.length) {
            throw new IllegalArgumentException(String.format("The second vector is longer than the first vector. " +
                    "First vector length: %d, Second vector length: %d", currentVector.length, b.length));
        }
    }

    public Vector<T> add(T[] b) {
        return add(b, true);
    }

    public Vector<T> subtract(T[] b, boolean isVertical) {

        Double[] baseVector = currentVector;

        checkVector(b, isVertical);

        Double[] result = new Double[baseVector.length];

        for (int i = 0; i < baseVector.length; i++) {
            result[i] = baseVector[i] - b[i].doubleValue();
        }

        currentVector = result;

        return this;
    }

    public String content() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Vector has %d rows and %d columns. Vector:\n", rows, columns));

        for (int i = 0; i < currentVector.length; i++) {
            if (isVertical)
                sb.append("\t");

            sb.append(currentVector[i]);

            if (i != currentVector.length - 1) {
                sb.append(", ");

                if (isVertical)
                    sb.append("\n");
            }
        };

        return sb.toString();
    }

    @Override
    public String toString() {
        return "BasicVector{\n" +
                content() + "\n" +
                '}';
    }


    public Vector<T> subtract(T[] b) {
        return subtract(b, true);
    }

    @Override
    Vector<T> dot(T[] vector) {
        // TODO
        return null;
    }


    @Override
    Vector<T> add(Vector<T> vector) {
        return add(((BasicVector<T>)vector).getCurrentVector(), ((BasicVector<T>)vector).isVertical());
    }

    @Override
    Vector<T> subtract(Vector<T> vector) {
        return subtract(((BasicVector<T>)vector).getCurrentVector(), ((BasicVector<T>)vector).isVertical());
    }

    @Override
    Vector<T> transpose() {
        // TODO
        return null;
    }

    @Override
    Vector<T> dot(Vector<T> vector) {
        // TODO
        return null;
    }

    @Override
    Vector<T> inverse() {
        // TODO
        return null;
    }

    public T[] getCurrentVector() {
        return (T[]) currentVector;
    }

    public Double[] toArray() {
        return currentVector;
    }

    public Integer[] toIntegerArray() {
        Integer[] integerArray = new Integer[currentVector.length];
        for (int i = 0; i < currentVector.length; i++) {
            integerArray[i] = currentVector[i].intValue();
        }
        return integerArray;
    }

    public Float[] toFloatArray() {
        Float[] floatArray = new Float[currentVector.length];
        for (int i = 0; i < currentVector.length; i++) {
            floatArray[i] = currentVector[i].floatValue();
        }
        return floatArray;
    }

    public Short[] toShortArray() {
        Short[] shortArray = new Short[currentVector.length];
        for (int i = 0; i < currentVector.length; i++) {
            shortArray[i] = currentVector[i].shortValue();
        }
        return shortArray;
    }

    public Long[] toLongArray() {
        Long[] longArray = new Long[currentVector.length];
        for (int i = 0; i < currentVector.length; i++) {
            longArray[i] = currentVector[i].longValue();
        }
        return longArray;
    }

    public Byte[] toByteArray() {
        Byte[] byteArray = new Byte[currentVector.length];
        for (int i = 0; i < currentVector.length; i++) {
            byteArray[i] = currentVector[i].byteValue();
        }
        return byteArray;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isVertical() {
        return isVertical;
    }
}
