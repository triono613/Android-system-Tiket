package com.example.aga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aga.Adapter.Ws;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import 	org.json.JSONObject;
import org.json.JSONException;
import org.json.*;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterWisatawanActivity extends AppCompatActivity {

    ImageView btn_pendaftaran_wisatawan_back;
    Button btn_daftar_wisatawan;
    TextView txt_view_wisatawan_email;
    String server;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private EditText btDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_wisatawan);

        btn_pendaftaran_wisatawan_back = findViewById(R.id.btn_pendaftaran_wisatawan_back);
        btn_daftar_wisatawan = findViewById(R.id.btn_daftar_wisatawan);
        txt_view_wisatawan_email = (TextView) findViewById(R.id.txt_view_wisatawan_email);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US) ;
        EditText  wstName, wstEmail, wstTgllhr,wstHp,wstPsswd1,wstPsswd2;
        TextView wstDatePicker;

        wstName = (EditText) findViewById(R.id.txt_wisatawan_name);
        wstEmail = (EditText) findViewById(R.id.txt_wisatawan_email);
        wstTgllhr = (EditText) findViewById(R.id.txt_wisatawan_tgllhr);
        wstDatePicker = (TextView) findViewById(R.id.txt_wisatawan_tgllhr);
        wstHp = (EditText) findViewById(R.id.txt_wisatawan_hp);
        wstPsswd1 = (EditText) findViewById(R.id.txt_wisatawan_passwd);
        wstPsswd2 = (EditText) findViewById(R.id.txt_wisatawan_passwd_2);

        btn_pendaftaran_wisatawan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goto_getstarted = new Intent(RegisterWisatawanActivity.this, GetStartedActivity.class);
                startActivity(goto_getstarted);
            }
        });

        btn_daftar_wisatawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                postWisatawan_x();
//                getData();
                getDataPostVolley();

            }
        });
    }




    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }



    private void getDataPostVolley() {


        EditText  wstName, wstEmail, wstTgllhr,wstHp,wstPsswd1,wstPsswd2;
        wstName = (EditText) findViewById(R.id.txt_wisatawan_name);
        wstEmail = (EditText) findViewById(R.id.txt_wisatawan_email);
        wstTgllhr = (EditText) findViewById(R.id.txt_wisatawan_tgllhr);
        wstHp = (EditText) findViewById(R.id.txt_wisatawan_hp);
        wstPsswd1 = (EditText) findViewById(R.id.txt_wisatawan_passwd);
        wstPsswd2 = (EditText) findViewById(R.id.txt_wisatawan_passwd_2);


        final String nama_val = wstName.getText().toString();
        final String email_val = wstEmail.getText().toString();
        final String tgllhr_val = wstTgllhr.getText().toString();
        final String phone_val = wstHp.getText().toString();
        final String passwd1_val = wstPsswd1.getText().toString();
        final String passwd2_val = wstPsswd2.getText().toString();


        if(TextUtils.isEmpty(nama_val) ) {
            wstName.setError("Nama Masih Kosong!");
        }

       if  (!EMAIL_ADDRESS_PATTERN.matcher(email_val).matches() ) {
           wstEmail.setError("Alamat Email Invalid!");
       }

        if(TextUtils.isEmpty(tgllhr_val) ) {
            wstTgllhr.setError("Tgl Lahir Masih Kosong!");
        }
        if(TextUtils.isEmpty(phone_val) ) {
            wstHp.setError("Nmr Telp Masih Kosong!");
        }
        if(TextUtils.isEmpty(passwd1_val) || passwd1_val.length() < 8 ) {
            wstPsswd1.setError("Password Masih Kosong || Password Min 8 Char!");
        }
        if( !passwd1_val.toString().equals(passwd2_val.toString()) ) {
            wstPsswd2.setError("Password Tidak Sesuai !");
        }

//       Toast.makeText(RegisterWisatawanActivity.this, "Error ...", Toast.LENGTH_LONG).show();
//       getDt();
        else {
            newPostDt();
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

    void newPostDt(){
        String USERNAME_KEY = "usernamekey";
        String username_key= "";

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/param_all.php";
        final RequestQueue requestQueue = Volley.newRequestQueue(RegisterWisatawanActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("triono", "response 1 ===" + response );
                        try {

                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                            JSONObject jObj = new JSONObject(response);
                            String data = jObj.getString("data");

                            JSONObject jObj2 = new JSONObject(data);
                            String ket = jObj2.getString("keterangan");
                            String email = jObj2.getString("alamat_email");


                            Boolean sukses = jObj.getBoolean("success");
//                            String result_dt = json.getString("data.keterangan");
                            Log.i("triono", "sukses data ===" + data );
                            Log.i("triono", "keterangan ===" + ket );


                            if (sukses){
                                Intent goto_register_success_wstwn = new Intent(RegisterWisatawanActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                goto_register_success_wstwn.putExtra("result_dt_ket", ket);
                                goto_register_success_wstwn.putExtra("result_dt_email", email);
                                startActivity(goto_register_success_wstwn);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }

//                        Log.i("triono", "response.jsonArray =" + jsonArray.toString() );
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("triono", "response =" + error.toString());
                error.printStackTrace();
                requestQueue.stop();
            }
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

                final String nama_val = wstName.getText().toString();
                final String email_val = wstEmail.getText().toString();
                final String tgllhr_val = wstTgllhr.getText().toString();
                final String phone_val = wstHp.getText().toString();
                final String passwd1_val = wstPsswd1.getText().toString();
                final String passwd2_val = wstPsswd2.getText().toString();


//                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(username_key, wstName.getText().toString());
//                editor.apply();

//                Toast.makeText(getApplicationContext(),"wstName"+
//                        wstName.getText().toString(),Toast.LENGTH_LONG).show();

//                obj.put("nama", "triono");
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

//                Log.i("triono", "obj ===" + obj );

//                System.exit(0);
                return obj;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void getDt() {

        Ws.q(this).add(new StringRequest("https://triono613.github.io/mountain.json",
                this::onJoke,
                this::onJokeError));
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




    void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/posts/1";
//        String url = "http://kaffah.amanahgitha.com/~androidwisata/param_all.php";
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonPost = new JSONObject(response.toString());
                            txt_view_wisatawan_email.setText(jsonPost.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response", error.toString());
            }
        });
        queue.add(stringRequest);

    }

    void getDataPost() {


    }

    void postWisatawan_x() {
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterWisatawanActivity.this);
        String BASE_URL = "http://kaffah.amanahgitha.com/~androidwisata/param_all.php";
        JSONObject jsonBody = new JSONObject();
        JSONObject jsonPost = new JSONObject();
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonPost = new JSONObject(response.toString());

                    Log.i("code_tim", "json - " + response);

//                        txt_view_wisatawan_email.setText(jsonPost.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", "triono");
                params.put("tgl_lahir", "2020-01-12");
                params.put("alamat_email", "triono@amanahgitha.com");
                params.put("flag", "daftar_wisatawan");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content_Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
