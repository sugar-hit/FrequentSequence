package data;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

public class Process {

    /**
     * negativeCtlArray()
     * 根据提供的反例文件列表获得反例操作集（词典）
     * @param opFilePathList 提供存有反例操作文件的列表（文件地址，ArrayList<String>类型）
     * @param opList 用于保存反例操作集（数据返回值，ArrayList<String>类型）
     */
    public void negativeCtlArray (ArrayList<String> opFilePathList, ArrayList<String> opList) {
        System.out.println("正在加载反例文件。" +
                "\n" +
                "如果反例文件较多且文件较大将花费较长时间，请耐心等待。");
        if (opFilePathList == null)
            return;
        if (opFilePathList.isEmpty())
            return;
        if (opList == null)
            opList = new ArrayList<>();
        for (String opFilePath : opFilePathList) {
            try {
                negativeCtlArraySingleFile(opFilePath, opList);
            }
            catch (IOException e) {
                System.out.println("File " + opFilePath + "got something wrong.");
            }
        }
    }

    private void negativeCtlArraySingleFile (String opFilePathStr, ArrayList<String> opList) throws IOException {
//        System.out.println("--------------------------------------------------------------------");
//        System.out.println(opFilePathStr);
//        System.out.println("--------------------------------------------------------------------");
        if (opFilePathStr == null)
            return ;
        if (opList == null)
            opList = new ArrayList<>();
        Transfer transfer = new Transfer();

        File file = new File(opFilePathStr);
        FileInputStream fileInputStream = new FileInputStream(file);
        StringWriter stringWriter = new StringWriter();
        int len = 1;
        byte[] temp = new byte[len];
        for (; (fileInputStream.read(temp, 0 , len)) != -1; ) {
            if (temp[0] > 0xf && temp[0] <= 0xff) {
                stringWriter.write(Integer.toHexString(temp[0]));
            } else if (temp[0] >= 0x0 && temp[0] <= 0xf) {//对于只有1位的16进制数前边补“0”
                stringWriter.write("0" + Integer.toHexString(temp[0]));
            } else { //对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
                stringWriter.write(Integer.toHexString(temp[0]).substring(6));
            }
        }
//        System.out.println(stringWriter.toString());
        removeRepeatedOperationList(stringWriter.toString(), opList);
//
//
//
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        String str = null;
//
//        while (null != (str = reader.readLine())) {
////            removeRepeatedOperationList(transfer.longHex2Bin(str), opList);
//            removeRepeatedOperationList(str, opList);
//        }
//        reader.close();
    }

    private void removeRepeatedOperationList(String op, ArrayList<String> opList) {
        if (op == null)
            return;
        int length = op.length();
        if (length < 10)
            return;
        if (opList == null)
            opList = new ArrayList<>();
        byte[] stringArr = op.getBytes();
        StringBuffer tmp = new StringBuffer();
        for (int i = 10; i < 41 ; i++) {
            if (length - i < 0)
                break;
            for (int j = 0; j < length - i + 1; j++) {
                tmp.setLength(0);
                for (int count = 0; count < i ; count++) {
                    tmp.append((char)stringArr[j + count]);
                }
                if (!opList.contains(tmp.toString()))
                    opList.add(tmp.toString());
            }
        }
    }

    public void positiveCtlMap (ArrayList<String> opFilePathList, HashMap<String, Integer> opListMap) {

        if (opFilePathList == null)
            return;
        if (opFilePathList.isEmpty())
            return;
        if (opListMap == null)
            opListMap = new HashMap<>();
        for (String opFilePath: opFilePathList) {
            try {
                positiveCtlMapSingleFile(opFilePath, opListMap);
            } catch (IOException e) {
                System.out.println("File " + opFilePath + " reading got something wrong leads IOException occurs. " +
                        "For system running safety, we ignore this exception.");
            }
        }

    }

    private void positiveCtlMapSingleFile (String opFilePathStr, HashMap<String, Integer> opListMap) throws IOException{
        if (opFilePathStr == null)
            return;
        if (opFilePathStr.length() == 0)
            return;
        if (opListMap == null)
            opListMap = new HashMap<>();
        File file = new File(opFilePathStr);
        FileInputStream fileInputStream = new FileInputStream(file);
        StringWriter stringWriter = new StringWriter();
        int len = 1;
        byte[] temp = new byte[len];
        for (; (fileInputStream.read(temp, 0 , len)) != -1; ) {
            if (temp[0] > 0xf && temp[0] <= 0xff) {
                stringWriter.write(Integer.toHexString(temp[0]));
            } else if (temp[0] >= 0x0 && temp[0] <= 0xf) {//对于只有1位的16进制数前边补“0”
                stringWriter.write("0" + Integer.toHexString(temp[0]));
            } else { //对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
                stringWriter.write(Integer.toHexString(temp[0]).substring(6));
            }
        }
//        BufferedReader reader = new BufferedReader(new FileReader(file));
        Transfer transfer = new Transfer();
        generatePositiveCtlMap(stringWriter.toString(), opListMap);

//        String str = null;
//        while (null != (str = reader.readLine())) {
////            generatePositiveCtlMap(str, opListMap);
//            generatePositiveCtlMap(remove0x(removeSpace(str)), opListMap);
//        }
//        reader.close();
    }

    private void generatePositiveCtlMap (String op, HashMap<String, Integer> opListMap) {
        if (op == null)
            return;
        int length = op.length();
        if (length < 10)
            return;
        if (opListMap == null)
            opListMap = new HashMap<>();
        List<String> opSingleFile = new ArrayList<>();
        Map<String, Integer> opSingleFileMap = new HashMap<>();
        byte[] stringArr = op.getBytes();
        StringBuffer tmp = new StringBuffer();
        for (int i = 10; i < 41; i++ ) {
            if (length - i < 0)
                break;
            for (int j = 0; j < length - i + 1 ; j++) {
                tmp.setLength(0);
                for (int count = 0 ; count < i ; count++) {
                    tmp.append((char) stringArr[j + count]);
//                    opSingleFile.(tmp.toString());
                    if (opSingleFileMap.get(tmp.toString()) == null)
                        opSingleFileMap.put(tmp.toString(), 1);
                    else
                        opSingleFileMap.put(tmp.toString(), opSingleFileMap.get(tmp.toString()) + 1);
                }
//                if (!opList.contains(tmp.toString()))
//                    opList.add(tmp.toString());
                if (opListMap.containsKey(tmp.toString())) {
                    if (opSingleFileMap.get(tmp.toString()) == null || opSingleFileMap.get(tmp.toString()) == 1)
                        opListMap.replace(tmp.toString(), opListMap.get(tmp.toString()) + 1);
                }

                else
                    opListMap.put(tmp.toString(), 1);
            }
        }
    }

    private String remove0x (String str) {
        return StringUtils.deleteAny(StringUtils.deleteAny(str, "\\X"), "\\x");
    }

    private String removeSpace (String str) {
        return StringUtils.deleteAny(str, " ");
    }

    public void removeStopOpreation (ArrayList<String> stopOpFiles, HashMap<String, Integer> opListMap) throws IOException{
        System.out.println("正在读取反例文件...");
        System.out.println("如果反例文件较多且文件较大将花费较长时间，请耐心等待。");
        if (stopOpFiles == null)
            return;
        if (stopOpFiles.size() == 0)
            return;
        if (opListMap == null)
            return;
        if (opListMap.size() == 0)
            return;
        /**
         *  为了提升等待时候的用户体验，
         *  在这里加入Console端进度条，可以在IDE控制台，
         */
        int fileNumber = stopOpFiles.size();
        int fileCounter = 0;
        double jobPercent = 0.0;
        int backspace = 0;

        int hitCount = 0;
        System.out.print("正在分析反例文件操作序列：");
        for (String stopOpFile : stopOpFiles) {
            fileCounter++;
            jobPercent = (double)fileCounter * 100.0 / (double)fileNumber;
            for (int i = 0; i < backspace ; i++) {
                System.out.print("\b");
            }
            System.out.format("%.2f", jobPercent);
            System.out.print("%");
            backspace = ("" + (int)(jobPercent / 1)).length() + 4;
            FileInputStream fileInputStream = new FileInputStream(new File(stopOpFile));
            StringWriter stringWriter = new StringWriter();
            int len = 1;
            byte[] temp = new byte[len];
            for (; (fileInputStream.read(temp, 0 , len)) != -1; ) {
                if (temp[0] > 0xf && temp[0] <= 0xff) {
                    stringWriter.write(Integer.toHexString(temp[0]));
                } else if (temp[0] >= 0x0 && temp[0] <= 0xf) {//对于只有1位的16进制数前边补“0”
                    stringWriter.write("0" + Integer.toHexString(temp[0]));
                } else { //对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
                    stringWriter.write(Integer.toHexString(temp[0]).substring(6));
                }
            }
            String str = stringWriter.toString();
            for (String k :opListMap.keySet()) {
                if (str.contains(k))
                    opListMap.put(k, 0);
            }
        }
        System.out.println("反例文件分析完成 [OK]");
    }

    public void fastRemoveStopOpreation (ArrayList<String> stopOpFiles, LinkedHashMap<String, Integer> opsMap)
            throws IOException{
        if (stopOpFiles == null)
            return;
        if (stopOpFiles.size() == 0)
            return;
        if (opsMap == null)
            return;
        if (opsMap.size() == 0)
            return;
        ArrayList<String> opArray = new ArrayList<>();
        ArrayList<Integer> opCountsArray = new ArrayList<>();
        for (String str: opsMap.keySet()) {
            opArray.add(str);
            opCountsArray.add(opsMap.get(str));
        }
        /**
         *  为了提升等待时候的用户体验，
         *  在这里加入Console端进度条，可以在IDE控制台查看，
         *  也可以在JVM运行时打印至terminal。
         */
        int fileNumber = stopOpFiles.size();
        int fileCounter = 0;
        double jobPercent = 0.0;
        int backspace = 0;
        int hitCount = 0;
        for (String stopOpFile : stopOpFiles) {
            fileCounter++;
            jobPercent = (double)fileCounter * 100.0 / (double)fileNumber;
            for (int i = 0; i < backspace ; i++) {
                System.out.print("\b");
            }
            System.out.format("%.2f", jobPercent);
            System.out.print("%");
            backspace = ("" + (int)(jobPercent / 1)).length() + 4;
            FileInputStream fileInputStream = new FileInputStream(new File(stopOpFile));
            StringWriter stringWriter = new StringWriter();
            int len = 1;
            byte[] temp = new byte[len];
            for (; (fileInputStream.read(temp, 0 , len)) != -1; ) {
                if (temp[0] > 0xf && temp[0] <= 0xff) {
                    stringWriter.write(Integer.toHexString(temp[0]));
                } else if (temp[0] >= 0x0 && temp[0] <= 0xf) {//对于只有1位的16进制数前边补“0”
                    stringWriter.write("0" + Integer.toHexString(temp[0]));
                } else { //对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
                    stringWriter.write(Integer.toHexString(temp[0]).substring(6));
                }
            }
            String str = stringWriter.toString();
            int length = opArray.size();
            for (int i = 0; i < length ; i++) {
                if (hitCount >= 100)
                    break;
                if (str.contains(opArray.get(i))) {
                    opArray.remove(i);
                    opCountsArray.remove(i);
                }
                else
                    hitCount++;
            }

        }

        LinkedHashMap<String ,Integer> opsMap2 = new LinkedHashMap<>();
        int length = opArray.size();
        for (int i = 0 ; i < length; i++) {
            opsMap2.put(opArray.get(i), opCountsArray.get(i));
        }
        opsMap = opsMap2;
        for (int i = 0; i < 40; i++) {
            System.out.print("\b");
        }
        System.out.println("反例文件分析完成 [OK]");
    }
}
