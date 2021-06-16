package test.arithmetic;

import java.util.Stack;

import static test.arithmetic.StackAndQueue.Solution.pop;

/**
 * 几道和「堆栈、队列」有关的算法题
 *
 * @author zjh
 * @version v1.0
 * @date 2019/3/25
 */
public class StackAndQueue {
    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效
     *
     * @param s 字符串
     * @return boolean 是否
     * @author zjh
     */
    public static boolean vaildParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (stack.size() == 0) {
                stack.push(aChar);
            } else if (isSym(stack.peek(), aChar)) {
                stack.pop();
            } else {
                stack.push(aChar);
            }
        }
        return stack.size() == 0;
    }

    private static boolean isSym(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c1 == '[' && c2 == ']') || (c1 == '{' && c2 == '}');
    }

    /**
     * 用两个栈来实现一个队列，完成队列的 Push 和 Pop 操作
     *
     * @author zjh
     */
    public static class Solution {
        static Stack<Integer> in = new Stack<Integer>();
        static Stack<Integer> out = new Stack<Integer>();

        public static void push(int node) {
            in.push(node);
        }

        public static int pop() throws Exception {
            if (out.isEmpty()) {
                while (!in.isEmpty()) {
                    out.push(in.pop());
                }
            }
            if (out.isEmpty()) {
                throw new Exception("queue is empty");
            }
            return out.pop();
        }
    }

    /**
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如序列 1，2，3，4，5 是某栈的压入顺序，序列 4，5，3，2，1是该压栈序列对应的一个弹出序列，但4，3，5，1，2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
     *
     * @param pushSequence 序列
     * @param popSequence  弹出序列
     * @return boolean 是否
     * @author zjh
     */
    public static boolean isPopOrder(int[] pushSequence, int[] popSequence) {
        int n = pushSequence.length;
        Stack<Integer> stack = new Stack<>();
        for (int pushIndex = 0, popIndex = 0; pushIndex < n; pushIndex++) {
            stack.push(pushSequence[pushIndex]);
            while (popIndex < n && !stack.isEmpty() && stack.peek() == popSequence[popIndex]) {
                stack.pop();
                popIndex++;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的 min 函数
     *
     * @param
     * @author zjh
     * @return
     */
    public static class Solution2 {
        static Stack stack = new Stack();

        public static void push(int node) {
            stack.push(node);
        }

        public static void pop() {
            if (stack.size() == 0) {
                return;
            }
            stack.pop();
        }

        public static int top() {
            int top = (int) stack.pop();
            return top;
        }

        public static int min() {
            Stack stac = new Stack();
            int min = (int) stack.pop();
            stac.push(min);
            while (!stack.isEmpty()) {
                int temp = (int) stack.pop();
                if (min > temp) {
                    min = temp;
                }
                stac.push(temp);
            }
            while (!stac.isEmpty()) {
                stack.push(stac.pop());
            }
            return min;
        }
    }

    public static class Solution3 {
        private static Stack<Integer> dataStack = new Stack<>();
        private static Stack<Integer> minStack = new Stack<>();

        public static void push(int node) {
            dataStack.push(node);
            minStack.push(minStack.isEmpty() ? node : Math.min(minStack.peek(), node));
        }

        public static void pop() {
            if(dataStack.isEmpty()){
                return;
            }
            dataStack.pop();
            if (dataStack.peek().equals(minStack.peek())) {
                minStack.pop();
            }
        }

        public static int min() {
            return minStack.peek();
        }
    }

    /**
     * java入口
     *
     * @return void 无
     * @author zjh
     */
    public static void main(String[] args) {
        System.out.println(vaildParentheses("(){}{{}}"));
        try {
            Solution.push(1);
            Solution.push(2);
            Solution.push(3);
            pop();
            Solution.push(4);
            System.out.println(Solution.in);
            System.out.println(Solution.out);
            System.out.println(pop());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isPopOrder(new int[]{1, 2, 3, 4, 5}, new int[]{4, 5, 3, 2, 1}));
        System.out.println(isPopOrder(new int[]{1, 2, 3, 4, 5}, new int[]{4, 5, 3, 1, 2}));
        Solution2.pop();
        Solution2.push(2);
        Solution2.push(4);
        Solution2.push(2);
        Solution2.push(7);
        Solution2.pop();
        Solution2.push(3);
        Solution2.push(2);
        Solution2.pop();
        Solution2.push(5);
        Solution3.pop();
        Solution3.push(2);
        Solution3.push(4);
        Solution3.push(2);
        Solution3.push(7);
        Solution3.pop();
        Solution3.push(3);
        Solution3.push(2);
        Solution3.pop();
        Solution3.push(5);
        System.out.println(Solution2.min());
        System.out.println(Solution3.min());
    }
}
