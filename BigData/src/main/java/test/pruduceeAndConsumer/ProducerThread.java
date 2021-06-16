package test.pruduceeAndConsumer;

public class ProducerThread extends Thread {
    private Producer p;
    public ProducerThread(Producer p){
        this.p = p;
    }
    @Override
    public void run() {
        while (true) {
            p.producer();
        }
    }
}