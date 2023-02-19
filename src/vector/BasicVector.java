package vector;

import java.util.Arrays;
import java.util.Objects;

public class BasicVector {

    private int[] currentVector;
    private final int rows;
    private final int columns;

    private final boolean isVertical;

    public BasicVector(int[] vector) {
        this.currentVector = vector;
        rows = vector.length;
        columns = 1;
        isVertical = true;
    }

    public BasicVector(int[] vector, int columns) {
        this.currentVector = vector;
        this.columns = columns;
        rows = 1;
        isVertical = false;

        if (vector.length != columns) {
            throw new IllegalArgumentException(String.format("The vector length is not the same as number of columns. " +
                    "Vector length: %d, Number of columns: %d", vector.length, columns));
        }
    }

    public BasicVector add(int[] b, boolean isVertical) {

        int[] baseVector = currentVector;

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format("The vectors are not the same orientation. " +
                    "First vector orientation: %s, Second vector orientation: %s", this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (baseVector.length > b.length) {
            throw new IllegalArgumentException(String
                    .format("The first vector is longer than the second vector. First vector length: %d, " +
                            "Second vector length: %d", baseVector.length, b.length));
        }

        if (baseVector.length < b.length) {
            throw new IllegalArgumentException(String.format("The second vector is longer than the first vector. " +
                    "First vector length: %d, Second vector length: %d", baseVector.length, b.length));
        }

        int[] result = new int[baseVector.length];

        for (int i = 0; i < baseVector.length; i++) {
            result[i] = baseVector[i] + b[i];
        }

        currentVector = result;

        return this;
    }

    public BasicVector add(int[] b) {
        return add(b, true);
    }

    public BasicVector subtract(int[] b, boolean isVertical) {

        int[] baseVector = currentVector;

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format("The vectors are not the same orientation. " +
                    "First vector orientation: %s, Second vector orientation: %s", this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (baseVector.length > b.length) {
            throw new IllegalArgumentException(String
                    .format("The first vector is longer than the second vector. First vector length: %d, " +
                            "Second vector length: %d", baseVector.length, b.length));
        }

        if (baseVector.length < b.length) {
            throw new IllegalArgumentException(String.format("The second vector is longer than the first vector. " +
                    "First vector length: %d, Second vector length: %d", baseVector.length, b.length));
        }

        int[] result = new int[baseVector.length];

        for (int i = 0; i < baseVector.length; i++) {
            result[i] = baseVector[i] - b[i];
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicVector that = (BasicVector) o;
        return rows == that.rows && columns == that.columns && isVertical == that.isVertical && Arrays.equals(currentVector, that.currentVector);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, columns, isVertical);
        result = 31 * result + Arrays.hashCode(currentVector);
        return result;
    }

    public BasicVector subtract(int[] b) {
        return subtract(b, true);
    }

    public int[] getCurrentVector() {
        return currentVector;
    }

    public void setCurrentVector(int[] currentVector) {
        this.currentVector = currentVector;
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
