package com.youguo.demo.consistentHashing;

public interface HashCalculator {
    int getPartitionHash(Node node, int index);

    int getObjectHash(Object object);
}
