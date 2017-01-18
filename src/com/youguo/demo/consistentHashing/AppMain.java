package com.youguo.demo.consistentHashing;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AppMain {
    public static void main(String[] args) {
        Shard shard = new Shard(generateNodes());
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            shard.add(random.nextInt());
        }

        Set<Node> nodes = shard.getNodes();
        for (Node node : nodes) {
            System.out.println(node + ": " + node.cachedCount());
            System.out.println("++++++++++++++++++++++++++++++");
        }
    }

    private static Set<Node> generateNodes() {
        Set<Node> nodes = new HashSet<Node>();

        for (int i = 0; i < 20; i++) {
            nodes.add(Node.instance("127.0.0.1", i));
        }

        return nodes;
    }

}
