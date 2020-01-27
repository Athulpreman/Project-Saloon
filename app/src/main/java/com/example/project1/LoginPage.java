package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity
{
    TextView register;
    EditText ownerShopID,password;
    String sownerShopID,spassword;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Login=(Button)findViewById(R.id.loginSignInButton);
        register=(TextView)findViewById(R.id.loginRegisterTextView);

        ownerShopID=(EditText)findViewById(R.id.loginUserName);
        password=(EditText)findViewById(R.id.loginUserPassword);

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sownerShopID=ownerShopID.getText().toString();
                spassword=password.getText().toString();

                if (sownerShopID.equals("admin")&&spassword.equals("1234"))
                {
                    Intent intent=new Intent(getApplicationContext(),AdminPage.class);
                    startActivity(intent);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}
