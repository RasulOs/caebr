package caebr.matrix;

import caebr.util.NumberUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

public class StandardMatrix<T extends Number> implements IMatrix<T> {

    private static final String MATRIX_DIMENSIONS_MUST_BE_EQUAL = "Matrix dimensions must be equal";
    private static final String CURRENT_MATRIX_COLUMN_NUMBER_FORMAT = "Current matrix column number must be equal to the given matrix row number. " +
            "Current matrix column number: %d, given matrix row number: %d";
    public static final String COLUMN_INDEX_MUST_BE_BETWEEN_0_AND = "Column index must be between 0 and ";
    private Double[][] currentMatrix;

    private int rowNumber;
    private int columnNumber;

    private static int determinantSign = 1;

    private static final String MATRIX_CANNOT_BE_NULL = "Matrix cannot be null";
    private static final String MATRIX_CANNOT_BE_EMPTY = "Matrix cannot be empty";
    private static final String MATRIX_CANNOT_BE_JAGGED = "Matrix cannot be jagged";
    private static final String COLUMN_INDEX_IS_OUT_OF_BOUNDS = "Column index is out of bounds";

    // Default epsilon value. Used for comparing doubles.
    private static double epsilon = 0.000001;

    public StandardMatrix(T[][] matrix) {

        if (matrix == null)
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_NULL);

        if (isMatrixEmpty(matrix))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_EMPTY);

        this.currentMatrix = transformToDoubleMatrix(matrix);

        if (isMatrixJagged(this.currentMatrix))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_JAGGED);

        initializeColumnsAndRows();
    }

    private Double[][] transformToDoubleMatrix(T[][] matrix) {
        Double[][] doubleMatrix = new Double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                doubleMatrix[i][j] = matrix[i][j].doubleValue();
            }
        }

        return doubleMatrix;
    }

    private Double[][] transformToDoubleMatrixWithNColumnPadding(Double[][] matrix, int n) {
        Double[][] doubleMatrix = new Double[matrix.length][matrix[0].length + n];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, doubleMatrix[i], 0, matrix[0].length);
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = matrix[0].length; j < matrix[0].length + n; j++) {
                doubleMatrix[i][j] = 0.0;
            }
        }

        return doubleMatrix;
    }

    private Double[][] transformToDoubleMatrixWithSpecialColumnPadding(Double[][] matrix, T[] column) {

        if (matrix.length != column.length)
            throw new IllegalArgumentException("Matrix and column dimensions must be equal");

        Double[][] doubleMatrix = new Double[matrix.length][matrix[0].length + 1];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, doubleMatrix[i], 0, matrix[0].length);
        }

        for (int i = 0; i < matrix.length; i++) {
            doubleMatrix[i][matrix[0].length] = column[i].doubleValue();
        }

        return doubleMatrix;

    }

    private Double[][] transformToDoubleMatrixDropExactColumn(Double[][] matrix, int index) {

        if (index < 0 || index >= matrix[0].length)
            throw new IllegalArgumentException("Index out of bounds");

        Double[][] doubleMatrix = new Double[matrix.length][matrix[0].length - 1];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, doubleMatrix[i], 0, index);
            System.arraycopy(matrix[i], index + 1, doubleMatrix[i], index, matrix[0].length - index - 1);
        }

        return doubleMatrix;
    }

    private boolean isMatrixEmpty(T[][] matrix) {
        return matrix.length == 0 || matrix[0].length == 0;
    }

    private boolean isMatrixEmpty(IMatrix<T> iMatrix) {
        return iMatrix.getRowNumber() == 0 || iMatrix.getColumnNumber() == 0;
    }

    private static boolean isMatrixEmptyInternal(Double[][] matrix) {
        return matrix.length == 0 || matrix[0].length == 0;
    }

    private static boolean isMatrixJagged(Double[][] matrix) {
        int columns = matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != columns)
                return true;
        }

        return false;
    }

    private boolean isMatrixJagged(IMatrix<T> iMatrix) {
        return isMatrixJagged(iMatrix.toMatrix());
    }

    private void checkMatrix(IMatrix<T> iMatrix) {
        if (iMatrix == null)
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_NULL);

        if (isMatrixEmpty(iMatrix))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_EMPTY);

        if (isMatrixJagged(iMatrix))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_JAGGED);
    }

    private void checkMatrix(T[][] matrix) {
        if (matrix == null)
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_NULL);

        if (isMatrixEmpty(matrix))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_EMPTY);

        if (isMatrixJagged(transformToDoubleMatrix(matrix)))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_JAGGED);
    }

    private static void checkMatrixInternal(Double[][] matrix) {

        if (matrix == null)
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_NULL);

        if (isMatrixEmptyInternal(matrix))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_EMPTY);

        if (isMatrixJagged(matrix))
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_JAGGED);
    }

    private void checkMatrixDimensionsForSymmetricity(IMatrix<T> iMatrix) {
        if (this.rowNumber != iMatrix.getRowNumber() || this.columnNumber != iMatrix.getColumnNumber())
            throw new IllegalArgumentException(MATRIX_DIMENSIONS_MUST_BE_EQUAL);
    }

    private void checkMatrixDimensionsForSymmetricity(T[][] matrix) {
        if (this.rowNumber != matrix.length || this.columnNumber != matrix[0].length)
            throw new IllegalArgumentException(MATRIX_DIMENSIONS_MUST_BE_EQUAL);
    }

    private void checkMatrixDimensionsForSymmetricityInternal(Double[][] matrix) {
        if (this.rowNumber != matrix.length || this.columnNumber != matrix[0].length)
            throw new IllegalArgumentException(MATRIX_DIMENSIONS_MUST_BE_EQUAL);
    }

    private static void checkMatrixDimensionsForSymmetricityInternal(Double[][] matrix1, Double[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length)
            throw new IllegalArgumentException(MATRIX_DIMENSIONS_MUST_BE_EQUAL);
    }

    private void checkMatrixDimensionsForMultiplication(IMatrix<T> iMatrix) {
        if (this.columnNumber != iMatrix.getRowNumber())
            throw new IllegalArgumentException(String
                    .format(CURRENT_MATRIX_COLUMN_NUMBER_FORMAT,
                            this.columnNumber, iMatrix.getRowNumber()));
    }

    private void checkMatrixDimensionsForMultiplication(T[][] matrix) {
        if (this.columnNumber != matrix.length)
            throw new IllegalArgumentException(String
                    .format(CURRENT_MATRIX_COLUMN_NUMBER_FORMAT,
                            this.columnNumber, matrix.length));
    }

    private void checkMatrixDimensionsForMultiplicationInternal(Double[][] matrix) {
        if (this.columnNumber != matrix.length)
            throw new IllegalArgumentException(String
                    .format(CURRENT_MATRIX_COLUMN_NUMBER_FORMAT,
                            this.columnNumber, matrix.length));
    }

    private static void checkMatrixDimensionsForMultiplicationInternal(Double[][] matrix1, Double[][] matrix2) {
        if (matrix1[0].length != matrix2.length)
            throw new IllegalArgumentException(String
                    .format(CURRENT_MATRIX_COLUMN_NUMBER_FORMAT,
                            matrix1[0].length, matrix2.length));
    }

    private void initializeColumnsAndRows() {

        this.rowNumber = this.currentMatrix.length;
        this.columnNumber = this.currentMatrix[0].length;

        if (this.rowNumber == 0 || this.columnNumber == 0)
            throw new IllegalArgumentException(MATRIX_CANNOT_BE_EMPTY);

    }

    @Override
    public IMatrix<T> add(T[][] matrix) {

        checkMatrix(matrix);

        checkMatrixDimensionsForSymmetricity(matrix);

        Double[][] result = new Double[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j] + matrix[i][j].doubleValue();
            }
        }

        currentMatrix = result;

        return this;
    }

    private IMatrix<T> addInternal(Double[][] matrix, boolean checkMatrix) {

        currentMatrix = addInternal(this.currentMatrix, matrix, checkMatrix);

        return this;
    }

    public static Double[][] add(Double[][] matrix1, Double[][] matrix2) {
        return addInternal(matrix1, matrix2, true);
    }

    private static Double[][] addInternal(Double[][] matrix1, Double[][] matrix2, boolean checkMatrix) {

        if (checkMatrix) {
            checkMatrixInternal(matrix1);

            checkMatrixInternal(matrix2);

            checkMatrixDimensionsForSymmetricityInternal(matrix1, matrix2);
        }

        Double[][] result = new Double[matrix1.length][matrix1[0].length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }

    @Override
    public IMatrix<T> subtract(T[][] matrix) {

        checkMatrix(matrix);

        checkMatrixDimensionsForSymmetricity(matrix);

        Double[][] result = new Double[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j] - matrix[i][j].doubleValue();
            }
        }

        currentMatrix = result;

        return this;
    }

    public static Double[][] subtract(Double[][] matrix1, Double[][] matrix2) {
        return subtractInternal(matrix1, matrix2, true);
    }

    private IMatrix<T> subtractInternal(Double[][] matrix, boolean checkMatrix) {

        currentMatrix = subtractInternal(this.currentMatrix, matrix, checkMatrix);

        return this;
    }

    private static Double[][] subtractInternal(Double[][] matrix1, Double[][] matrix2, boolean checkMatrix) {

        if (checkMatrix) {
            checkMatrixInternal(matrix1);

            checkMatrixInternal(matrix2);

            checkMatrixDimensionsForSymmetricityInternal(matrix1, matrix2);
        }

        Double[][] result = new Double[matrix1.length][matrix1[0].length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }

        return result;
    }

    @Override
    public IMatrix<T> multiply(T[][] matrix) {

        checkMatrix(matrix);

        checkMatrixDimensionsForMultiplication(matrix);

        Double[][] resultMatrix = new Double[this.rowNumber][matrix[0].length];

        double sum = 0.0;

        for (int i = 0; i < currentMatrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                for (int k = 0; k < currentMatrix[0].length; k++) {
                    sum += currentMatrix[i][k] * matrix[k][j].doubleValue();
                }

                resultMatrix[i][j] = sum;
                sum = 0.0;
            }
        }

        currentMatrix = resultMatrix;

        return this;
    }

    public static Double[][] multiply(Double[][] matrix1, Double[][] matrix2) {
        return multiplyInternal(matrix1, matrix2, true);
    }

    private IMatrix<T> multiplyInternal(Double[][] matrix, boolean checkMatrix) {

        currentMatrix = multiplyInternal(this.currentMatrix, matrix, checkMatrix);

        initializeColumnsAndRows();

        return this;
    }

   private static Double[][] multiplyInternal(Double[][] matrix1, Double[][] matrix2, boolean checkMatrix) {
       if (checkMatrix) {
           checkMatrixInternal(matrix1);
           checkMatrixInternal(matrix2);
           checkMatrixDimensionsForMultiplicationInternal(matrix1, matrix2);
       }

       Double[][] resultMatrix = new Double[matrix1.length][matrix2[0].length];

       double sum = 0.0;

       for (int i = 0; i < matrix1.length; i++) {
           for (int j = 0; j < matrix2[0].length; j++) {
               for (int k = 0; k < matrix1[0].length; k++) {
                   sum += matrix1[i][k] * matrix2[k][j];
               }

               resultMatrix[i][j] = sum;
               sum = 0.0;
           }
       }

       return resultMatrix;
   }

    @Override
    public IMatrix<T> add(IMatrix<T> iMatrix) {
        addInternal(iMatrix.toMatrix(), true);

        return this;
    }

    @Override
    public IMatrix<T> subtract(IMatrix<T> iMatrix) {
        subtractInternal(iMatrix.toMatrix(), true);

        return this;
    }

    @Override
    public IMatrix<T> transpose() {

        currentMatrix = transpose(this.currentMatrix);

        initializeColumnsAndRows();

        return this;
    }

    public static Double[][] transpose(Double[][] matrix) {

        checkMatrixInternal(matrix);

        if (matrix.length == 1 && matrix[0].length == 1)
            return matrix;

        Double[][] resultMatrix = new Double[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                resultMatrix[j][i] = matrix[i][j];
            }
        }

        return resultMatrix;
    }

    @Override
    public IMatrix<T> multiply(IMatrix<T> iMatrix) {
        multiplyInternal(iMatrix.toMatrix(), true);

        return this;
    }

    @Override
    public IMatrix<T> multiply(T number) {

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                this.currentMatrix[i][j] *= number.doubleValue();
            }
        }

        return this;
    }

    @Override
    public Double sum() {

        return sum(0, this.columnNumber);
    }

    public static Double sum(Double[][] matrix) {
        return sum(matrix, 0, matrix[0].length);
    }

    @Override
    public Double sum(int column) {
        return sum(column, column + 1);
    }

    public static Double sum(Double[][] matrix, int column) {
        return sum(matrix, column, column + 1);
    }

    @Override
    public Double sum(int fromColumn, int toColumn) {

        return sum(this.currentMatrix, fromColumn, toColumn);
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



    private void checkColumnIndex(int column) {
        checkColumnIndex(this.currentMatrix, column);
    }

    private static void checkColumnIndex(Double[][] matrix, int column) {
        if (column < 0)
            throw new IllegalArgumentException("Column index cannot be negative");

        if (column >= matrix[0].length)
            throw new IllegalArgumentException("Column index cannot be greater than the number of columns");
    }

    @Override
    public Double mean() {
        return mean(this.currentMatrix, 0, this.columnNumber);
    }

    public static Double mean(Double[][] matrix) {
        return mean(matrix, 0, matrix[0].length);
    }

    @Override
    public Double mean(int column) {
        return mean(this.currentMatrix, column, column + 1);
    }

    public static Double mean(Double[][] matrix, int column) {
        return mean(matrix, column, column + 1);
    }

    @Override
    public Double mean(int fromColumn, int toColumn) {

        return mean(this.currentMatrix, fromColumn, toColumn);
    }

    public static Double mean(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        if (matrix.length == 0 || matrix[0].length == 0)
            return 0.0;

        return sum(matrix, fromColumn, toColumn) / (matrix.length * (toColumn - fromColumn));
    }

    @Override
    public Double max() {
        return max(this.currentMatrix, 0, this.columnNumber);
    }

    public static Double max(Double[][] matrix) {
        return max(matrix, 0, matrix[0].length);
    }

    @Override
    public Double max(int column) {
        return max(this.currentMatrix, column, column + 1);
    }

    public static Double max(Double[][] matrix, int column) {
        return max(matrix, column, column + 1);
    }

    @Override
    public Double max(int fromColumn, int toColumn) {
        return max(this.currentMatrix, fromColumn, toColumn);
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

    @Override
    public Double min() {
        return min(this.currentMatrix, 0, this.columnNumber);
    }

    public static Double min(Double[][] matrix) {
        return min(matrix, 0, matrix[0].length);
    }

    @Override
    public Double min(int column) {
        return min(this.currentMatrix, column, column + 1);
    }

    public static Double min(Double[][] matrix, int column) {
        return min(matrix, column, column + 1);
    }

    @Override
    public Double min(int fromColumn, int toColumn) {
        return min(this.currentMatrix, fromColumn, toColumn);
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

    @Override
    public List<Double> mode() {
        return mode(this.currentMatrix, 0, this.columnNumber);
    }

    public static List<Double> mode(Double[][] matrix) {
        return mode(matrix, 0, matrix[0].length);
    }

    @Override
    public List<Double> mode(int column) {
        return mode(this.currentMatrix, column, column + 1);
    }

    public static List<Double> mode(Double[][] matrix, int column) {
        return mode(matrix, column, column + 1);
    }

    @Override
    public List<Double> mode(int fromColumn, int toColumn) {

        return mode(this.currentMatrix, fromColumn, toColumn);
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

    @Override
    public Double variance() {
        return variance(this.currentMatrix, 0, this.columnNumber);
    }

    public static Double variance(Double[][] matrix) {
        return variance(matrix, 0, matrix[0].length);
    }

    @Override
    public Double variance(int column) {
        return variance(this.currentMatrix, column, column + 1);
    }

    public static Double variance(Double[][] matrix, int column) {
        return variance(matrix, column, column + 1);
    }

    @Override
    public Double variance(int fromColumn, int toColumn) {
        return variance(this.currentMatrix, fromColumn, toColumn);
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

    @Override
    public Double standardDeviation() {

        return standardDeviation(this.currentMatrix, 0, this.columnNumber);
    }

    public static Double standardDeviation(Double[][] matrix) {
        return standardDeviation(matrix, 0, matrix[0].length);
    }

    @Override
    public Double standardDeviation(int column) {

        return standardDeviation(this.currentMatrix, column, column + 1);
    }

    public static Double standardDeviation(Double[][] matrix, int column) {
        return standardDeviation(matrix, column, column + 1);
    }

    @Override
    public Double standardDeviation(int fromColumn, int toColumn) {

        return Math.sqrt(variance(this.currentMatrix, fromColumn, toColumn));
    }

    public static Double standardDeviation(Double[][] matrix, int fromColumn, int toColumn) {
        return Math.sqrt(variance(matrix, fromColumn, toColumn));
    }

    @Override
    public Double range() {
        return range(this.currentMatrix, 0, this.columnNumber);
    }

    public static Double range(Double[][] matrix) {
        return range(matrix, 0, matrix[0].length);
    }

    @Override
    public Double range(int column) {
        return range(this.currentMatrix, column, column + 1);
    }

    public static Double range(Double[][] matrix, int column) {
        return range(matrix, column, column + 1);
    }

    @Override
    public Double range(int fromColumn, int toColumn) {
        return range(this.currentMatrix, fromColumn, toColumn);
    }

    public static Double range(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        return max(matrix, fromColumn, toColumn) - min(matrix, fromColumn, toColumn);
    }

    @Override
    public Double median() {
        return median(this.currentMatrix, 0, this.columnNumber);
    }

    public static Double median(Double[][] matrix) {
        return median(matrix, 0, matrix[0].length);
    }

    @Override
    public Double median(int column) {
        return median(this.currentMatrix, column, column + 1);
    }

    public static Double median(Double[][] matrix, int column) {
        return median(matrix, column, column + 1);
    }

    @Override
    public Double median(int fromColumn, int toColumn) {
        return median(this.currentMatrix, fromColumn, toColumn);
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

    @Override
    public IMatrix<T> sort(int column) {

        return sort(column, true);
    }

    public static Double[][] sort(Double[][] matrix, int column) {
        return sort(matrix, column, column + 1, true);
    }

    @Override
    public IMatrix<T> sort(int column, boolean ascending) {

        sort(this.currentMatrix, column, ascending);

        return this;
    }

    public static Double[][] sort(Double[][] matrix, int column, boolean ascending) {

        if (column < 0 || column >= matrix[0].length)
            throw new IllegalArgumentException(COLUMN_INDEX_IS_OUT_OF_BOUNDS);

        Double[] columnArray = toArray(matrix, column, column + 1);

        if (ascending)
            Arrays.sort(columnArray);
        else
            Arrays.sort(columnArray, Collections.reverseOrder());

        return setColumn(matrix, columnArray, column);
    }

    @Override
    public IMatrix<T> sort(int fromColumn, int toColumn) {

        return sort(fromColumn, toColumn, true);
    }

    public static Double[][] sort(Double[][] matrix, int fromColumn, int toColumn) {
        return sort(matrix, fromColumn, toColumn, true);
    }

    @Override
    public IMatrix<T> sort(int fromColumn, int toColumn, boolean ascending) {
        sort(this.currentMatrix, fromColumn, toColumn, ascending);

        return this;
    }

    public static Double[][] sort(Double[][] matrix, int fromColumn, int toColumn, boolean ascending) {

        if (fromColumn < 0 || fromColumn >= matrix[0].length)
            throw new IllegalArgumentException(COLUMN_INDEX_IS_OUT_OF_BOUNDS);

        if (toColumn < 0 || toColumn > matrix[0].length)
            throw new IllegalArgumentException(COLUMN_INDEX_IS_OUT_OF_BOUNDS);

        if (fromColumn > toColumn)
            throw new IllegalArgumentException("From column index is greater than to column index");

        for (int i = fromColumn; i < toColumn; i++) {
            sort(matrix, i, ascending);
        }

        return matrix;
    }

    @Override
    public IMatrix<T> sort() {

        return sort(0, this.columnNumber, true);
    }

    public static Double[][] sort(Double[][] matrix) {
        return sort(matrix, 0, matrix[0].length, true);
    }

    // Sorts a column with rows swaps like in a bubble sort algorithm
    @Override
    public IMatrix<T> sortWithRowSwap(int column, boolean ascending) {

        sortWithRowSwap(this.currentMatrix, column, ascending);

        return this;
    }

    public static Double[][] sortWithRowSwap(Double[][] matrix, int column, boolean ascending) {

        checkColumnIndex(matrix, column);

        if (matrix.length == 1 || matrix[0].length == 1)
            return matrix;

        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix.length - i - 1; j++) {
                if (ascending) {
                    if (matrix[j][column] > matrix[j + 1][column]) {
                        swapRows(matrix, j, j + 1);
                    }
                }
                else {
                    if (matrix[j][column] < matrix[j + 1][column]) {
                        swapRows(matrix, j, j + 1);
                    }
                }
            }
        }

        return matrix;
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max, int column) {
        return minMaxNormalization(min, max, column, column + 1);
    }

    public static Double[][] minMaxNormalization(Double[][] matrix, long min, long max, int column) {
        return minMaxNormalization(matrix, min, max, column, column + 1);
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max, int fromColumn, int toColumn) {
        minMaxNormalization(this.currentMatrix, min, max, fromColumn, toColumn);

        return this;
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

    @Override
    public IMatrix<T> minMaxNormalization() {
        minMaxNormalization(this.currentMatrix, 0, 1, 0, this.columnNumber);

        return this;
    }

    public static Double[][] minMaxNormalization(Double[][] matrix) {
        return minMaxNormalization(matrix, 0, 1, 0, matrix[0].length);
    }

    @Override
    public IMatrix<T> minMaxNormalization(int column) {
        minMaxNormalization(this.currentMatrix, 0, 1, column, column + 1);

        return this;
    }

    public static Double[][] minMaxNormalization(Double[][] matrix, int column) {
        return minMaxNormalization(matrix, 0, 1, column, column + 1);
    }

    @Override
    public IMatrix<T> minMaxNormalization(int fromColumn, int toColumn) {
        minMaxNormalization(this.currentMatrix, 0, 1, fromColumn, toColumn);

        return this;
    }

    @Override
    public IMatrix<T> zScoreStandardization() {
        return zScoreStandardization(0, this.columnNumber);
    }

    public static Double[][] zScoreStandardization(Double[][] matrix) {
        return zScoreStandardization(matrix, 0, matrix[0].length);
    }

    @Override
    public IMatrix<T> zScoreStandardization(int column) {
        return zScoreStandardization(column, column + 1);
    }

    public static Double[][] zScoreStandardization(Double[][] matrix, int column) {
        return zScoreStandardization(matrix, column, column + 1);
    }

    @Override
    public IMatrix<T> zScoreStandardization(int fromColumn, int toColumn) {

        zScoreStandardization(this.currentMatrix, fromColumn, toColumn);

        return this;
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

    @Override
    public Integer l0Norm(int column) {
        return l0Norm(this.currentMatrix, column);
    }

    public static Integer l0Norm(Double[][] matrix, int column) {
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

    @Override
    public Double l1Norm(int column) {
        return l1Norm(this.currentMatrix, column);
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

    @Override
    public Double l2Norm(int column) {
        return l2Norm(this.currentMatrix, column);
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

    @Override
    public Double lInfinityNorm(int column) {
        return lInfinityNorm(this.currentMatrix, column);
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

    @Override
    public int getRowNumber() {
        return this.rowNumber;
    }

    @Override
    public int getColumnNumber() {
        return this.columnNumber;
    }

    @Override
    public boolean isSquare() {

        return this.rowNumber == this.columnNumber;
    }

    public static boolean isSquare(Double[][] matrix) {

        return matrix.length == matrix[0].length;
    }

    @Override
    public boolean isSymmetric() {
        return isSymmetric(this.currentMatrix);
    }

    public static boolean isSymmetric(Double[][] matrix) {
        if (!isSquare(matrix))
            return false;

        for (int i = 0; i < matrix.length; i++) {
            for(int j = i + 1; j < matrix[0].length; j++) {
                if (!Objects.equals(matrix[i][j], matrix[j][i])) return false;
            }
        }

        return true;

    }

    @Override
    public boolean isIdentity() {
        return isIdentity(this.currentMatrix);
    }

    public static boolean isIdentity(Double[][] matrix) {

        if (!isSquare(matrix))
            return false;

        for (int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if (i == j && matrix[i][j] != 1) return false;
                if (i != j && !NumberUtils.approximatelyZero(matrix[i][j], epsilon)) return false;
            }
        }

        return true;
    }

    @Override
    public boolean isDiagonal() {
        return isDiagonal(this.currentMatrix);
    }

    public static boolean isDiagonal(Double[][] matrix) {
        if (!isSquare(matrix))
            return false;

        for (int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if (i != j && !NumberUtils.approximatelyZero(matrix[i][j], epsilon)) return false;
            }
        }

        return true;
    }

    @Override
    public boolean isAntiDiagonal() {
        return isAntiDiagonal(this.currentMatrix);
    }

    public static boolean isAntiDiagonal(Double[][] matrix) {
        if (!isSquare(matrix))
            return false;

        for (int i = 0; i < matrix.length; i++) {
            for(int j = matrix[0].length - 1; j >= 0; j--) {
                if (i + j != matrix[0].length - 1 && !NumberUtils.approximatelyZero(matrix[i][j], epsilon))
                    return false;
            }
        }

        return true;
    }

    @Override
    public boolean isUpperTriangular() {
        return isUpperTriangular(this.currentMatrix);
    }

    @Override
    public boolean isLowerTriangular() {
        return isLowerTriangular(this.currentMatrix);
    }

    public static boolean isUpperTriangular(Double[][] matrix) {
        if (!isSquare(matrix))
            return false;

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if (!NumberUtils.approximatelyZero(matrix[i][j], epsilon))
                    return false;
            }
        }

        return true;
    }

    public static boolean isLowerTriangular(Double[][] matrix) {
        if (!isSquare(matrix))
            return false;

        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = i + 1; j < matrix[0].length; j++) {
                if (!NumberUtils.approximatelyZero(matrix[i][j], epsilon))
                    return false;
            }
        }

        return true;
    }

    // Find the determinant of a triangular matrix
    private double multiplyDiagonalElements() {
        return multiplyDiagonalElements(this.currentMatrix);
    }


    // Find the determinant of a triangular matrix
    private static double multiplyDiagonalElements(Double[][] matrix) {

        if (!isSquare(matrix))
            throw new IllegalArgumentException("Matrix must be square");

        double result = 1.0;

        for (int i = 0; i < matrix.length; i++) {
            result *= matrix[i][i];
        }

        return result;
    }

    @Override
    public Double determinant() {

        return determinant(this.currentMatrix);
    }

    public static Double determinant(Double[][] matrix) {

        determinantSign = 1;

        if (!isSquare(matrix))
            throw new IllegalArgumentException("Matrix must be square to have a determinant");

        if (isUpperTriangular(matrix) || isLowerTriangular(matrix))
            return multiplyDiagonalElements(matrix);


        Double[][] copyOfCurrentMatrix = copy(matrix);

        transformToUpperTriangularMatrix(copyOfCurrentMatrix);

        return multiplyDiagonalElements(copyOfCurrentMatrix) * determinantSign;
    }

    public static Double[][] copy(Double[][] matrix) {
        Double[][] copy = new Double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }

        return copy;
    }

    // Code reference from Mohsen Mousavi
    public static Double[][] transformToUpperTriangularMatrix(Double[][] matrix) {

        for (int columnIndex = 0; columnIndex < matrix[0].length; columnIndex++) {

            // Swaps rows to put the biggest absolute value on the top
            sortColumnWithRespectToAbsoluteValue(matrix, columnIndex);

            // No need to check the main diagonal. That is why rowIndex > column index.
            for (int rowIndex = matrix.length - 1; rowIndex > columnIndex; rowIndex--) {

                if (NumberUtils.approximatelyZero(matrix[rowIndex][columnIndex], epsilon))
                    continue;

                double x = matrix[rowIndex][columnIndex];
                double y = matrix[rowIndex - 1][columnIndex];

                // We need to cancel the value in currentMatrix[rowIndex][columnIndex] by
                // adding currentMatrix[rowIndex - 1][columnIndex] to it.
                // That is why we divide it by itself and multiply with a negative value of value above it:
                // currentMatrix[rowIndex - 1][columnIndex].
                // And then we cancel the value in matrix[rowIndex - 1][columnIndex] by adding
                // matrix[rowIndex][columnIndex] to it.
                // But don't forget, that multiplying a row by a constant also multiplies the determinant
                // by that constant.
                if ((-y/x) < 0) determinantSign *= -1;
                multiplyRow(matrix, rowIndex, (-y / x));

                addRows(matrix, rowIndex, rowIndex - 1);

                // We return the determinant to its original value by dividing it by the constant we
                // multiplied the row with before.
                if ((-y/x) < 0) determinantSign *= -1;
                multiplyRow(matrix, rowIndex, (-x / y));
            }
        }

        return matrix;
    }

    public static Double[][] addRows(Double[][] matrix, int toRow, int fromRow) {

        checkRow(matrix, toRow, fromRow);

        for (int i = 0; i < matrix[0].length; i++) {
            matrix[toRow][i] += matrix[fromRow][i];
        }

        return matrix;
    }

    public static Double[][] multiplyRow(Double[][] matrix, int rowIndex, double constant) {

        checkRow(matrix, rowIndex, rowIndex);

        for (int i = 0; i < matrix[0].length; i++) {
            matrix[rowIndex][i] *= constant;
        }

        return matrix;
    }

    // Bubble sort algorithm with row swaps to put the biggest absolute value on the top
    private void sortColumnWithRespectToAbsoluteValue(int column) {
        sortColumnWithRespectToAbsoluteValue(this.currentMatrix, column);
    }

    // Bubble sort algorithm with row swaps to put the biggest absolute value on the top
    public static Double[][] sortColumnWithRespectToAbsoluteValue(Double[][] matrix, int column) {

        checkColumnIndex(matrix, column);

        if (matrix.length == 1 || matrix[0].length == 1)
            return matrix;

        for (int i = matrix.length - 1; i >= column; i--) {
            for (int j = matrix.length - 1; j >= column; j--) {
                if (Math.abs(matrix[i][column]) < Math.abs(matrix[j][column])) {

                    if (i != j) determinantSign *= -1;

                    swapRows(matrix, i, j);
                }
            }
        }

        return matrix;
    }

    @Override
    public IMatrix<T> inverse() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> dropColumn(int column) {

        popColumn(column);

        return this;
    }

    @Override
    public IMatrix<T> dropColumn() {
        popColumn(this.columnNumber - 1);

        return this;
    }

    @Override
    public IMatrix<T> setColumn(T[] column, int index) {
        checkColumn(column, index);

        for (int i = 0; i < column.length; i++) {
            this.currentMatrix[i][index] = column[i].doubleValue();
        }

        return this;
    }

    private IMatrix<T> setColumn(Double[] column, int index) {
        setColumn(this.currentMatrix, column, index);

        return this;
    }

    public static Double[][] setColumn(Double[][] matrix, Double[] column, int index) {
        checkColumn(matrix, column, index);

        for (int i = 0; i < column.length; i++) {
            matrix[i][index] = column[i];
        }

        return matrix;
    }

    @Override
    public IMatrix<T> putColumn(T[] column) {
        checkColumn(column, 0);

        currentMatrix = transformToDoubleMatrixWithSpecialColumnPadding(currentMatrix, column);

        initializeColumnsAndRows();

        return this;
    }

    private void checkColumn(T[] column) {
        checkColumn(column, 0);
    }

    private void checkColumn(T[] column, int index) {
        if (column.length != this.rowNumber)
            throw new IllegalArgumentException("Column length must be equal to matrix column number.");

        if (index < 0 || index >= this.columnNumber)
            throw new IllegalArgumentException(COLUMN_INDEX_MUST_BE_BETWEEN_0_AND + (this.columnNumber - 1));
    }

    private void checkColumn(Double[] column, int index) {
        checkColumn((T[]) column, index);
    }

    private static void checkColumn(Double[][] matrix, Double[] column, int index) {
        if (column.length != matrix.length)
            throw new IllegalArgumentException("Column length must be equal to matrix column number.");

        if (index < 0 || index >= matrix[0].length)
            throw new IllegalArgumentException(COLUMN_INDEX_MUST_BE_BETWEEN_0_AND + (matrix[0].length - 1));
    }

    @Override
    public Double[] popColumn() {
        return popColumn(this.columnNumber - 1);
    }

    @Override
    public IMatrix<T> replaceRow(int index, T[] row) {

        checkRow(index, index);

        checkRow(row);

        for (int i = 0; i < row.length; i++) {
            this.currentMatrix[index][i] = row[i].doubleValue();
        }

        return this;
    }

    public static Double[][] replaceRow(Double[][] matrix, int index, Double[] row) {

        checkRow(matrix, index, index);

        checkRow(matrix, row);

        matrix[index] = row;

        return matrix;
    }

    @Override
    public IMatrix<T> swapRows(int firstIndex, int secondIndex) {

        swapRows(this.currentMatrix, firstIndex, secondIndex);

        return this;
    }


    public static Double[][] swapRows(Double[][] matrix, int firstIndex, int secondIndex) {

        checkRow(matrix, firstIndex, secondIndex);

        Double[] temp = matrix[firstIndex];

        matrix[firstIndex] = matrix[secondIndex];

        matrix[secondIndex] = temp;

        return matrix;
    }

    private void checkRow(int fromRow, int toRow) {

        checkRow(this.currentMatrix, fromRow, toRow);
    }

    private static void checkRow(Double[][] matrix, int firstRow, int secondRow) {

        if (firstRow < 0 || firstRow >= matrix.length)
            throw new IllegalArgumentException("Row index must be between 0 and " + (matrix.length - 1));

        if (secondRow < 0 || secondRow >= matrix.length)
            throw new IllegalArgumentException("Row index must be between 0 and " + (matrix.length - 1));
    }

    private void checkRowFromTo(int from, int toRow) {

        if (from > toRow)
            throw new IllegalArgumentException("From row index cannot be greater than to row index");

        checkRow(from, toRow - 1);
    }

    private static void checkRowFromTo(Double[][] matrix, int from, int toRow) {
        checkRow(matrix, from, toRow - 1);
    }

    private void checkRow(T[] row) {

        Objects.requireNonNull(row);

        if (row.length != this.columnNumber)
            throw new IllegalArgumentException("Row length must be equal to matrix column number.");
    }

    private static void checkRow(Double[][] matrix, Double[] row) {

        Objects.requireNonNull(row);

        if (row.length != matrix[0].length)
            throw new IllegalArgumentException("Row length must be equal to matrix column number.");
    }


    @Override
    public IMatrix<T> map(Function<Double, Double> function) {
        map(this.currentMatrix, 0, this.columnNumber, function);

        return this;
    }

    public static Double[][] map(Double[][] matrix, Function<Double, Double> function) {
        return map(matrix, 0, matrix[0].length, function);
    }

    @Override
    public IMatrix<T> map(int column, Function<Double, Double> function) {
        map(this.currentMatrix, column, column + 1, function);

        return this;
    }

    public static Double[][] map(Double[][] matrix, int column, Function<Double, Double> function) {
        return map(matrix, column, column + 1, function);
    }

    @Override
    public IMatrix<T> map(int fromColumn, int toColumn, Function<Double, Double> function) {
        map(this.currentMatrix, fromColumn, toColumn, function);

        return this;
    }

    public static Double[][] map(Double[][] matrix, int fromColumn, int toColumn,
                                 Function<Double, Double> function) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        Objects.requireNonNull(function);

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = function.apply(matrix[j][i]);
            }
        }

        return matrix;
    }

    @Override
    public IMatrix<T> mapRow(Function<Double, Double> function) {
        mapRow(this.currentMatrix, 0, this.rowNumber, function);

        return this;
    }

    public static Double[][] mapRow(Double[][] matrix, Function<Double, Double> function) {
        return mapRow(matrix, 0, matrix.length, function);
    }

    @Override
    public IMatrix<T> mapRow(int row, Function<Double, Double> function) {
        mapRow(this.currentMatrix, row, row + 1, function);

        return this;
    }

    public static Double[][] mapRow(Double[][] matrix, int row, Function<Double, Double> function) {
        return mapRow(matrix, row, row + 1, function);
    }

    @Override
    public IMatrix<T> addRows(int toRow, int fromRow) {

        addRows(this.currentMatrix, toRow, fromRow);

        return this;
    }

    @Override
    public IMatrix<T> multiplyRows(int toRow, int fromRow) {

        multiplyRows(this.currentMatrix, toRow, fromRow);

        return this;
    }

    public static Double[][] multiplyRows(Double[][] matrix, int toRow, int fromRow) {

        checkRow(matrix, toRow, fromRow);

        for (int i = 0; i < matrix[0].length; i++) {
            matrix[toRow][i] *= matrix[fromRow][i];
        }

        return matrix;
    }

    @Override
    public IMatrix<T> mapRow(int fromRow, int toRow, Function<Double, Double> function) {

        mapRow(this.currentMatrix, fromRow, toRow, function);

        return this;
    }

    public static Double[][] mapRow(Double[][] matrix, int fromRow, int toRow, Function<Double, Double> function) {
        checkRowFromTo(matrix, fromRow, toRow);

        Objects.requireNonNull(function);

        for (int i = fromRow; i < toRow; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = function.apply(matrix[i][j]);
            }
        }

        return matrix;
    }

    @Override
    public Double reduce(int column, BinaryOperator<Double> accumulator) {
        return reduce(this.currentMatrix, column, column + 1, 0d, accumulator);
    }

    public static Double reduce(Double[][] matrix, int column, BinaryOperator<Double> accumulator) {
        return reduce(matrix, column, column + 1, 0d, accumulator);
    }

    @Override
    public Double reduce(int fromColumn, int toColumn, BinaryOperator<Double> accumulator) {
        return reduce(this.currentMatrix, fromColumn, toColumn, 0d, accumulator);
    }

    public static Double reduce(Double[][] matrix, int fromColumn, int toColumn,
                                BinaryOperator<Double> accumulator) {
        return reduce(matrix, fromColumn, toColumn, 0d, accumulator);
    }

    @Override
    public Double reduce(int fromColumn, int toColumn, Double identity, BinaryOperator<Double> accumulator) {
        return reduce(this.currentMatrix, fromColumn, toColumn, identity, accumulator);
    }

    public static Double reduce(Double[][] matrix, int fromColumn, int toColumn,
                                Double identity, BinaryOperator<Double> accumulator) {
        checkColumnIndexes(matrix, fromColumn, toColumn);

        Objects.requireNonNull(accumulator);

        double result = identity;

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result = accumulator.apply(result, matrix[j][i]);
            }
        }

        return result;
    }

    @Override
    public Double reduce(int column, Double identity, BinaryOperator<Double> accumulator) {
        return reduce(column, column + 1, identity, accumulator);
    }

    public static Double reduce(Double[][] matrix, int column, Double identity,
                                BinaryOperator<Double> accumulator) {
        return reduce(matrix, column, column + 1, identity, accumulator);
    }

    @Override
    public void forEach(int column, Consumer<Double> consumer) {
        forEach(this.currentMatrix, column, consumer);
    }

    public static void forEach(Double[][] matrix, int column, Consumer<Double> consumer) {
        checkColumnIndex(matrix, column);

        Objects.requireNonNull(consumer);

        for (int i = 0; i < matrix.length; i++) {
            consumer.accept(matrix[i][column]);
        }
    }

    @Override
    public List<Double> toList() {
        return toList(this.currentMatrix, 0, this.columnNumber);
    }

    public static List<Double> toList(Double[][] matrix) {
        return toList(matrix, 0, matrix[0].length);
    }

    @Override
    public List<Double> toList(int column) {
        return toList(this.currentMatrix, column, column + 1);
    }

    public static List<Double> toList(Double[][] matrix, int column) {
        return toList(matrix, column, column + 1);
    }

    @Override
    public List<Double> toList(int fromColumn, int toColumn) {
        return toList(this.currentMatrix, fromColumn, toColumn);
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

    @Override
    public List<Double> distinct() {
        return distinct(this.currentMatrix, 0, this.columnNumber);
    }

    public static List<Double> distinct(Double[][] matrix) {
        return distinct(matrix, 0, matrix[0].length);
    }

    @Override
    public List<Double> distinct(int column) {
        return distinct(this.currentMatrix, column, column + 1);
    }

    public static List<Double> distinct(Double[][] matrix, int column) {
        return distinct(matrix, column, column + 1);
    }

    @Override
    public List<Double> distinct(int fromColumn, int toColumn) {
        return distinct(this.currentMatrix, fromColumn, toColumn);
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

    @Override
    public Double[] popColumn(int index) {

        if (index < 0 || index >= this.columnNumber)
            throw new IllegalArgumentException(COLUMN_INDEX_MUST_BE_BETWEEN_0_AND + (this.columnNumber - 1));

        if (this.columnNumber < 2)
            throw new IllegalArgumentException("Matrix must have at least two columns.");

        Double[] column = new Double[this.rowNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            column[i] = this.currentMatrix[i][index];
        }

        currentMatrix = transformToDoubleMatrixDropExactColumn(currentMatrix, index);

        initializeColumnsAndRows();

        return column;

    }

    private String content() {
        StringBuilder sb = new StringBuilder();

        sb.append("Matrix has ")
                .append(this.rowNumber == 1 ? "1 row" : this.rowNumber + " rows")
                .append(" and ")
                .append(this.columnNumber == 1 ? "1 column" : this.columnNumber + " columns")
                .append(". Matrix:\n[\n");

        for (int i = 0; i < currentMatrix.length; i++) {
            sb.append("\t[");
            for (int j = 0; j < currentMatrix[0].length; j++) {
                sb.append(currentMatrix[i][j]);

                if (j < currentMatrix[0].length - 1)
                    sb.append(", ");
            }
            sb.append("]");
            sb.append("\n");
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public Double[][] toMatrix() {

        return this.currentMatrix;
    }

    @Override
    public Double[] toArray(int column) {
        return toArray(this.currentMatrix, column, column + 1);
    }

    public static Double[] toArray(Double[][] matrix, int column) {

        return toArray(matrix, column, column + 1);
    }

    @Override
    public Double[] toArray(int fromColumn, int toColumn) {
        return toArray(this.currentMatrix, fromColumn, toColumn);
    }

    public static Double[] toArray(Double[][] matrix, int fromColumn, int toColumn) {

        checkColumnIndexes(matrix, fromColumn, toColumn);

        Double[] result = new Double[matrix.length * (toColumn - fromColumn)];

        int index = 0;

        for (int j = fromColumn; j < toColumn; j++) {
            for (int i = 0; i < matrix.length; i++) {
                result[index++] = matrix[i][j];
            }
        }

        return result;
    }

    @Override
    public Integer[][] toIntegerMatrix() {

        Integer[][] result = new Integer[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].intValue();
            }
        }

        return result;
    }

    @Override
    public Integer[] toIntegerArray(int column) {

        return toIntegerArray(column, column + 1);
    }

    @Override
    public Integer[] toIntegerArray(int fromColumn, int toColumn) {

        checkColumnIndexes(currentMatrix, fromColumn, toColumn);

        Integer[] result = new Integer[this.rowNumber * (toColumn - fromColumn)];

        int index = 0;

        for (int j = fromColumn; j < toColumn; j++) {
            for (int i = 0; i < this.rowNumber; i++) {
                result[index++] = this.currentMatrix[i][j].intValue();
            }
        }

        return result;
    }

    @Override
    public Float[][] toFloatMatrix() {

        Float[][] result = new Float[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].floatValue();
            }
        }

        return result;
    }

    @Override
    public Float[] toFloatArray(int column) {

        return toFloatArray(column, column + 1);
    }

    @Override
    public Float[] toFloatArray(int fromColumn, int toColumn) {

        checkColumnIndexes(currentMatrix, fromColumn, toColumn);

        Float[] result = new Float[this.rowNumber * (toColumn - fromColumn)];

        int index = 0;

        for (int j = fromColumn; j < toColumn; j++) {
            for (int i = 0; i < this.rowNumber; i++) {
                result[index++] = this.currentMatrix[i][j].floatValue();
            }
        }

        return result;
    }

    @Override
    public Byte[][] toByteMatrix() {

        Byte[][] result = new Byte[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].byteValue();
            }
        }

        return result;
    }

    @Override
    public Byte[] toByteArray(int column) {
        return toByteArray(column, column + 1);
    }

    @Override
    public Byte[] toByteArray(int fromColumn, int toColumn) {

        checkColumnIndexes(currentMatrix, fromColumn, toColumn);

        Byte[] result = new Byte[this.rowNumber * (toColumn - fromColumn)];

        int index = 0;

        for (int j = fromColumn; j < toColumn; j++) {
            for (int i = 0; i < this.rowNumber; i++) {
                result[index++] = this.currentMatrix[i][j].byteValue();
            }
        }

        return result;
    }

    @Override
    public Short[][] toShortMatrix() {

        Short[][] result = new Short[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].shortValue();
            }
        }

        return result;
    }

    @Override
    public Short[] toShortArray(int column) {

        return toShortArray(column, column + 1);
    }

    @Override
    public Short[] toShortArray(int fromColumn, int toColumn) {

        checkColumnIndexes(currentMatrix, fromColumn, toColumn);

        Short[] result = new Short[this.rowNumber * (toColumn - fromColumn)];

        int index = 0;

        for (int j = fromColumn; j < toColumn; j++) {
            for (int i = 0; i < this.rowNumber; i++) {
                result[index++] = this.currentMatrix[i][j].shortValue();
            }
        }

        return result;
    }

    @Override
    public Long[][] toLongMatrix() {

        Long[][] result = new Long[this.rowNumber][this.columnNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                result[i][j] = this.currentMatrix[i][j].longValue();
            }
        }

        return result;
    }

    @Override
    public Long[] toLongArray(int column) {

        return toLongArray(column, column + 1);
    }

    @Override
    public Long[] toLongArray(int fromColumn, int toColumn) {

        checkColumnIndexes(currentMatrix, fromColumn, toColumn);

        Long[] result = new Long[this.rowNumber * (toColumn - fromColumn)];

        int index = 0;

        for (int j = fromColumn; j < toColumn; j++) {
            for (int i = 0; i < this.rowNumber; i++) {
                result[index++] = this.currentMatrix[i][j].longValue();
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "StandardMatrix {\n" +
                content() + "\n" +
                '}';
    }

    public static void setEpsilon(double epsilon) {
        StandardMatrix.epsilon = epsilon;
    }

    public static double getEpsilon() {
        return StandardMatrix.epsilon;
    }
}
