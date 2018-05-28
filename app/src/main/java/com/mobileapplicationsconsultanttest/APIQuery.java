package com.mobileapplicationsconsultanttest;

/**
 * Created by huhuapop on 5/25/2018.
 */

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;;
import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huhuapop on 5/25/2018.
 */

public class APIQuery extends AsyncTask<String, Integer, String> {
    private TextView outputText = null;
    private ProgressBar proview = null;
    private  int ProcessPercent=0;
    private static final int TIME_OUT=5000;
    private static final int STREAM_MAX_LENGTH=19024000;

    public APIQuery(TextView outputText,ProgressBar proview) {
        this.outputText = outputText;
        this.proview=proview;
        ProcessPercent=10;
       // proview.setProgress(10);
    }

    @Override
    protected void onPreExecute() {

    }

    /// need an url string, use new connection to server and get return string
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection=null;
        InputStream inputStream = null;
        String result = "";
        BufferedReader bufferedReader = null;
        byte[] responseBody = null;//响应体
        ProcessPercent=0;
        publishProgress(ProcessPercent);
        try {

            // This is getting the url from the string we passed in
            URL url = new URL(params[0]);
            result+=params[0];

            // Create the urlConnection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIME_OUT);
            urlConnection.setReadTimeout(TIME_OUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();
            ProcessPercent=20;
            publishProgress(ProcessPercent);
            //result+="Status code" +Integer.toString(statusCode);
            if (statusCode ==  200) {

                inputStream = urlConnection.getInputStream();
                responseBody = getBytesByInputStream(inputStream);
                ProcessPercent=60;
                publishProgress(ProcessPercent);
                result = getStringByBytes(responseBody);
            } else {
                // Status code is not 200
                // Do something to handle the error
            }

        } catch (Exception e) {
            e.printStackTrace();
            result+=e.toString();
        }
        finally {
            //use for the close stream and connection
            if(inputStream !=null){
                try{
                    inputStream.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                    result+=e.toString();
                }
            }
            urlConnection.disconnect();
        }
        ProcessPercent=80;
        publishProgress(ProcessPercent);
        return   result;
        //return "123445";
    }

    ///trim result string and use Gson to parse the result to title and imageURL
    @Override
    protected void onPostExecute(String result) {
       // ProcessPercent=80;
       // proview.setProgress(80);
        String trimmed = result.trim();
        Log.i("resultlength",Integer.toString(result.length()));
        //String trimmed = trimEnd(result);
        String Returnvalue="";
        int lastchar = Character.getNumericValue(result.charAt(trimmed.length()-1));
        Log.i("lastchar:",Integer.toString(lastchar));
        Log.i("resultlength:",Integer.toString(result.length()));
        Log.i("trimmedlength:",Integer.toString(trimmed.length()));

//        String json = "[{\n" +
//                "\t\"title\":\"Harry Potter: Complete 8-Film Collection (DVD, 2011, 8-Disc Set)\", \n" +
//                "\t\"imageURL\":\"http://i.ebayimg.com/00/$(KGrHqV,!g0E6ZCwQ)wpBOuWbUNB,g~~_6.JPG?set_id=89040003C1\"\n" +
//                "},\n" +
//                "{"+ "\t\"title\":\"Harry Potter2: Complete 8-Film Collection (DVD, 2011, 8-Disc Set)\", \n" +
//                "\t\"author\":\"Bob McCabe\", \n" +
//                                "\t\"imageURL\":\"http://i.ebayimg.com/00/$(KGrHqV,!g0E6ZCwQ)wpBOuWbUNB,g~~_6.JPG?set_id=89040003C1\"\n" +
//                                "}\n]";
//        Returnvalue+=json;
        Type type = new TypeToken<List<Book>>(){}.getType();
        Gson gson =new Gson();
        try {
            JsonReader reader = new JsonReader(new StringReader(trimmed));
            reader.setLenient(true);
            List<Book> bookList = gson.fromJson(reader, type);
            StringBuilder bookout = new StringBuilder();//Use String Builder to replace string . This makes system run from 1.5 minute to about 10 seconds
            for (Book book : bookList) {
                // System.out.println("book.getTitle() = " + book.getTitle());
//                Returnvalue += "Title:" + book.getTitle()+"\n";
//                Returnvalue += "Author:" + book.getauthor()+"\n";
//                Returnvalue += "Url:" + book.getimageURL()+"\n";
                bookout.append("Title:" + book.getTitle()+"\n");
                bookout.append("Author:" + book.getauthor()+"\n");
                bookout.append("Url:" + book.getimageURL()+"\n");
            }
            Returnvalue+=bookout.toString();
        }
        catch (Exception e){
            Returnvalue+="\n"+e.toString();
        }

        Returnvalue+=trimmed.substring(trimmed.length()-100);
        Log.i("trimmed last 300:",trimmed.substring(trimmed.length()-300));
        String temp =Returnvalue.replace("\\n", "\n");//use for branch

        outputText.setText(temp);
        outputText.setVisibility(outputText.VISIBLE);
        ProcessPercent=100;
        proview.setProgress(100);

        //proview.setVisibility(proview.INVISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
       proview.setProgress(value);
        //super.onProgressUpdate(values);

    }

    public String trimEnd(String value) {
        int len = value.length();
        int st = 0;
        while ((st < len) && (Character.getNumericValue(value.charAt(len-1))==-1)) {
            //len--;
            len=len-1;
        }
        return value.substring(0, len);
    }
    //from byte array to build string
    private String getStringByBytes(byte[] bytes) {
        String str = "";
        try {
            str = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    //read from InputStream，transfter to byte array，and close InputStream at the end
    private byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        //int count =0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
                //count++;
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }


}

