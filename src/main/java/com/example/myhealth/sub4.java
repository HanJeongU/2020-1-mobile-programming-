package com.example.myhealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sub4 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub4);
        setTitle("팔운동");


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴버튼 생성
        getMenuInflater().inflate(R.menu.activity_menu, menu);


        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.dev_name)
        {
            //토스트로 개발자 정보 보여주기
            Toast.makeText(this, "developer: Hannam university 20160739 Han jeong U", Toast.LENGTH_LONG).show();
        }

        if(id==R.id.re_home)
        {
            // 홈으로 돌아가기
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void play(View v) {
        int id = v.getId();
        LinearLayout layout = (LinearLayout)v.findViewById(id);
        String tag = (String)layout.getTag();

        Intent it = new Intent(this, SubActivity4.class);
        it.putExtra("it_tag",  tag);
        startActivity(it);
    }
}