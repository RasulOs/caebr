package vector;

import matrix.IMatrix;
import matrix.StandardMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class StandardVector<T extends Number> implements IVector<T> {

    private List<Double> currentVector = new ArrayList<>();

    private int rows;

    private int columns;

    private boolean isVertical;

    public StandardVector(T[] vector) {
        Objects.requireNonNull(vector, "Vector cannot be null");
        transformToDoubleVectorList(vector);

        initializeColumnsAndRows(currentVector, true);
    }

    public StandardVector(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, "Vector cannot be null");
        transformToDoubleVectorList(vector);

        initializeColumnsAndRows(currentVector, isVertical);
    }

    private Double[] transformToDoubleVector(T[] tVector) {
        Double[] doubleVector = new Double[tVector.length];
        for (int i = 0; i < tVector.length; i++) {
            doubleVector[i] = tVector[i].doubleValue();
        }
        return doubleVector;
    }

    private void transformToDoubleVectorList(T[] vector) {
        for (T t: vector) {
            currentVector.add(t.doubleValue());
        }
    }

    private void initializeColumnsAndRows(List<Double> vector, boolean isVertical) {

        if (vector.isEmpty())
            throw new IllegalArgumentException("Vector cannot be empty");

        if (isVertical) {
            this.rows = vector.size();
            this.columns = 1;
        } else {
            this.rows = 1;
            this.columns = vector.size();
        }

        this.isVertical = isVertical;
    }

    public IVector<T> add(T[] b, boolean isVertical) {

        checkVectorForAdditionOrSubtraction(b, isVertical);

        List<Double> result = new ArrayList<>(currentVector.size());

        for (int i = 0; i < currentVector.size(); i++) {
            result.add(currentVector.get(i) + b[i].doubleValue());
        }

        currentVector = result;

        return this;
    }

    private void checkVectorForAdditionOrSubtraction(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, "Vector cannot be null");
        checkDimensions(vector, isVertical);
    }

    private void checkDimensions(T[] b, boolean isVertical) {

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format("The vectors are not the same orientation. " +
                            "First vector orientation: %s, Second vector orientation: %s", this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() > b.length) {
            throw new IllegalArgumentException(String
                    .format("The first vector is longer than the second vector. First vector length: %d, " +
                            "Second vector length: %d", currentVector.size(), b.length));
        }

        if (currentVector.size() < b.length) {
            throw new IllegalArgumentException(String.format("The second vector is longer than the first vector. " +
                    "First vector length: %d, Second vector length: %d", currentVector.size(), b.length));
        }
    }

    public IVector<T> add(T[] b) {
        return add(b, true);
    }

    public IVector<T> subtract(T[] b, boolean isVertical) {

        checkVectorForAdditionOrSubtraction(b, isVertical);

        List<Double> result = new ArrayList<>(currentVector.size());

        for (int i = 0; i < currentVector.size(); i++) {
            result.add(currentVector.get(i) - b[i].doubleValue());
        }

        currentVector = result;

        return this;
    }

    public String content() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vector has ")
                .append(rows == 1 ? "1 row" : rows + " rows")
                .append(" and ")
                .append(columns == 1 ? "1 column" : columns + " columns")
                .append(". Vector:\n[\n");

        for (int i = 0; i < currentVector.size(); i++) {
            if (isVertical)
                sb.append("\t");

            sb.append(currentVector.get(i));

            if (i != currentVector.size() - 1) {
                sb.append(", ");

                if (isVertical)
                    sb.append("\n");
            }
        };

        sb.append("\n]");

        return sb.toString();
    }

    @Override
    public String toString() {
        return "BasicVector {\n" +
                content() + "\n" +
                '}';
    }


    public IVector<T> subtract(T[] b) {
        return subtract(b, true);
    }

    @Override
    public IMatrix<Double> multiply(T[] vector) {
        return multiply(vector, true);
    }

    public IMatrix<Double> multiply(T[] vector, boolean isVertical) {
        checkVectorForMultiplication(vector, isVertical);

        double result = 0d;
        StandardMatrix<Double> matrix = null;

        if (isResultOfMultiplicationVector(vector, isVertical)) {
            for (int i = 0; i < currentVector.size(); i++) {
                result += currentVector.get(i) * vector[i].doubleValue();
            }

            currentVector.clear();
            currentVector.add(result);
            initializeColumnsAndRows(currentVector, this.isVertical);

            matrix = new StandardMatrix<>(new Double[][]{{result}});

        } else {
            Double[][] matrixResult = new Double[currentVector.size()][vector.length];


            for (int i = 0; i < currentVector.size(); i++) {
                for (int j = 0; j < vector.length; j++) {
                    matrixResult[i][j] = currentVector.get(i) * vector[j].doubleValue();
                }
            }

            matrix = new StandardMatrix<>(matrixResult);
        }

        return matrix;
    }

    @Override
    public IMatrix<Double> multiply(IVector<T> iVector) {
        return multiply((T[]) iVector.toArray(), iVector.isVertical());
    }

    @Override
    public IVector<T> multiply(T number) {

        currentVector.replaceAll(x -> x * number.doubleValue());

        return this;
    }

    private boolean isResultOfMultiplicationVector(T[] vector, boolean isVertical) {
        return !this.isVertical && isVertical;
    }


    @Override
    public IVector<T> put(T number) {

        currentVector.add(number.doubleValue());

        return this;
    }

    @Override
    public IVector<T> set(T number, int index) {

        currentVector.set(index, number.doubleValue());

        return this;
    }

    @Override
    public IVector<T> drop(int index) {

        currentVector.remove(index);

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    @Override
    public IVector<T> drop(int fromIndex, int toIndex) {

        currentVector.subList(fromIndex, toIndex + 1)
                .clear();

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    @Override
    public IVector<T> drop() {

        currentVector.remove(currentVector.size() - 1);

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    @Override
    public Double pop(int index) {
        double d = currentVector.remove(index);
        initializeColumnsAndRows(currentVector, isVertical);
        return d;
    }

    @Override
    public Double pop() {
        double d = currentVector.remove(currentVector.size() - 1);
        initializeColumnsAndRows(currentVector, isVertical);
        return d;
    }

    private void checkVectorForMultiplication(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, "Vector cannot be null");

        checkDimensionsForMultiplication(vector, isVertical);
    }

    private void checkDimensionsForMultiplication(T[] vector, boolean isVertical) {
        if (this.isVertical == isVertical) {
                    throw new IllegalArgumentException(String.format("The vectors are the same orientation. " +
                            "First vector orientation: %s, Second vector orientation: %s", this.isVertical ? "vertical" : "horizontal",
                            isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() != vector.length) {
            throw new IllegalArgumentException(String.format("The vectors are not the same length. " +
                    "First vector length: %d, Second vector length: %d", currentVector.size(), vector.length));
        }
    }


    @Override
    public IVector<T> add(IVector<T> iVector) {
        return add((T[]) ((StandardVector<T>)iVector).toArray(), ((StandardVector<T>) iVector).isVertical());
    }

    @Override
    public IVector<T> subtract(IVector<T> iVector) {
        return subtract((T[]) ((StandardVector<T>)iVector).toArray(), ((StandardVector<T>) iVector).isVertical());
    }

    @Override
    public IVector<T> transpose() {
        initializeColumnsAndRows(currentVector, !isVertical);

        return this;
    }

    @Override
    public Double sum() {
        Double sum = 0.0;

        for (Double d: currentVector) {
            sum += d;
        }
        return sum;
    }

    @Override
    public Double mean() {
        if (currentVector.size() == 0)
            return 0.0;

        Double sum = sum();
        return sum / currentVector.size();
    }

    @Override
    public Double max() {

        if (currentVector.size() == 0)
            return 0.0;

        Double max = Double.MIN_VALUE;

        for (Double d: currentVector) {
            if (d > max)
                max = d;
        }

       return max;
    }

    @Override
    public Double min() {

        if (currentVector.size() == 0)
            return 0.0;

        Double min = Double.MAX_VALUE;

        for (Double d: currentVector) {
            if (d < min)
                min = d;
        }

        return min;
    }

    @Override
    public Double median() {
        if (currentVector.size() == 0)
            return 0.0;

        List<Double> tempList = new ArrayList<>(currentVector);

        sortTemporaryList(tempList);

        if (tempList.size() % 2 == 0)
            return (tempList.get(tempList.size() / 2) + tempList.get(tempList.size() / 2 - 1)) / 2;
        else
            return tempList.get(tempList.size() / 2);
    }

    @Override
    public List<Double> mode() {
        List<Double> listOfModes = new ArrayList<>();

        HashMap<Double, Integer> map = new HashMap<>();

        Integer maxCount = 0;

        for (Double d: currentVector) {
            if (map.containsKey(d)) {
                map.put(d, map.get(d) + 1);
                if (map.get(d) > maxCount)
                    maxCount = map.get(d);
            }
            else
                map.put(d, 1);
        }

        if (maxCount == 1)
            return listOfModes;

        for (Double d: map.keySet()) {
            if (map.get(d).equals(maxCount))
                listOfModes.add(d);
        }

        return listOfModes;
    }

    @Override
    public Double variance() {

        if (currentVector.size() == 0)
            return 0.0;

        Double mean = mean();

        double sumOfSquaredDifferences = 0.0;

        for (Double d : currentVector) {
            sumOfSquaredDifferences += Math.pow(d - mean, 2);
        }

        int n = currentVector.size() >= 30 ? currentVector.size() : currentVector.size() - 1;

        return sumOfSquaredDifferences / n;
    }

    @Override
    public Double standardDeviation() {
        return Math.sqrt(variance());
    }

    @Override
    public Double range() {
        return max() - min();
    }

    @Override
    public IVector<T> sort() {

        Collections.sort(currentVector);

        return this;
    }

    private List<Double> sortTemporaryList(List<Double> tempList) {
        Collections.sort(tempList);

        return tempList;
    }

    private void swap(int i, int j) {
        Double temp = currentVector.get(i);
        currentVector.set(i, currentVector.get(j));
        currentVector.set(j, temp);
    }

    @Override
    public IVector<T> reverse() {
        Collections.reverse(currentVector);

        return this;
    }

    @Override
    public IVector<T> shuffle() {
        Collections.shuffle(currentVector);

        return this;
    }

    @Override
    public IVector<T> slice(int start, int end) {

        slice(start, end, currentVector.size());

        return this;
    }

    @Override
    public IVector<T> slice(int start) {
        slice(start, currentVector.size(), currentVector.size());

        return this;
    }

    private void slice(int start, int end, int size) {

        if (start < 0 || end < 0)
            throw new IllegalArgumentException("Start or end index cannot be negative");

        if (start > end)
            throw new IllegalArgumentException("Start index cannot be greater than end index");

        if (start > size || end > size)
            throw new IllegalArgumentException("Start or end index cannot be greater than size of vector");

        currentVector = currentVector.subList(start, end);

        initializeColumnsAndRows(currentVector, isVertical);
    }

    @Override
    public IVector<T> minMaxNormalization(long min, long max) {
        currentVector = minMaxNormalization(min, max, currentVector);

        return this;
    }

    @Override
    public IVector<T> minMaxNormalization() {
        currentVector = minMaxNormalization(0, 1, currentVector);

        return this;
    }

    private List<Double> minMaxNormalization(double min, double max, List<Double> doubleList) {

        if (doubleList.size() == 0)
            return doubleList;

        if (min > max)
            throw new IllegalArgumentException("Min cannot be greater than max");

        double minDouble = doubleList
                .stream()
                .min(Double::compareTo)
                .orElseThrow(NoSuchElementException::new);

        double maxDouble = doubleList
                .stream()
                .max(Double::compareTo)
                .orElseThrow(NoSuchElementException::new);

        return doubleList
                .stream()
                .map(d -> (d - minDouble) / (maxDouble - minDouble) * (max - min) + min)
                .collect(Collectors.toList());
    }

    @Override
    public IVector<T> zScoreStandardization() {
        currentVector = zScoreStandardizationInternal();

        return this;
    }

    @Override
    public Integer l0Norm() {
        if (currentVector.size() == 0)
            return 0;

        int count = 0;

        for (Double d: currentVector)
            if (d != 0) count++;

        return count;
    }

    @Override
    public Double l1Norm() {

        if (currentVector.size() == 0)
            return 0d;

        double absSum = 0d;

        for (Double d: currentVector)
            absSum += Math.abs(d);

        return absSum;
    }

    @Override
    public Double l2Norm() {

        if (currentVector.size() == 0)
            return 0d;

        double sumOfSquares = 0d;

        for (Double d: currentVector)
            sumOfSquares += Math.pow(d, 2);

        return Math.sqrt(sumOfSquares);
    }

    @Override
    public Double lInfinityNorm() {
        if (currentVector.size() == 0)
            return 0d;

        double absMax = Double.MIN_VALUE;

        for (Double d: currentVector)
            if (Math.abs(d) > absMax) absMax = Math.abs(d);

        return absMax;
    }

    private List<Double> zScoreStandardizationInternal() {

        if (currentVector.size() == 0)
            return currentVector;

        Double mean = mean();

        Double standardDeviation = standardDeviation();

        return currentVector
                .stream()
                .map(d -> (d - mean) / standardDeviation)
                .collect(Collectors.toList());
    }

    @Override
    public IVector<T> map(Function<Double, Double> function) {
        Objects.requireNonNull(function);

        for (int i = 0; i < currentVector.size(); i++) {
            currentVector.set(i, function.apply(currentVector.get(i)));
        }

        return this;
    }

    @Override
    public IVector<T> filter(Predicate<Double> predicate) {
        Objects.requireNonNull(predicate);

        currentVector = currentVector
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());

        if (currentVector.size() == 0)
            throw new IllegalArgumentException("Vector cannot be empty after filtering");

        initializeColumnsAndRows(currentVector, isVertical());

        return this;
    }

    @Override
    public Double reduce(BinaryOperator<Double> accumulator) {
        return reduce(0d, accumulator);
    }

    @Override
    public Double reduce(Double identity, BinaryOperator<Double> accumulator) {

        Objects.requireNonNull(accumulator);

        double result = identity;

        for (Double d: currentVector)
            result = accumulator.apply(result, d);

        return result;
    }

    @Override
    public void forEach(Consumer<Double> consumer) {
        Objects.requireNonNull(consumer);

        for (Double d: currentVector)
            consumer.accept(d);
    }

    public Double[] toArray() {
        Double[] doubleArray = new Double[currentVector.size()];

        for (int i = 0; i < currentVector.size(); i++) {
            doubleArray[i] = currentVector.get(i);
        }

        return doubleArray;
    }

    public Integer[] toIntegerArray() {
        Integer[] integerArray = new Integer[currentVector.size()];

        for (int i = 0; i < currentVector.size(); i++) {
            integerArray[i] = currentVector.get(i).intValue();
        }

        return integerArray;
    }

    public Float[] toFloatArray() {
        Float[] floatArray = new Float[currentVector.size()];

        for (int i = 0; i < currentVector.size(); i++) {
            floatArray[i] = currentVector.get(i).floatValue();
        }
        return floatArray;
    }

    public Short[] toShortArray() {
        Short[] shortArray = new Short[currentVector.size()];
        for (int i = 0; i < currentVector.size(); i++) {
            shortArray[i] = currentVector.get(i).shortValue();
        }
        return shortArray;
    }

    public Long[] toLongArray() {
        Long[] longArray = new Long[currentVector.size()];
        for (int i = 0; i < currentVector.size(); i++) {
            longArray[i] = currentVector.get(i).longValue();
        }
        return longArray;
    }

    public Byte[] toByteArray() {
        Byte[] byteArray = new Byte[currentVector.size()];
        for (int i = 0; i < currentVector.size(); i++) {
            byteArray[i] = currentVector.get(i).byteValue();
        }
        return byteArray;
    }

    public int getRowNumber() {
        return rows;
    }

    public int getColumnNumber() {
        return columns;
    }

    public boolean isVertical() {
        return isVertical;
    }
}
