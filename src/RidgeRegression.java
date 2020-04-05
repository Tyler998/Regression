import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;


import java.io.*;

public class RidgeRegression implements Serializable {
    private static final long serialVersionUID = 1234567L;
    private RealMatrix ws;//各个特征的权重组成的矩阵,最后一个维度是bias
    private int xSize;//样本集大小
    private int dim;//样本集维度
    private double scroe;//模型评分

    /**
     * 基于最小二乘法的岭回归分析
     *
     * @param xData 输入样本
     * @param yData 样本标签
     * @param lam   惩罚系数
     * @return 权重估计值
     */
    public void fit(double[][] xData, double[][] yData, double lam) {
        addColumn(xData, 1);//训练集上增加一列，值为1
        RealMatrix xMat = new Array2DRowRealMatrix(xData); //将数组转化为矩阵
        RealMatrix yMat = new Array2DRowRealMatrix(yData); //将数组转化为矩阵

        RealMatrix xTx = xMat.transpose().multiply(xMat);//xtx=x.T * x
        this.dim = xMat.getColumnDimension();
        RealMatrix denom = xTx.add(diagonalMatrix(dim, lam));//x.T * x + lam * I
        this.ws = inverseMatrix(denom).multiply(xMat.transpose().multiply(yMat));// (x.T * x + lam * I).I * x.T * y
    }

    /**
     * 基于梯度下降的岭回归分析
     *
     * @param xData         输入样本
     * @param yData         样本标签
     * @param lam          惩罚系数
     * @param alpha         学习率
     * @param initialWeight 初始权重
     * @param times         迭代次数
     */
    public void fit(double[][] xData, double[][] yData, double lam, double[][] initialWeight, double alpha, int times) {
        addColumn(xData, 1);//训练集上增加一列，值为1
        RealMatrix xMat = new Array2DRowRealMatrix(xData); //将数组转化为矩阵
        this.dim = xMat.getColumnDimension();
        RealMatrix yMat = new Array2DRowRealMatrix(yData); //将数组转化为矩阵
        RealMatrix ws = new Array2DRowRealMatrix(initialWeight); //将数组转化为矩阵

        this.xSize = xMat.getRowDimension();
        RealMatrix xMat_T = xMat.transpose();
        for (int i = 0; i < times; i++) {
            RealMatrix grad = xMat_T.multiply(xMat).add(diagonalMatrix(dim, lam)).multiply(ws).subtract(xMat_T.multiply(yMat));
            RealMatrix learnRateMatrx = diagonalMatrix(dim, alpha/ this.xSize);//  学习率/n
            ws = ws.subtract(learnRateMatrx.multiply(grad));
            if (i % 5000 == 0) {
                System.out.println(ws);
            }
        }
        this.ws = ws;
    }

    /**
     * 根据输入的特征数据预测结果
     *
     * @param xData
     * @return 各个特征权重的估计值
     */
    public RealMatrix predict(double[][] xData) throws Exception {
        addColumn(xData, 1);
        RealMatrix xMat = new Array2DRowRealMatrix(xData);
        if (ws != null) {
            return xMat.multiply(this.ws);
        } else {
            throw new Exception("please fit model first！");
        }

    }

    /**
     * 计算矩阵的逆
     *
     * @param matrix 矩阵
     * @return 逆矩阵
     */
    public RealMatrix inverseMatrix(RealMatrix matrix) {
        RealMatrix result = new LUDecomposition(matrix).getSolver().getInverse();
        return result;
    }

    /**
     * 计算模型的r2_score
     *
     * @param yData        测试集Y
     * @param yDataPredict 预测出的Y
     * @return
     */
    public double calculateScroe(double[][] yData, RealMatrix yDataPredict) {
        //TODO

        double[] yDataTemp = new double[yData.length];
        for (int i = 0; i < yData.length; i++) {
            yDataTemp[i] = yData[i][0];
        }
        double yData_var = Regularization.variance(yDataTemp);

        double[][] yDataPredict_ = yDataPredict.getData();
        double sum = 0;
        for (int i = 0; i < yData.length; i++) {
            sum += Math.pow(yData[i][0] - yDataPredict_[i][0], 2);
        }
        this.scroe = (1 - sum / yData_var);
        return this.scroe;

    }

    /**
     * 生成对角矩阵。
     *
     * @param dim        矩阵的维度
     * @param eigenvalue 特征值。
     * @return
     */
    public RealMatrix diagonalMatrix(int dim, double eigenvalue) {
        double[][] I = new double[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == j) {
                    I[i][j] = eigenvalue;
                } else {
                    I[i][j] = 0;
                }
            }
        }
        RealMatrix result = new Array2DRowRealMatrix(I);
        return result;
    }

    /**
     * 矩阵右增元素相等的一列
     *
     * @param data  需要更改的矩阵
     * @param value 要增加的列的特征值
     */

    public void addColumn(double[][] data, float value) {
        for (int i = 0; i < data.length; i++) {
            int dim = data[i].length;

            double[] tempArray = new double[dim + 1];
            for (int j = 0; j < dim; j++) {
                tempArray[j] = data[i][j];
            }
            tempArray[dim] = value;
            data[i] = tempArray;
        }

    }



    public RealMatrix getWs() {
        return ws;
    }

    public void setWs(RealMatrix ws) {
        this.ws = ws;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public double getScroe() {
        return scroe;
    }

    public void setScroe(double scroe) {
        this.scroe = scroe;
    }

    @Override
    public String toString() {
        return "ridgeRegression{" +
                "ws=" + ws +
                ", xSize=" + xSize +
                ", dim=" + dim +
                ", scroe=" + scroe +
                '}';
    }
}
