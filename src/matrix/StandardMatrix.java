package matrix;

import java.util.List;

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

        this.rowNumber = matrix.length;
        this.columnNumber = matrix[0].length;
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
    public IMatrix<T> dot(T[][] matrix) {

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
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> dot(IMatrix<T> iMatrix) {
        dot((T[][]) iMatrix.toMatrix());

        return this;
    }

    @Override
    public Double sum() {

        return sum(0, this.columnNumber - 1);
    }

    @Override
    public Double sum(int column) {
        return sum(column, column);
    }

    @Override
    public Double sum(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        Double sum = 0.0;

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = fromColumn; j <= toColumn; j++) {
                sum += this.currentMatrix[i][j];
            }
        }

        return sum;
    }

    private void checkColumnIndexes(int fromColumn, int toColumn) {
        if (fromColumn < 0 || toColumn < 0)
            throw new IllegalArgumentException("Column indexes cannot be negative");

        if (fromColumn > toColumn)
            throw new IllegalArgumentException("From column index cannot be greater than to column index");

        if (fromColumn >= this.columnNumber || toColumn >= this.columnNumber)
            throw new IllegalArgumentException("Column indexes cannot be greater than the number of columns");
    }

    @Override
    public Double mean() {
        return mean(0, this.columnNumber - 1);
    }

    @Override
    public Double mean(int column) {
        return mean(column, column);
    }

    @Override
    public Double mean(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        return sum(fromColumn, toColumn) / (rowNumber * (toColumn - fromColumn + 1));
    }

    @Override
    public Double max() {
        return max(0, this.columnNumber - 1);
    }

    @Override
    public Double max(int column) {
        return max(column, column);
    }

    @Override
    public Double max(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        Double max = Double.MIN_VALUE;

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = fromColumn; j <= toColumn; j++) {
                if (this.currentMatrix[i][j] > max)
                    max = this.currentMatrix[i][j];
            }
        }

        return max;
    }

    @Override
    public Double min() {
        return min(0, this.columnNumber - 1);
    }

    @Override
    public Double min(int column) {
        return min(column, column);
    }

    @Override
    public Double min(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        if (rowNumber == 0 || columnNumber == 0)
            return 0.0;

        Double min = Double.MAX_VALUE;

        for (int i = 0; i < this.rowNumber; i++) {
            for (int j = fromColumn; j <= toColumn; j++) {
                if (this.currentMatrix[i][j] < min)
                    min = this.currentMatrix[i][j];
            }
        }

        return min;
    }


    @Override
    public List<Double> mode() {
        // TODO
        return null;
    }

    @Override
    public List<Double> mode(int column) {
        // TODO
        return null;
    }

    @Override
    public List<Double> mode(int fromColumn, int toColumn) {
        // TODO
        return null;
    }

    @Override
    public Double variance() {
        // TODO
        return null;
    }

    @Override
    public Double variance(int column) {
        // TODO
        return null;
    }

    @Override
    public Double variance(int fromColumn, int toColumn) {
        // TODO
        return null;
    }

    @Override
    public Double standardDeviation() {

        // TODO
        return null;
    }

    @Override
    public Double standardDeviation(int column) {
        // TODO
        return null;
    }

    @Override
    public Double standardDeviation(int fromColumn, int toColumn) {
        // TODO
        return null;
    }

    @Override
    public Double range() {
        return range(0, this.columnNumber - 1);
    }

    @Override
    public Double range(int column) {
        return range(column, column);
    }

    @Override
    public Double range(int fromColumn, int toColumn) {

        checkColumnIndexes(fromColumn, toColumn);

        return max(fromColumn, toColumn) - min(fromColumn, toColumn);
    }

    @Override
    public Double median(int column) {
        return null;
    }

    @Override
    public IMatrix<T> sort(int column) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> sort(int fromColumn, int toColumn) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max, int column) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization(long min, long max, int fromColumn, int toColumn) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization(int column) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> minMaxNormalization(int fromColumn, int toColumn) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> zScoreStandardization() {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> zScoreStandardization(int column) {
        // TODO
        return null;
    }

    @Override
    public IMatrix<T> zScoreStandardization(int fromColumn, int toColumn) {
        // TODO
        return null;
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

    private String content() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Matrix has %d rows and %d columns. Matrix:\n[\n",
                this.rowNumber, this.columnNumber));

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
        // TODO
        return new Double[0];
    }

    @Override
    public Double[] toArray(int fromColumn, int toColumn) {
        // TODO
        return new Double[0];
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
        // TODO
        return new Integer[0];
    }

    @Override
    public Integer[] toIntegerArray(int fromColumn, int toColumn) {
        // TODO
        return new Integer[0];
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
        // TODO
        return new Float[0];
    }

    @Override
    public Float[] toFloatArray(int fromColumn, int toColumn) {
        // TODO
        return new Float[0];
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
        // TODO
        return new Byte[0];
    }

    @Override
    public Byte[] toByteArray(int fromColumn, int toColumn) {
        // TODO
        return new Byte[0];
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
        // TODO
        return new Short[0];
    }

    @Override
    public Short[] toShortArray(int fromColumn, int toColumn) {
        // TODO
        return new Short[0];
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
        // TODO
        return new Long[0];
    }

    @Override
    public Long[] toLongArray(int fromColumn, int toColumn) {
        // TODO
        return new Long[0];
    }

    @Override
    public String toString() {
        return "BasicMatrix {\n" +
                content() + "\n" +
                '}';
    }
}
