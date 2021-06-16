public class StringPoolDemo {
    public static void main(String[] args) {
        {
            String str1 = new StringBuilder("ali").append("baba").toString();
            System.out.println(str1);
            System.out.println(str1.intern());
            System.out.println(str1 == str1.intern());
            System.out.println();
            String str2 = new StringBuilder("j").append("ava").toString();
            System.out.println(str2);
            System.out.println(str2.intern());
            System.out.println(str2 == str2.intern());
        }
    }
}
