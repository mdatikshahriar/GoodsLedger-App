package com.example.goods_ledger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.goods_ledger.ConsumerPart.ConsumerStartActivity;
import com.example.goods_ledger.ManufacturerPart.AddManufacturerActivity;
import com.example.goods_ledger.ManufacturerPart.ManufacturerStartActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;
    private TextView forgotPasswordTextView, signUpTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_activity_username_editText);
        password = findViewById(R.id.login_activity_password_editText);
        loginButton = findViewById(R.id.login_activity_login_button);
        forgotPasswordTextView = findViewById(R.id.login_activity_forgotPassword_textView);
        signUpTextView = findViewById(R.id.login_activity_signup_textView);
        progressBar = findViewById(R.id.activity_login_progressbar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String accountUsername = username.getText().toString();
                final String accountPassword = password.getText().toString();

                if(accountUsername.isEmpty()){
                    username.setError("Please type something!");
                    return;
                }
                else if(accountPassword.isEmpty() || accountPassword.length() < 6) {
                    password.setError("You must have 6 characters in your password");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, MainActivity.getLinks().getURL_loginAccount(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    JSONObject object = jsonObject.getJSONObject("Record");

                                    String accountKeyResponse = jsonObject.getString("Key").trim();
                                    String accountTokenResponse = object.getString("AccountToken").trim();
                                    String accountTypeResponse = object.getString("AccountType").trim();
                                    String accountNameResponse = object.getString("AccountName").trim();
                                    String accountUsernameResponse = object.getString("AccountUsername").trim();
                                    String accountEmailResponse = object.getString("AccountEmail").trim();
                                    String accountIsManufacturerAssignedResponse = object.getString("AccountIsManufacturerAssigned").trim();

                                    MainActivity.getSavedValues().setAccountKey(accountKeyResponse);
                                    MainActivity.getSavedValues().setAccountToken(accountTokenResponse);
                                    MainActivity.getSavedValues().setAccountType(accountTypeResponse);
                                    MainActivity.getSavedValues().setAccountName(accountNameResponse);
                                    MainActivity.getSavedValues().setAccountUsername(accountUsernameResponse);
                                    MainActivity.getSavedValues().setAccountEmail(accountEmailResponse);
                                    MainActivity.getSavedValues().setAccountIsManufacturerAssigned(accountIsManufacturerAssignedResponse);

                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                    if(accountTypeResponse.equals("Manufacturer")){

                                        if(accountIsManufacturerAssignedResponse.equals("true")){

                                            startActivity(new Intent(LoginActivity.this, ManufacturerStartActivity.class));
                                            finish();
                                        }
                                        else if (accountIsManufacturerAssignedResponse.equals("false")){

                                            startActivity(new Intent(LoginActivity.this, AddManufacturerActivity.class));
                                            finish();
                                        }
                                    }
                                    else if(accountTypeResponse.equals("Consumer")){
                                        startActivity(new  Intent(LoginActivity.this, ConsumerStartActivity.class));
                                        finish();
                                    }
                                } catch (JSONException e){
                                    progressBar.setVisibility(View.GONE);
                                    e.printStackTrace();
                                    Toast.makeText(LoginActivity.this, "Login failed! Error: " + response, Toast.LENGTH_SHORT).show();
                                    Log.d("catchError", e.toString());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login failed! Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("responseError", error.toString());
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("accountUsername", accountUsername);
                        params.put("accountPassword", accountPassword);
                        return params;
                    }
                };

                stringRequest.setRetryPolicy(MainActivity.getRetryPolicy());

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /////
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}
