package com.wolfman.util.list;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.Vector;

public class VectorRandomAccessTest {

    public static void main(String[] args) {
        Vector vec= new Vector();
        for (int i=0; i<100000; i++)
            vec.add(i);
        iteratorThroughRandomAccess(vec) ;
        iteratorThroughIterator(vec) ;
        iteratorThroughFor2(vec) ;
        iteratorThroughEnumeration(vec) ;

        //运行结果
        //iteratorThroughRandomAccess：8 ms
        //iteratorThroughIterator：12 ms
        //iteratorThroughFor2：10 ms
        //iteratorThroughEnumeration：8 ms

    }

    private static void isRandomAccessSupported(Vector list) {
        if (list instanceof RandomAccess) {
            System.out.println("RandomAccess implemented!");
        } else {
            System.out.println("RandomAccess not implemented!");
        }

    }

    public static void iteratorThroughRandomAccess(Vector list) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i=0; i<list.size(); i++) {
            list.get(i);
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughRandomAccess：" + interval+" ms");
    }

    public static void iteratorThroughIterator(Vector list) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for(Iterator iter = list.iterator(); iter.hasNext(); ) {
            iter.next();
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughIterator：" + interval+" ms");
    }


    public static void iteratorThroughFor2(Vector list) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for(Object obj:list)
            ;
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughFor2：" + interval+" ms");
    }

    public static void iteratorThroughEnumeration(Vector vec) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for(Enumeration enu = vec.elements(); enu.hasMoreElements(); ) {
            enu.nextElement();
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughEnumeration：" + interval+" ms");
    }





}
