# TreeMap



# 类结构

图解

![这里写图片描述](http://img.blog.csdn.net/20171216145332341?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

类定义

```
public class TreeMap<K,V>
    extends AbstractMap<K,V>
    implements NavigableMap<K,V>, Cloneable, java.io.Serializable {}
```

## 属性

```
    static final class Entry<K,V> implements Map.Entry<K,V> {
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        boolean color = BLACK;
```



#算法

    红黑树
    平衡二叉B树


# put


## 添加子节点

思路

    1.判断root是否为空,
    1.1 root为空,则将该节点设置为root
    2.root不为空,用该节点和root比较
    2.1 root等于该节点,用该节点的值替换root的值,退出
    2.2 root大于该节点,用root的左节点和该节点比较,递归
    2.3 root小于该节点,用root的右节点和该节点比较,递归
    3.直至被比较节点没有子节点
    4.将该节点设置为比较节点的子节点


实现

```
public V put(K key, V value) {
           //用t表示二叉树的当前节点
            Entry<K,V> t = root;
            //t为null表示一个空树，即TreeMap中没有任何元素，直接插入
            if (t == null) {
                //比较key值，个人觉得这句代码没有任何意义，空树还需要比较、排序？
                compare(key, key); // type (and possibly null) check
                //将新的key-value键值对创建为一个Entry节点，并将该节点赋予给root
                root = new Entry<>(key, value, null);
                //容器的size = 1，表示TreeMap集合中存在一个元素
                size = 1;
                //修改次数 + 1
                modCount++;
                return null;
            }
            int cmp;     //cmp表示key排序的返回结果
            Entry<K,V> parent;   //父节点
            // split comparator and comparable paths
            Comparator<? super K> cpr = comparator;    //指定的排序算法
            //如果cpr不为空，则采用既定的排序算法进行创建TreeMap集合
            if (cpr != null) {
                do {
                    parent = t;      //parent指向上次循环后的t
                    //比较新增节点的key和当前节点key的大小
                    cmp = cpr.compare(key, t.key);
                    //cmp返回值小于0，表示新增节点的key小于当前节点的key，则以当前节点的左子节点作为新的当前节点
                    if (cmp < 0)
                        t = t.left;
                    //cmp返回值大于0，表示新增节点的key大于当前节点的key，则以当前节点的右子节点作为新的当前节点
                    else if (cmp > 0)
                        t = t.right;
                    //cmp返回值等于0，表示两个key值相等，则新值覆盖旧值，并返回新值
                    else
                        return t.setValue(value);
                } while (t != null);
            }
            //如果cpr为空，则采用默认的排序算法进行创建TreeMap集合
            else {
                if (key == null)     //key值为空抛出异常
                    throw new NullPointerException();
                /* 下面处理过程和上面一样 */
                Comparable<? super K> k = (Comparable<? super K>) key;
                do {
                    parent = t;
                    cmp = k.compareTo(t.key);
                    if (cmp < 0)
                        t = t.left;
                    else if (cmp > 0)
                        t = t.right;
                    else
                        return t.setValue(value);
                } while (t != null);
            }
            //将新增节点当做parent的子节点
            Entry<K,V> e = new Entry<>(key, value, parent);
            //如果新增节点的key小于parent的key，则当做左子节点
            if (cmp < 0)
                parent.left = e;
          //如果新增节点的key大于parent的key，则当做右子节点
            else
                parent.right = e;
            /*
             *  上面已经完成了排序二叉树的的构建，将新增节点插入该树中的合适位置
             *  下面fixAfterInsertion()方法就是对这棵树进行调整、平衡，具体过程参考上面的五种情况
             */
            fixAfterInsertion(e);
            //TreeMap元素数量 + 1
            size++;
            //TreeMap容器修改次数 + 1
            modCount++;
            return null;
        }

```



## 平衡树

思路

    1. 设置新节点为红色
    2. 新节点的平衡条件 为root 父节点为黑色
    3. 父节点为爷节点的左支
    3.1 叔节点为红色,则爷节点变为红色,父和叔节点转为黑色.并从爷节点重新平衡
    3.2 叔节点为黑色,或者不存在
    (3.2 该节点为右节点,以父节点为轴左转,(转化为下面的情况))
    3.2 该节点为左节点,设置爷节点为红色,父节点为黑色,以爷节点为轴右转
    

实现

```
     x 表示新增节点
     private void fixAfterInsertion(Entry<K,V> x) {
            x.color = RED;    //新增节点的颜色为红色

            //循环 直到 x不是根节点，且x的父节点不为红色
            while (x != null && x != root && x.parent.color == RED) {
                //如果X的父节点（P）是其父节点的父节点（G）的左节点
                if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                    //获取X的叔节点(U)
                    Entry<K,V> y = rightOf(parentOf(parentOf(x)));
                    //如果X的叔节点（U） 为红色（情况三）
                    if (colorOf(y) == RED) {     
                        //将X的父节点（P）设置为黑色
                        setColor(parentOf(x), BLACK);
                        //将X的叔节点（U）设置为黑色
                        setColor(y, BLACK);
                        //将X的父节点的父节点（G）设置红色
                        setColor(parentOf(parentOf(x)), RED);
                        x = parentOf(parentOf(x));
                    }
                    //如果X的叔节点（U为黑色）；这里会存在两种情况（情况四、情况五）
                    else {   
                        //如果X节点为其父节点（P）的右子树，则进行左旋转（情况四）
                        if (x == rightOf(parentOf(x))) {
                            //将X的父节点作为X
                            x = parentOf(x);
                            //右旋转
                            rotateLeft(x);
                        }
                        //（情况五）
                        //将X的父节点（P）设置为黑色
                        setColor(parentOf(x), BLACK);
                        //将X的父节点的父节点（G）设置红色
                        setColor(parentOf(parentOf(x)), RED);
                        //以X的父节点的父节点（G）为中心右旋转
                        rotateRight(parentOf(parentOf(x)));
                    }
                }
                //如果X的父节点（P）是其父节点的父节点（G）的右节点
                else {
                    //获取X的叔节点（U）
                    Entry<K,V> y = leftOf(parentOf(parentOf(x)));
                  //如果X的叔节点（U） 为红色（情况三）
                    if (colorOf(y) == RED) {
                        //将X的父节点（P）设置为黑色
                        setColor(parentOf(x), BLACK);
                        //将X的叔节点（U）设置为黑色
                        setColor(y, BLACK);
                        //将X的父节点的父节点（G）设置红色
                        setColor(parentOf(parentOf(x)), RED);
                        x = parentOf(parentOf(x));
                    }
                  //如果X的叔节点（U为黑色）；这里会存在两种情况（情况四、情况五）
                    else {
                        //如果X节点为其父节点（P）的右子树，则进行左旋转（情况四）
                        if (x == leftOf(parentOf(x))) {
                            //将X的父节点作为X
                            x = parentOf(x);
                           //右旋转
                            rotateRight(x);
                        }
                        //（情况五）
                        //将X的父节点（P）设置为黑色
                        setColor(parentOf(x), BLACK);
                        //将X的父节点的父节点（G）设置红色
                        setColor(parentOf(parentOf(x)), RED);
                        //以X的父节点的父节点（G）为中心右旋转
                        rotateLeft(parentOf(parentOf(x)));
                    }
                }
            }
            //将根节点G强制设置为黑色
            root.color = BLACK;
        }
```

## 左旋转

旋转不会

图例

![这里写图片描述](http://img.blog.csdn.net/20180309153455448?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9kX2pvaG4=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)


思路

    原先右支
        右支的父节点变为爷节点
        右支的左节点设置为旋转节点
    原先右支的左孩子
        父节点改为旋转节点
    爷节点
        爷节点的原先子节点设置为原先的右支节点
    旋转节点        
        旋转节点的父节点改为原先的右节点
        旋转节点的右支设置为原先右支的左节点
    
实现

```
private void rotateLeft(Entry<K,V> p) {  
        if (p != null) {  
            //获取P的右子节点，其实这里就相当于新增节点N（情况四而言）  
            Entry<K,V> r = p.right;  
            //将R的左子树设置为P的右子树  
            p.right = r.left;  
            //若R的左子树不为空，则将P设置为R左子树的父亲  
            if (r.left != null)  
                r.left.parent = p;  
            //将P的父亲设置R的父亲  
            r.parent = p.parent;  
            //如果P的父亲为空，则将R设置为跟节点  
            if (p.parent == null)  
                root = r;  
            //如果P为其父节点（G）的左子树，则将R设置为P父节点(G)左子树  
            else if (p.parent.left == p)  
                p.parent.left = r;  
            //否则R设置为P的父节点（G）的右子树  
            else  
                p.parent.right = r;  
            //将P设置为R的左子树  
            r.left = p;  
            //将R设置为P的父节点  
            p.parent = r;  
        }  
    }  
```
