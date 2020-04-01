public class Regularization {
    /**
     * 0均值\标准差规范化 公式：X(norm) = (X - μ) / σ
     * X(norm) = (X - 均值) / 标准差
     *
     * @param points 原始数据
     * @return 规范化后的数据
     */
    public static double[][] normalize4ZScore(double[][] points) {
        if (points == null || points.length < 1) {
            return points;
        }
        double[][] result = new double[points.length][points[0].length];
        double[] matrixJ;
        double avg;
        double std;
        for (int j = 0; j < points[0].length; j++) {
            matrixJ = getMatrixCol(points, j);
            avg = average(matrixJ);
            //std = standardDeviation(matrixJ);
            std = variance(matrixJ);
            for (int i = 0; i < points.length; i++) {
                result[i][j] = std == 0 ? points[i][j] : (points[i][j] - avg) / std;
            }
        }
        return result;
    }

    /**
     * 计算方差
     *
     * @param x x
     * @return 方差
     */
    public static double variance(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x[i];
        }
        double dAve = sum / m;//求平均值
        double dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    /**
     * 计算标准差：方法为σ=sqrt(s^2)
     *
     * @param x x
     * @return 标准差
     */
    public static double standardDeviation(double[] x) {
        return Math.sqrt(variance(x));
    }

    /**
     * 计算平均值
     *
     * @param x x
     * @return 平均值
     */
    public static double average(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum += x[i];
        }
        double dAve = sum / m;
        return dAve;
    }

    /**
     * 获取矩阵的某一列
     *
     * @param points points
     * @param column column
     * @return double[]
     */
    public static double[] getMatrixCol(double[][] points, int column) {
        double[] matrixJ = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            matrixJ[i] = points[i][column];
        }
        return matrixJ;
    }
}
