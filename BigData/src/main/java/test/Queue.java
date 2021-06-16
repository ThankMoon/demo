package test;

public interface Queue {

    // 入队
    void append(Object obj) throws Exception;

    // 出队
    Object delete() throws Exception;

    // 获得队头元素
    Object getFront() throws Exception;

    // 判断是否为空
    boolean isEmpty();

}