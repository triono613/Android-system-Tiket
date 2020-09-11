package com.amanahgithawisata.aga;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;

public class SuccessClaimActivity extends AppCompatActivity {

    TextView _textview_ket_claim;
    TextView _textview_email_claim;
    Button _btn_continue_claim;
    ImageView _btn_back_regis_claim;
    SessionManager sessionManager;
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SuccessClaimActivity.this, GetStartedActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void onSuperBackPressed(){
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_claim);


        sessionManager = new SessionManager(getApplicationContext());

        _textview_ket_claim = (TextView) findViewById(R.id.textview_ket_claim);
        _textview_email_claim = (TextView) findViewById(R.id.textview_email_claim);
        _btn_continue_claim = (Button) findViewById(R.id.btn_continue_claim);
        _btn_back_regis_claim = (ImageView) findViewById(R.id.btn_back_regis_claim);

        String sessionIntentFlag = getIntent().getStringExtra("result_dt_flag");
        String sessionIntentKet = getIntent().getStringExtra("result_dt_ket");
        String sessionIntentEmail = getIntent().getStringExtra("result_dt_email");
        Boolean sessionIntentBerhasil = getIntent().getBooleanExtra("result_dt_berhasil",false);

        assert sessionIntentKet != null;
        _textview_ket_claim.setText(sessionIntentKet.toUpperCase());
        assert sessionIntentEmail != null;
        _textview_email_claim.setText(sessionIntentEmail.toLowerCase());

        if ( sessionIntentBerhasil.equals(true) ){
            _textview_ket_claim.setText(sessionIntentKet.toUpperCase());
            _textview_email_claim.setText(sessionIntentEmail.toLowerCase());
            findViewById(R.id.btn_back_regis).setVisibility(View.GONE);
        } else {
            _textview_ket_claim.setTextColor(Color.parseColor("#E64788"));
            _textview_ket_claim.setText(sessionIntentKet.toUpperCase());
            _textview_email_claim.setText(sessionIntentEmail.toLowerCase());
            findViewById(R.id.btn_continue).setVisibility(View.GONE);
        }



    _btn_continue_claim.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                    Intent i = new Intent(SuccessClaimActivity.this,DashboardPetugasActivity.class);
                    startActivity(i);
        }
    });

        _btn_back_regis_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
