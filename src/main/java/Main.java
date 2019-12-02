import data.Sort;
import data.Transfer;
import util.FileOperation;

import java.io.IOException;
import java.util.*;

public class Main {
    //TEST
    public static void main(String[] args) {
        System.out.println("----------------------------------------------------");
        System.out.println("高频次操作码检测  / (C) 2019 Harbin Institute of Technology, All right reserved.");
        System.out.println("----------------------------------------------------");
        FileOperation fp = new FileOperation();
        // 获取全部正反例文件（文件夹下所有文件）名
        ArrayList<String> positiveFiles = new ArrayList<>();
        ArrayList<String> negativeFiles = new ArrayList<>();

        fp.positiveDirPathReader(positiveFiles);
        fp.negativeDirPathReader(negativeFiles);

        // 初始化正反例文件分析所需要的数据处理工具：dataProcess
        ArrayList<String> negativeCtlArray = new ArrayList<>();
        data.Process dataProcess = new data.Process();
//        dataProcess.negativeCtlArray(negativeFiles, negativeCtlArray);

        // 统计整理文件中的高频词，保存为HashMap
        System.out.print("正在读取正例文件...\n如果正例文件较多且文件较大将花费较长时间，请耐心等待");
        HashMap<String, Integer> hotDict = new HashMap<>();
        dataProcess.positiveCtlMap(positiveFiles, hotDict);
        for (int i = 0; i < 26; i++) {
            System.out.print("\b");
        }
        System.out.println("正例文件读取完成");
        System.out.println("----------------------------------------------------");
        // 在高频词中 过滤停用词
//        for (String bannedOps: negativeCtlArray) {
//            hotDict.put(bannedOps, 0L);
//        }
//        try {
//            dataProcess.removeStopOpreation(negativeFiles, hotDict);
//        } catch (IOException e) {
//            System.out.println("Something wrong occurs, due complexity much intersected negative operations removed in hot keys.");
//        }

        // 分析高频操作序列，排序。
        System.out.print("正在分析正例文件...\n如果正例文件较多且文件较大将花费较长时间，请耐心等待");
        Sort sort = new Sort();
        // 排序（O(n^2) ^^ = 100）
//        ArrayList<String> ops = new ArrayList<>();
//        ArrayList<Integer> opsCount = new ArrayList<>();
//        int length = sort.top(hotDict.size(), hotDict, ops, opsCount);
        LinkedHashMap<String, Integer> hotDictSorted = sort.sort(hotDict);
        for (int i = 0; i < 26; i++) {
            System.out.print("\b");
        }
        System.out.println("正例文件分析完成");
        System.out.println("----------------------------------------------------");
        // 在高频词中 过滤停用词
        System.out.println("正在读取反例文件...\n如果反例文件较多且文件较大将花费较长时间，请耐心等待");
        for (int i = 0; i < 26; i++) {
            System.out.print("\b");
        }
        try {
            dataProcess.fastRemoveStopOpreation(negativeFiles, hotDictSorted);
        } catch (IOException e) {
            System.out.println("Something wrong occurs, due complexity much intersected negative operations removed in hot keys.");
        }
        System.out.print("\n");
        System.out.println("反例文件分析完成");
        System.out.println("----------------------------------------------------");
        // 结果输出至文件
        System.out.print("正在向文件输出...");
        Transfer transfer = new Transfer();
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (String str: hotDictSorted.keySet()) {
            if (i >= 100)
                break;
            buffer.append(transfer.outputHex(str.toUpperCase()))
                    .append("/")
                    .append(hotDictSorted.get(str))
                    .append('\n');
            i++;
        }
        // 结果输出到文件
//        Transfer transfer = new Transfer();
//        StringBuffer buffer = new StringBuffer();
////        StringBuffer bufferHex = new StringBuffer();
////        StringBuffer bufferHexBin = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            buffer.append(transfer.outputHex(ops.get(i).toUpperCase()))
//                    .append("/")
//                    .append(opsCount.get(i))
//                    .append('\n');
////            buffer.append(ops.get(i).toUpperCase())
////                    .append("/")
////                    .append(opsCount.get(i))
////                    .append('\n');
//////            bufferHex.append(transfer.bin2hex(ops.get(i)).toUpperCase())
////                    .append("/")
////                    .append(opsCount.get(i))
////                    .append("\n");
////            bufferHexBin.append(ops.get(i))
////                    .append("/")
////                    .append(opsCount.get(i))
////                    .append("\n");
//        }
        try {
            fp.outputFileAppend(buffer.toString());
//            fp.outputFileAppendHex(bufferHex.toString());
//            fp.outputFileAppendHexBin(bufferHexBin.toString());
        } catch (IOException e) {
            System.out.println("Written Error Occurs when outputting.");
        }
        for (i = 0; i < 10; i++) {
            System.out.print("\b");
        }
        System.out.println("成功检测并输出。请查阅配置文件处已设定好的结果文件。");
        System.out.println("----------------------------------------------------");
//        for (String s : ops) {
//            System.out.format("%s", s);
//        }

//        System.out.println(transfer.longBin2Hex(transfer.longHex2Bin("ABABABABABABABABABAB")).toUpperCase());
//        System.out.println(transfer.longHex2Bin(transfer.longBin2Hex("10101011101010111010101110101011101010111010101110101011101010111010101110101011")));

    }
}
