package com.recipx.recipx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.recipx.recipx.firebase.user.User;
import com.recipx.recipx.firebase.util.After;


public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    Button logout_btn;
    TextView create_btn;
    Button delete_btn;
    EditText email_et;
    EditText password_et;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.button_login);

        create_btn = findViewById(R.id.button_create);


        email_et = findViewById(R.id.editText_email);
        password_et = findViewById(R.id.editText_password);

        user = new User(this);

        login_btn.setOnClickListener(view -> {
            String email = email_et.getText().toString();
            String password = password_et.getText().toString();

            user.login(email, password, new After() {
                @Override
                public Object success(Object result) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    return null;
                }

                @Override
                public Object fail(Object result) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    return null;
                }
            });
        });
        logout_btn.setOnClickListener(view -> {
            if (user.isLogin())
                user.logout();
            else
                Toast.makeText(getApplicationContext(), "not login", Toast.LENGTH_SHORT).show();
        });
        create_btn.setOnClickListener(view -> {
            String email = email_et.getText().toString();
            String password = password_et.getText().toString();

            user.create(email, password, new After() {
                @Override
                public Object success(Object result) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    return null;
                }

                @Override
                public Object fail(Object result) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    return null;
                }
            });
        });
        delete_btn.setOnClickListener(view -> {
            user.delete(new After() {
                @Override
                public Object success(Object result) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    return null;
                }

                @Override
                public Object fail(Object result) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                    return null;
                }
            });
        });
    }
}
