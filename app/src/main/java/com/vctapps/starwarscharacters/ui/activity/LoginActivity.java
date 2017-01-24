package com.vctapps.starwarscharacters.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vctapps.starwarscharacters.R;
import com.vctapps.starwarscharacters.model.PerfilSingleton;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText editUserName;
    private TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputLayout = (TextInputLayout) findViewById(R.id.input_layout_user_name);
        editUserName = (EditText) findViewById(R.id.edit_user_name);
        btnLogin = (Button) findViewById(R.id.button_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUserName.getText().toString().equals("")) {
                    textInputLayout.setError("Por favor, me diga seu nome.");
                    return;
                }

                String userName = editUserName.getText().toString();

                PerfilSingleton.getInstance().setUserName(userName);

                Intent it = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(it);
            }
        });
    }
}
