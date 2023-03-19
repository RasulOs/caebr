package vector;

import matrix.IMatrix;

import java.util.*;

public class BasicVector<T extends Number> implements IVector<T> {

    private List<Double> currentVector = new ArrayList<>();
    private final int rows;
    private final int columns;

    private final boolean isVertical;

    public BasicVector(T[] vector) {
        Objects.requireNonNull(vector, "Vector cannot be null");
        transformToDoubleVectorList(vector);
        rows = vector.length;
        columns = 1;
        isVertical = true;
    }

    public BasicVector(T[] vector, int columns) {
        Objects.requireNonNull(vector, "Vector cannot be null");
        transformToDoubleVectorList(vector);
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

    private void transformToDoubleVectorList(T[] vector) {
        for (T t: vector) {
            currentVector.add(t.doubleValue());
        }
    }

    public IVector<T> add(T[] b, boolean isVertical) {

        checkVector(b, isVertical);

        List<Double> result = new ArrayList<>(currentVector.size());

        for (int i = 0; i < currentVector.size(); i++) {
            result.add(currentVector.get(i) + b[i].doubleValue());
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

        checkVector(b, isVertical);

        List<Double> result = new ArrayList<>(currentVector.size());

        for (int i = 0; i < currentVector.size(); i++) {
            result.add(currentVector.get(i) - b[i].doubleValue());
        }

        currentVector = result;

        return this;
    }

    public String content() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Vector has %d rows and %d columns. Vector:\n[\n", rows, columns));

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
    public IMatrix<T> dot(T[] vector) {
        // TODO
        return null;
    }

    public IMatrix<T> dot(T[] vector, boolean isVertical) {
        // TODO
        return null;
    }

    private void checkVectorForDot(T[] vector, boolean isVertical) {
        Objects.requireNonNull(vector, "Vector cannot be null");

        checkDimensionsForDot(vector, isVertical);
    }

    private void checkDimensionsForDot(T[] vector, boolean isVertical) {
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
        return add((T[]) ((BasicVector<T>)iVector).toArray(), ((BasicVector<T>) iVector).isVertical());
    }

    @Override
    public IVector<T> subtract(IVector<T> iVector) {
        return subtract((T[]) ((BasicVector<T>)iVector).toArray(), ((BasicVector<T>) iVector).isVertical());
    }

    @Override
    public IVector<T> transpose() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> dot(IVector<T> iVector) {
        // TODO
        return null;
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
                .toList();
    }

    @Override
    public IVector<T> zScoreStandardization() {
        currentVector = zScoreStandardizationInternal();

        return this;
    }

    private List<Double> zScoreStandardizationInternal() {

        if (currentVector.size() == 0)
            return currentVector;

        Double mean = mean();

        Double standardDeviation = standardDeviation();

        return currentVector
                .stream()
                .map(d -> (d - mean) / standardDeviation)
                .toList();

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
