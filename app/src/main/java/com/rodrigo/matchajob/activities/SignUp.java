package com.rodrigo.matchajob.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rodrigo.matchajob.R;
import com.rodrigo.matchajob.config.Util;
import com.rodrigo.matchajob.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends Activity {
    public final String PARAM_EMAIL = "Email";
    public final String PARAM_PASSWORD = "Password";

    private RequestQueue requestQueue;
    private Map<String, String> postParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Create user");

        requestQueue = Volley.newRequestQueue(this);
    }

    public void register(View v){
        EditText editEmail = (EditText) findViewById(R.id.login_email);
        EditText editPassword = (EditText) findViewById(R.id.login_password);
        EditText editPasswordRepeat = (EditText) findViewById(R.id.login_password_repeat);

        if (!editEmail.getText().toString().equals("") && !editPassword.getText().toString().equals("") && !editPasswordRepeat.getText().toString().equals("")){
            if(!editPassword.getText().toString().equals("") && editPassword.getText().toString().equals(editPasswordRepeat.getText().toString())){
                sendUser(editEmail.getText().toString(), editPassword.getText().toString());

//            send User to api
            }
            else{
                Toast toast = Toast.makeText(this, "Check the passwords", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else{
            Toast toast = Toast.makeText(this, "Fill all fields", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    public void sendUser(String email, String password){
        postParams = new HashMap<String, String>();
        postParams.put(PARAM_EMAIL, email);
        postParams.put(PARAM_PASSWORD, password);

        JsonObjectRequest userReq = new JsonObjectRequest(Request.Method.POST, Util.URL_API + "/user/add", new JSONObject(postParams), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                User user = gson.fromJson(response.toString(), User.class);

                if(user.getEmail() != null){
                    Toast toast = Toast.makeText(SignUp.this, "Ok, user registered", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    String erro = "";
                    try {
                        erro = response.getString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast toast = Toast.makeText(SignUp.this, erro , Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        userReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(userReq);
    }


}
