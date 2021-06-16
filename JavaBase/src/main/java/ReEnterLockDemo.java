import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReEnterLockDemo {
    static Object objectLockA = new Object();
    /**
    * 同步代码块
    */
    public static void m1() {
        new Thread(() -> {
            synchronized (objectLockA){
                System.out.println(Thread.currentThread().getName()+"-外层调用");
                 synchronized (objectLockA){
                    System.out.println(Thread.currentThread().getName()+"-中层调用");
                    synchronized (objectLockA){
                        System.out.println(Thread.currentThread().getName()+"-内层调用");
                    }
                }
            }
        },"t1").start();
    }

    //同步方法
    public synchronized void m2() {
        System.out.println("外层调用");
        m3();
    }

    public synchronized void m3() {
        System.out.println("中层调用");
        m4();
    }

    public synchronized void m4() {
        System.out.println("内层调用");
    }

    Lock lock = new ReentrantLock();;
    public static void m5(){
        new Thread(()->{

        },"t2").start();
    }
    public static void main(String[] args) {
        m1();
        new ReEnterLockDemo().m2();
    }
}
