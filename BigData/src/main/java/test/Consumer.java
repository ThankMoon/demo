package test;//卖票者

public class Consumer implements Runnable {
    WindowQueue queue;

    // 保证卖票与买票同步
    public Consumer(WindowQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        // 判断是否可以继续卖票
        while (queue.isAlive) {
            try {
                // 卖票
                queue.consumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}