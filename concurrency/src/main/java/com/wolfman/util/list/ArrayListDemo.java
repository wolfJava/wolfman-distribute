package com.wolfman.util.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListDemo {

    public static void main(String[] args) {

        // 创建ArrayList
        ArrayList list = new ArrayList();
        for (int i = 0; i <5; i++) {
            list.add(i+1+"");
        }
        // 将下面的元素添加到第1个位置
        list.add(0,"99");
        // 获取第1个元素
        System.out.println("the first element is: "+ list.get(0));
        // 删除“3”
        list.remove("3");
        // 获取ArrayList的大小
        System.out.println("Arraylist size=: "+ list.size());
        // 判断list中是否包含"3"
        System.out.println("ArrayList contains 3 is: "+ list.contains(3));
        // 设置第2个元素为10
        list.set(1, "10");
        // 通过Iterator遍历ArrayList
        for(Iterator iter = list.iterator(); iter.hasNext(); ) {
            System.out.println("next is: "+ iter.next());
        }
        // 将ArrayList转换为数组
        String[] arr = (String[])list.toArray(new String[0]);
        for (String str:arr)
            System.out.println("str: "+ str);
        // 清空ArrayList
        list.clear();
        // 判断ArrayList是否为空
        System.out.println("ArrayList is empty: "+ list.isEmpty());

        //运行结果
        //the first element is: 99
        //Arraylist size=: 5
        //ArrayList contains 3 is: false
        //next is: 99
        //next is: 10
        //next is: 2
        //next is: 4
        //next is: 5
        //str: 99
        //str: 10
        //str: 2
        //str: 4
        //str: 5
        //ArrayList is empty: true

    }


}
