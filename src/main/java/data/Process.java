package data;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Process {

    /**
     * negativeCtlArray()
     * 根据提供的反例文件列表获得反例操作集（词典）
     * @param opFilePathList 提供存有反例操作文件的列表（文件地址，ArrayList<String>类型）
     * @param opList 用于保存反例操作集（数据返回值，ArrayList<String>类型）
     */
    public void negativeCtlArray (ArrayList<String> opFilePathList, ArrayList<String> opList) {
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
        if (opFilePathStr == null)
            return ;
        if (opList == null)
            opList = new ArrayList<>();
        Transfer transfer = new Transfer();
        File file = new File(opFilePathStr);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = null;
        while (null != (str = reader.readLine())) {
//            removeRepeatedOperationList(transfer.longHex2Bin(str), opList);
            removeRepeatedOperationList(str, opList);
        }
        reader.close();
    }

    private void removeRepeatedOperationList(String op, ArrayList<String> opList) {
        if (op == null)
            return;
        int length = op.length();
        if (length < 5)
            return;
        if (opList == null)
            opList = new ArrayList<>();
        byte[] stringArr = op.getBytes();
        StringBuffer tmp = new StringBuffer();
        for (int i = 5; i < 21 ; i++) {
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

    public void positiveCtlMap (ArrayList<String> opFilePathList, HashMap<String, Long> opListMap) {
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

    private void positiveCtlMapSingleFile (String opFilePathStr, HashMap<String, Long> opListMap) throws IOException{
        if (opFilePathStr == null)
            return;
        if (opFilePathStr.length() == 0)
            return;
        if (opListMap == null)
            opListMap = new HashMap<>();
        File file = new File(opFilePathStr);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Transfer transfer = new Transfer();
        String str = null;
        while (null != (str = reader.readLine())) {
//            generatePositiveCtlMap(str, opListMap);
            generatePositiveCtlMap(remove0x(removeSpace(str)), opListMap);
        }
        reader.close();
    }

    private void generatePositiveCtlMap (String op, HashMap<String, Long> opListMap) {
        if (op == null)
            return;
        int length = op.length();
        if (length < 5)
            return;
        if (opListMap == null)
            opListMap = new HashMap<>();
        List<String> opSingleFile = new ArrayList<>();
        Map<String, Long> opSingleFileMap = new HashMap<>();
        byte[] stringArr = op.getBytes();
        StringBuffer tmp = new StringBuffer();
        for (int i = 5; i < 21; i++ ) {
            if (length - i < 0)
                break;
            for (int j = 0; j < length - i + 1 ; j++) {
                tmp.setLength(0);
                for (int count = 0 ; count < i ; count++) {
                    tmp.append((char) stringArr[j + count]);
//                    opSingleFile.(tmp.toString());
                    if (opSingleFileMap.get(tmp.toString()) == null)
                        opSingleFileMap.put(tmp.toString(), 1L);
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
                    opListMap.put(tmp.toString(), 1L);
            }
        }
    }

    private String remove0x (String str) {
        return StringUtils.deleteAny(StringUtils.deleteAny(str, "\\X"), "\\x");
    }

    private String removeSpace (String str) {
        return StringUtils.deleteAny(str, " ");
    }
}
