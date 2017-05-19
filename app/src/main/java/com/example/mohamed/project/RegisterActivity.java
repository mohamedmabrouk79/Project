package com.example.mohamed.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {
    private static final String SIGNUP_URL = "http://haladoctor.com/testSec/register.php";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_FNAME = "fname";
    public static final String KEY_LNAME = "lname";
    public static final String KEY_PASSWORD = "password";

    private EditText email, password, fname, lname;
    private Button newaccount;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        newaccount = (Button) findViewById(R.id.buNewAccount);

        email = (EditText) findViewById(R.id.emailtxt);
        password = (EditText) findViewById(R.id.passwordtxt);
        fname = (EditText) findViewById(R.id.fnametxt);
        lname = (EditText) findViewById(R.id.lnametxt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                final String Fname = fname.getText().toString().trim();
                final String Lname = lname.getText().toString().trim();
                 if(TextUtils.isEmpty(Email)){
                     Toast.makeText(RegisterActivity.this, "Enter your Email", Toast.LENGTH_LONG).show();
                 }else if (TextUtils.isEmpty(Password)){
                     Toast.makeText(RegisterActivity.this, " Enter Your password", Toast.LENGTH_LONG).show();
                 }else if (TextUtils.isEmpty(Fname)){
                     Toast.makeText(RegisterActivity.this, " Enter Your First Name", Toast.LENGTH_LONG).show();
                 }else if (TextUtils.isEmpty(Lname)){
                     Toast.makeText(RegisterActivity.this, " Enter Your Last Name", Toast.LENGTH_LONG).show();
                 } else {
                     progressDialog.show();
                     final String[] responseMessage = {""};
                     StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNUP_URL,
                             new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {

                                     try {
                                         JSONObject jsonObject = new JSONObject(response);
                                         responseMessage[0] = jsonObject.getString("status");

                                         if (!responseMessage[0].equals("1")) {
                                             Toast.makeText(RegisterActivity.this, "Login Error " + response, Toast.LENGTH_LONG).show();
                                             ResetLogin();
                                             progressDialog.dismiss();

                                         } else {
                                             progressDialog.dismiss();
                                             startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                             finish();
                                         }

                                     } catch (JSONException ex) {
                                         ex.printStackTrace();
                                     }

                                 }
                             }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                             progressDialog.dismiss();
                         }
                     }) {

                         @Override
                         protected Map<String, String> getParams() throws AuthFailureError {
                             Map<String, String> map = new HashMap<String, String>();
                             map.put(KEY_EMAIL, Email);
                             map.put(KEY_PASSWORD, Password);
                             map.put(KEY_FNAME, Fname);
                             map.put(KEY_LNAME, Lname);
                             return map;

                         }
                     };
                     RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                     requestQueue.add(stringRequest);
                     startActivity(new Intent(getBaseContext(), LoginActivity.class));
                 }
    }

});


    }
    private void ResetLogin() {
        email.setText("");
        password.setText("");
    }
}
