package ru.startandroid.appforyandex;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Михаил on 22.04.16.
 */
public class forNetThread extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... path) {
        String content;
        try{
            content = getContent(path[0]);
        }
        catch (IOException ex){
            content = ex.getMessage();
        }

        return content;
    }

    private String getContent(String path) throws IOException {
        BufferedReader reader=null;
        try {
            URL url=new URL(path);
            HttpURLConnection c=(HttpURLConnection)url.openConnection();  // подключение к удаленному ресурсу
            c.setRequestMethod("GET");
            c.setReadTimeout(10000);
            c.connect();
            reader= new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf=new StringBuilder();
            String line=null;                                              //считывание строки из удаленного ресурса
            while ((line=reader.readLine()) != null) {
                buf.append(line);
            }
            return(buf.toString());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
