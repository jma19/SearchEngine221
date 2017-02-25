package com.uci.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by junm5 on 1/18/17.
 */
public class MyFileWriter {

    private FileWriter fstream;
    private BufferedWriter out;

    public static void createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println(String.format("create file %s failed", filePath));
        }
    }

    public MyFileWriter(String filePath, boolean isAppend) {
        try {
            this.fstream = new FileWriter(filePath, isAppend);
            this.out = new BufferedWriter(fstream);
        } catch (IOException e) {
            System.out.println(String.format("open file %s failed", filePath));
            // throw ProcessException.FILE_NOT_EXISTS_EXCEPTION;
        }
    }

    public void writeLine(String str) {
        try {
            out.write(str);
            out.newLine();
        } catch (IOException e) {
            System.out.println(String.format("write line failed : %s", e.getMessage()));
        }
    }

    public void flush() {
        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (fstream != null) {
                fstream.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("close file failed!!!");

        }
    }
}
