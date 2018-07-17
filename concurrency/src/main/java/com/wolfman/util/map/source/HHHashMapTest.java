package com.wolfman.util.map.source;

/**
 * @ClassName HHHashMapTest
 * @Description TODU
 * @Author huhao
 * @Date Create in 2018/5/23 10:48
 * @Version 1.0
 */
public class HHHashMapTest {

    public static void main(String[] args) {

        //step1.空构造函数
//        HHHashMap<String,Object> test1 = new HHHashMap<String,Object>();
//        System.out.println(test1.size);
        //step2.指定初始容量，指定填充比的构造函数
        //校验参数，赋值装载因子，初始容量（根据你传过去的容量算出一个）
//        HHHashMap<String,Object> test2 = new HHHashMap<String,Object>(100,2);
//        System.out.println(test2.size);


        //添加方法 无线添加，熟知 桶位、链表、红黑树的转化
        //step1.创建HashMap对象对象
        //填充比loadFactor为默认值 0.75放，其他属性均为默认值
        HHHashMap<String,Object> person = new HHHashMap<>();
        System.out.println(person.size);
        //添加第一条数据
        person.put(null,"aaa");//i=0;
        //继续添加一跳数据
        //person.put("name","胡昊");//i=8;
        //添加i都为0的key值
        for (int i = 0; i<10000;i++) {
            int hash = hash(i+"");//计算key的hash值
            int  j = 15 & hash;
            if (j == 0){
                //System.out.println(i+"");
                person.put(i+"",i+1);//i=8;
            }
        }




        System.out.println(person.size);



    }

    static final int hash(Object key) {
        int h;
        if (key == null){
            return 0;
        }else{
            h = key.hashCode();
            return h^(h >>> 16);
        }
    }






}
