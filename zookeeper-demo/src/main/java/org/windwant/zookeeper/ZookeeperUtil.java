package org.windwant.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * zookeeper util
 */
public class ZookeeperUtil {
    private static final int SESSION_TIMEOUT = 30000;

    public static ZooKeeper getInstance(String domain, Watcher w){
        try {
            return new ZooKeeper(domain,SESSION_TIMEOUT, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String createNode(ZooKeeper zk, String path, byte[] data){
        try {
            return zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getChildrenNode(ZooKeeper zk, String path){
        try {
            return zk.getChildren(path, false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Stat setNodeData(ZooKeeper zk, String path, byte[] data, int version){
        try {
            return zk.setData(path, data, version);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getNodeData(ZooKeeper zk, String path){
        try {
            return zk.getData(path, false, null);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteNode(ZooKeeper zk, String path, int version){
        try {
            zk.delete(path, version);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void closeZk(ZooKeeper zk){
        try {
            if(zk != null) {
                zk.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
