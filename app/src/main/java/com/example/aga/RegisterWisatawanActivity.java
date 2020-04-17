package com.example.aga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterWisatawanActivity extends AppCompatActivity {

    ImageView btn_pendaftaran_wisatawan_back;
    Button btn_daftar_wisatawan;
    TextView txt_view_wisatawan_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_wisatawan);

        btn_pendaftaran_wisatawan_back = findViewById(R.id.btn_pendaftaran_wisatawan_back);
        btn_daftar_wisatawan = findViewById(R.id.btn_daftar_wisatawan);
        txt_view_wisatawan_email = (TextView) findViewById(R.id.txt_view_wisatawan_email);

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
//                postWisatawan();
//                getData();
                getDataPost();
            }
        });
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
                Log.d("Error Response",error.toString());
            }
        });
        queue.add(stringRequest);

    }

    void getDataPost() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://triono613.github.io/mountain.json";
//        String url = "http://kaffah.amanahgitha.com/~androidwisata/param_all.php";
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonPost = new JSONObject(response.toString());
                            txt_view_wisatawan_email.setText(jsonPost.getString("data"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response",error.toString());
            }
        });
        queue.add(stringRequest);

    }

        void postWisatawan_x () {
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
                        txt_view_wisatawan_email.setText(jsonPost.getString("data"));
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
