import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {


        Map dataset = LoadAsMatrix.load("/Users/icu/Documents/java learning/Regression/data/data.txt");//数据载入
        double[][] xdata = (double[][]) dataset.get("Feature");
        double[][] ydata = (double[][]) dataset.get("Label");

        double[][] xdataNorm = Regularization.normalize4ZScore(xdata); //规范化
        double[][] ydataNorm = Regularization.normalize4ZScore(ydata); //规范化

        RidgeRegression ridgeRegressionModel = new RidgeRegression();
        ridgeRegressionModel.fit(xdataNorm, ydataNorm, (float) 0.2);


        //model to file
        ModelOperation.savetoFile(ridgeRegressionModel, "/Users/icu/Documents/java learning/Regression/model.pkl");

        //file to model
        RidgeRegression model = ModelOperation.loadasModel("/Users/icu/Documents/java learning/Regression/model.pkl");

        double[][] x = {{1, 1, 1, 1, 1, 1, 1, 1}};
        System.out.println(model.predict(x));

    }
}
