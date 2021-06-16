package jvm;

import java.nio.ByteBuffer;

//-Xms10m -Xmx10m -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        //调用底层函数，最大执行内存约等于服务器内存的1/4
        //System.out.println("运行内存："+(Runtime.getRuntime().maxMemory()/ (double) 1024 / 1024) + "MB");
        System.out.println("配置的maxDirectMemory: " + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}