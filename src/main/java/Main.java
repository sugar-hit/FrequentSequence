import data.Sort;
import data.Transfer;
import util.FileOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    //TEST
    public static void main(String[] args) {

        FileOperation fp = new FileOperation();
        // 获取全部正反例文件（文件夹下所有文件）名
        ArrayList<String> positiveFiles = new ArrayList<>();
        ArrayList<String> negativeFiles = new ArrayList<>();

        fp.positiveDirPathReader(positiveFiles);
        fp.negativeDirPathReader(negativeFiles);

        // 获取反例文件所设计的全部反例操作码 -- 停用词（队列）
        ArrayList<String> negativeCtlArray = new ArrayList<>();
        data.Process dataProcess = new data.Process();
        dataProcess.negativeCtlArray(negativeFiles, negativeCtlArray);

        // 统计整理文件中的高频词，保存为HashMap
        HashMap<String, Long> hotDict = new HashMap<>();
        dataProcess.positiveCtlMap(positiveFiles, hotDict);

        // 在高频词中 过滤停用词
        for (String bannedOps: negativeCtlArray) {
            hotDict.put(bannedOps, 0L);
        }

        Sort sort = new Sort();

        // 排序（O(kn) k = 100）
        ArrayList<String> ops = new ArrayList<>();
        ArrayList<Long> opsCount = new ArrayList<>();
        int length = sort.top(100, hotDict, ops, opsCount);

        // 结果输出到文件
        Transfer transfer = new Transfer();
        StringBuffer buffer = new StringBuffer();
//        StringBuffer bufferHex = new StringBuffer();
//        StringBuffer bufferHexBin = new StringBuffer();
        for (int i = 0; i < length; i++) {
//            buffer.append(transfer.outputHex(ops.get(i).toUpperCase()))
//                    .append("/")
//                    .append(opsCount.get(i))
//                    .append('\n');
            buffer.append(ops.get(i).toUpperCase())
                    .append("/")
                    .append(opsCount.get(i))
                    .append('\n');
////            bufferHex.append(transfer.bin2hex(ops.get(i)).toUpperCase())
//                    .append("/")
//                    .append(opsCount.get(i))
//                    .append("\n");
//            bufferHexBin.append(ops.get(i))
//                    .append("/")
//                    .append(opsCount.get(i))
//                    .append("\n");
        }
        try {
            fp.outputFileAppend(buffer.toString());
//            fp.outputFileAppendHex(bufferHex.toString());
//            fp.outputFileAppendHexBin(bufferHexBin.toString());
        } catch (IOException e) {
            System.out.println("Written Error Occurs when outputting.");
        }


//        System.out.println(transfer.longBin2Hex(transfer.longHex2Bin("ABABABABABABABABABAB")).toUpperCase());
//        System.out.println(transfer.longHex2Bin(transfer.longBin2Hex("10101011101010111010101110101011101010111010101110101011101010111010101110101011")));

    }
}
