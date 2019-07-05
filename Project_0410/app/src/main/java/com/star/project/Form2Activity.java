package com.star.project;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form2Activity extends AppCompatActivity {
    // Store question list, each element is a ArrayList Object, each ArrayList Object's element is a Model Object.
    private ArrayList<ArrayList> pages = new ArrayList<ArrayList>();
    private ArrayList<Model> questions = new ArrayList<Model>();
    // Store the answer list, "views" is for the answer of each question, each element is a EditText View.
    private ArrayList<EditText> views = new ArrayList<EditText>();
    // Title of pages for test
    //private String[] title = {"基本資料", "1", "2", "3", "4", "5", "6", "7", "8"};

    // Which page it is now
    private int num = 0, b_year, b_month, b_date;
    private JSONObject jsonData;
    private String id_p;
    private int done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_layout);
        if (num == 0) {
            Button b_left = (Button) findViewById(R.id.b_left);
            b_left.setVisibility(View.INVISIBLE);
        }
        if (pages.size() <= 1) {
            Button b_right = (Button) findViewById(R.id.b_right);
            Button b_enter = (Button) findViewById(R.id.b_enter);
            b_right.setVisibility(View.VISIBLE);
            b_enter.setVisibility(View.INVISIBLE);
        }
        setListenteners();

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
        getPatient();
        onClickLeft();
        onClickRight();
        //onClickEnter();
        initQuestion();
        initView(num);
    }

    // Control the left button
    private void onClickLeft() {
        Button b_left = (Button) findViewById(R.id.b_left);
        b_left.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveAnswer();
                if (num > 0) {
                    num = num - 1;
                    if (num == 0) {
                        Button b_left = (Button) findViewById(R.id.b_left);
                        b_left.setVisibility(View.INVISIBLE);
                    }
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.question);
                    linearLayout.removeAllViews();
                    initView(num);
                    if (num == pages.size() - 2) {
                        Button b_right = (Button) findViewById(R.id.b_right);
                        Button b_enter = (Button) findViewById(R.id.b_enter);
                        b_right.setVisibility(View.VISIBLE);
                        b_enter.setVisibility(View.INVISIBLE);
                    }
                }
            }

        });
    }

    // Control the right button
    private void onClickRight() {
        Button b_right = (Button) findViewById(R.id.b_right);
        b_right.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveAnswer();
                if (checkAnsewrEmpty(num)) {
                    if (num < pages.size() - 1) {
                        num = num + 1;
                        Button b_left = (Button) findViewById(R.id.b_left);
                        b_left.setVisibility(View.VISIBLE);
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.question);
                        linearLayout.removeAllViews();
                        initView(num);
                        if (num == pages.size() - 1) {
                            Button b_right = (Button) findViewById(R.id.b_right);
                            Button b_enter = (Button) findViewById(R.id.b_enter);
                            b_right.setVisibility(View.INVISIBLE);
                            b_enter.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }
        });
    }

    //  Control the enter button. If the page is end page, the enter button will be show

    private Button b_enter;

    private void setListenteners() {
        b_enter = (Button) findViewById(R.id.b_enter);
        b_enter.setOnClickListener(onClickEnter);
    }

    private Button.OnClickListener onClickEnter = new Button.OnClickListener() {

        public void onClick(View v) {
            saveAnswer();
            String s = "";
            for (int i = 0; i < pages.size(); i++) {
                ArrayList<Model> question = pages.get(i);
                for (int j = 0; j < question.size(); j++) {
                    Model q = question.get(j);
                    s = s + q.getName_tw() + q.getText() + "\n";
                }
            }

            new AlertDialog.Builder(Form2Activity.this)
                    .setTitle("確認")
                    .setMessage(s + "\n確認送出？")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DBstring dBstring = new DBstring();
                            if (done == 1) {
                                dBstring.u_executeQuery(id_p, pages);
                            } else {
                                dBstring.c_executeQuery(id_p, pages);
                            }
                            goback();
                        }
                    })
                    .setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //
                        }
                    })
                    .show();
        }
    };

    // For Buttons use
    private void saveAnswer() {
        ArrayList question = pages.get(num);
        for (int i = 0; i < question.size(); i++) {
            Model q = (Model) question.get(i);
            switch (q.getType()) {
                case 1:
                    q.setText(views.get(i).getText().toString());
                    break;
                default:
                    q.setText(views.get(i).getText().toString());
                    break;
            }
        }
    }

    // Check the question is all done
    private boolean checkAnsewrEmpty(int thispage) {
        ArrayList<Model> question = pages.get(thispage);
        String s = "";
        for (int i = 0; i < question.size(); i++) {
            Model q = question.get(i);
            //check is it done every question
            if (q.getType() != 0) {
                if (q.getText().equals("")) {
                    s = s + q.getName_tw() + "\n";
                }
            }
        }
        if (!s.equals("")) {
            new AlertDialog.Builder(Form2Activity.this)
                    .setTitle("錯誤(未填寫)")
                    .setMessage("以下未填寫\n" + s)
                    .setPositiveButton("OK", null)
                    .show();
            return false;
        } else {
            return true;
        }

    }

    // Initiate questions view
    private void initView(int page) {
        views = new ArrayList<EditText>();
        for (int i = 0; i < pages.get(page).size(); i++) {
            //TextView textView = (TextView) findViewById(R.id.t_title);
            //textView.setText(title[page]);
            views.add(new EditText(this));
            Model question = (Model) pages.get(page).get(i);
            switch (question.getType()) {
                case 0:
                    TextView textView = (TextView) findViewById(R.id.t_title);
                    textView.setText(question.getName_tw());
                    break;
                case 1:
                    editCreate(i, question);
                    break;
                case 2:
                    editCreate(i, question);
                    break;
                case 3:
                    checkBoxCreate(i, question);
                    break;
                case 4:
                    spinnerCreate(i, question);
                    break;
                case 31:
                    calenderCreate(i, question);
                    break;
                case 41:
                    checkBoxCreate(i, question);
                    break;
            }
        }
    }

    // Create Calender View
    private void calenderCreate(int id, final Model question) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.question);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View questionView = inflater.inflate(R.layout.calender_layout, null);
        TextView textView = (TextView) questionView.findViewById(R.id.textView);
        textView.setText(question.getName_tw());
        ImageButton imageButton = (ImageButton) questionView.findViewById(R.id.imageButton);
        final EditText editText = (EditText) questionView.findViewById(R.id.editText);
        String dateformat = "yyyyMMdd";
        final SimpleDateFormat df = new SimpleDateFormat(dateformat);

        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();

                if (question.getId().equals(" BIRTH")) {

                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(questionView.getContext(), DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            b_year = year;
                            b_month = month;
                            b_date = dayOfMonth;

                            String mon = "", day = "";
                            if (month < 9) {
                                mon = "0" + String.valueOf(month + 1);
                            } else {
                                mon = String.valueOf(month + 1);
                            }
                            if (dayOfMonth < 10) {
                                day = "0" + String.valueOf(dayOfMonth);
                            } else {
                                day = String.valueOf(dayOfMonth);
                            }
                            editText.setText(String.valueOf(year) + mon + day);
                        }
                    }, year, month, dayOfMonth).show();


                } else {
                    new DatePickerDialog(questionView.getContext(), DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            String mon = "", day = "";
                            if (month < 9) {
                                mon = "0" + String.valueOf(month + 1);
                            } else {
                                mon = String.valueOf(month + 1);
                            }
                            if (dayOfMonth < 10) {
                                day = "0" + String.valueOf(dayOfMonth);
                            } else {
                                day = String.valueOf(dayOfMonth);
                            }

                            editText.setText(String.valueOf(year) + mon + day);

                        }
                    }, b_year + 15, b_month, b_date).show();
                }
            }
        });

        editText.setText(question.getText());

        if (question.getId().equals(" EDATE")) {
            imageButton.setEnabled(false);
            Calendar calendar = Calendar.getInstance();
            String today = df.format(calendar.getTime());
            editText.setText(today);
        }

        views.set(id, editText);
        linearLayout.addView(questionView);
    }

    // Create EditText View
    private void editCreate(int id, Model question) {
        if (question.getId().equals(" BIRTH")) {
            calenderCreate(id, question);
        } else {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.question);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View questionView = inflater.inflate(R.layout.edit_layout, null);
            TextView textView = (TextView) questionView.findViewById(R.id.textView);
            textView.setText(question.getName_tw());
            final EditText editText = (EditText) questionView.findViewById(R.id.editText);
            //telephone check
            if (question.getId().equals(" TELMO")) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                editText.setHint("例：0912345678");
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                //if phone formate is error can not go to next page
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Button b_right = (Button) findViewById(R.id.b_right);
                        if (editText.length() != 10) {
                            editText.setError("請輸入10碼");
                            b_right.setEnabled(false);
                        } else {
                            Pattern pattern = Pattern.compile("(09)+[\\d]{8}");
                            Matcher matcher = pattern.matcher(editText.getText());
                            if(matcher.find()) {

                                b_right.setEnabled(true);
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                editText.clearFocus();
                            }else {
                                editText.setError("請輸入正確格式");
                                b_right.setEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            } else if (question.getId().equals("ID")) {
                editText.setEnabled(false);
            }
            editText.setText(question.getText());
            views.set(id, editText);
            linearLayout.addView(questionView);
        }
    }

    // Create Spinner View
    private void spinnerCreate(int id, Model question) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.question);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View questionView = inflater.inflate(R.layout.spinner_layout, null);
        TextView textView = (TextView) questionView.findViewById(R.id.textView);
        textView.setText(question.getName_tw());
        final EditText editText = (EditText) questionView.findViewById(R.id.editText);
        Spinner spinner = (Spinner) questionView.findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, question.getList()));
        if (question.getText() != null) {
            for (int i = 0; i < question.getList().length; i++) {
                if (question.getList()[i] == question.getText()) {
                    spinner.setSelection(i);
                }
            }
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        views.set(id, editText);
        linearLayout.addView(questionView);
    }

    // Create CheckBox View
    private void checkBoxCreate(int id, final Model question) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.question);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View questionView = inflater.inflate(R.layout.checkbox_layout, null);
        TextView textView = (TextView) questionView.findViewById(R.id.textView);
        textView.setText(question.getName_tw());
        final EditText editText = (EditText) questionView.findViewById(R.id.editText);
        editText.setText(question.getText());
        final boolean[] check = new boolean[question.getList().length];
        for (int j = 0; j < check.length; j++) {
            check[j] = false;
        }
        final ListView listView = (ListView) questionView.findViewById(R.id.listView);
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.checkbox_item, question.getList());
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (check[i]) {
                    check[i] = false;
                } else {
                    check[i] = true;
                }
                String s = "";
                for (int j = 0; j < check.length; j++) {
                    if (check[j]) {
                        s = s + question.getList()[j] + ", ";
                    }
                }
                editText.setText(s);
            }
        });
        views.set(id, editText);
        questionView.findViewById(R.id.ll).getLayoutParams().height = 95 * question.getList().length;
        linearLayout.addView(questionView);
    }

    // Initiate questions
    private void initQuestion() {
        try {
            //String s = "{\"page1\":[{\"name_tw\":\"姓名\",\"name_eg\":\"name\",\"id\":\"NAME\",\"type\":\"1\",\"list\":[]},{\"name_tw\":\"性別\",\"name_eg\":\"Gender\",\"id\":\"SEX\",\"type\":\"2\",\"list\":[\"\",\"女\",\"男\"]},{\"name_tw\":\"您是否曾患有下列慢性疾病\",\"name_eg\":\"Past hx\",\"id\":\"PASTHXLIST\",\"type\":\"3\",\"list\":[\"心臟病\",\"糖尿病\",\"中風\",\"肝炎\"]},{\"name_tw\":\"其他疾病\",\"name_eg\":\"Other past hx.\",\"id\":\"PASTHX\",\"type\":\"1\",\"list\":[]}],\"page2\":[{\"name_tw\":\"過去一個月內是否有吸菸\",\"name_eg\":\"Smoking\",\"id\":\"SMOKINGHX\",\"type\":\"2\",\"list\":[\"\",\"從未吸菸\",\"偶爾吸(不是天天)\",\"(幾乎)每天吸\",\"已經戒菸\"]},{\"name_tw\":\"若(幾乎)每天吸，平均每天吸幾支\",\"name_eg\":\"Smoking dose\",\"id\":\"SMOKING_DOSE\",\"type\":\"1\",\"list\":[]},{\"name_tw\":\"若(幾乎)每天吸，已吸菸幾年\",\"name_eg\":\"Smoking yrs\",\"id\":\"SMOKING_YRS\",\"type\":\"1\",\"list\":[]},{\"name_tw\":\"若已經戒菸，已戒幾年幾個月\",\"name_eg\":\"Stop Smoking\",\"id\":\"STOP_SMOKING_YRS\",\"type\":\"1\",\"list\":[]}]}";
            String s = DBstring.a_executeQuery();
            JSONObject jsonObject = new JSONObject(s);
            for (int k = 0; k < jsonObject.length(); k++) {
                String s1 = "page" + (k + 1);
                questions = new ArrayList<Model>();
                JSONArray jsonArray_question = jsonObject.getJSONArray(s1);
                //Log.d(Form2Activity.ACTIVITY_SERVICE, String.valueOf(jsonArray_question.length()));
                for (int i = 0; i < jsonArray_question.length(); i++) {
                    JSONObject json = jsonArray_question.getJSONObject(i);
                    String name_tw = json.getString("name_tw") + "：";
                    String name_eg = json.getString("name_eg") + ":";
                    String id = json.getString("id");
                    int type = json.getInt("type");
                    //Log.d(Form2Activity.ACTIVITY_SERVICE,name_tw+name_eg+id+type);
                    // int type = Integer.parseInt(jsonObject.getString("type"));
                    Model model;
                    //Log.d(Form2Activity.ACTIVITY_SERVICE, String.valueOf(json.getJSONArray("list").length()));
                    if (json.getJSONArray("list").length() != 0) {
                        String[] list = new String[json.getJSONArray("list").length()];
                        for (int j = 0; j < json.getJSONArray("list").length(); j++) {
                            list[j] = json.getJSONArray("list").get(j).toString();
                        }
                        model = new Model(name_tw, name_eg, id, type, list);
                    } else {
                        model = new Model(name_tw, name_eg, id, type);
                    }
                    if (model.getId().equals(" NAME")) {
                        try {
                            //Log.d(Form2Activity.ACTIVITY_SERVICE,model.getId());
                            model.setText(jsonData.getString("Name"));
                            //model.setText(name_p);
                        } catch (Exception e) {

                        }
                    }
                    if (model.getId().equals("ID")) {
                        try {
                            //Log.d(Form2Activity.ACTIVITY_SERVICE,model.getId());
                            model.setText(jsonData.getString("id"));
                            //model.setText(name_p);
                        } catch (Exception e) {

                        }
                    }
                    questions.add(model);
                }
                pages.add(questions);
                Log.d("TAX", String.valueOf(pages.size()));
                //Log.d(Form2Activity.ACTIVITY_SERVICE, String.valueOf(pages));
            }
        } catch (Exception e) {
        }
    }

    private void getPatient() {
        Intent intent = getIntent();
        id_p = intent.getStringExtra("ID_EXTRA");
        try {
            String s = "SELECT * FROM `patients` WHERE `id` = '" + id_p + "'";
            String result = DBstring.executeQuery(s);
            /*
                   SQL 結果有多筆資料時使用JSONArray
                   只有一筆資料時直接建立JSONObject物件
                   JSONObject jsonData = new JSONObject(result);
            */

            JSONArray jsonArray_p = new JSONArray(result);
            jsonData = jsonArray_p.getJSONObject(0);
            done = Integer.parseInt(jsonData.getString("done"));
            //name_p=jsonData.getString("Name");
            //Log.d(Form2Activity.ACTIVITY_SERVICE,name_p);
        } catch (Exception e) {
            //
        }
    }

    private void goback() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}