package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // 添加登录验证逻辑
                int userId = loginUser(username, password);

                SharedPreferences sharedPreferences = getSharedPreferences("LoginInfor", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("UserID", userId);
                editor.apply();
                /*将用户id进行储存，需要时，用以下代码进行调用：（注意userId是int，若写到TextView里需要转换成String
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginInfor", MODE_PRIVATE);
                    int userId = sharedPreferences.getInt("UserID", -1);
                    我在MainActivity中进行了示范
                */

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private int loginUser(String username, String password) {
        //通过数据库查找id，先返回定值
        return 123;
    }
}
