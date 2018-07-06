package org.windwant.io.io;

import java.io.*;

/**
 * Created by Administrator on 18-5-29.
 */
public class ObjectInputOutStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        deepCopy();
    }

    public static void serialize() throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("io-demo/user.dat"));
        out.writeObject(new User(1, "lilei", 1));
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("io-demo/user.dat"));
        System.out.println(in.readObject());
    }

    public static void deepCopy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        User user = new User(1, "lilei", 1);
        out.writeObject(user);
        User user1 = (User)new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray())).readObject();
        System.out.println(user.equals(user1));
        bout.close();
        out.close();
    }


}
class User implements Serializable{

    private static final long serialVersionUID = -5283575377384417229L;

    private int id;

    private String name;

    private transient int status;

    private static String LEVEL = "HIGH";

    public static String getLEVEL() {
        return LEVEL;
    }

    public static void setLEVEL(String LEVEL) {
        User.LEVEL = LEVEL;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeInt(id);
//        out.writeUTF(name);
//        out.writeInt(status);
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        id = in.readInt();
//        name = in.readUTF();
//        status = in.readInt();
//    }


}