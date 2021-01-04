package com.amanahgithawisata.aga;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.amanahgithawisata.aga.Adapter.DatePickerOneFragment;
import com.amanahgithawisata.aga.Helper.Help;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;




public class RegisterWisatawanActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ImageView btn_pendaftaran_wisatawan_back;
    Button btn_daftar_wisatawan;
    TextView txt_view_wisatawan_email, txt_wisatawan_tgllhr;
    String server;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private EditText btDatePicker;



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        TextView txt_wisatawan_tgllhr = (TextView) findViewById(R.id.txt_wisatawan_tgllhr);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_wisatawan_tgllhr.setText(sdf.format(c.getTime()));

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_wisatawan);

        btn_pendaftaran_wisatawan_back = findViewById(R.id.btn_pendaftaran_wisatawan_back);
        btn_daftar_wisatawan = findViewById(R.id.btn_daftar_wisatawan);
        txt_view_wisatawan_email = (TextView) findViewById(R.id.txt_view_wisatawan_email);
        txt_wisatawan_tgllhr = (TextView) findViewById(R.id.txt_wisatawan_tgllhr);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        EditText  wstName, wstEmail, wstTgllhr,wstHp,wstPsswd1,wstPsswd2;
        TextView wstDatePicker;


        wstName = (EditText) findViewById(R.id.txt_wisatawan_name);
        wstEmail = (EditText) findViewById(R.id.txt_wisatawan_email);
        wstTgllhr = (EditText) findViewById(R.id.txt_wisatawan_tgllhr);
//        wstDatePicker = (TextView) findViewById(R.id.txt_wisatawan_tgllhr);
        wstHp = (EditText) findViewById(R.id.txt_wisatawan_hp);
        wstPsswd1 = (EditText) findViewById(R.id.txt_wisatawan_passwd);
        wstPsswd2 = (EditText) findViewById(R.id.txt_wisatawan_passwd_2);


        btn_pendaftaran_wisatawan_back.setOnClickListener(v -> {
            Intent goto_getstarted = new Intent(RegisterWisatawanActivity.this, GetStartedActivity.class);
            startActivity(goto_getstarted);
        });

        btn_daftar_wisatawan.setOnClickListener(v -> getDataPostVolley());


        txt_wisatawan_tgllhr.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){

                DialogFragment datePicker = new DatePickerOneFragment();
                datePicker.show(getSupportFragmentManager(),"date picker 1");
            }
        });



    }



    private void getDataPostVolley() {


        EditText  wstName, wstEmail, wstTgllhr,wstHp,wstPsswd1,wstPsswd2;
        wstName = (EditText) findViewById(R.id.txt_wisatawan_name);
        wstEmail = (EditText) findViewById(R.id.txt_wisatawan_email);
        wstTgllhr = (EditText) findViewById(R.id.txt_wisatawan_tgllhr);
        wstHp = (EditText) findViewById(R.id.txt_wisatawan_hp);
        wstPsswd1 = (EditText) findViewById(R.id.txt_wisatawan_passwd);
        wstPsswd2 = (EditText) findViewById(R.id.txt_wisatawan_passwd_2);


        final String nama_val = wstName.getText().toString().trim();
        final String email_val = wstEmail.getText().toString().trim();
        final String tgllhr_val = wstTgllhr.getText().toString().trim();
        final String phone_val = wstHp.getText().toString().trim();
        final String passwd1_val = wstPsswd1.getText().toString().trim();
        final String passwd2_val = wstPsswd2.getText().toString().trim();


        if(TextUtils.isEmpty(nama_val) ) {
            wstName.setError("Nama Masih Kosong!");
        }

       if  (!EMAIL_ADDRESS_PATTERN.matcher(email_val).matches() ) {
           wstEmail.setError("Alamat Email Invalid!");
       }

        else if(TextUtils.isEmpty(tgllhr_val) ) {
            wstTgllhr.setError("Tgl Lahir Masih Kosong!");
        }
        else if(TextUtils.isEmpty(phone_val) ) {
            wstHp.setError("Nmr Telp Masih Kosong!");
        }
        else if(TextUtils.isEmpty(passwd1_val) || passwd1_val.length() < 8 ) {
            wstPsswd1.setError("Password Masih Kosong || Password Min 8 Char!");
        }
        else if( !passwd1_val.toString().equals(passwd2_val.toString()) ) {
            wstPsswd2.setError("Password Tidak Sesuai !");
        }

        else {
            newPostDt("daftar_wisatawan");
        }
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    void newPostDt(String EP){
        String USERNAME_KEY = "usernamekey";
        String username_key= "";

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://"+ Help.domain_api() +"/~androidwisata/?data="+ EP;
        final RequestQueue requestQueue = Volley.newRequestQueue(RegisterWisatawanActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                response -> {
                    Log.i("", "response 1 ===" + response );
                    try {

                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                        JSONObject jObj = new JSONObject(response);
                        String data = jObj.getString("data");


                        JSONObject jObj2 = new JSONObject(data);

                        Boolean berhasil = jObj2.getBoolean("berhasil");
                        String ket = jObj2.getString("keterangan").trim();
                        String email = jObj2.getString("alamat_email").trim();



                        boolean sukses = jObj.getBoolean("success");
//                            String result_dt = json.getString("data.keterangan");
                        Log.i("triono", "sukses data ===" + data );
                        Log.i("triono", "keterangan ===" + ket );


                        if (sukses){
                            Intent i = new Intent(RegisterWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                            i.putExtra("result_dt_ket", ket);
                            i.putExtra("result_dt_email", email);
                            i.putExtra("result_dt_flag", "wisatawan");
                            i.putExtra("result_dt_berhasil", berhasil);

                            startActivity(i);
                        }

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Log.i("", "error ===" + e.toString() );
                        e.printStackTrace();
                    }

//                        Log.i("triono", "response.jsonArray =" + jsonArray.toString() );
                    requestQueue.stop();
                }, error -> {
                    Log.i("triono", "response =" + error.toString());
                    error.printStackTrace();
                    requestQueue.stop();

                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterWisatawanActivity.this);
                    builder.setMessage("Terjadi Gangguan, Refresh Halaman")
                            .setCancelable(false)
                            .setPositiveButton("Ya", (dialog, id) -> {
                                finish();
                                startActivity(getIntent());
                            })
                            .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> obj = new HashMap<String, String>();

                EditText  wstName, wstEmail, wstTgllhr,wstHp,wstPsswd1,wstPsswd2;
                wstName = (EditText) findViewById(R.id.txt_wisatawan_name);
                wstEmail = (EditText) findViewById(R.id.txt_wisatawan_email);
                wstTgllhr = (EditText) findViewById(R.id.txt_wisatawan_tgllhr);
                wstHp = (EditText) findViewById(R.id.txt_wisatawan_hp);
                wstPsswd1 = (EditText) findViewById(R.id.txt_wisatawan_passwd);
                wstPsswd2 = (EditText) findViewById(R.id.txt_wisatawan_passwd_2);

                final String nama_val = wstName.getText().toString().trim();
                final String email_val = wstEmail.getText().toString().trim();
                final String tgllhr_val = wstTgllhr.getText().toString().trim();
                final String phone_val = wstHp.getText().toString().trim().trim();
                final String passwd1_val = wstPsswd1.getText().toString().trim();
                final String passwd2_val = wstPsswd2.getText().toString().trim();


//                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(username_key, wstName.getText().toString());
//                editor.apply();
//                Toast.makeText(getApplicationContext(),"wstName"+
//                wstName.getText().toString(),Toast.LENGTH_LONG).show();
//                3put("nama", "triono");
//                obj.put("tgl_lahir", "2020-01-01");
//                obj.put("jenis_kelamin", "L");
//                obj.put("sellular_no", "089508611088");
//                obj.put("alamat_email", "triono@amanahgitha.com");
//                obj.put("alamat_email", "triono.triono1@gmail.com");
//                obj.put("kata_kunci", "1234");
//                obj.put("flag", "daftar_wisatawan");
//                return obj;

                obj.put("nama",nama_val);
                obj.put("alamat_email",email_val );
                obj.put("tgl_lahir", tgllhr_val);
                obj.put("jenis_kelamin", "L");
                obj.put("sellular_no", phone_val);
                obj.put("flag", "daftar_wisatawan");
                obj.put("kata_kunci", passwd1_val );
                return obj;
            }

        };
//        requestQueue.add(stringRequest);
        int socketTimeout = 0;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }



    private void onJokeError(VolleyError volleyError) {
        Log.i("code_tim", volleyError.toString());
    }

    private void onJoke(String s) {
        Log.i("code_tim", "json - " + s);
    }


    private void onPostSuccess(JSONObject ob) {
        Log.i("triono", "response =" + ob.toString());

    }

    private void onPostError(VolleyError e) {
        Log.i("triono", "error =" + e.toString());

    }



}
