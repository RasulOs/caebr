package caebr.vector;

import caebr.matrix.IMatrix;
import caebr.matrix.StandardMatrix;
import caebr.util.NumberUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class StandardVector<T extends Number> implements IVector<T> {

    public static final String THE_VECTORS_ARE_NOT_THE_SAME_ORIENTATION = "The vectors are not the same orientation. ";
    public static final String FIRST_VECTOR_ORIENTATION_S_SECOND_VECTOR_ORIENTATION_S = "First vector orientation: %s, Second vector orientation: %s";
    public static final String FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D = "First vector length: %d, Second vector length: %d";
    public static final String THE_VECTORS_ARE_NOT_THE_SAME_LENGTH = "The vectors are not the same length. ";
    public static final String VECTOR_IS_EMPTY = "Vector is empty";
    public static final String INDEXES_CANNOT_BE_NEGATIVE = "Indexes cannot be negative";
    public static final String THE_SECOND_VECTOR_IS_LONGER_THAN_THE_FIRST_VECTOR = "The second vector is longer than the first vector. ";

    private List<Double> currentVector = new ArrayList<>();

    private int rows;

    private int columns;

    // Default orientation is vertical
    private boolean isVertical;

    private static final String VECTOR_CANNOT_BE_NULL = "Vector cannot be null";

    // Default epsilon value. Used for comparing doubles.
    private static double epsilon = 0.000001;

    public StandardVector(T[] vector) {
        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);
        transformToDoubleVectorList(vector);

        initializeColumnsAndRows(currentVector, true);
    }

    public StandardVector(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);
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

    public static Double[] add(Double[] a, Double[] b) {
        checkVectorForAdditionSubtractionMultiplication(a, b);

        Double[] result = new Double[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }

        return result;
    }

    private IVector<T> addInternal(Double[] b, boolean isVertical, boolean checkDimensions) {

        if (checkDimensions)
            checkVectorForAdditionOrSubtractionInternal(b, isVertical);

        List<Double> result = new ArrayList<>(currentVector.size());

        for (int i = 0; i < currentVector.size(); i++) {
            result.add(currentVector.get(i) + b[i]);
        }

        currentVector = result;

        return this;
    }

    private void checkVectorForAdditionOrSubtraction(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);
        checkDimensions(vector, isVertical);
    }

    private static void checkVectorForAdditionSubtractionMultiplication(Double[] a, Double[] b) {
        Objects.requireNonNull(a, VECTOR_CANNOT_BE_NULL);
        Objects.requireNonNull(b, VECTOR_CANNOT_BE_NULL);

        if (a.length > b.length)
            throw new IllegalArgumentException(String.format("The first vector is longer than the second vector. " +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, a.length, b.length));

        if (a.length < b.length)
            throw new IllegalArgumentException(String.format(THE_SECOND_VECTOR_IS_LONGER_THAN_THE_FIRST_VECTOR +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, a.length, b.length));

        if (a.length == 0)
            throw new IllegalArgumentException("The vectors cannot be empty");

    }

    private void checkDimensions(T[] b, boolean isVertical) {

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_ORIENTATION +
                            FIRST_VECTOR_ORIENTATION_S_SECOND_VECTOR_ORIENTATION_S, this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() > b.length) {
            throw new IllegalArgumentException(String
                    .format("The first vector is longer than the second vector. First vector length: %d, " +
                            "Second vector length: %d", currentVector.size(), b.length));
        }

        if (currentVector.size() < b.length) {
            throw new IllegalArgumentException(String.format(THE_SECOND_VECTOR_IS_LONGER_THAN_THE_FIRST_VECTOR +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, currentVector.size(), b.length));
        }
    }

    private void checkVectorForAdditionOrSubtractionInternal(Double[] vector, boolean isVertical) {

        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_ORIENTATION +
                            FIRST_VECTOR_ORIENTATION_S_SECOND_VECTOR_ORIENTATION_S, this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() > vector.length) {
            throw new IllegalArgumentException(String
                    .format("The first vector is longer than the second vector. First vector length: %d, " +
                            "Second vector length: %d", currentVector.size(), vector.length));
        }

        if (currentVector.size() < vector.length) {
            throw new IllegalArgumentException(String.format(THE_SECOND_VECTOR_IS_LONGER_THAN_THE_FIRST_VECTOR +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, currentVector.size(), vector.length));
        }
    }

    public IVector<T> add(T[] b) {
        return add(b, true);
    }

    public static Double[] add(Double[] a, double b) {
        Double[] result = new Double[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b;
        }

        return result;
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

    public static Double[] subtract(Double[] a, Double[] b) {
        checkVectorForAdditionSubtractionMultiplication(a, b);

        Double[] result = new Double[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }

        return result;
    }

    private IVector<T> subtractInternal(Double[] b, boolean isVertical, boolean checkDimensions) {

        if (checkDimensions)
            checkVectorForAdditionOrSubtractionInternal(b, isVertical);

        List<Double> result = new ArrayList<>(currentVector.size());

        for (int i = 0; i < currentVector.size(); i++) {
            result.add(currentVector.get(i) - b[i]);
        }

        currentVector = result;

        return this;
    }


    public IVector<T> subtract(T[] b) {
        return subtract(b, true);
    }

    public static Double[] subtract(Double[] a, Double b) {
        Double[] result = new Double[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b;
        }

        return result;
    }

    @Override
    public IMatrix<Double> multiply(T[] vector) {
        return multiply(vector, true);
    }

    public IMatrix<Double> multiply(T[] vector, boolean isVertical) {
        checkVectorForMultiplication(vector, isVertical);

        return multiplyInternal(transformToDoubleVector(vector), isVertical, false);
    }

    private IMatrix<Double> multiplyInternal(Double[] vector, boolean isVertical, boolean checkDimensions) {

        if (checkDimensions)
            checkDimensionsForMultiplicationInternal(vector, isVertical);

        double result = 0d;
        StandardMatrix<Double> matrix;

        if (isResultOfMultiplicationVectorInternal(vector, isVertical)) {
            for (int i = 0; i < currentVector.size(); i++) {
                result += currentVector.get(i) * vector[i];
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

    // The function assumes that first vector is vertical and second vector is horizontal
    // If you want to multiply horizontal vector by vertical vector, use the dotProduct function
    public static IMatrix<Double> multiply(Double[] a, Double[] b) {
        checkVectorForAdditionSubtractionMultiplication(a, b);

        StandardMatrix<Double> matrix;

        Double[][] matrixResult = new Double[a.length][b.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                matrixResult[i][j] = a[i] * b[j];
            }
        }

        matrix = new StandardMatrix<>(matrixResult);

        return matrix;
    }

    @Override
    public IMatrix<Double> multiply(IVector<T> iVector) {
        return multiplyInternal(iVector.toArray(), iVector.isVertical(), true);
    }

    @Override
    public IVector<T> multiply(T number) {

        currentVector.replaceAll(x -> x * number.doubleValue());

        return this;
    }

    public static Double[] multiply(Double[] a, double b) {
        Double[] result = new Double[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * b;
        }

        return result;
    }

    @Override
    public Double dotProduct(T[] vector) {
        return dotProduct(vector, true);
    }

    @Override
    public Double dotProduct(T[] vector, boolean isVertical) {

        checkDimensionsForDotProduct(vector, isVertical);

        return dotProductInternal(transformToDoubleVector(vector), isVertical, false);
    }

    @Override
    public Double dotProduct(IVector<T> iVector) {
        return dotProductInternal(iVector.toArray(), iVector.isVertical(), true);
    }

    private Double dotProductInternal(Double[] vector, boolean isVertical, boolean checkDimensions) {

        if (checkDimensions)
            checkDimensionsForDotProductInternal(vector, isVertical);

        double result = 0d;

        for (int i = 0; i < currentVector.size(); i++) {
            result += currentVector.get(i) * vector[i];
        }

        return result;
    }

    public static Double dotProduct(Double[] a, Double[] b) {
        checkVectorForAdditionSubtractionMultiplication(a, b);

        double result = 0d;

        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }

        return result;
    }

    private boolean isResultOfMultiplicationVector(T[] vector, boolean isVertical) {
        return !this.isVertical && isVertical;
    }

    private boolean isResultOfMultiplicationVectorInternal(Double[] vector, boolean isVertical) {
        return !this.isVertical && isVertical;
    }

    @Override
    public IVector<T> put(T number) {

        currentVector.add(number.doubleValue());

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    @Override
    public IVector<T> put(T[] numbers) {

        for (T number : numbers)
            currentVector.add(number.doubleValue());

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    public static Double[] put(Double[] toVector, Double[] fromVector) {

        List<Double> result = new ArrayList<>(Arrays.asList(toVector));

        result.addAll(Arrays.asList(fromVector));

        return result.toArray(new Double[0]);
    }


    @Override
    public IVector<T> set(T number, int index) {

        checkIndexes(index, index);

        currentVector.set(index, number.doubleValue());

        return this;
    }

    private void checkIndexes(int firstIndex, int secondIndex) {

        if (firstIndex < 0 || secondIndex < 0)
            throw new IllegalArgumentException(INDEXES_CANNOT_BE_NEGATIVE);

        if (firstIndex > secondIndex)
            throw new IllegalArgumentException("First index cannot be greater than second index");

        if (firstIndex > currentVector.size() - 1 || secondIndex > currentVector.size() - 1)
            throw new IllegalArgumentException("Indexes cannot be greater or equal to a vector size");
    }


    private void checkIndexesFromTo(int fromIndex, int toIndex) {

        if (fromIndex < 0 || toIndex < 0)
            throw new IllegalArgumentException(INDEXES_CANNOT_BE_NEGATIVE);

        if (fromIndex > toIndex)
            throw new IllegalArgumentException("From index cannot be greater than to index");

        if (fromIndex > currentVector.size() - 1)
            throw new IllegalArgumentException("From index cannot be greater or equal to a vector size");
    }

    private static void checkIndexes(Double[] vector, int firstIndex, int secondIndex) {

            if (firstIndex < 0 || secondIndex < 0)
                throw new IllegalArgumentException(INDEXES_CANNOT_BE_NEGATIVE);

            if (firstIndex > secondIndex)
                throw new IllegalArgumentException("First index cannot be greater than second index");

            if (firstIndex > vector.length - 1 || secondIndex > vector.length - 1)
                throw new IllegalArgumentException("Indexes cannot be greater or equal to a vector size");
    }

    private static void checkIndexesFromTo(Double[] vector, int fromIndex, int toIndex) {

        if (fromIndex < 0 || toIndex < 0)
            throw new IllegalArgumentException(INDEXES_CANNOT_BE_NEGATIVE);

        if (fromIndex > toIndex)
            throw new IllegalArgumentException("From index cannot be greater than to index");

        if (fromIndex > vector.length - 1)
            throw new IllegalArgumentException("From index cannot be greater or equal to a vector size");
    }


    @Override
    public IVector<T> drop(int index) {

        checkIndexes(index, index);

        currentVector.remove(index);

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    public static Double[] drop(Double[] vector, int index) {

        return drop(vector, index, index + 1);
    }

    @Override
    public IVector<T> drop(int fromIndex, int toIndex) {

        checkIndexesFromTo(fromIndex, toIndex);

        currentVector.subList(fromIndex, toIndex)
                .clear();

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    public static Double[] drop(Double[] vector, int fromIndex, int toIndex) {

        checkIndexesFromTo(vector, fromIndex, toIndex);

        List<Double> result = new ArrayList<>(Arrays.asList(vector));

        result.subList(fromIndex, toIndex)
                .clear();

        return result.toArray(new Double[0]);
    }

    @Override
    public IVector<T> drop() {

        if (currentVector.isEmpty())
            throw new IllegalArgumentException(VECTOR_IS_EMPTY);

        currentVector.remove(currentVector.size() - 1);

        initializeColumnsAndRows(currentVector, isVertical);

        return this;
    }

    public static Double[] drop(Double[] vector) {

        if (vector.length == 0)
            throw new IllegalArgumentException(VECTOR_IS_EMPTY);

        List<Double> result = new ArrayList<>(Arrays.asList(vector));

        result.remove(result.size() - 1);

        return result.toArray(new Double[0]);
    }

    @Override
    public Double pop(int index) {

        checkIndexes(index, index);

        double d = currentVector.remove(index);
        initializeColumnsAndRows(currentVector, isVertical);
        return d;
    }

    @Override
    public Double pop() {

        if (currentVector.isEmpty())
            throw new IllegalArgumentException(VECTOR_IS_EMPTY);

        double d = currentVector.remove(currentVector.size() - 1);
        initializeColumnsAndRows(currentVector, isVertical);
        return d;
    }

    private void checkVectorForMultiplication(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);

        checkDimensionsForMultiplication(vector, isVertical);
    }

    private void checkDimensionsForMultiplication(T[] vector, boolean isVertical) {
        if (this.isVertical == isVertical) {
                    throw new IllegalArgumentException(String.format("The vectors are the same orientation. " +
                                    FIRST_VECTOR_ORIENTATION_S_SECOND_VECTOR_ORIENTATION_S, this.isVertical ? "vertical" : "horizontal",
                            isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() != vector.length) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_LENGTH +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, currentVector.size(), vector.length));
        }
    }

    private void checkDimensionsForMultiplicationInternal(Double[] vector, boolean isVertical) {

        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);

        if (this.isVertical == isVertical) {
            throw new IllegalArgumentException(String.format("The vectors are the same orientation. " +
                            FIRST_VECTOR_ORIENTATION_S_SECOND_VECTOR_ORIENTATION_S, this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() != vector.length) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_LENGTH +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, currentVector.size(), vector.length));
        }
    }

    private static void checkDimensionsForMultiplicationInternal(Double[] a, Double[] b) {
        Objects.requireNonNull(a, VECTOR_CANNOT_BE_NULL);
        Objects.requireNonNull(b, VECTOR_CANNOT_BE_NULL);

        if (a.length != b.length) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_LENGTH +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, a.length, b.length));
        }
    }

    private void checkDimensionsForDotProduct(T[] vector, boolean isVertical) {

        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_ORIENTATION +
                            FIRST_VECTOR_ORIENTATION_S_SECOND_VECTOR_ORIENTATION_S, this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() != vector.length) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_LENGTH +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, currentVector.size(), vector.length));
        }
    }

    private void checkDimensionsForDotProductInternal(Double[] vector, boolean isVertical) {

        Objects.requireNonNull(vector, VECTOR_CANNOT_BE_NULL);

        if (this.isVertical != isVertical) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_ORIENTATION +
                            FIRST_VECTOR_ORIENTATION_S_SECOND_VECTOR_ORIENTATION_S, this.isVertical ? "vertical" : "horizontal",
                    isVertical ? "vertical" : "horizontal"));
        }

        if (currentVector.size() != vector.length) {
            throw new IllegalArgumentException(String.format(THE_VECTORS_ARE_NOT_THE_SAME_LENGTH +
                    FIRST_VECTOR_LENGTH_D_SECOND_VECTOR_LENGTH_D, currentVector.size(), vector.length));
        }
    }

    @Override
    public IVector<T> add(IVector<T> iVector) {
        return addInternal(iVector.toArray(), iVector.isVertical(), true);
    }

    @Override
    public IVector<T> add(T number) {
        currentVector.replaceAll(x -> x + number.doubleValue());

        return this;
    }

    @Override
    public IVector<T> subtract(IVector<T> iVector) {
        return subtractInternal(iVector.toArray(), iVector.isVertical(), true);
    }

    @Override
    public IVector<T> subtract(T number) {
        currentVector.replaceAll(x -> x - number.doubleValue());

        return this;
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

    public static Double sum(Double[] vector) {
        Double sum = 0.0;

        for (Double d: vector) {
            sum += d;
        }
        return sum;
    }

    @Override
    public Double mean() {
        if (currentVector.isEmpty())
            return 0.0;

        Double sum = sum();
        return sum / currentVector.size();
    }

    public static Double mean(Double[] vector) {
        if (vector.length == 0)
            return 0.0;

        Double sum = sum(vector);
        return sum / vector.length;
    }

    @Override
    public Double max() {

        if (currentVector.isEmpty())
            return 0.0;

        Double max = Double.MIN_VALUE;

        for (Double d: currentVector) {
            if (d > max)
                max = d;
        }

       return max;
    }

    public static Double max(Double[] vector) {

        if (vector.length == 0)
            return 0.0;

        Double max = Double.MIN_VALUE;

        for (Double d: vector) {
            if (d > max)
                max = d;
        }

        return max;
    }

    @Override
    public Double min() {

        if (currentVector.isEmpty())
            return 0.0;

        Double min = Double.MAX_VALUE;

        for (Double d: currentVector) {
            if (d < min)
                min = d;
        }

        return min;
    }

    public static Double min(Double[] vector) {

        if (vector.length == 0)
            return 0.0;

        Double min = Double.MAX_VALUE;

        for (Double d: vector) {
            if (d < min)
                min = d;
        }

        return min;
    }

    @Override
    public Double median() {
        if (currentVector.isEmpty())
            return 0.0;

        List<Double> tempList = new ArrayList<>(currentVector);

        sortTemporaryList(tempList);

        if (tempList.size() % 2 == 0)
            return (tempList.get(tempList.size() / 2) + tempList.get(tempList.size() / 2 - 1)) / 2;
        else
            return tempList.get(tempList.size() / 2);
    }

    public static Double median(Double[] vector) {
        if (vector.length == 0)
            return 0.0;

        List<Double> tempList = new ArrayList<>(Arrays.asList(vector));

        Collections.sort(tempList);

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

    public static List<Double> mode(Double[] vector) {
        List<Double> listOfModes = new ArrayList<>();

        HashMap<Double, Integer> map = new HashMap<>();

        Integer maxCount = 0;

        for (Double d: vector) {
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

        if (currentVector.isEmpty())
            return 0.0;

        Double mean = mean();

        double sumOfSquaredDifferences = 0.0;

        for (Double d : currentVector) {
            sumOfSquaredDifferences += Math.pow(d - mean, 2);
        }

        int n = currentVector.size() >= 30 ? currentVector.size() : currentVector.size() - 1;

        return sumOfSquaredDifferences / n;
    }

    public static Double variance(Double[] vector) {

        if (vector.length == 0)
            return 0.0;

        Double mean = mean(vector);

        double sumOfSquaredDifferences = 0.0;

        for (Double d : vector) {
            sumOfSquaredDifferences += Math.pow(d - mean, 2);
        }

        int n = vector.length >= 30 ? vector.length : vector.length - 1;

        return sumOfSquaredDifferences / n;
    }

    @Override
    public Double standardDeviation() {
        return Math.sqrt(variance());
    }

    public static Double standardDeviation(Double[] vector) {
        return Math.sqrt(variance(vector));
    }

    @Override
    public List<Double> distinct() {
        if (currentVector.isEmpty())
            return new ArrayList<>();

        Set<Double> set = new LinkedHashSet<>();

        for (int i = 0; i < currentVector.size(); i++)
            set.add(this.currentVector.get(i));

        return new ArrayList<>(set);
    }

    public static List<Double> distinct(Double[] vector) {
        if (vector.length == 0)
            return new ArrayList<>();

        Set<Double> set = new LinkedHashSet<>(Arrays.asList(vector));

        return new ArrayList<>(set);
    }

    @Override
    public Double range() {
        return max() - min();
    }

    public static Double range(Double[] vector) {
        return max(vector) - min(vector);
    }

    @Override
    public IVector<T> sort() {
        return sort(true);
    }

    public static Double[] sort(Double[] vector) {
        return sort(vector, true);
    }

    @Override
    public IVector<T> sort(boolean ascending) {

        if (ascending)
            Collections.sort(currentVector);
        else
            Collections.sort(currentVector, Collections.reverseOrder());

        return this;
    }

    public static Double[] sort(Double[] vector, boolean ascending) {

        if (ascending)
            Arrays.sort(vector);
        else
            Arrays.sort(vector, Collections.reverseOrder());

        return vector;
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

    public static Double[] reverse(Double[] vector) {
        Collections.reverse(Arrays.asList(vector));

        return vector;
    }

    @Override
    public IVector<T> shuffle() {
        Collections.shuffle(currentVector);

        return this;
    }

    public static Double[] shuffle(Double[] vector) {
        Collections.shuffle(Arrays.asList(vector));

        return vector;
    }

    @Override
    public IVector<T> slice(int start, int end) {

        slice(start, end, currentVector.size());

        return this;
    }

    public static Double[] slice(Double[] vector, int start, int end) {

        checkIndexesFromTo(vector, start, end);

        List<Double> result = new ArrayList<>(Arrays.asList(vector));

        result = result.subList(start, end);

        return result.toArray(new Double[0]);
    }

    @Override
    public IVector<T> slice(int start) {
        slice(start, currentVector.size(), currentVector.size());

        return this;
    }

    public static Double[] slice(Double[] vector, int start) {
        return slice(vector, start, vector.length);
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

    public static Double[] minMaxNormalization(Double[] vector, double min, double max) {

        return minMaxNormalization(min, max, Arrays.asList(vector))
                .toArray(new Double[0]);
    }

    @Override
    public IVector<T> minMaxNormalization() {
        currentVector = minMaxNormalization(0, 1, currentVector);

        return this;
    }

    private static List<Double> minMaxNormalization(double min, double max, List<Double> doubleList) {

        if (doubleList.isEmpty())
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

    public static Double[] zScoreStandardization(Double[] vector) {
        return zScoreStandardizationInternal(Arrays.asList(vector))
                .toArray(new Double[0]);
    }

    @Override
    public Integer l0Norm() {
        if (currentVector.isEmpty())
            return 0;

        int count = 0;

        for (Double d: currentVector)
            if (!NumberUtils.approximatelyZero(d, epsilon)) count++;

        return count;
    }

    public static Integer l0Norm(Double[] vector) {
        if (vector.length == 0)
            return 0;

        int count = 0;

        for (Double d: vector)
            if (!NumberUtils.approximatelyZero(d, epsilon)) count++;

        return count;
    }

    @Override
    public Double l1Norm() {

        if (currentVector.isEmpty())
            return 0d;

        double absSum = 0d;

        for (Double d: currentVector)
            absSum += Math.abs(d);

        return absSum;
    }

    public static Double l1Norm(Double[] vector) {

        if (vector.length == 0)
            return 0d;

        double absSum = 0d;

        for (Double d: vector)
            absSum += Math.abs(d);

        return absSum;
    }

    @Override
    public Double l2Norm() {

        if (currentVector.isEmpty())
            return 0d;

        double sumOfSquares = 0d;

        for (Double d: currentVector)
            sumOfSquares += Math.pow(d, 2);

        return Math.sqrt(sumOfSquares);
    }

    public static Double l2Norm(Double[] vector) {

        if (vector.length == 0)
            return 0d;

        double sumOfSquares = 0d;

        for (Double d: vector)
            sumOfSquares += Math.pow(d, 2);

        return Math.sqrt(sumOfSquares);
    }

    @Override
    public Double lInfinityNorm() {
        if (currentVector.isEmpty())
            return 0d;

        double absMax = Double.MIN_VALUE;

        for (Double d: currentVector)
            if (Math.abs(d) > absMax) absMax = Math.abs(d);

        return absMax;
    }

    public static Double lInfinityNorm(Double[] vector) {
        if (vector.length == 0)
            return 0d;

        double absMax = Double.MIN_VALUE;

        for (Double d: vector)
            if (Math.abs(d) > absMax) absMax = Math.abs(d);

        return absMax;
    }

    private List<Double> zScoreStandardizationInternal() {
        return zScoreStandardizationInternal(currentVector);
    }

    private static List<Double> zScoreStandardizationInternal(List<Double> vector) {

        if (vector.isEmpty())
            return vector;

        Double[] doubleArray = vector.toArray(new Double[0]);

        Double mean = StandardVector.mean(doubleArray);

        Double standardDeviation = standardDeviation(doubleArray);

        return vector
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

    public static Double[] map(Double[] vector, Function<Double, Double> function) {
        Objects.requireNonNull(function);

        for (int i = 0; i < vector.length; i++) {
            vector[i] = function.apply(vector[i]);
        }

        return vector;
    }

    @Override
    public IVector<T> filter(Predicate<Double> predicate) {
        Objects.requireNonNull(predicate);

        currentVector = currentVector
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());

        if (currentVector.isEmpty())
            throw new IllegalArgumentException("Vector cannot be empty after filtering");

        initializeColumnsAndRows(currentVector, isVertical());

        return this;
    }

    public static Double[] filter(Double[] vector, Predicate<Double> predicate) {
        Objects.requireNonNull(predicate);

        List<Double> result = Arrays.stream(vector)
                .filter(predicate)
                .collect(Collectors.toList());

        if (result.isEmpty())
            throw new IllegalArgumentException("Vector cannot be empty after filtering");

        return result.toArray(new Double[0]);
    }

    @Override
    public Double reduce(BinaryOperator<Double> accumulator) {
        return reduce(0d, accumulator);
    }

    public static Double reduce(Double[] vector, BinaryOperator<Double> accumulator) {
        return reduce(vector, 0d, accumulator);
    }

    @Override
    public Double reduce(Double identity, BinaryOperator<Double> accumulator) {

        Objects.requireNonNull(accumulator);

        double result = identity;

        for (Double d: currentVector)
            result = accumulator.apply(result, d);

        return result;
    }
    public static Double reduce(Double[] vector, Double identity, BinaryOperator<Double> accumulator) {

        Objects.requireNonNull(accumulator);

        double result = identity;

        for (Double d: vector)
            result = accumulator.apply(result, d);

        return result;
    }

    @Override
    public void forEach(Consumer<Double> consumer) {
        Objects.requireNonNull(consumer);

        for (Double d: currentVector)
            consumer.accept(d);
    }

    public static void forEach(Double[] vector, Consumer<Double> consumer) {
        Objects.requireNonNull(consumer);

        for (Double d: vector)
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
        }

        sb.append("\n]");

        return sb.toString();
    }

    @Override
    public String toString() {
        return "StandardVector {\n" +
                content() + "\n" +
                '}';
    }

    public static void setEpsilon(double epsilon) {
        StandardVector.epsilon = epsilon;
    }

    public static double getEpsilon() {
        return StandardVector.epsilon;
    }
}
