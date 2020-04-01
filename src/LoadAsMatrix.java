import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadAsMatrix {
    /**
     * 加载本地文件，最后一列需为标签列。
     *
     * @param fileAdd 文件路径
     * @return 矩阵（二维数组）
     * @throws FileNotFoundException
     */
    public static Map load(String fileAdd) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileInputStream(fileAdd));//通过FileInputStream构建Scanner
        ArrayList<Double[]> DataList = new ArrayList<Double[]>();//初始化数据存放list，arrayList中的每一项是一行数据
        while (scanner.hasNext()) {
            String line = scanner.nextLine();//读入一行数据
            String[] data = line.split("\t");//根据空格分隔字符串
            if (data.length != 9) {
                //如果分割后的数据不足9个，说明数据错误，抛弃本条数据
                continue;
            }
            //构建double类型数组，保存本行数据
            Double[] doubleData = new Double[9];
            //通过Float.valueOf方法将字符串转换为浮点数
            for (int i = 0; i < 9; i++) {
                doubleData[i] = Double.valueOf(data[i]);
            }
            //将本行数据添加到所有数据的集合中
            DataList.add(doubleData);
        }
        //将ArrayList转为double类型的二维数组
        int dataSize = DataList.size();
        int dim = DataList.get(0).length;
        double[][] xdata = new double[dataSize][dim - 1];
        double[][] ydata = new double[dataSize][1];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dim; j++) {
                if (j == dim - 1) {
                    ydata[i][0] = DataList.get(i)[j].doubleValue();
                } else {
                    xdata[i][j] = DataList.get(i)[j].doubleValue();
                }
            }
        }

        Map<String, double[][]> result = new HashMap<String, double[][]>();
        result.put("Feature", xdata);
        result.put("Label", ydata);
        return result;
    }
}
