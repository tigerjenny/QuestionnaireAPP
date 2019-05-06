package com.star.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity
{
    private IntentIntegrator scanIntegrator;
    private String scanContent;
    private EditText tx_id;
    private boolean pass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onbuttonclick(View v) {
        //View button1 = (View) findViewById(R.id.scan_btn);

        scanIntegrator = new IntentIntegrator(MainActivity.this);
        scanIntegrator.setPrompt("請掃描");
        scanIntegrator.initiateScan();
    }

    public void clean(View v)
    {
        tx_id = (EditText)findViewById(R.id.editText2);
        tx_id.setText("");
    }

    public void nextPage(View v)
    {
        tx_id = (EditText)findViewById(R.id.editText2);
        String id = tx_id.getText().toString();
        if(id.length()!=10){
            new AlertDialog.Builder(this).setMessage("輸入錯誤").setTitle("訊息").setPositiveButton("OK",null).show();
            tx_id.setText("");
        }else {
            Intent intent = new Intent(this,Form2Activity.class);
            intent.putExtra("ID_EXTRA", id);
            //intent.putExtra("ID_EXTRA","C221561209");
            startActivity(intent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null)
        {
            if(scanningResult.getContents() != null)
            {
                scanContent = scanningResult.getContents();
                if (!scanContent.equals(""))
                {
                    tx_id = (EditText)findViewById(R.id.editText2);
                    tx_id.setText(scanContent.toString());
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, intent);
            Toast.makeText(getApplicationContext(),"發生錯誤",Toast.LENGTH_LONG).show();
        }
    }
}