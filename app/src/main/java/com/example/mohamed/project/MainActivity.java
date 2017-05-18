package com.example.mohamed.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private static final String LOGIN_URL = "http://haladoctor.com/testSec/login.php";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    private EditText email, password;
    private TextView  SignupBtn;
private Button Login;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //--------------------------------------------------
        Login = (Button) findViewById(R.id.loginbu);
        SignupBtn = (TextView) findViewById(R.id.newAccount);

        email = (EditText) findViewById(R.id.emailtxt);
        password = (EditText) findViewById(R.id.passwordtxt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);


        //------------------------------------------------------
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Email = email.getText().toString().trim();
                final String Password = password.getText().toString().trim();


                if (Email.equals("") || Password.equals("")) {
                    Toast.makeText(MainActivity.this, "No Data", Toast.LENGTH_LONG).show();
                    return;
                }
                final SherdPrefarnaceForLogin login=new SherdPrefarnaceForLogin(MainActivity.this);
                progressDialog.show();
                if (CheckConnection.isNetworkAvailableAndConnected(MainActivity.this)&& CheckConnection.isNetworkConnected(MainActivity.this)) {
                    final String[] responseMessage = {""};
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        responseMessage[0] = jsonObject.getString("status");

                                        if (!responseMessage[0].equals("1")) {
                                            Toast.makeText(MainActivity.this, "Login Error " + response, Toast.LENGTH_LONG).show();
                                            ResetLogin();
                                            progressDialog.dismiss();

                                        } else {
                                            login.Save(Email,Password);
                                            progressDialog.dismiss();
                                            startActivity(new Intent(MainActivity.this, screen3.class));
                                            finish();
                                        }

                                    } catch (JSONException ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "check your email and password ", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }) {


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put(KEY_EMAIL, Email);
                            map.put(KEY_PASSWORD, Password);
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    if (login.CheckUser(Email,Password)){
                        progressDialog.dismiss();
                        startActivity(new Intent(MainActivity.this, screen3.class));
                    }else{
                        Toast.makeText(MainActivity.this, "check your email and password ", Toast.LENGTH_LONG).show();
                         progressDialog.dismiss();
                    }
                }

            }

        });
        //---------------------------------------------------------------------------------
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getBaseContext(), screen2.class);
                startActivity(k);
                finish();
            }
        });
    }

    //-------------------------------------------------
    private void ResetLogin() {
        email.setText("");
        password.setText("");
    }


}
