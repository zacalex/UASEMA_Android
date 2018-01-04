package edu.usc.cesr.ema_uas.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by cal on 1/3/18.
 */

public class FileManager {

    private static String filename = "acce_data_android.txt";
    private static String initString = "rtid_replacement!";

    public static void  initFile(Context context){

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(initString.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("on create", "acc file created");


    }
    public static void appendFile(Context context, String str){

        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(("\n" + str).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d("on create", "acc file append");
    }
    public static String loadFile(Context context){

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str = "";
        String line;
        try{
            while((line=bufferedReader.readLine()) != null){
                str += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            fileInputStream.getChannel().position(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("load file content", str);
        return str;
    }
    public static void uplaodFile(){

    }

}
