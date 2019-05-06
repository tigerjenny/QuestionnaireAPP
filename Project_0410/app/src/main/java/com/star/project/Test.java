package com.star.project;

import java.lang.*;
import java.io.*;

public class Test {
    static String[] volume;
    static String formate;
    static String ret;

    public static void main(String[] args) throws IOException {
        String data;

        try {
            FileReader fRead = new FileReader("/Users/Jenny/Desktop/專題報告/fielddeflabor_q.cfg");
            BufferedReader fIn = new BufferedReader(fRead);
            do {
                data = fIn.readLine(); // 讀取一行字串資料
                if (data == null)
                    break; // 若資料讀取完畢，跳離迴圈
                if (data.length() != 0) {
                    String str = data.substring(0, data.indexOf(" "));
                    switch (str) {
                        case "title":
                            ret = ret+"title\n";
                            //System.out.println("title");
                            title_spilt(data);
                            break;
                        case "field2":
                            ret = ret+"field2\n";
                            //System.out.println("field2");
                            field2_spilt(data);
                            break;
                        case "extfld":
                            ret = ret+"extfld\n";
                            //System.out.print("extfld");
                            extfld_spilt(data);
                            break;
                        case "extdef":
                            ret = ret+"extdef\n";
                            //System.out.print("extdef");
                            extdef_spilt(data);
                        default:
                            ret = ret+"fault\n";
                            //System.out.println("fault");
                    }
                } else
                    continue;
            } while (true);
            fRead.close();
        } catch (IOException e) {
            System.out.println("檔案處理有誤!!");
        }
        System.out.print(ret);
        //return ret ;
    }

    public static void title_spilt(String data) {

        formate = data.substring(data.indexOf(" ") + 1);
        volume = formate.split(",");
        for (int i = 0; i < volume.length; i++) {
            ret = ret+volume[i];
            //System.out.print(volume[i]);
        }
        ret = ret+"\n";
        //System.out.println();
    }

    public static void field2_spilt(String data) {

        formate = data.substring(data.indexOf(",") + 1);
        volume = formate.split(",");
        for (int i = 0; i < volume.length; i++) {
            ret = ret+volume[i];
            //System.out.print(volume[i]);
        }
        ret = ret+"\n";
        //System.out.println();
    }

    public static void extfld_spilt(String data) {

        formate = data.substring(data.indexOf(" ") + 1);
        //System.out.println(" " + formate);
        ret = ret+" "+formate+"\n";
    }

    public static void extdef_spilt(String data) {

        formate = data.substring(data.indexOf(" ") + 1);
        ret = ret+" "+formate+"\n";
        //System.out.println(" " + formate);
    }
    }

    /*public static void main(String[] args) throws IOException {
        String data;

        try {
            FileReader fRead = new FileReader("/Users/Jenny/Desktop/專題報告/fielddeflabor_q.cfg");
            BufferedReader fIn = new BufferedReader(fRead);
            do {
                data = fIn.readLine(); // 讀取一行字串資料
                if (data == null)
                    break; // 若資料讀取完畢，跳離迴圈
                if (data.length() != 0) {
                    String str = data.substring(0, data.indexOf(" "));
                    switch (str) {
                        case "title":
                            System.out.println("title");
                            title_spilt(data);
                            break;
                        case "field2":
                            System.out.println("field2");
                            field2_spilt(data);
                            break;
                        case "extfld":
                            System.out.print("extfld");
                            extfld_spilt(data);
                            break;
                        case "extdef":
                            System.out.print("extdef");
                            extdef_spilt(data);
                        default:
                            System.out.println("fault");
                    }
                } else
                    continue;
            } while (true);
            fRead.close();
        } catch (IOException e) {
            System.out.println("檔案處理有誤!!");
        }

    }

    public static void title_spilt(String data) {

        formate = data.substring(data.indexOf(" ") + 1);
        volume = formate.split(",");
        for (int i = 0; i < volume.length; i++) {
            System.out.print(volume[i]);
        }
        System.out.println();
    }

    public static void field2_spilt(String data) {

        formate = data.substring(data.indexOf(",") + 1);
        volume = formate.split(",");
        for (int i = 0; i < volume.length; i++) {
            System.out.print(volume[i]);
        }
        System.out.println();
    }

    public static void extfld_spilt(String data) {

        formate = data.substring(data.indexOf(" ") + 1);
        System.out.println(" " + formate);
    }

    public static void extdef_spilt(String data) {

        formate = data.substring(data.indexOf(" ") + 1);
        System.out.println(" " + formate);
    }

}*/
