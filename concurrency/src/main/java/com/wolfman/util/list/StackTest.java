package com.wolfman.util.list;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class StackTest {

    public static void main(String[] args) {

        Stack stack = new Stack();
        // 将1,2,3,4,5添加到栈中
        for(int i=1; i<6; i++) {
            stack.push(String.valueOf(i));
        }

        // 遍历并打印出该栈
        iteratorThroughRandomAccess(stack) ;
        //运行结果：1 2 3 4 5

        // 查找“2”在栈中的位置，并输出
        int pos = stack.search("2");
        System.out.println("the postion of 2 is:"+pos);
        //运行结果：the postion of 2 is:4

        // pup栈顶元素之后，遍历栈
        stack.pop();
        iteratorThroughRandomAccess(stack) ;
        //运行结果：1 2 3 4

        // peek栈顶元素之后，遍历栈
        String val = (String)stack.peek();
        System.out.println("peek:"+val);
        iteratorThroughRandomAccess(stack) ;
        //运行结果：
        // peek:4
        //1 2 3 4

        // 通过Iterator去遍历Stack
        iteratorThroughIterator(stack) ;
        //运行结果：
        //1 2 3 4

    }

    private static void iteratorThroughIterator(List stack) {
        String val = null;
        for (Iterator iter = (Iterator) stack.iterator(); iter.hasNext();) {
            val = (String)iter.next();
            System.out.print(val+" ");
        }
        System.out.println();
    }

    /**
     * 通过快速访问遍历Stack
     */
    private static void iteratorThroughRandomAccess(Stack stack) {
        String val = null;
        for (int i = 0; i < stack.size(); i++) {
            val = (String)stack.get(i);
            System.out.print(val+" ");
        }
        System.out.println();
    }


}
