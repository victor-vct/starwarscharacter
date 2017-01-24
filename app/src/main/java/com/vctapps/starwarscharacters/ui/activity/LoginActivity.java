package com.vctapps.starwarscharacters.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vctapps.starwarscharacters.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUserName = (EditText) findViewById(R.id.edit_user_name);
        btnLogin = (Button) findViewById(R.id.button_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUserName.getText().toString().equals("")) return;

                Intent it = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(it);
            }
        });
    }
}
