package test;

public class Demo2_5 {
    public static void main(String[] args) {
        Integer a=new Integer(1);
        Integer b=new Integer(2);
        swap(a,b);
        System.out.println("a="+a+"  b="+b);
        int x = 1;
        int y = 2;
        swap(x,y);
        System.out.println("x="+x+"  y="+y);
        Integer a1=new Integer(1);
        Integer b1=new Integer(2);
        Integer temp1=a1;
        a1=b1;
        b1=temp1;
        System.out.println("a1="+a1+"  b1="+b1);
    }

    public static void  swap(Integer a,Integer b){
        Integer temp=a;
        a=b;
        b=temp;
    }

    public static void  swap(int a,int b){
        int temp = a;
        a=b;
        b=temp;
    }
}