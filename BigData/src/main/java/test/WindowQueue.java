package test;

/*得到循环链表后，对其进行应用，之类主要是模拟卖票窗口
  实例：使用顺序循环队列和多线程实现一个排队买票的例子。
        ·  生产者（等候买票）
        ·  消费者 (买票离开)
代码的分割线，使用生产者消费者模式进行设计，主要是使用同步机制*/
//卖票窗口
public class WindowQueue {

    // 卖票的队列默认长度10
    int maxSize = 10;
    CircleSequenceQueue queue = new CircleSequenceQueue(maxSize);
    // 用来统计卖票的数量，一天最多卖100张票
    int num = 0;
    boolean isAlive = true; // 判断是否继续卖票

    // 排队买票,使用同步机制
    public synchronized void producer() throws Exception {
        // count队列中的元素个数，如果该值小于maxSize则可以买票
        if (queue.count < maxSize) {
            queue.append(num++); // 等待买票的数量+1
            System.out.println("第" + num + "个客户排队等待买票");
            this.notifyAll(); // 通知卖票线程可以卖票了
        }
        // 如果满了
        else {
            try {
                System.out.println("队列已满...请等待");
                this.wait(); // 队列满时，排队买票线程等待，其实等待卖票队伍里面离开一个人后来唤醒自己
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 排队卖票,使用同步机制
    public synchronized void consumer() throws Exception {
        // count队列中的元素个数，如果该值大于0，则说明有票可以继续卖票
        if (queue.count > 0) {
            Object obj = queue.delete();
            // 第几个人买到票了
            int temp = Integer.parseInt(obj.toString());
            System.out.println("第" + (temp + 1) + "个客户排队买到票离开队列");
            // 如果当前队列为空，并且卖出票的数量的大于等于100说明卖票要结束
            if (queue.isEmpty() && this.num >= 100) {
                this.isAlive = false;
            }
            // 排队队伍离开一个人，可以进来一个人进行买票了。
            this.notifyAll(); // 通知买票线程可以买了，唤醒买票线程
        }
        // 如果满了
        else {
            try {
                System.out.println("队列已空...请进入队伍准备买票");
                this.wait();// 队列空时，排队卖票线程等待，其实等待买票队伍里面进来一个人后买票来唤醒自己
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}