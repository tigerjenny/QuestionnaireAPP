package com.star.project;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/*public class DBstring {
    public static String DB1(String i){

        String result = "";
        HttpURLConnection urlConnection = null;
        InputStream is = null;

        try{
            URL url = new URL("http://192.168.64.2/index.php");
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            is = urlConnection.getInputStream();


            /*HttpClient HC = new DefaultHttpClient();
            HttpPost HP = new HttpPost("http://192.168.64.2/index.php");
            ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("id",i));
            HP.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
            HttpResponse HR = HC.execute(HP);
            HttpEntity HE = HR.getEntity();
            InputStream IS = ((HttpEntity) HE).getContent();

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            is.close();
            result = builder.toString();
            //result = EntityUtils.toString(HR.getEntity(),HTTP.UTF_8);

        }catch (Exception e){
            Log.i("錯誤訊息",e.toString());
        }
        return result;
    }
}*/
public class DBstring {
    //
    public static String executeQuery(String query_string) {
        String result = "";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.64.2/test1/index.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("query_string", query_string));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //view_account.setText(httpResponse.getStatusLine().toString());
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }

        return result;
    }
    /*public void s_executeQuery(String query_string) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.64.2/test1/db_connection.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("query_string", query_string));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }

    }*/
    public void s_executeQuery(String query_string) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.64.2/test1/db_connection.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("number", "3"));
            params.add(new BasicNameValuePair("ID", "A987654321"));
            params.add(new BasicNameValuePair("NAME", "Test"));
            params.add(new BasicNameValuePair("SEX", "Test"));
            params.add(new BasicNameValuePair("PASTHXLIST", "Test"));
            params.add(new BasicNameValuePair("PASTHX", "no"));
            params.add(new BasicNameValuePair("SMOKINGHX", "Test"));
            params.add(new BasicNameValuePair("SMOKING_DOSE", "0"));
            params.add(new BasicNameValuePair("SMOKING_YRS", "0"));
            params.add(new BasicNameValuePair("STOP_SMOKING_YRS", "0"));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }

    }
    public static String a_executeQuery() {
        String result = "";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.64.2/test3/index.php");
            //ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("query_string", query_string));
            //httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //view_account.setText(httpResponse.getStatusLine().toString());
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }
        Log.d("TAX",result);
        return result;
    }

    // Create new done form.
    public void c_executeQuery(String id, ArrayList<ArrayList> pages) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.64.2/test1/db_connection.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("number", null));
            params.add(new BasicNameValuePair("ID", id));
            for (int i = 0; i < pages.size(); i++) {
                ArrayList<Model> questions = pages.get(i);
                for (int j = 0; j < questions.size(); j++) {
                    Model q = questions.get(j);
                    params.add(new BasicNameValuePair(q.getId(), q.getText()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }
    }

    // Update data
    public void u_executeQuery(String id, ArrayList<ArrayList> pages) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.64.2/test1/db_update.php");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", id));
            for (int i = 0; i < pages.size(); i++) {
                ArrayList<Model> questions = pages.get(i);
                for (int j = 0; j < questions.size(); j++) {
                    Model q = questions.get(j);
                    params.add(new BasicNameValuePair(q.getId(), q.getText()));
                }
            }
            Log.d("TT", String.valueOf(params));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }

    }

}