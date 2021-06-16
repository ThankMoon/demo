package test;

/**
 * 成员变量和局部变量
 *
 * */
public class VariableTest {

    //成员变量
    public static String staticname = "类属性";
    public String nostaticname = "实例属性";
    int ii;

    //没有初始化的成员变量
    public static String staticname1;
    public String nostaticname1;

    //定义一个方法
    public void info(){
        //在方法中定义一个局部变量i
        //int i;
        //直接输出是输出不出来的，因为没有初始化
        //System.out.println(i);
        //定义一个局部变量i并初始化
        int i = 10;
        //输出i
        System.out.println(i);
        System.out.println(ii);
    }

    //定义了一个静态的方法
    public static void infos(){
        int i = 20;
        System.out.println(i);
    }

    public static void main(String[] args) {
        /*第一问：类属性和实例属性的范围一样吗？*/
        //在没创建实例之前 可以调用类属性,但不能调用实例属性
        System.out.println(VariableTest.staticname);//结果：类属性
        //实例化对象之后,就可以调用实例属性了
        VariableTest vt = new VariableTest();
        System.out.println(vt.nostaticname);//结果：实例属性
        /*--- 结论：在成员变量中，类属性的范围比实例属性大一点 ---*/

        System.out.println("----------");

        /*第二问：成员变量需要显性初始化吗？*/
        //直接调用没有初始化的类属性
        System.out.println(VariableTest.staticname1);//结果：null
        //用实例化对象调用没有初始化的实例属性
        System.out.println(vt.nostaticname1);//结果：null
        /*--- 结论：成员变量会自动隐性初始化，赋给变量一个默认值  ---*/

        System.out.println("----------");

        /*第三问：如果用实例化后的对象去调用类属性会怎么样？*/
        //vt.staticname;
        //这样会报错
        //Syntax error, insert "VariableDeclarators" to complete LocalVariableDeclaration
        //翻译：语法错误,插入“变量声明符”来完成局部变量声明
        /*为什么会报错。一开始我以为是因为eclipse出错了
         *后来我直接用文本文档重写了一个test
         *编译文件后，报不是语句的错，然后我又试了一下
         *Test.VariableTest.staticname
         *也是报错，说明这种写法是不正确的，具体为什么有待研究*/
        vt.staticname = "改变了的类属性";
        //如果同时给类属性赋值，就不会报错...有警告
        //The static field Variable Test.Test.static name should be accessed in a static way
        //翻译：静态字段变量测试。静态的名字应该在一个静态方法访问
        System.out.println(vt.staticname);//结果：改变了的类属性
        //这样就不会报错，但是会有警告，同上↑
        /*结论：用实例化后的对象调用类属性，格式正确的情况下，是可以调用的，但有警告
         *通过对象调用类属性，同样可以改变类属性的值*/

        System.out.println("----------");

        //定义在方法中的局部变量
        /*第四问：定义在方法中的局部变量，出了方法还能访问吗？*/
        //调用方法
        vt.info();//结果：10
        //现在还能用info中的i吗？
        //System.out.println(i);
        //报错：i cannot be resolved to a variable
        //翻译：i 不能转换成一个变量
        /*结论：定义在方法中的局部变量，出了方法就不能被访问了*/

        System.out.println("----------");

        //定义在代码块中的局部变量
        /*第五问：定义在代码块中的局部变量，出了代码块还能访问吗？*/
        {
            int j = 11;
            System.out.println(j);//结果：11
        }
        //出了代码块
        //System.out.println(j);
        //同样报错，内容与上面的一样
        /*定义在代码块中的局部变量，出了代码块就不能访问了*/

        System.out.println("----------");

        //后续：一个静态方法
        infos();//结果：20
        //这样依然报错
        //System.out.println(i);

    }
}