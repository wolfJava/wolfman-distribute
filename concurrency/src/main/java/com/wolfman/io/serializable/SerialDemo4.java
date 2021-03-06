package com.wolfman.io.serializable;

import java.io.*;

public class SerialDemo4 {

    private static final String FILE_NAME = "d://serial3.tmp";

    public static void main(String[] args) {
        testWrite();
        testRead();
        //运行结果：
        //testWrite box: [desk: (80, 48) ]
        //testRead  box: [desk: (80, 48) ]
    }

    /**
     * 将Box对象通过序列化，保存到文件中
     */
    private static void testWrite() {
        try {
            // 获取文件TMP_FILE对应的对象输出流。
            // ObjectOutputStream中，只能写入“基本数据”或“支持序列化的对象”
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(FILE_NAME));
            // 创建Box对象，Box实现了Serializable序列化接口
            Box4 box = new Box4("desk", 80, 48);
            // 将box对象写入到对象输出流out中，即相当于将对象保存到文件TMP_FILE中
            out.writeObject(box);
            // 打印“Box对象”
            System.out.println("testWrite box: " + box);

            box = new Box4("room", 100, 50);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 从文件中读取出“序列化的Box对象”
     */
    private static void testRead() {
        try {
            // 获取文件TMP_FILE对应的对象输入流。
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(FILE_NAME));
            // 从对象输入流中，读取先前保存的box对象。
            Box4 box = (Box4) in.readObject();
            // 打印“Box对象”
            System.out.println("testRead  box: " + box);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Box类“支持序列化”。因为Box实现了Serializable接口。
 * 实际上，一个类只需要实现Serializable即可实现序列化，而不需要实现任何函数。
 */
class Box4 implements Serializable{

    private static int width;
    private transient int height;
    private String name;

    public Box4(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();//使定制的writeObject()方法可以利用自动序列化中内置的逻辑。
        out.writeInt(height);
        out.writeInt(width);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        height = in.readInt();
        width = in.readInt();
    }


    @Override
    public String toString() {
        return "["+name+": ("+width+", "+height+") ]";
    }
}