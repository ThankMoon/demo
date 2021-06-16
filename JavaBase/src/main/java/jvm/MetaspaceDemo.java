package jvm;
/*
 * Java8之后的版本使用Metaspace来替代永久代
 * Metaspace是方法区在HotSpot中的实现，它与持久带最大的区别在于：Metaspace并不在虚拟机内存中而是使用
 * 本地内存，也即在java8中，class metaspace（the virtual machines internal presentation of java class）
 * ,被存储在叫做Metaspace的native memory
 *
 * 永久代（Metaspace）存放以下信息：
 * 虚拟机加载的类信息
 * 常量池
 * 静态变量
 * 即时编译后的代码
 * */

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
* -XX:MetaspaceSize=5m -XX:MaxMetaspaceSize=5m
* 增加了两个JVM启动参数来观察类的加载、卸载信息：-XX:+TraceClassLoading -XX:+TraceClassUnloading
* @author zhoujh
* @version v1.0
* @date 2019/8/9
*/
public class MetaspaceDemo {
    static class OOMTest {

    }
    public static void main(String[] args) {
        //模拟多少次后发生异常
        int i = 0;
        try {
            while (true) {
                i++;
                //创建代理
                Enhancer enhancer = new Enhancer();
                //确定父类
                enhancer.setSuperclass(OOMTest.class);
                //关闭缓存
                enhancer.setUseCache(false);
                //向代理对象的方法中织入切面代码
                enhancer.setCallback(new MethodInterceptor() {
                                         @Override
                                         public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                                             return methodProxy.invokeSuper(o,args);
                                         }
                                     }
                );
                //使用enhancer创建代理对象
                enhancer.create();
            }
        } catch (Throwable e) {
            System.out.println("********多少次后发生了异常：" + i);
            e.printStackTrace();
        }
    }
}
