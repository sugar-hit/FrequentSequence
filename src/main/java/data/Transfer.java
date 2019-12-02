package data;

import java.util.ArrayList;
import java.util.List;

public class Transfer {

    private String hex2Bin (int hex) {
        return Integer.toHexString(hex);
    }

    private String hex2Bin (String hexStr) {
        return Integer.toBinaryString(Integer.parseInt(hexStr.trim(), 16));
    }

    public String bin2hex (String binStr) {
        return Integer.toHexString(Integer.parseInt(binStr.trim(), 2));
    }

    public String hexArray2BinStream (String hexArrayStr) {
        StringBuffer buffer = new StringBuffer();
        String[] hexArray = hexArrayStr.split(" ");
        for (String hex : hexArray) {
            buffer.append(longHex2Bin(hex));
        }
        return buffer.toString();
    }


    public String longHex2Bin(String hex) {
        int length = hex.length();
        if (length <= 4)
            return hex2Bin(hex);

        StringBuffer buffer = new StringBuffer();
        List<String> bufferList = new ArrayList<>();
        int i = length - 1;
        for ( ; i >= 0 ; i = i - 1 ) {
            bufferList.add(hex2Bin(hex.substring(i, i + 1)));
        }
        length = bufferList.size() - 1;
        for (; length >= 0; length --) {
            buffer.append(bufferList.get(length));
        }
        return buffer.toString();
    }

    public String longBin2Hex(String bin) {
        int length = bin.length();
        if (length <= 4)
            return bin2hex(bin);

        StringBuffer stringBuffer = new StringBuffer();
        int i = length - 4;
        for ( ; i >= 0 ; i = i - 4 ) {
            stringBuffer.append(bin2hex(bin.substring(i , i + 4)));
        }
        if (i != -4) {
            i = i + 4;
            stringBuffer.append(bin2hex(bin.substring(0, i)));
        }
        return stringBuffer.reverse().toString();
    }

    public String outputHex (String hex) {
        if (hex == null)
            return null;
        if (hex.length()==0)
            return null;
        if (hex.length()==1)
            return "\\x0" + hex;
        List<String> resultList = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        int i = hex.length();
        for (; i >= 2; i = i - 2) {
            resultList.add("\\x" + hex.substring(i - 2, i));
        }
        if (i == 1)
            resultList.add(outputHex(hex.substring(0,1)));
        i = resultList.size() - 1;
        for (; i >= 0 ; i-- ) {
            buffer.append(resultList.get(i));
        }
        return buffer.toString();
    }
}
