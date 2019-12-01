package data;

import java.util.*;

public class Sort {
    public int top(int number, HashMap<String, Long> opDict, ArrayList<String> strResult, ArrayList<Long> recordResult) {
        if (number < 1)
            return 0;
        if (opDict.size() < number)
            number = opDict.size();
        StringBuffer textTmp = new StringBuffer();
        Long recordTmp = 0L;
        for (int i = 0; i < number ; i++) {
            textTmp.setLength(0);
            recordTmp = 0L;
            for (String str: opDict.keySet()) {
                if (opDict.get(str) >= recordTmp) {
                    textTmp.setLength(0);
                    textTmp.append(str);
                    recordTmp = opDict.get(str);
                }
            }
            strResult.add(textTmp.toString());
            recordResult.add(recordTmp);
            opDict.replace(textTmp.toString() ,0L);
        }
        return number - removeZeroRecords(strResult, recordResult);
    }

    private int removeZeroRecords(ArrayList<String> strResult, ArrayList<Long> recordResult) {
        int count = 0;
        for (Long tmp : recordResult) {
            if (tmp == 0L)
                count++;
        }
        for (int i = 0; i < count ; i++) {
            strResult.remove(strResult.size() - 1);
            recordResult.remove(recordResult.size() - 1);
        }
        return count;
    }


}
