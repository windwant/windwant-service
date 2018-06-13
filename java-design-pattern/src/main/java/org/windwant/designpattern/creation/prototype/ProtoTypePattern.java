package org.windwant.designpattern.creation.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2016/9/19.
 */
public class ProtoTypePattern implements Cloneable, Serializable{

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /* ��������л��ͷ����л� */
    public Object deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bas);
        oos.writeObject(this);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bas.toByteArray()));
        return ois.readObject();
    }

    public List<String> getList() {
        return list;
    }

    private List<String> list = new ArrayList<String>();

}
