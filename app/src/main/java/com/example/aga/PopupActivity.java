package com.example.aga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PopupActivity extends AppCompatActivity {

    TextView textview_ket_popup, textview_email_popup,txtview_name_popup;
    Button btn_continue_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        txtview_name_popup = (TextView) findViewById(R.id.txt_view_name_popup);
        textview_ket_popup = (TextView) findViewById(R.id.textview_ket_popup);
        textview_email_popup = (TextView) findViewById(R.id.textview_email_popup);
        btn_continue_popup = (Button) findViewById(R.id.btn_continue_popup);

        String sessionIntentName = getIntent().getStringExtra("result_dt_name");
        String sessionIntentFlag = getIntent().getStringExtra("result_dt_flag");
        String sessionIntentKet = getIntent().getStringExtra("result_dt_ket");
        String sessionIntentEmail = getIntent().getStringExtra("result_dt_email");

        textview_ket_popup.setText(sessionIntentKet.toUpperCase());
        textview_email_popup.setText(sessionIntentEmail.toLowerCase());
        txtview_name_popup.setText(sessionIntentName);

    }
}
