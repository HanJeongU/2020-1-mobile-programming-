package com.example.myhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker datePicker;  //  datePicker - 날짜를 선택하는 달력
    TextView viewDatePick;  //  viewDatePick - 선택한 날짜를 보여주는 textView
    EditText edtDiary;   //  edtDiary - 선택한 날짜의 일기를 쓰거나 기존에 저장된 일기가 있다면 보여주고 수정하는 영역
    Button btnSave;   //  btnSave - 선택한 날짜의 일기 저장 및 수정(덮어쓰기) 버튼

    String fileName;   //  fileName - 돌고 도는 선택된 날짜의 파일 이름

    //종료 변수
    private long backBtnTime = 0;
    // 전역변수 생성/ 다른 함수에서도 쓸수 있게.
    EditText etheight, etweight;
    TextView bmires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("My Health");

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();


        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab1").setIndicator("운동목록");
        tabSpec1.setContent(R.id.tab1);
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("ARTIST").setIndicator("BMI 측정");
        tabSpec2.setContent(R.id.tab2);
        tabHost.addTab(tabSpec2);
        etheight = (EditText) findViewById(R.id.etheight);
        etweight = (EditText) findViewById(R.id.etweight);
        bmires = (TextView) findViewById(R.id.tvbmires);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("ALBUM").setIndicator("운동일기");
        tabSpec3.setContent(R.id.tab3);
        tabHost.addTab(tabSpec3);
        tabHost.setCurrentTab(0);
        // 뷰에 있는 위젯들 리턴 받아두기
                datePicker = (DatePicker) findViewById(R.id.datePicker);
        viewDatePick = (TextView) findViewById(R.id.viewDatePick);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnSave = (Button) findViewById(R.id.btnSave);

        // 오늘 날짜를 받게해주는 Calender 친구들
        Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        // 첫 시작 시에는 오늘 날짜 일기 읽어주기
        checkedDay(cYear, cMonth, cDay);

        // datePick 기능 만들기
        // datePicker.init(연도,달,일)
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 이미 선택한 날짜에 일기가 있는지 없는지 체크해야할 시간이다
                checkedDay(year, monthOfYear, dayOfMonth);
            }
        });

        // 저장/수정 버튼 누르면 실행되는 리스너
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fileName을 넣고 저장 시키는 메소드를 호출
                saveDiary(fileName);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴버튼 생성
        getMenuInflater().inflate(R.menu.activity_menu, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.dev_name) {
            //토스트로 개발자 정보 보여주기
            Toast.makeText(this, "developer: Hannam university 20160739 Han jeong U", Toast.LENGTH_LONG).show();
        }

        if (id == R.id.re_home) {
            // 홈으로 돌아가기
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }


    //종료 물어보기
    public void onBackPressed() {

        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }


    //각 클래스로 it 넘겨주기
    public void sub1(View v) {
        Intent it = new Intent(getApplicationContext(), sub1.class);
        startActivity(it);
    }

    public void sub2(View v) {
        Intent it = new Intent(getApplicationContext(), sub2.class);
        startActivity(it);
    }

    public void sub3(View v) {
        Intent it = new Intent(getApplicationContext(), sub3.class);
        startActivity(it);
    }

    public void sub4(View v) {
        Intent it = new Intent(getApplicationContext(), sub4.class);
        startActivity(it);
    }

    public void sub5(View v) {
        Intent it = new Intent(getApplicationContext(), sub5.class);
        startActivity(it);
    }
    //bmi 계산기
    public void bmilistener(View v) {
        if (etheight.getText().toString().replace(" ", "").equals("") || etweight.getText().toString().replace(" ", "").equals("")) {
            checkemptybmi();
        } else {
            if (v.getId() == R.id.bmibutton) {
                double h = Double.parseDouble(etheight.getText().toString());
                double w = Double.parseDouble(etweight.getText().toString());
                double bmi = w / ((h / 100) * (h / 100));
                if (bmi < 18.5) {
                    bmires.setText("저체중이에요~");
                } else if (bmi >= 18.5 && bmi < 23) {
                    bmires.setText("정상입니다! ^ㅅ^");
                } else if (bmi >= 23 && bmi < 25) {
                    bmires.setText("과체중이에요..;ㅁ;");
                } else {
                    bmires.setText("비만입니다!!! :-(");
                }
            }
        }


    }
    //bmi미 입력시
    public void checkemptybmi()
    {
        if(etheight.getText().toString().replace(" ","").equals(""))
        {
            Toast.makeText(getApplicationContext(), "값을 입력하세요", Toast.LENGTH_SHORT).show();
            etheight.requestFocus();
        }
        else if (etweight.getText().toString().replace(" ","").equals(""))
        {
            Toast.makeText(getApplicationContext(), "값을 입력하세요", Toast.LENGTH_SHORT).show();
            etweight.requestFocus();
        }
        else if (etweight.getText().toString().replace(" ","").equals("") && etheight.getText().toString().replace(" ","").equals(""))
        {
            Toast.makeText(getApplicationContext(), "값을 입력하세요", Toast.LENGTH_SHORT).show();
            etheight.requestFocus();
        }
    }
    // 일기 파일 읽기
    private void checkedDay(int year, int monthOfYear, int dayOfMonth) {
monthOfYear=monthOfYear+1;
        // 받은 날짜로 날짜 보여주는
        viewDatePick.setText(year + " - " + monthOfYear + " - " + dayOfMonth);

        // 파일 이름을 만들어준다. 파일 이름은 "20170318.txt" 이런식으로 나옴
        fileName = year + "" + monthOfYear + "" + dayOfMonth + ".txt";

        // 읽어봐서 읽어지면 일기 가져오고
        // 없으면 catch 그냥 살아? 아주 위험한 생각같다..
        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            String str = new String(fileData, "UTF8");
            // 읽어서 토스트 메시지로 보여줌
            Toast.makeText(getApplicationContext(), "일기 써둔 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText(str);
            btnSave.setText("수정하기");
        } catch (Exception e) { // UnsupportedEncodingException , FileNotFoundException , IOException
            // 없어서 오류가 나면 일기가 없는 것 -> 일기를 쓰게 한다.
            Toast.makeText(getApplicationContext(), "일기 없는 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText("");
            btnSave.setText("새 일기 저장");
            e.printStackTrace();
        }

    }

    // 일기 저장하는 메소드
    private void saveDiary(String readDay) {

        FileOutputStream fos = null;

        try {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS); //MODE_WORLD_WRITEABLE
            String content = edtDiary.getText().toString();

            // String.getBytes() = 스트링을 배열형으로 변환?
            fos.write(content.getBytes());
            //fos.flush();
            fos.close();

            // getApplicationContext() = 현재 클래스.this ?
            Toast.makeText(getApplicationContext(), "일기 저장됨", Toast.LENGTH_SHORT).show();

        } catch (Exception e) { // Exception - 에러 종류 제일 상위 // FileNotFoundException , IOException
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류오류", Toast.LENGTH_SHORT).show();
        }
    }




}