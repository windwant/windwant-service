package org.windwant.io.io;

import java.io.ByteArrayInputStream;

/**
 * Created by Administrator on 18-6-1.
 */
public class ByteArrayStream {
    public static void main(String[] args) {
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[]{2, 4, 56});
        int i = 0;
        while ((i = in.read()) != -1){
            System.out.println(i);
        }
    }
}
