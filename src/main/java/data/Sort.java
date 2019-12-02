package data;

import java.util.*;

public class Sort {
    public int top(int number, HashMap<String, Integer> opDict, ArrayList<String> strResult, ArrayList<Integer> recordResult) {
        if (number < 1)
            return 0;
        if (opDict.size() < number)
            number = opDict.size();
        StringBuffer textTmp = new StringBuffer();
        Integer recordTmp = 0;
        for (int i = 0; i < number ; i++) {
            textTmp.setLength(0);
            recordTmp = 0;
            for (String str: opDict.keySet()) {
                if (opDict.get(str) >= recordTmp) {
                    textTmp.setLength(0);
                    textTmp.append(str);
                    recordTmp = opDict.get(str);
                }
            }
            strResult.add(textTmp.toString());
            recordResult.add(recordTmp);
            opDict.replace(textTmp.toString() ,0);
        }
        return number - removeZeroRecords(strResult, recordResult);
    }

    private int removeZeroRecords(ArrayList<String> strResult, ArrayList<Integer> recordResult) {
        int count = 0;
        for (Integer tmp : recordResult) {
            if (tmp == 0L)
                count++;
        }
        for (int i = 0; i < count ; i++) {
            strResult.remove(strResult.size() - 1);
            recordResult.remove(recordResult.size() - 1);
        }
        return count;
    }

    public LinkedHashMap<String, Integer> sort (HashMap<String, Integer> opMap) {

        Set<Map.Entry<String, Integer>> entry = opMap.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(entry);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (int)o2.getValue() - (int)o1.getValue();
            }
        });
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> mapEntry : list) {
            linkedHashMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return linkedHashMap;
    }

}
