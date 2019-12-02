package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileOperation {

    private String configFile = "D:\\Project\\2020\\FrequentSequence\\config.cfg";

    public String read(String path) {
        if (path == null)
            return null;
        try {
            File file = new File(path);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String readTmp = bufferedReader.readLine();
            bufferedReader.close();
            return readTmp;
        } catch (Exception e) {
            System.out.println("Something wrong here when file reading. " +
                    "May system cannot find the file at the place you given.");
        }
        return null;
    }

    public void write (String path, String text) {
        if (path == null)
            return;
        if (text == null)
            return;
        try {
            File file = new File(path);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("System wrong here when file writing. " +
                    "May system cannot find the file path you given.");
        }
    }

    private String configFileReader (String key) {
        if (key == null)
            return null;
        File file = new File(configFile);
        if (!file.canRead())
            return "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            String keyFromFile;
            int cutter;
            while (null != (str = bufferedReader.readLine())) {
                cutter = str.indexOf("=");
                if (cutter <= 0)
                    continue;
                keyFromFile = str.substring(0, cutter);
                if (keyFromFile.equals(key)) {
                    bufferedReader.close();
                    return str.substring(cutter + 1);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error occurs when config file reading. " +
                    "Please check up settings file all right.");
        }
        return null;
    }

    public void positiveDirPathReader (ArrayList<String> resultSaver) {
        String path = configFileReader("positive_dir");
        filesListReader(path, resultSaver);
    }

    public void negativeDirPathReader (ArrayList<String> resultSaver) {
        String path = configFileReader("negative_dir");
        filesListReader(path, resultSaver);
    }

    private void filesListReader (String path, List<String> resultSaver) {
        File file = new File(path);
        if (!file.isDirectory())
            return ;
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names!=null) {
            String[] completeNames = new String[names.length];
            for (int i = 0; i < names.length; i++) {
                completeNames[i] = path + names[i];
            }
            resultSaver.addAll(Arrays.asList(completeNames));
        }
    }

    public void outputFileAppend (String context) throws IOException {
        String path = configFileReader("output_filepath");
        if (path == null)
            return;
        File file = new File(path);
        if (!file.exists())
            file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(context);
        writer.close();
    }

    public void outputFileAppendHex (String context) throws IOException {
        String path = configFileReader("output_filepath") + ".hex";
        if (path.equals(".hex"))
            return;
        File file = new File(path);
        if (!file.exists())
            file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(context);
        writer.close();
    }

    public void outputFileAppendHexBin (String context) throws IOException {
        String path = configFileReader("output_filepath") + ".bin";
        if (path.equals(".bin"))
            return;
        File file = new File(path);
        if (!file.exists())
            file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(context);
        writer.close();
    }

}
