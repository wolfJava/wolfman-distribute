package com.wolfman.io.file;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FileDemo {


    public static void main(String[] args) throws URISyntaxException {

        try {
            File dir = new File("dir");    // 获取目录“dir”对应的File对象
            File file1 = new File(dir, "file1.txt");
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}