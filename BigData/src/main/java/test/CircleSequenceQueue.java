package test;

//循环链表的具体实现
/*
 * 循环顺序队列
 */
public class CircleSequenceQueue implements Queue {

    static final int defaultsize = 10;// 默认队列的长度
    int front; // 对头
    int rear; // 队尾
    int count;// 统计元素个数的计数器
    int maxSize; // 队的最大长度
    Object[] queue; // 队列，使用数组实现

    // 默认构造
    public CircleSequenceQueue() {
        init(defaultsize);
    }

    public CircleSequenceQueue(int size) {
        // 通过给定长度进行构造
        init(size);
    }

    public void init(int size) {
        maxSize = size;
        front = rear = 0;
        count = 0;
        queue = new Object[size];
    }

    @Override
    public void append(Object obj) throws Exception {
        if (count > 0 && front == rear) {
            throw new Exception("队列已满");
        }
        // 队尾插入数据
        queue[rear] = obj;
        // 通过这种方法让对标索引值不停的重复！！！
        rear = (rear + 1) % maxSize;
        count++;
    }

    @Override
    public Object delete() throws Exception {
        if (isEmpty()) {
            throw new Exception("队列为空");
        }
        // 去除队头的元素，同时修改队头的索引值
        Object obj = queue[front];
        queue[front] = null;
        // 对头索引值，一样通过+1驱魔运算来实现循环索引效果
        front = (front + 1) % maxSize;
        count--;
        return obj;
    }

    @Override
    public Object getFront() {
        return isEmpty() ? null : queue[front];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}