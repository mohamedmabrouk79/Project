package com.example.mohamed.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.login);
        Login = (Button) findViewById(R.id.loginbu);
        SignupBtn = (TextView) findViewById(R.id.newAccount);

        email = (EditText) findViewById(R.id.emailtxt);
        password = (EditText) findViewById(R.id.passwordtxt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Email = email.getText().toString().trim();
                final String Password = password.getText().toString().trim();


                if (TextUtils.isEmpty(Email)){
                    Toast.makeText(LoginActivity.this, " Enter your Email", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(Password)){
                    Toast.makeText(LoginActivity.this, " Enter Your Password", Toast.LENGTH_LONG).show();
                }else {
                    final SherdPrefarnaceForLogin login = new SherdPrefarnaceForLogin(LoginActivity.this);
                    progressDialog.show();
                    if (CheckConnection.isNetworkAvailableAndConnected(LoginActivity.this) && CheckConnection.isNetworkConnected(LoginActivity.this)) {
                        final String[] responseMessage = {""};
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            responseMessage[0] = jsonObject.getString("status");

                                            if (!responseMessage[0].equals("1")) {
                                                Toast.makeText(LoginActivity.this, "Login Error " + response, Toast.LENGTH_LONG).show();
                                                ResetLogin();
                                                progressDialog.dismiss();

                                            } else {
                                                login.Save(Email, Password);
                                                progressDialog.dismiss();
                                                startActivity(new Intent(LoginActivity.this, MovieSelectActvity.class));
                                                finish();
                                            }

                                        } catch (JSONException ex) {
                                            ex.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "check your email and password ", Toast.LENGTH_LONG).show();
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
                    } else {
                        Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                        if (login.CheckUser(Email, Password)) {
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MovieSelectActvity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "check your email and password ", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }

            }

        });
        //---------------------------------------------------------------------------------
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getBaseContext(), RegisterActivity.class);
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
