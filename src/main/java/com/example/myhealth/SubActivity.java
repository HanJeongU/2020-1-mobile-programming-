package com.example.myhealth;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



        TextView tv_title  = (TextView)findViewById(R.id.title);
        TextView tv_author = (TextView)findViewById(R.id.artist);
        ImageView iv_picture  = (ImageView)findViewById(R.id.picture);
        TextView tv_info = (TextView)findViewById(R.id.info);
        TextView tv_tip = (TextView)findViewById(R.id.tip);
        WebView webview =(WebView)findViewById(R.id.webview);

        Intent it = getIntent();
        String tag  = it.getStringExtra("it_tag");

        Resources res = getResources();

        int id_title = res.getIdentifier("sub1_title" + tag, "string", getPackageName());
        String title = res.getString(id_title);
        tv_title.setText(title);

        setTitle(title);

        int id_author = res.getIdentifier("sub1_place" + tag, "string", getPackageName());
        String author = res.getString(id_author);
        tv_author.setText(author);
//
        int id_picture = res.getIdentifier("sub1_picture" + tag, "string", getPackageName());
        String picture = res.getString(id_picture);
        int id_img = res.getIdentifier(picture, "drawable", getPackageName());
        Drawable drawable = res.getDrawable(id_img);
        iv_picture.setBackground(drawable);

        int id_info = res.getIdentifier("sub1_info" + tag, "string", getPackageName());
        String info = res.getString(id_info);
        tv_info.setText(info);

        int id_tip = res.getIdentifier("sub1_tip" + tag, "string", getPackageName());
        String tip = res.getString(id_tip);
        tv_tip.setText(tip);

        int id_webview = res.getIdentifier("sub1_url" + tag, "string", getPackageName());
        String web = res.getString(id_webview);
        webview.setWebViewClient(new WebViewClient());
        WebSettings set = webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);

        webview.loadUrl(web);

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
}

