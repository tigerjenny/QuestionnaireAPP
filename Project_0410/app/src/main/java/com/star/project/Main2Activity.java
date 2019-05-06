package com.star.project;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {


    private Button button_get_record,button_send_record;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViews();
        setListeners();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private void findViews() {
        button_get_record = (Button)findViewById(R.id.get_record);
        button_send_record = (Button)findViewById(R.id.send_record);
    }

    private void setListeners() {
        button_get_record.setOnClickListener(getDBRecord);
        button_send_record.setOnClickListener(sendDBRecord);
    }

    private Button.OnClickListener getDBRecord = new Button.OnClickListener() {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            TableLayout user_list = (TableLayout)findViewById(R.id.user_list);
            user_list.setStretchAllColumns(true);
            TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams view_layout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

            Intent intent = getIntent();
            id = intent.getStringExtra("ID_EXTRA");

            try {
                String s = "SELECT * FROM `patients` WHERE `id` = '"+id+"'" ;
                String result = DBstring.executeQuery(s);
                /*
                    SQL 結果有多筆資料時使用JSONArray
                    只有一筆資料時直接建立JSONObject物件
                    JSONObject jsonData = new JSONObject(result);
                */

                Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();

                JSONArray jsonArray = new JSONArray(result);

                //Toast.makeText(getApplicationContext(),"haha", Toast.LENGTH_LONG).show();

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    TableRow tr = new TableRow(Main2Activity.this);
                    tr.setLayoutParams(row_layout);
                    tr.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView user_num = new TextView(Main2Activity.this);
                    user_num.setText(jsonData.getString("number"));
                    user_num.setLayoutParams(view_layout);

                    TextView user_que = new TextView(Main2Activity.this);
                    user_que.setText(jsonData.getString("questionnaire"));
                    user_que.setLayoutParams(view_layout);

                    TextView user_edate = new TextView(Main2Activity.this);
                    user_edate.setText(jsonData.getString("edate"));
                    user_edate.setLayoutParams(view_layout);

                    TextView user_h_edate = new TextView(Main2Activity.this);
                    user_h_edate.setText(jsonData.getString("hired_date"));
                    user_h_edate.setLayoutParams(view_layout);

                    TextView user_done = new TextView(Main2Activity.this);
                    user_done.setText(jsonData.getString("done"));
                    user_done.setLayoutParams(view_layout);

                    tr.addView(user_num);
                    tr.addView(user_que);
                    tr.addView(user_edate);
                    tr.addView(user_h_edate);
                    tr.addView(user_done);
                    user_list.addView(tr);
                }
            } catch(Exception e) {
                // Log.e("log_tag", e.toString());
            }
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(id);
        }
    };


    private Button.OnClickListener sendDBRecord = new Button.OnClickListener() {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            try {
                Intent intent = getIntent();
                id = intent.getStringExtra("ID_EXTRA");
                DBstring dBstring = new DBstring();
                String s = "UPDATE `patients` SET`done`=1 WHERE `id`='"+id+"'" ;
                dBstring.s_executeQuery(s);
                goback();
            } catch(Exception e) {
                // Log.e("log_tag", e.toString());
            }
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(id);
        }
    };

    private void goback(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void btnClick(View v){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
