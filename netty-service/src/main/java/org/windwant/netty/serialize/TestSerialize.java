package org.windwant.netty.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * TestSerialize
 */
public class TestSerialize {

    public static void main(String[] args) {

        User user = new User();
        user.buildUserId(10).buildUserName("lili");
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bs);
            oo.writeObject(user);
            oo.flush();
            oo.close();
            byte[] b = bs.toByteArray();
            System.out.println("JDK SERIALIZE: " + b.length);
            System.out.println("BYTE ARRAY SERIALIZE: " + user.codeC().length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class User implements Serializable{
    private String userName;

    private int id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User buildUserId(int id){
        this.id = id;
        return this;
    }

    public User buildUserName(String name){
        this.userName = name;
        return this;
    }

    public byte[] codeC(){
        ByteBuffer buf = ByteBuffer.allocate(1024);
        byte[] name = this.getUserName().getBytes();
        buf.putInt(name.length);
        buf.put(name);
        buf.putInt(this.getId());
        buf.flip();
        name = null;
        byte[] rst = new byte[buf.remaining()];
        buf.get(rst);
        return rst;
    }

}
