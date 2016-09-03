package com.rodrigo.matchajob.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rodrigo.matchajob.R;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Create user");
    }

    public void register(View v){
        EditText editEmail = (EditText) findViewById(R.id.login_email);
        EditText editPassword = (EditText) findViewById(R.id.login_password);
        EditText editPasswordRepeat = (EditText) findViewById(R.id.login_password_repeat);

        if (!editEmail.getText().toString().equals("") && !editPassword.getText().toString().equals("") && !editPasswordRepeat.getText().toString().equals("")){
            if(!editPassword.getText().toString().equals("") && editPassword.getText().toString().equals(editPasswordRepeat.getText().toString())){
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
}
