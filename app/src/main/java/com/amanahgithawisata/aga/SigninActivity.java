package com.amanahgithawisata.aga;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amanahgithawisata.aga.Adapter.SessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SigninActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    SessionManager sessionManager;
    Button btn_login_user;
    Button _btn_hide_pswd;
    EditText txt_login_email;
    LinearLayout _linear_lupa_password;
    CheckBox checkbox;
    EditText _txt_login_passwd;
    LoginButton _facebook_sign_in;
    SignInButton _google_sign_in;
    GoogleApiClient googleApiClient;
    public static  final  int SIGN_IN = 1;
    CallbackManager callbackManager;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SigninActivity.this, GetStartedActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
//        Toast.makeText(SigninActivity.this,"sessionManager.isLoggedIn()= "+sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }


    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(SigninActivity.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        _google_sign_in = findViewById(R.id.google_sign_in);
        _google_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,SIGN_IN);
            }
        });


        _facebook_sign_in =  findViewById(R.id.facebook_sign_in);
        _facebook_sign_in.setReadPermissions("email");
        // If using in a fragment
//        _facebook_sign_in.setFragment(this);
        _facebook_sign_in.setReadPermissions(Arrays.asList("email","public_profile"));

        setupView();
        btn_login_user.setEnabled(true);
        btn_login_user.setText("Login");
        sessionManager = new SessionManager(getApplicationContext());
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        txt_login_email = (EditText) findViewById(R.id.txt_login_email);
        _linear_lupa_password = (LinearLayout) findViewById(R.id.linear_lupa_password);
//        _btn_hide_pswd = findViewById(R.id.btn_hide_pswd);
        _txt_login_passwd = findViewById(R.id.txt_login_passwd);

        Log.i("SigninActivity","sessionManager.isLoggedIn() = "+ sessionManager.isLoggedIn() );
        Log.i("SigninActivity","sessionManager.getFlag= "+ sessionManager.getFlag());
        Log.i("SigninActivity","SessionManager.key_kode_lokasi= "+  sessionManager.getUserDetail().get(SessionManager.key_kode_lokasi));




        checkbox =  findViewById(R.id.checkbox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    _txt_login_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    _txt_login_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        if( sessionManager.isLoggedIn() ){
//            Intent intent;
            /*
            switch (sessionManager.getFlag())
            {
                case "1":
                    intent = new Intent(SigninActivity.this, DashboardWisatawanOLdActivity.class);
                    break;
                case "0":
                    intent = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
                    break;
                default:
                    intent = new Intent(SigninActivity.this,SigninActivity.class);
                    break;
            }
            */

            Intent intent;
            switch (sessionManager.getFlag())
            {
                case "1":
                    if (Objects.equals(sessionManager.getUserDetail().get(SessionManager.key_user_level), "0")){
//                        intent = new Intent(SigninActivity.this, DashboardWisatawanOLdActivity.class);
                        intent = new Intent(SigninActivity.this, DashboardWisatawanActivity.class);
                        break;
                    } else {
                        intent = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
                        break;
                    }

                case "0":
                    intent = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
                    break;
                default:
                    intent = new Intent(SigninActivity.this,SigninActivity.class);
                    break;
            }
            startActivity(intent);
            finish();
        }

        btn_login_user.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (isOnline()) {
                    getDataPostVolley();
                } else {
                    try {
                        Toast.makeText(SigninActivity.this, "Tidak Ada Koneksi Internet", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("", "Show Dialog: " + e.getMessage());
                    }
                }

            }
        });

        _linear_lupa_password.setOnClickListener(v -> {
                Intent x = new Intent(SigninActivity.this, LupaPasswordActivity.class);
                startActivity(x);
        });


        _facebook_sign_in.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                Log.e("","respon profile1"+ profile.toString());
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        (object, response) -> {
                            Log.e("","respon registerCallback"+ response.toString());
                            Log.e("","respon profile2"+ profile.toString());
                            try {
                                    Log.i("","Hi "+object.getString("name"));
                            } catch (JSONException ex){
                                ex.printStackTrace();
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","id,name");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
//                Log.i("tag","error="+ error.toString());
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplication(),"Error Login", Toast.LENGTH_SHORT).show();
                Log.i("tag","error="+ error.toString());
            }
        });

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };

    }

    private void setReadPermissions(String email) {
    }



    private void getDataPostVolley() {
        EditText loginEmail, loginPsswd;

        loginEmail = (EditText) findViewById(R.id.txt_login_email);
        loginPsswd = (EditText) findViewById(R.id.txt_login_passwd);


        final String email_val = loginEmail.getText().toString();
        final String passwd_val = loginPsswd.getText().toString();


//        if  (!Help.EMAIL_ADDRESS_PATTERN.matcher(email_val).matches() ) {
//            loginEmail.setError("Alamat Email Invalid!");
//        }
        if(TextUtils.isEmpty(passwd_val) ) {
            loginPsswd.setError("Password Masih Kosong!");
        }
        else {
            newPostLogin();
        }
    }


    @SuppressLint("SetTextI18n")
    void newPostLogin(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        btn_login_user.setEnabled(false);
        btn_login_user.setText("Loading...");

        String server_url = "http://kaffah.amanahgitha.com/~androidwisata/?data=login_wisata";
        final RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Boolean sukses = jObj.getBoolean("success");

                            Log.i("triono ","jObj "+ jObj);
                            Log.i("triono ","sukses "+ sukses);

                            btn_login_user.setEnabled(true);
                            btn_login_user.setText("LOGIN");
                            btn_login_user.refreshDrawableState();

                            if(sukses) {

                                String data = jObj.getString("data");
                                JSONObject jObj_child = new JSONObject(data);

                                Log.i("triono", "sukses= " + sukses);
                                Log.i("triono", "data ===" + data);

                                Boolean is_login = true;
                                String val_id = jObj_child.getString("id");
                                String val_email = jObj_child.getString("alamat_email");
                                String val_flag = jObj_child.getString("flag");
                                String val_name = jObj_child.getString("full_name");
                                String val_ket = jObj_child.getString("keterangan");
                                String val_kode_lokasi = jObj_child.getString("kode_lokasi");
                                String val_nama_lokasi = jObj_child.getString("nama_lokasi");
                                String val_tgl_lahir = jObj_child.getString("tgl_lahir");
                                String val_jenis_kelamin = jObj_child.getString("jenis_kelamin");
                                String val_sellular_no = jObj_child.getString("sellular_no");
                                String val_user_level = jObj_child.getString("user_level");
                                String val_kode_pintu = jObj_child.getString("kode_pintu");

                                Log.i("SigninActivity", "val_flag ===" + val_flag);
                                Log.i("SigninActivity", "val_kode_lokasi ===" + val_kode_lokasi);

                                if (jObj_child.getString("flag").equals("0")) {

                                    Intent i = new Intent(SigninActivity.this, SuccessRegistrasiWisatawanActivity.class);
                                    i.putExtra("result_dt_ket",val_ket);
                                    i.putExtra("result_dt_email", val_email);
                                    i.putExtra("result_dt_berhasil", false);
                                    i.putExtra("result_dt_flag", "flagSignin");
                                    startActivity(i);


                                } else if (jObj_child.getString("flag").equals("1")) {

                                    sessionManager.createSessionUserLogin( val_email,
                                                                            val_flag,
                                                                            val_name,
                                                                            val_id,
                                                                            val_ket,
                                                                            val_kode_lokasi,
                                                                            val_nama_lokasi,
                                                                            val_tgl_lahir,
                                                                            val_jenis_kelamin,
                                                                            val_sellular_no,
                                                                            val_user_level,
                                                                            val_kode_pintu);

                                    if (jObj_child.getString("kode_lokasi").equals("null")) {

                                        /*  activity for Wisatawan */
                                        Log.i("SigninActivity", "kode_lokasi ===" + jObj_child.getString("kode_lokasi"));
//                                                Toast.makeText(SigninActivity.this,"kode_lokasi equals null= "+ jObj_child.getString("kode_lokasi"), Toast.LENGTH_LONG).show();

                                        Intent ii = new Intent(SigninActivity.this, DashboardWisatawanActivity.class);
                                        ii.putExtra("result_dt_ket", val_ket);
                                        ii.putExtra("result_dt_email", val_email);
                                        ii.putExtra("result_dt_berhasil", true);
                                        ii.putExtra("result_dt_flag", "");
                                        startActivity(ii);
                                        overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
                                    } else {
                                        /*  activity for Petugas */

//                                        Toast.makeText(SigninActivity.this,"kode_lokasi not null= "+ jObj_child.getString("kode_lokasi"), Toast.LENGTH_LONG).show();

                                        Intent iii = new Intent(SigninActivity.this, DashboardPetugasActivity.class);
                                        iii.putExtra("result_dt_ket", val_ket);
                                        iii.putExtra("result_dt_email", val_email);
                                        iii.putExtra("result_dt_berhasil", true);
                                        iii.putExtra("result_dt_flag", "");
                                        startActivity(iii);
                                        overridePendingTransition(R.anim.app_getstarted,R.anim.btt);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            Log.i("triono", "error ===" + e.toString() );
                            e.printStackTrace();
                        }

                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("", "response =" + error.toString());
//                Toast.makeText(SigninActivity.this,"Kesalahan Network= "+ error.toString(), Toast.LENGTH_LONG).show();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                btn_login_user.setEnabled(true);
                btn_login_user.setText("Login");

                AlertDialog.Builder builder = new AlertDialog.Builder(SigninActivity.this);
                builder.setMessage("Terjadi Gangguan, Refresh ")
                        .setCancelable(false)
                        .setPositiveButton("Ya", (dialog, id) -> {
                            newPostLogin();
                        })
                        .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();

                error.printStackTrace();
                requestQueue.stop();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> obj = new HashMap<String, String>();

                EditText loginEmail, loginPsswd ;
                Spinner spinnerLokWisata;

                loginEmail = (EditText) findViewById(R.id.txt_login_email);
                loginPsswd = (EditText) findViewById(R.id.txt_login_passwd);

                final String email_val = loginEmail.getText().toString().trim();
                final String passwd1_val = loginPsswd.getText().toString().trim();

                obj.put("alamat_email",email_val );
                obj.put("kata_kunci", passwd1_val );
                return obj;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void setupView() {
        btn_login_user = (Button) findViewById(R.id.btn_login_user);
        txt_login_email = (EditText) findViewById(R.id.txt_login_email);

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == SIGN_IN ){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            assert result != null;
            if( result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                assert account != null;
                Log.i("","getDisplayName="+account.getDisplayName());
                Log.i("","getEmail="+account.getEmail());
                Log.i("","getId="+account.getId());
                Log.i("","getAccount="+account.getAccount());

                Toast.makeText(SigninActivity.this,"account= "+ account.getDisplayName()  +" and "+account.getEmail(), Toast.LENGTH_LONG).show();

            }
        }

        // for facebook
        callbackManager.onActivityResult(requestCode,resultCode,data);


    }
}
