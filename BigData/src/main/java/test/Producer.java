package test;//买票者

public class Producer implements Runnable {
    // 买票窗口
    WindowQueue queue;

    // 保证和消费者使用同一个对象
    public Producer(WindowQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (queue.num < 100) {
            try {
                //执行买票，消费者
                queue.producer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}