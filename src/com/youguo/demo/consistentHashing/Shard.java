package com.youguo.demo.consistentHashing;


import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Shard {
    private TreeMap<Integer, Node> partitions;
    private Set<Node> nodes;
    private final int PARTITION_COUNT_PER_NODE = 1000;
    private HashCalculator hashCalculator = new DefaultHashCalculator();

    public Shard(Set<Node> nodes) {
        this.nodes = nodes;
        init();
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    private void init() {
        partitions = new TreeMap<Integer, Node>();

        for (Node node : nodes) {
            for (int i = 0; i < PARTITION_COUNT_PER_NODE; i++) {
                partitions.put(hashCalculator.getPartitionHash(node, i), node);
            }
        }
    }

    public void add(Object o) {
        int hash = hashCalculator.getObjectHash(o);
        Node node = getNode(hash);
        node.add(o);
    }

    private Node getNode(int hash) {
        SortedMap<Integer, Node> tail = this.partitions.tailMap(hash);
        if (tail.size() > 0) {
            return this.partitions.get(tail.firstKey());
        } else {
            return this.partitions.firstEntry().getValue();
        }
    }

}
