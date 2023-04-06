package matrix;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

public class StandardMatrix<T extends Number> implements IMatrix<T> {

    private Double[][] currentMatrix;

    private int rowNumber;
    private int columnNumber;

    public StandardMatrix(T[][] matrix) {

        if (matrix == null)
            throw new IllegalArgumentException("Matrix cannot be null");

        if (isMatrixEmpty(matrix))
            throw new IllegalArgumentException("Matrix cannot be empty");

        this.currentMatrix = transformToDoubleMatrix(matrix);

        if (isMatrixJagged(this.currentMatrix))
            throw new IllegalArgumentException("Matrix cannot be jagged");

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

    private boolean isMatrixJagged(Double[][] matrix) {
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
            throw new IllegalArgumentException("Matrix cannot be null");

        if (isMatrixEmpty(iMatrix))
            throw new IllegalArgumentException("Matrix cannot be empty");

        if (isMatrixJagged(iMatrix))
            throw new IllegalArgumentException("Matrix cannot be jagged");
    }

    private void checkMatrix(T[][] matrix) {
        if (matrix == null)
            throw new IllegalArgumentException("Matrix cannot be null");

        if (isMatrixEmpty(matrix))
            throw new IllegalArgumentException("Matrix cannot be empty");

        if (isMatrixJagged(transformToDoubleMatrix(matrix)))
            throw new IllegalArgumentException("Matrix cannot be jagged");
    }

    private void checkMatrixDimensionsForSymmetricity(IMatrix<T> iMatrix) {
        if (this.rowNumber != iMatrix.getRowNumber() || this.columnNumber != iMatrix.getColumnNumber())
            throw new IllegalArgumentException("Matrix dimensions must be equal");
    }

    private void checkMatrixDimensionsForSymmetricity(T[][] matrix) {
        if (this.rowNumber != matrix.length || this.columnNumber != matrix[0].length)
            throw new IllegalArgumentException("Matrix dimensions must be equal");
    }

    private void checkMatrixDimensionsForMultiplication(IMatrix<T> iMatrix) {
        if (this.columnNumber != iMatrix.getRowNumber())
            throw new IllegalArgumentException(String
                    .format("Current matrix column number must be equal to the given matrix row number. " +
                            "Current matrix column number: %d, given matrix row number: %d",
                            this.columnNumber, iMatrix.getRowNumber()));
    }

    private void checkMatrixDimensionsForMultiplication(T[][] matrix) {
        if (this.columnNumber != matrix.length)
            throw new IllegalArgumentException(String
                    .format("Current matrix column number must be equal to the given matrix row number. " +
                            "Current matrix column number: %d, given matrix row number: %d",
                            this.columnNumber, matrix.length));
    }

    private void initializeColumnsAndRows() {

        this.rowNumber = this.currentMatrix.length;
        this.columnNumber = this.currentMatrix[0].length;

        if (this.rowNumber == 0 || this.columnNumber == 0)
            throw new IllegalArgumentException("Matrix cannot be empty");

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

    @Override
    public IMatrix<T> add(IMatrix<T> iMatrix) {
        add((T[][]) iMatrix.toMatrix());

        return this;
    }

    @Override
    public IMatrix<T> subtract(IMatrix<T> iMatrix) {
        subtract((T[][]) iMatrix.toMatrix());

        return this;
    }

    @Override
    public IMatrix<T> transpose() {

        if (this.rowNumber == 1 && this.columnNumber == 1)
            return this;

        Double[][] resultMatrix = new Double[this.columnNumber][this.rowNumber];

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = 0; j < this.columnNumber; j++) {
                resultMatrix[j][i] = this.currentMatrix[i][j];
            }
        }

        currentMatrix = resultMatrix;

        initializeColumnsAndRows();

        return this;
    }

    @Override
    public IMatrix<T> multiply(IMatrix<T> iMatrix) {
        multiply((T[][]) iMatrix.toMatrix());

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

    @Override
    public Double sum(int column) {
        return sum(column, column + 1);
    }

    @Override
    public Double sum(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        Double sum = 0.0;

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = fromColumn; j < toColumn; j++) {
                sum += this.currentMatrix[i][j];
            }
        }

        return sum;
    }

    private void checkColumnIndexes(int fromColumn, int toColumn) {
        if (fromColumn < 0 || toColumn < 0)
            throw new IllegalArgumentException("Column indexes cannot be negative");

        if (fromColumn > toColumn)
            throw new IllegalArgumentException("fromColumn index cannot be greater than toColumn index");

        if (fromColumn > this.columnNumber || toColumn > this.columnNumber)
            throw new IllegalArgumentException("Column indexes cannot be greater than the number of columns");
    }

    private void checkColumnIndex(int column) {
        if (column < 0)
            throw new IllegalArgumentException("Column index cannot be negative");

        if (column > this.columnNumber)
            throw new IllegalArgumentException("Column index cannot be greater than the number of columns");
    }

    @Override
    public Double mean() {
        return mean(0, this.columnNumber);
    }

    @Override
    public Double mean(int column) {
        return mean(column, column + 1);
    }

    @Override
    public Double mean(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        return sum(fromColumn, toColumn) / (rowNumber * (toColumn - fromColumn));
    }

    @Override
    public Double max() {
        return max(0, this.columnNumber);
    }

    @Override
    public Double max(int column) {
        return max(column, column + 1);
    }

    @Override
    public Double max(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        Double max = Double.MIN_VALUE;

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = fromColumn; j < toColumn; j++) {
                if (this.currentMatrix[i][j] > max)
                    max = this.currentMatrix[i][j];
            }
        }

        return max;
    }

    @Override
    public Double min() {
        return min(0, this.columnNumber);
    }

    @Override
    public Double min(int column) {
        return min(column, column + 1);
    }

    @Override
    public Double min(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        Double min = Double.MAX_VALUE;

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = fromColumn; j < toColumn; j++) {
                if (this.currentMatrix[i][j] < min)
                    min = this.currentMatrix[i][j];
            }
        }

        return min;
    }


    @Override
    public List<Double> mode() {
        return mode(0, this.columnNumber);
    }

    @Override
    public List<Double> mode(int column) {
        return mode(column, column + 1);
    }

    @Override
    public List<Double> mode(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        List<Double> listOfModes = new ArrayList<>();

        HashMap<Double, Integer> map = new HashMap<>();

        Integer maxCount = 0;

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < this.rowNumber; j++) {

                Double d = this.currentMatrix[j][i];

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

        return variance(0, this.columnNumber);
    }

    @Override
    public Double variance(int column) {

        return variance(column, column + 1);
    }

    @Override
    public Double variance(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        Double mean = mean(fromColumn, toColumn);

        double sumOfSquaredDifferences = 0.0;

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < this.rowNumber; j++) {
                Double d = this.currentMatrix[j][i];
                sumOfSquaredDifferences += Math.pow(d - mean, 2);
            }
        }

        int n = this.rowNumber * (toColumn - fromColumn);

        n = n >= 30 ? n : n - 1;

        return sumOfSquaredDifferences / n;
    }

    @Override
    public Double standardDeviation() {

        return standardDeviation(0, this.columnNumber);
    }

    @Override
    public Double standardDeviation(int column) {

        return standardDeviation(column, column + 1);
    }

    @Override
    public Double standardDeviation(int fromColumn, int toColumn) {

        return Math.sqrt(variance(fromColumn, toColumn));
    }

    @Override
    public Double range() {
        return range(0, this.columnNumber);
    }

    @Override
    public Double range(int column) {
        return range(column, column + 1);
    }

    @Override
    public Double range(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        return max(fromColumn, toColumn) - min(fromColumn, toColumn);
    }

    @Override
    public Double median() {
        return median(0, this.columnNumber);
    }

    @Override
    public Double median(int column) {
        return median(column, column + 1);
    }

    @Override
    public Double median(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        Double[] columnArray = toArray(fromColumn, toColumn);

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

        if (column < 0 || column >= this.columnNumber)
            throw new IllegalArgumentException("Column index is out of bounds");

        Double[] columnArray = toArray(column, column + 1);

        Arrays.sort(columnArray);

        return setColumn(columnArray, column);
    }

    @Override
    public IMatrix<T> sort(int fromColumn, int toColumn) {


        if (fromColumn < 0 || fromColumn >= this.columnNumber)
            throw new IllegalArgumentException("Column index is out of bounds");

        if (toColumn < 0 || toColumn > this.columnNumber)
            throw new IllegalArgumentException("Column index is out of bounds");

        if (fromColumn > toColumn)
            throw new IllegalArgumentException("From column index is greater than to column index");

        for (int i = fromColumn; i < toColumn; i++) {
            sort(i);
        }

        return this;
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max, int column) {
        return minMaxNormalization(min, max, column, column + 1);
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max, int fromColumn, int toColumn) {

        if (min > max)
            throw new IllegalArgumentException("Min value is greater than max value");

        if (min == max)
            throw new IllegalArgumentException("Min value is equal to max value");

        checkColumnIndexes(fromColumn, toColumn);


        for (int i = fromColumn; i < toColumn; i++) {

            Double minDouble = min(i);
            Double maxDouble = max(i);

            for (int j = 0; j < this.rowNumber; j++) {
                Double d = this.currentMatrix[j][i];
                this.currentMatrix[j][i] = (d - minDouble) / (maxDouble - minDouble) * (max - min) + min;
            }
        }

        return this;
    }

    @Override
    public IMatrix<T> minMaxNormalization() {
        return minMaxNormalization(0, 1, 0, this.columnNumber);
    }

    @Override
    public IMatrix<T> minMaxNormalization(int column) {
        return minMaxNormalization(0, 1, column, column + 1);
    }

    @Override
    public IMatrix<T> minMaxNormalization(int fromColumn, int toColumn) {
        return minMaxNormalization(0, 1, fromColumn, toColumn);
    }

    @Override
    public IMatrix<T> zScoreStandardization() {
        return zScoreStandardization(0, this.columnNumber);
    }

    @Override
    public IMatrix<T> zScoreStandardization(int column) {
        return zScoreStandardization(column, column + 1);
    }

    @Override
    public IMatrix<T> zScoreStandardization(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return this;

        for (int i = fromColumn; i < toColumn; i++) {

            double meanOfColumn = mean(i);
            double sd = standardDeviation(i);

            for (int j = 0; j < this.rowNumber; j++) {
                double d = this.currentMatrix[j][i];
                this.currentMatrix[j][i] = (d - meanOfColumn) / sd;
            }
        }

        return this;
    }

    @Override
    public Integer l0Norm(int column) {
        checkColumnIndex(column);

        if (rowNumber == 0 || columnNumber == 0)
            return 0;

        int count = 0;

        for (int i = 0; i < this.rowNumber; i++) {
            if (currentMatrix[i][column] != 0) count++;
        }

        return count;
    }

    @Override
    public Double l1Norm(int column) {
        checkColumnIndex(column);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        double absSum = 0d;

        for (int i = 0; i < this.rowNumber; i++)
            absSum += Math.abs(currentMatrix[i][column]);

        return absSum;
    }

    @Override
    public Double l2Norm(int column) {
        checkColumnIndex(column);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        double sumOfSquares = 0d;

        for (int i = 0; i < this.rowNumber; i++)
            sumOfSquares += Math.pow(currentMatrix[i][column], 2);

        return Math.sqrt(sumOfSquares);
    }

    @Override
    public Double lInfinityNorm(int column) {
        checkColumnIndex(column);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        double absMax = Double.MIN_VALUE;

        for (int i = 0; i < this.rowNumber; i++) {
            if (Math.abs(currentMatrix[i][column]) > absMax) absMax = Math.abs(currentMatrix[i][column]);
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

    @Override
    public boolean isSymmetric() {

        // TODO
        return false;
    }

    @Override
    public boolean isIdentity() {

        // TODO
        return false;
    }

    @Override
    public boolean isDiagonal() {
        // TODO
        return false;
    }

    @Override
    public boolean isAntiDiagonal() {
        // TODO
        return false;
    }

    @Override
    public Double determinant() {
        // TODO
        return null;
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
        checkColumn(column, index);

        for (int i = 0; i < column.length; i++) {
            this.currentMatrix[i][index] = column[i].doubleValue();
        }

        return this;
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
            throw new IllegalArgumentException("Column index must be between 0 and " + (this.columnNumber - 1));
    }

    private void checkColumn(Double[] column, int index) {
    }

    @Override
    public Double[] popColumn() {
        return popColumn(this.columnNumber - 1);
    }

    @Override
    public IMatrix<T> map(Function<Double, Double> function) {
        return map(0, this.columnNumber, function);
    }

    @Override
    public IMatrix<T> map(int column, Function<Double, Double> function) {
        return map(column, column + 1, function);
    }

    @Override
    public IMatrix<T> map(int fromColumn, int toColumn, Function<Double, Double> function) {

        checkColumnIndexes(fromColumn, toColumn);

        Objects.requireNonNull(function);

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < this.rowNumber; j++) {
                this.currentMatrix[j][i] = function.apply(this.currentMatrix[j][i]);
            }
        }

        return this;
    }

    @Override
    public Double reduce(int column, BinaryOperator<Double> accumulator) {
        return reduce(column, column + 1, 0d, accumulator);
    }

    @Override
    public Double reduce(int fromColumn, int toColumn, BinaryOperator<Double> accumulator) {
        return reduce(fromColumn, toColumn, 0d, accumulator);
    }

    @Override
    public Double reduce(int fromColumn, int toColumn, Double identity, BinaryOperator<Double> accumulator) {
        checkColumnIndexes(fromColumn, toColumn);

        Objects.requireNonNull(accumulator);

        double result = identity;

        for (int i = fromColumn; i < toColumn; i++) {
            for (int j = 0; j < this.rowNumber; j++) {
                result = accumulator.apply(result, this.currentMatrix[j][i]);
            }
        }

        return result;
    }

    @Override
    public Double reduce(int column, Double identity, BinaryOperator<Double> accumulator) {
        return reduce(column, column + 1, identity, accumulator);
    }

    @Override
    public void forEach(int column, Consumer<Double> consumer) {
        checkColumnIndex(column);

        Objects.requireNonNull(consumer);

        for (int i = 0; i < this.rowNumber; i++) {
            consumer.accept(this.currentMatrix[i][column]);
        }
    }

    @Override
    public Double[] popColumn(int index) {

        if (index < 0 || index >= this.columnNumber)
            throw new IllegalArgumentException("Column index must be between 0 and " + (this.columnNumber - 1));

        if (this.columnNumber <= 2)
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
            sb.append("[");
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

        return toArray(column, column + 1);
    }

    @Override
    public Double[] toArray(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        Double[] result = new Double[this.rowNumber * (toColumn - fromColumn)];

        int index = 0;

        for (int j = fromColumn; j < toColumn; j++) {
            for (int i = 0; i < this.rowNumber; i++) {
                result[index++] = this.currentMatrix[i][j];
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

        checkColumnIndexes(fromColumn, toColumn);

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

        checkColumnIndexes(fromColumn, toColumn);

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

        checkColumnIndexes(fromColumn, toColumn);

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

        checkColumnIndexes(fromColumn, toColumn);

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

        checkColumnIndexes(fromColumn, toColumn);

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
        return "BasicMatrix {\n" +
                content() + "\n" +
                '}';
    }
}
