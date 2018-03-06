package org.windwant.algorithm.consistency;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrator on 2017/12/8.
 */
public class ConsistentHash<T> {
    private final ConsistentHashFun consistentHashFun;
    private final int vitrualNodes;// 每个服务器节点对应的虚拟节点个数
    private final Map<Long, String> vitrualNodeNameMap = new HashMap<>();// 虚拟服务节点名称
    private final SortedMap<Long, T> distributeCircle = new TreeMap<Long, T>();// 存储虚服务器节点hash与节点的映射

    public ConsistentHash(ConsistentHashFun consistentHashFun, int vitrualNodes,
                          Collection<T> nodes) {
        this.consistentHashFun = consistentHashFun;
        this.vitrualNodes = vitrualNodes;
        for (T node : nodes)
            add(node);
    }

    /**
     * 将实际服务器对应的虚拟机点hash映射添加到服务器映射map
     * 设定虚拟节点取hash值因子为 node.toString() + "@" + i
     * 数据存储在顺时针方向的虚拟节点上
     * @param node
     */
    public void add(T node) {
        for (int i = 0; i < vitrualNodes; i++) {
            String vname = node.toString() + "@v" + i;
            Long vhash = consistentHashFun.getHash(vname);
            distributeCircle.put(vhash, node);
            vitrualNodeNameMap.put(vhash, vname);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < vitrualNodes; i++)
            distributeCircle.remove(consistentHashFun.getHash(node.toString() + "@" + i));
    }

    /**
     * 根据key获取服务器节点
     * 计算key hash =》顺时针查找最近的虚拟节点=》获取对应实际服务器节点
     * @param key
     * @return
     */
    public T get(Object key) {
        if (distributeCircle.isEmpty())
            return null;
        long hash = consistentHashFun.getHash((String) key);// 计算元素key hash
        SortedMap<Long, T> tailMap = distributeCircle.tailMap(hash); //查找大于元素 key hash 的服务器节点
        hash = tailMap.isEmpty() ? distributeCircle.firstKey() : tailMap.firstKey(); //不存在则存储在第一个服务器节点，否则返回所有大于 key hash 的服务器中最近的节点 hash
        return distributeCircle.get(hash);
    }

    public long getSize() {
        return distributeCircle.size();
    }

    /**
     * 查看一致性hash detail
     */
    public void printTestDetail(SortedSet<Long> init, SortedSet<Long> added, SortedSet<Long> removed, Map<Long, String> all){
        added.stream().forEach(item -> {
            System.out.print(init.contains(item) ? item : getBlank(String.valueOf(item).length()));
            System.out.print(" || ");
            System.out.print(item);
            System.out.print(" || ");
            System.out.print(removed.contains(item) ? item : getBlank(String.valueOf(item).length()));
            System.out.println();
        });

        System.out.println("server distribution...");
        added.stream().forEach(item -> {
            System.out.print(init.contains(item) ? all.get(item) : getBlank(String.valueOf(item).length()));
            System.out.print(" || ");
            System.out.print(all.get(item));
            System.out.print(" || ");
            System.out.print(removed.contains(item) ? all.get(item) : getBlank(String.valueOf(item).length()));
            System.out.println();
        });
    }

    public String getBlank(int size){
        String rn = "";
        for (int i = 0; i < size; i++) {
            rn += " ";
        }
        return rn;
    }

    public static void main(String[] args) {
        Set<String> nodes = new HashSet<String>();
        nodes.add("192.168.100.1");
        nodes.add("192.168.100.2");
        nodes.add("192.168.100.3");
        ConsistentHash<String> consistentHash = new ConsistentHash<String>(new ConsistentHashFun(), 5, nodes);
        System.out.println("consistent hash distribution: " + consistentHash.getSize());
        SortedSet<Long> init = new TreeSet<Long>(consistentHash.distributeCircle.keySet());

        consistentHash.add("192.168.100.4");
        consistentHash.add("192.168.100.5");
        consistentHash.add("192.168.100.6");
        System.out.println("consistent hash distribution num: " + consistentHash.getSize());
        SortedSet<Long> added = new TreeSet<Long>(consistentHash.distributeCircle.keySet());
        Map<Long, String> all = new HashMap<>();
        all.putAll(consistentHash.vitrualNodeNameMap);

        consistentHash.remove("192.168.100.6");
        System.out.println("consistent hash distribution num: " + consistentHash.getSize());
        SortedSet<Long> removed = new TreeSet<Long>(consistentHash.distributeCircle.keySet());

        consistentHash.printTestDetail(init, added, removed, all);
    }
}

/**
 * MurMurHash算法，是非加密HASH算法，
 */
class ConsistentHashFun {
    private MessageDigest md5 = null;

    public long getHash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return Math.abs(h);
    }
}
