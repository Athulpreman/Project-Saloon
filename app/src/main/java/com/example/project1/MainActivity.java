package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity
{
    Button blogin,gotosignup;
    TextView tshopOption,tforgotPass;
    EditText ephoneNo,epasswoed;
    String mobno,password;
    long backpress;
    Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blogin=(Button)findViewById(R.id.loginButtonlogin);
        gotosignup=(Button)findViewById(R.id.signinSignupButton);
        tshopOption=(TextView)findViewById(R.id.loginShopOption);
        tforgotPass=(TextView)findViewById(R.id.loginForgotPassword);
        ephoneNo=(EditText)findViewById(R.id.loginMobNo);
        epasswoed=(EditText)findViewById(R.id.loginPassword);


        blogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mobno=ephoneNo.getText().toString();
                password=epasswoed.getText().toString();
                if (mobno.isEmpty()||mobno.length()<10)
                {
                    ephoneNo.setError("Enter correct mob no");
                    ephoneNo.requestFocus();
                }
                else if (password.isEmpty())
                {
                    epasswoed.setError("Password is rquired");
                    epasswoed.requestFocus();
                }
                else
                {

                }
            }
        });
        gotosignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),Sign_up.class);
                startActivity(intent);
            }
        });
        tshopOption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(intent);
            }
        });
        tforgotPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }
    public void onBackPressed()
    {
        if (backpress+2000>System.currentTimeMillis())
            {
                backToast.cancel();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
            else
            {
                backToast= Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backpress=System.currentTimeMillis();
    }
}
