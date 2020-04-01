import java.io.*;

/**
 * 将模型对象序列化与反序列化
 */
public class ModelOperation {
    /**
     * 序列化
     * @param ridgeRegressionModel
     * @param add 路径
     * @throws IOException
     */

    public static void savetoFile(RidgeRegression ridgeRegressionModel, String add) throws IOException {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(new File(add)));
        writer.writeObject(ridgeRegressionModel);
        writer.close();
    }

    /**
     * 反序列化
     * @param add 路径
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static RidgeRegression loadasModel(String add) throws IOException, ClassNotFoundException {
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream(new File(add)));
        RidgeRegression readObject = (RidgeRegression) reader.readObject();
        return readObject;
    }
}
