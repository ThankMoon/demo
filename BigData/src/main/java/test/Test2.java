package test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test2 {
    public static void main(String[] args) {
        try {
            Class<?> clazz = PrivateClass.class;// 获取PrivateClass整个类
            PrivateClass pc = (PrivateClass) clazz.newInstance();// 创建一个实例

            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);// 将目标属性设置为可以访问
                System.out.println("赋值前：" + fs[i].getName() + ":" + fs[i].get(pc));
                fs[i].set(pc, "null");//将属性值重新赋值
                System.out.println("赋值后：" + fs[i].getName() + ":" + fs[i].get(pc));
            }

            Method[] ms = clazz.getDeclaredMethods();// 获取PrivateClass所有的方法
            for (int i = 0; i < ms.length; i++) {
                ms[i].setAccessible(true);// 将目标属性设置为可以访问
                System.out.println("方法 ： " + ms[i].getName());//输出所以方法的名称
            }
            Method m = clazz.getDeclaredMethod("url");
            m.setAccessible(true);
            System.out.println("url方法返回值：" + m.invoke(pc));//得到结果应该是重新赋值后的值null:null

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
