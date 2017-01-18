package com.youguo.demo.consistentHashing;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AppMain {
    public static void main(String[] args) {
        Shard shard = new Shard(generateNodes());
        int totalCount = 100000;
        Random random = new Random();
        for (int i = 0; i < totalCount; i++) {
            shard.add(random.nextInt());
        }

        Set<Node> nodes = shard.getNodes();
        int max = 0;
        int min = totalCount;
        for (Node node : nodes) {
            int nodeCacheCount = node.cachedCount();
            System.out.println(node + ": " + nodeCacheCount);
            System.out.println("++++++++++++++++++++++++++++++");

            max = Math.max(max, nodeCacheCount);
            min = Math.min(min, nodeCacheCount);
        }

        System.out.println("*******************************");
        int Avg = totalCount/nodes.size();
        System.out.println("Max: " + max);
        System.out.println("Min: " + min);
        System.out.println("AVG: " + Avg);

    }

    private static Set<Node> generateNodes() {
        Set<Node> nodes = new HashSet<Node>();

        for (int i = 0; i < 20; i++) {
            nodes.add(Node.instance("127.0.0.1", i));
        }

        return nodes;
    }

}
