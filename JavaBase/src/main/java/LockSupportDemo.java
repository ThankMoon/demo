import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo {

    static Object objectLock1 = new Object();
    static Object objectLock2 = new Object();
    static Object objectLock3 = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        //synchronizedDemo();
        //lockDemo();
        Thread A = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "-awaked");
        }, "A");
        A.start();

        Thread B = new Thread(() -> {
            LockSupport.unpark(A);
            System.out.println(Thread.currentThread().getName() + "-notify");
        }, "B");
        B.start();
    }

    private static void lockDemo() {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "-come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "-awaked");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "-notify");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "B").start();

        new Thread(() -> {
            //lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "-come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "-awaked");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //lock.unlock();
            }
        }, "C").start();

        new Thread(() -> {
            //lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "-notify");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //lock.unlock();
            }
        }, "D").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "-come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "-awaked");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "E").start();

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "-notify");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "F").start();
    }

    private static void synchronizedDemo() {
        new Thread(() -> {
            synchronized (objectLock1) {
                System.out.println(Thread.currentThread().getName() + "-come in");
                try {
                    objectLock1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-awaked");
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (objectLock1) {
                objectLock1.notify();
                System.out.println(Thread.currentThread().getName() + "-notify");
            }
        }, "B").start();

        //异常原因：都去掉同步代码块
        //结果：IllegalMonitorStateException报错
        //结论：Object类中的wait、notify、notifyAll用于线程等待和唤醒的方法，都必须在synchronized内部执行（必须用到关键字synchronized）
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-come in");
            try {
                objectLock2.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-awaked");
        }, "C").start();

        new Thread(() -> {
            objectLock2.notify();
            System.out.println(Thread.currentThread().getName() + "-notify");
        }, "D").start();

        //异常原因：将notify放在wait方法前面
        //结果：程序无法执行，无法唤醒
        //结论：先wait后notify、notifyAll方法，等待中的线程才会被唤醒，否则无法唤醒
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objectLock3) {
                System.out.println(Thread.currentThread().getName() + "-come in");
                try {
                    objectLock3.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-awaked");
            }
        }, "E").start();

        new Thread(() -> {
            synchronized (objectLock3) {
                objectLock3.notify();
                System.out.println(Thread.currentThread().getName() + "-notify");
            }
        }, "F").start();
    }
}
