import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {


        Map dataset = LoadAsMatrix.load("/Users/icu/Documents/java learning/Regression/data/agg.txt");//数据载入 join数据需要用LoadAsMatrix.loads加载
        double[][] xdata = (double[][]) dataset.get("Feature");
        double[][] ydata = (double[][]) dataset.get("Label");

        double[][] xdataNorm = Regularization.normalize4ZScore(xdata); //规范化
        double[][] ydataNorm = Regularization.normalize4ZScore(MatrixOperator.getColumn(ydata, 0)); //取出标签矩阵中的第0列并规范化，0对应CPU消耗，1对应内存消耗，2对应时间消耗


        RidgeRegression ridgeRegressionModel = new RidgeRegression();
        //用正规方程进行回归分析
        ridgeRegressionModel.fit(xdataNorm, ydataNorm, 0.2);


        double[][] ydataNorm1 = Regularization.normalize4ZScore(MatrixOperator.getColumn(ydata, 1)); //取出标签矩阵中的第0列并规范化，0对应CPU消耗，1对应内存消耗，2对应时间消耗
        RidgeRegression ridgeRegressionModel1 = new RidgeRegression();
        //用正规方程进行回归分析
        ridgeRegressionModel1.fit(xdataNorm, ydataNorm1, 0.2);


/*
        //用梯度下降进行回归分析
        double[][] ws = {{1},{1}, {1}, {1}};
        ridgeRegressionModel.fit(xdataNorm, ydataNorm,  0.2,ws,0.01,2000000);
*/

        //model to file
        ModelOperation.savetoFile(ridgeRegressionModel, "/Users/icu/Documents/java learning/Regression/model.pkl");

        //file to model
        RidgeRegression model = ModelOperation.loadasModel("/Users/icu/Documents/java learning/Regression/model.pkl");

        //预测
        // double[][] x = {{500, 6000, 2999}};//预测fliter时用的X的格式
        double[][] x = {{500, 6000, 2999, 100}};//预测agg时用的X的格式
        //double[][] x = {{ 500,6000,0,600000,70000,500,1243400,98233,0,0,0,0,1}};//预测join时用的X的格式
        System.out.println(model.predict(x));


    }
}
