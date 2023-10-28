package caebr.statistics;

import caebr.util.NumberUtils;

import java.util.*;
import java.util.stream.Collectors;

import static caebr.matrix.StandardMatrix.toArray;

public class Stats {

    private Stats() {}


    // Methods for a Vector

    public static Double sum(List<Double> currentVector) {
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

    public static Double mean(List<Double> currentVector) {
        if (currentVector.isEmpty())
            return 0.0;

        Double sum = sum(currentVector);
        return sum / currentVector.size();
    }

    public static Double mean(Double[] vector) {
        if (vector.length == 0)
            return 0.0;

        Double sum = sum(vector);
        return sum / vector.length;
    }

    public static Double max(List<Double> currentVector) {

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

    public static Double min(List<Double> currentVector) {

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

    public static Double median(List<Double> currentVector) {
        if (currentVector.isEmpty())
            return 0.0;

        List<Double> tempList = new ArrayList<>(currentVector);

        // TODO
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

    public static List<Double> mode(List<Double> currentVector) {
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

    public static Double variance(List<Double> currentVector) {

        if (currentVector.isEmpty())
            return 0.0;

        Double mean = mean(currentVector);

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

    public static Double standardDeviation(List<Double> currentVector) {
        return Math.sqrt(variance(currentVector));
    }

    public static Double standardDeviation(Double[] vector) {
        return Math.sqrt(variance(vector));
    }

    public static List<Double> distinct(List<Double> currentVector) {
        if (currentVector.isEmpty())
            return new ArrayList<>();

        Set<Double> set = new LinkedHashSet<>();

        for (int i = 0; i < currentVector.size(); i++)
            set.add(currentVector.get(i));

        return new ArrayList<>(set);
    }

    public static List<Double> distinct(Double[] vector) {
        if (vector.length == 0)
            return new ArrayList<>();

        Set<Double> set = new LinkedHashSet<>(Arrays.asList(vector));

        return new ArrayList<>(set);
    }

    public static Double range(List<Double> currentVector) {
        return max(currentVector) - min(currentVector);
    }

    public static Double range(Double[] vector) {
        return max(vector) - min(vector);
    }

    public static Double[] minMaxNormalization(Double[] vector, double min, double max) {

        return minMaxNormalization(min, max, Arrays.asList(vector))
                .toArray(new Double[0]);
    }

    public static List<Double> minMaxNormalization(double min, double max, List<Double> doubleList) {

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

    public static Integer l0Norm(List<Double> currentVector, double epsilon) {
        if (currentVector.isEmpty())
            return 0;

        int count = 0;

        for (Double d: currentVector)
            if (!NumberUtils.approximatelyZero(d, epsilon)) count++;

        return count;
    }

    public static Integer l0Norm(Double[] vector, double epsilon) {
        if (vector.length == 0)
            return 0;

        int count = 0;

        for (Double d: vector)
            if (!NumberUtils.approximatelyZero(d, epsilon)) count++;

        return count;
    }

    public static Double l1Norm(List<Double> currentVector) {

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

    public static Double l2Norm(List<Double> currentVector) {

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

    public static Double lInfinityNorm(List<Double> currentVector) {
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

    public static Double[] zScoreStandardization(Double[] vector) {
        return zScoreStandardizationInternal(Arrays.asList(vector))
                .toArray(new Double[0]);
    }

    private static List<Double> sortTemporaryList(List<Double> tempList) {
        Collections.sort(tempList);

        return tempList;
    }

    private static List<Double> zScoreStandardizationInternal(List<Double> vector) {

        if (vector.isEmpty())
            return vector;

        Double[] doubleArray = vector.toArray(new Double[0]);

        Double mean = Stats.mean(doubleArray);

        Double standardDeviation = standardDeviation(doubleArray);

        return vector
                .stream()
                .map(d -> (d - mean) / standardDeviation)
                .collect(Collectors.toList());
    }

    // Methods for a Matrix

    public static Double sum(Double[][] matrix) {
        return sum(matrix, 0, matrix[0].length);
    }

    public static Double sum(Double[][] matrix, int column) {
        return sum(matrix, column, column + 1);
    }

    public static Double sum(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        Double sum = 0.0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = fromColumn; j < toColumn; j++) {
                sum += matrix[i][j];
            }
        }

        return sum;
    }

    private static void checkColumnIndexes(Double[][] matrix, int fromColumn, int toColumn) {
        if (fromColumn < 0 || toColumn < 0)
            throw new IllegalArgumentException("Column indexes cannot be negative");

        if (fromColumn > toColumn)
            throw new IllegalArgumentException("fromColumn index cannot be greater than toColumn index");

        if (fromColumn > matrix[0].length || toColumn > matrix[0].length)
            throw new IllegalArgumentException("Column indexes cannot be greater than the number of columns");
    }

    private static void checkColumnIndex(Double[][] matrix, int column) {
        if (column < 0)
            throw new IllegalArgumentException("Column index cannot be negative");

        if (column >= matrix[0].length)
            throw new IllegalArgumentException("Column index cannot be greater than the number of columns");
    }

    public static Double mean(Double[][] matrix) {
        return mean(matrix, 0, matrix[0].length);
    }

    public static Double mean(Double[][] matrix, int column) {
        return mean(matrix, column, column + 1);
    }

    public static Double mean(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        return sum(matrix, fromColumn, toColumn) / (matrix.length * (toColumn - fromColumn));
    }

    public static Double max(Double[][] matrix) {
        return max(matrix, 0, matrix[0].length);
    }

    public static Double max(Double[][] matrix, int column) {
        return max(matrix, column, column + 1);
    }

    public static Double max(Double[][] matrix, int fromColumn, int toColumn) {
        checkColumnIndexes(matrix, fromColumn, toColumn);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        Double max = Double.MIN_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = fromColumn; j < toColumn; j++) {
                if (matrix[i][j] > max)
                    max = matrix[i][j];
            }
        }

        return max;
    }

    public static Double min(Double[][] matrix) {
        return min(matrix, 0, matrix[0].length);
    }

    public static Double min(Double[][] matrix, int column) {
        return min(matrix, column, column + 1);
    }

    public static Double min(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        Double min = Double.MAX_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = fromColumn; j < toColumn; j++) {
                if (matrix[i][j] < min)
                    min = matrix[i][j];
            }
        }

        return min;
    }

    public static List<Double> mode(Double[][] matrix) {
        return mode(matrix, 0, matrix[0].length);
    }

    public static List<Double> mode(Double[][] matrix, int column) {
        return mode(matrix, column, column + 1);
    }

    public static List<Double> mode(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        List<Double> listOfModes = new ArrayList<>();

        HashMap<Double, Integer> map = new HashMap<>();

        Integer maxCount = 0;

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < matrix.length; j++) {

                Double d = matrix[j][i];

                if (map.containsKey(d)) {
                    map.put(d, map.get(d) + 1);
                    if (map.get(d) > maxCount)
                        maxCount = map.get(d);
                }
                else
                    map.put(d, 1);
            }
        }

        if (maxCount == 1)
            return listOfModes;

        for (Double d: map.keySet()) {
            if (map.get(d).equals(maxCount))
                listOfModes.add(d);
        }

        return listOfModes;
    }

    public static Double variance(Double[][] matrix) {
        return variance(matrix, 0, matrix[0].length);
    }

    public static Double variance(Double[][] matrix, int column) {
        return variance(matrix, column, column + 1);
    }

    public static Double variance(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        Double mean = mean(matrix, fromColumn, toColumn);

        double sumOfSquaredDifferences = 0.0;

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Double d = matrix[j][i];
                sumOfSquaredDifferences += Math.pow(d - mean, 2);
            }
        }

        int n = matrix.length * (toColumn - fromColumn);

        n = n >= 30 ? n : n - 1;

        return sumOfSquaredDifferences / n;
    }

    public static Double standardDeviation(Double[][] matrix) {
        return standardDeviation(matrix, 0, matrix[0].length);
    }

    public static Double standardDeviation(Double[][] matrix, int column) {
        return standardDeviation(matrix, column, column + 1);
    }

    public static Double standardDeviation(Double[][] matrix, int fromColumn, int toColumn) {
        return Math.sqrt(variance(matrix, fromColumn, toColumn));
    }

    public static Double range(Double[][] matrix) {
        return range(matrix, 0, matrix[0].length);
    }

    public static Double range(Double[][] matrix, int column) {
        return range(matrix, column, column + 1);
    }

    public static Double range(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        return max(matrix, fromColumn, toColumn) - min(matrix, fromColumn, toColumn);
    }

    public static Double median(Double[][] matrix) {
        return median(matrix, 0, matrix[0].length);
    }

    public static Double median(Double[][] matrix, int column) {
        return median(matrix, column, column + 1);
    }

    public static Double median(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        Double[] columnArray = toArray(matrix, fromColumn, toColumn);

        Arrays.sort(columnArray);

        if (columnArray.length % 2 == 0) {
            return (columnArray[columnArray.length / 2] + columnArray[columnArray.length / 2 - 1]) / 2;
        }
        else {
            return columnArray[columnArray.length / 2];
        }
    }

    public static Double[][] minMaxNormalization(Double[][] matrix, long min, long max, int column) {
        return minMaxNormalization(matrix, min, max, column, column + 1);
    }

    public static Double[][] minMaxNormalization(Double[][] matrix, long min, long max, int fromColumn, int toColumn) {

        if (min > max)
            throw new IllegalArgumentException("Min value is greater than max value");

        if (min == max)
            throw new IllegalArgumentException("Min value is equal to max value");

        checkColumnIndexes(matrix, fromColumn, toColumn);

        for (int i = fromColumn; i < toColumn; i++) {

            Double minDouble = min(matrix, i);
            Double maxDouble = max(matrix, i);

            if (minDouble.equals(maxDouble))
                throw new IllegalArgumentException("Min value is equal to max value");

            for (int j = 0; j < matrix.length; j++) {
                Double d = matrix[j][i];
                matrix[j][i] = (d - minDouble) / (maxDouble - minDouble) * (max - min) + min;
            }
        }

        return matrix;
    }

    public static Double[][] minMaxNormalization(Double[][] matrix) {
        return minMaxNormalization(matrix, 0, 1, 0, matrix[0].length);
    }

    public static Double[][] minMaxNormalization(Double[][] matrix, int column) {
        return minMaxNormalization(matrix, 0, 1, column, column + 1);
    }

    public static Double[][] zScoreStandardization(Double[][] matrix) {
        return zScoreStandardization(matrix, 0, matrix[0].length);
    }

    public static Double[][] zScoreStandardization(Double[][] matrix, int column) {
        return zScoreStandardization(matrix, column, column + 1);
    }

    public static Double[][] zScoreStandardization(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        if (matrix.length == 0 || matrix[0].length == 0)
            return matrix;

        for (int i = fromColumn; i < toColumn; i++) {

            double meanOfColumn = mean(matrix, i);
            double sd = standardDeviation(matrix, i);

            for (int j = 0; j < matrix.length; j++) {
                double d = matrix[j][i];
                matrix[j][i] = (d - meanOfColumn) / sd;
            }
        }

        return matrix;
    }

    public static Integer l0Norm(Double[][] matrix, int column, double epsilon) {
        checkColumnIndex(matrix, column);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int count = 0;

        for (int i = 0; i < matrix.length; i++) {
            if (!NumberUtils.approximatelyZero(matrix[i][column], epsilon))
                count++;
        }

        return count;
    }

    public static Double l1Norm(Double[][] matrix, int column) {
        checkColumnIndex(matrix, column);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        double absSum = 0d;

        for (int i = 0; i < matrix.length; i++)
            absSum += Math.abs(matrix[i][column]);

        return absSum;
    }

    public static Double l2Norm(Double[][] matrix, int column) {
        checkColumnIndex(matrix, column);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        double sumOfSquares = 0d;

        for (int i = 0; i < matrix.length; i++)
            sumOfSquares += Math.pow(matrix[i][column], 2);

        return Math.sqrt(sumOfSquares);
    }

    public static Double lInfinityNorm(Double[][] matrix, int column) {
        checkColumnIndex(matrix, column);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        double absMax = Double.MIN_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            if (Math.abs(matrix[i][column]) > absMax) absMax = Math.abs(matrix[i][column]);
        }

        return absMax;
    }

    public static List<Double> toList(Double[][] matrix) {
        return toList(matrix, 0, matrix[0].length);
    }

    public static List<Double> toList(Double[][] matrix, int column) {
        return toList(matrix, column, column + 1);
    }

    public static List<Double> toList(Double[][] matrix, int fromColumn, int toColumn) {
        checkColumnIndexes(matrix, fromColumn, toColumn);

        List<Double> list = new ArrayList<>();

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < matrix.length; j++) {
                list.add(matrix[j][i]);
            }
        }

        return list;
    }

    public static List<Double> distinct(Double[][] matrix) {
        return distinct(matrix, 0, matrix[0].length);
    }

    public static List<Double> distinct(Double[][] matrix, int column) {
        return distinct(matrix, column, column + 1);
    }

    public static List<Double> distinct(Double[][] matrix, int fromColumn, int toColumn) {
        checkColumnIndexes(matrix, fromColumn, toColumn);

        Set<Double> set = new LinkedHashSet<>();

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < matrix.length; j++) {
                set.add(matrix[j][i]);
            }
        }

        return new ArrayList<>(set);
    }
}
