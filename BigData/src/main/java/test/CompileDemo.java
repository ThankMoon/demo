package test;

public class CompileDemo {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        int k = 1;
        Integer m = 194;
        Integer n = 127;
        System.out.println(c == d);                   //true
        System.out.println(c.equals(d));   	 //true
        System.out.println(e == f); 			//false
        System.out.println(c == (a +b));		//false
        System.out.println(c.equals(a + b));	//false
        System.out.println(g == (a + b));   	 //true
        System.out.println(g.equals(a+b));	//false
        System.out.println(a == k);   		 //true
        System.out.println(f == (m + n));    	//true
    }
}
