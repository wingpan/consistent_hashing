# 一致性哈希算法理解 ( consistent hashing )

####概述
维基百科上的解释：

	Consistent hashing is a special kind of hashing such that when a hash table is resized, only { K/n} K/n keys need to be remapped on average, where  K is the number of keys, and { n} n is the number of slots. In contrast, in most traditional hash tables, a change in the number of array slots causes nearly all keys to be remapped because the mapping between the keys and the slots is defined by a modular operation.
	
一致性哈希算法是一种特殊的哈希算法，当哈希表槽位的数量（大小）发生变化时，平均只会引起 {K/n}个关键字需要重新映射，其中 K 是关键字的数量，n是哈希表的大小。而其他哈希算法因为涉及到哈希表槽位数量取模（ hash(o) mod n）运算，如果增加或减少哈希表的大小（槽位），几乎所有关键字都需要重新映射。


一致性哈希算法经常运用在分布式缓存系统中，映射数据到对应的缓存主机中。

####原理
可以这样理解一致性哈希算法，将所有的可能的hash值组成一个环（ring），首尾相接，先将哈希表的槽位（node，也就是分布式缓存系统中的主机）取hash值，在环中定好位置。如下图所示：

![My cool picture](http://upload-images.jianshu.io/upload_images/4417484-c4c5472d135c2823.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240).

如果有数据对象需要被缓存，计算出该对象的哈希值后，在该环中找到比它哈希值大且最近的node，这个node就是该对象的缓存主机。如下图所示，B是1对象的缓存主机：

![My cool picture](http://upload-images.jianshu.io/upload_images/4417484-12fd36f1f10024ca.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240).


######增加节点
假设有如下图的哈希环，对象8分配到 A，对象1、4、6、7被分配到 B，对象2、3被分配到 C：

![My cool picture](http://upload-images.jianshu.io/upload_images/4417484-cd68df2eefa01f49.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240).

如果此时加入另一台主机 D，其哈希的位置在 B 和 A之间，那么在系统中，只要重新计算对象6的哈希值，将其映射到D，其余对象的映射不需要重新计算：

![My cool picture](http://upload-images.jianshu.io/upload_images/4417484-aee75e2000d50249.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240).


######删除节点
如果现在主机 C 宕机无法连接，在系统中，也只要重新计算对象2、3的哈希值，映射到 A，其余对象的映射也不需要重新计算：

![My cool picture](http://oepiu0s1i.bkt.clouddn.com/delete_c.png).


######平衡
假设以 node 的实际hash值作为其在环中的位置，由于这些值并不会均匀的落在环上，导致这些 node 所“管辖”的范围并不平均，最终结果是数据分布的不平均，从图 1 可以看出 B 节点的范围最大，而 A 节点的范围最小。

解决这种不平衡的方式是引入虚节点 (Partition)：一个物理节点（node）会分到非常多的虚节点（Partition），实际缓存对象会被映射到虚节点上，然后系统根据虚节点找到物理节点，最后把对象缓存到物理节点上。

这样由虚节点的大规模分布，来保证物理节点的“管辖”范围的均匀分布。如果不同物理节点存在不同权重，可以通过给高权重的节点分配更多的虚节点来达到目的。

![My cool picture](http://upload-images.jianshu.io/upload_images/4417484-82d36370042ca0cd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240).


####参考文档
https://en.wikipedia.org/wiki/Consistent_hashing

https://community.oracle.com/blogs/tomwhite/2007/11/27/consistent-hashing

http://yikun.github.io/2016/06/09/%E4%B8%80%E8%87%B4%E6%80%A7%E5%93%88%E5%B8%8C%E7%AE%97%E6%B3%95%E7%9A%84%E7%90%86%E8%A7%A3%E4%B8%8E%E5%AE%9E%E8%B7%B5/

https://www.codeproject.com/articles/56138/consistent-hashing




