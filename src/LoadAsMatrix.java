import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class LoadAsMatrix {
    /**
     * 加载本地filter、agg数据集,前三列为标签列，后面是特征列。
     *
     * @param fileAdd 文件路径
     * @return 矩阵（二维数组）
     * @throws FileNotFoundException
     */
    public static Map load(String fileAdd) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileInputStream(fileAdd));//通过FileInputStream构建Scanner
        ArrayList<Double[]> DataList = new ArrayList<Double[]>();//初始化数据存放list，arrayList中的每一项是一行数据
        int linnumber = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();//读入一行数据
            String[] data = line.split("\t");//根据空格分隔字符串

            /*
            if (data.length != 9) {
                //如果分割后的数据不足9个，说明数据错误，抛弃本条数据
                continue;
            }
            */

            //构建double类型数组，保存本行数据
            Double[] doubleData = new Double[data.length];
            if (linnumber != 0) {
                //通过Float.valueOf方法将字符串转换为浮点数
                for (int i = 0; i < data.length; i++) {
                    doubleData[i] = Double.valueOf(data[i]);
                }
                //将本行数据添加到所有数据的集合中
                DataList.add(doubleData);
            }
            linnumber++;
        }
        //将ArrayList转为double类型的二维数组
        int dataSize = DataList.size();
        int dim = DataList.get(0).length;
        double[][] xdata = new double[dataSize][dim - 3];
        double[][] ydata = new double[dataSize][3];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dim; j++) {
                if (j < 3) {
                    ydata[i][j] = DataList.get(i)[j].doubleValue();
                } else {
                    xdata[i][j - 3] = DataList.get(i)[j].doubleValue();
                }
            }
        }

        Map<String, double[][]> result = new HashMap<String, double[][]>();
        result.put("Feature", xdata);
        result.put("Label", ydata);
        return result;
    }

    /**
     * 加载本地join数据集,前三列为标签列，后面是特征列,。
     *
     * @param fileAdd
     * @return
     * @throws FileNotFoundException
     */

    public static Map loads(String fileAdd) throws FileNotFoundException {

        Scanner scanner = new Scanner(new FileInputStream(fileAdd));//通过FileInputStream构建Scanner
        ArrayList<Double[]> DataList = new ArrayList<Double[]>();//初始化数据存放list，arrayList中的每一项是一行数据
        int linnumber = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();//读入一行数据
            String[] data = line.split("\t");//根据空格分隔字符串

            /*
            if (data.length != 9) {
                //如果分割后的数据不足9个，说明数据错误，抛弃本条数据
                continue;
            }
            */

            //构建double类型数组，保存本行数据
            int number = data.length;
            Double[] doubleData = new Double[number + 3];
            if (linnumber != 0) {
                //通过Float.valueOf方法将字符串转换为浮点数
                for (int i = 0; i < data.length - 1; i++) {
                    doubleData[i] = Double.valueOf(data[i]);
                }
                switch (data[number - 1]) {
                    case "INNER JOIN":
                        doubleData[number - 1] = Double.valueOf(1);
                        doubleData[number] = Double.valueOf(0);
                        doubleData[number + 1] = Double.valueOf(0);
                        doubleData[number + 2] = Double.valueOf(0);
                        break;

                    case "LEFT JOIN":
                        doubleData[number - 1] = Double.valueOf(0);
                        doubleData[number] = Double.valueOf(1);
                        doubleData[number + 1] = Double.valueOf(0);
                        doubleData[number + 2] = Double.valueOf(0);
                        break;
                    case "RIGHT JOIN":
                        doubleData[number - 1] = Double.valueOf(0);
                        doubleData[number] = Double.valueOf(0);
                        doubleData[number + 1] = Double.valueOf(1);
                        doubleData[number + 2] = Double.valueOf(0);
                        break;
                    case "FULL OUT JOIN":
                        doubleData[number - 1] = Double.valueOf(0);
                        doubleData[number] = Double.valueOf(0);
                        doubleData[number + 1] = Double.valueOf(0);
                        doubleData[number + 2] = Double.valueOf(1);
                        break;
                    default:
                        System.out.println("Unknown join type");
                }
                //将本行数据添加到所有数据的集合中
                DataList.add(doubleData);
            }
            linnumber++;
        }
        //将ArrayList转为double类型的二维数组
        int dataSize = DataList.size();
        int dim = DataList.get(0).length;
        double[][] xdata = new double[dataSize][dim - 3];
        double[][] ydata = new double[dataSize][3];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dim; j++) {
                if (j < 3) {
                    ydata[i][j] = DataList.get(i)[j].doubleValue();
                } else {
                    xdata[i][j - 3] = DataList.get(i)[j].doubleValue();
                }
            }
        }

        Map<String, double[][]> result = new HashMap<String, double[][]>();
        result.put("Feature", xdata);
        result.put("Label", ydata);
        return result;
    }
}
