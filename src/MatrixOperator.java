public class MatrixOperator {
    /**
     * 矩阵右增一列
     *
     * @param data   需要更改的矩阵
     * @param column 要增加的列
     */

    public static double[][] addColumnOnRight(double[][] data, double[][] column) {
        for (int i = 0; i < data.length; i++) {
            int dim = data[i].length;

            double[] tempArray = new double[dim + 1];
            for (int j = 0; j < dim; j++) {
                tempArray[j] = data[i][j];
            }
            tempArray[dim] = column[i][0];
            data[i] = tempArray;
        }
        return data;

    }

    /**
     * 矩阵左增一列
     *
     * @param data   需要更改的矩阵
     * @param column 要增加的列
     */

    public static double[][] addColumnOnleft(double[][] data, double[][] column) {
        for (int i = 0; i < data.length; i++) {
            int dim = data[i].length;
            double[] tempArray = new double[dim + 1];
            for (int j = 1; j < dim + 1; j++) {
                tempArray[j] = data[i][j - 1];
            }
            tempArray[0] = column[i][0];
            data[i] = tempArray;
        }
        return data;
    }

    /**
     * 矩阵删除一列
     *
     * @param data         需要更改的矩阵
     * @param columnNumber 要增加的列
     */

    public static double[][] rmColumn(double[][] data, int columnNumber) {
        for (int i = 0; i < data.length; i++) {
            int dim = data[i].length;
            double[] tempArray = new double[dim - 1];
            int number = 0;
            for (int j = 0; j < dim; j++) {
                if (j != columnNumber) {
                    tempArray[number] = data[i][j];
                    number++;
                }
            }
            data[i] = tempArray;
        }
        return data;
    }

    /**
     * 矩阵拼接
     *
     * @param Ddata 目的矩阵
     * @param Sdata 源矩阵
     */

    public static double[][] matrixCombine(double[][] Ddata, double[][] Sdata) {
        if (Ddata.length == Sdata.length) {
            for (int i = 0; i < Ddata.length; i++) {
                int sdim = Sdata[i].length;
                int ddim = Ddata[i].length;
                double[] tempArray = new double[ddim + sdim];
                for (int j = 0; j < ddim + sdim; j++) {
                    if (j < ddim) {
                        tempArray[j] = Ddata[i][j];
                    } else {
                        tempArray[j] = Sdata[i][j - ddim];
                    }
                }
                Ddata[i] = tempArray;
            }

        } else {
            System.out.println("The splicing failed when the number of rows of the matrix is different.");
        }
        return Ddata;
    }

    /**
     * 矩阵列索引
     *
     * @param data         待索引矩阵
     * @param columnNumber 列编号
     */

    public static double[][] getColumn(double[][] data, int columnNumber) {
        int dataSize = data.length;
        double[][] result = new double[dataSize][1];
        for (int i = 0; i < dataSize; i++) {
            result[i][0] = data[i][columnNumber];
        }
        return result;
    }

}

