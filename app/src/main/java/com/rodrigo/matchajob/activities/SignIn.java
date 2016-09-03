package com.rodrigo.matchajob.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends Activity {
    public final String SHARED_APP = "matchAJob";
    public final String SHARED_EMAIL = "email";

    public final String PARAM_EMAIL = "Email";
    public final String PARAM_PASSWORD = "Password";

    private RequestQueue requestQueue;
    private Map<String, String> postParams;
    private String url = "http://192.168.2.103:50286/user/find";
    private SharedPreferences sharedPreferences;

    public User user;

    public SignIn(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        sharedPreferences = getSharedPreferences(SHARED_APP, 0);

        user = new User();

        String emailLogged = sharedPreferences.getString(SHARED_EMAIL, "");
        if (emailLogged != ""){
//            open Home activity
            Toast toast = Toast.makeText(this ,"Login ok", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
        }
        requestQueue = Volley.newRequestQueue(this);
    }

    public void signIn(View v){
        EditText editEmail = (EditText) findViewById(R.id.login_email);
        EditText editPassword = (EditText) findViewById(R.id.login_password);

        User userReq = new User(editEmail.getText().toString(), editPassword.getText().toString());

        postParams = new HashMap<String, String>();
        postParams.put(PARAM_EMAIL, userReq.getEmail());
        postParams.put(PARAM_PASSWORD, userReq.getPassword());

        String url = Util.URL_API + "/user/find";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(postParams), new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                User userRes = new User();
                Gson gsonUser = new Gson();

                userRes = gsonUser.fromJson(response.toString(), User.class);

                if (userRes.getEmail() == null){
                    Toast toast = Toast.makeText(SignIn.this, "User not found!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(SignIn.this, "User found!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(SignIn.this, "Error!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void signUp(View v) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
//        open signUp activity
    }
}
