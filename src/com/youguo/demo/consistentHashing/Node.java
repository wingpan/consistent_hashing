package com.youguo.demo.consistentHashing;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private String ip;
    private int port;
    private Set<Object> objects = new HashSet<Object>();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static Node instance(String ip, int port) {
        Node node = new Node();
        node.setIp(ip);
        node.setPort(port);
        return node;
    }

    public void add(Object object) {
        this.objects.add(object);
    }

    public int cachedCount() {
        return this.objects.size();
    }

    @Override
    public String toString() {
        return "Node{" +
                "port=" + port +
                ", ip='" + ip + '\'' +
                '}';
    }
}
