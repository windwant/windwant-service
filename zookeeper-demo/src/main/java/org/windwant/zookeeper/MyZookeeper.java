package org.windwant.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by windwant on 2016/6/1.
 */
public class MyZookeeper {
    public static void main(String[] args){
        /*Watcher w = new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("this is the watcher process");
            }
        };*/

        Watcher w = event -> System.out.println("this is the watcher process");
        ZooKeeper zk = ZookeeperUtil.getInstance("192.168.7.10:2181", w);


        try {
            zk.exists("/test", w);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setNodeData(zk, "/test", "change info", 2);
    }

    public static void setNodeData(ZooKeeper zk, String path, String info, int version){
        byte[] data = info.getBytes();
        Stat st = ZookeeperUtil.setNodeData(zk, path, data, version);
        System.out.println("Set NodeData: " + st.toString());
    }

    public static void getNodeData(ZooKeeper zk, String path){
        byte[] data = ZookeeperUtil.getNodeData(zk, path);
        try {
            System.out.println("Get NodeData: " + new String(data, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void getChildrenNode(ZooKeeper zk, String path){
        List<String> children = ZookeeperUtil.getChildrenNode(zk, path);
        System.out.println("get ChildNode: ");
        if(children != null){
            for(String child: children){
                System.out.println("child: " + child);
            }
        }
    }

    public static void createNode(ZooKeeper zk) {
        if(zk != null){
            byte[] data = "test info".getBytes();
            String create = ZookeeperUtil.createNode(zk, "/test", data);
            if(create != null){
                System.out.println("create node: " + create);
            }
        }
    }
}
