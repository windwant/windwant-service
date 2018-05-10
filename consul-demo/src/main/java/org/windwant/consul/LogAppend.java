package org.windwant.consul;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by windwant on 2016/12/8.
 */
public class LogAppend {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\xxx\\Desktop\\100x1000");
        FileWriter fw = new FileWriter("C:\\Users\\xxx\\Desktop\\n\\result100000.log");
        File[] list = file.listFiles();
        for (int i = 0; i < list.length; i++) {
            FileReader temp = new FileReader(list[i]);
            int c;
            while ((c = temp.read()) != -1) {
                fw.append((char) c);
            }
            temp.close();
            fw.append("\r\n");
        }
        fw.flush();
        fw.close();
    }
}
