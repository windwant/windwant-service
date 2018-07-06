package org.windwant.zookeeper.master_workers;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * Created by Administrator on 18-6-14.
 */
public abstract class Unit {
    protected String threadName;
    protected String prefix;

    protected void log(String info){
        System.out.println(prefix + info);
    }

    protected void deleteNode(ZooKeeper zk, String path){
        try {
            List<String> children = zk.getChildren(path, false);
            if (children != null && !children.isEmpty()) {
                children.stream().forEach(child -> {
                    deleteNode(zk, path + "/" + child);
                });
            } else {
                zk.delete(path, -1);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void deleteChildren(ZooKeeper zk, String path){
        try {
            List<String> children = zk.getChildren(path, false);
            if (children != null && !children.isEmpty()) {
                children.stream().forEach(child -> {
                    deleteNode(zk, path + "/" + child);
                });
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void deleteNode(ZooKeeper zk, List<String> paths){
        try {
            for (int i = 0; i < paths.size(); i++) {
                zk.delete(paths.get(i), -1);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
