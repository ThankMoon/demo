package test.pruduceeAndConsumer;

import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Object object = new Object();
        ArrayList<Integer> list = new ArrayList<Integer>();
        Producer p = new Producer(object, list);
        Consumer c = new Consumer(object, list);
        ProducerThread[] pt = new ProducerThread[2];
        ConsumeThread[] ct = new ConsumeThread[2];
        for(int i=0;i<2;i++){
            pt[i] = new ProducerThread(p);
            pt[i].setName("生产者 "+(i+1));
            ct[i] = new ConsumeThread(c);
            ct[i].setName("消费者"+(i+1));
            pt[i].start();
            ct[i].start();
        }
    }
}