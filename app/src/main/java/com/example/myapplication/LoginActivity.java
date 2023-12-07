package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
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
    private Button buttonRegister;
    private Button buttonForgetPwd;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonForgetPwd = findViewById(R.id.buttonForgetPwd);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (checkLogin(username, password)) {
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
                } else {
                    showAlert("登陆失败", "用户名或密码不正确");
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动注册活动
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        buttonForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkLogin(String username, String password) {
        dataBaseHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
        return dataBaseHelper.checkUser(username, password);
    }

    private int loginUser(String username, String password) {
        dataBaseHelper = new DataBaseHelper(this, "DataBase.db", null, 1);
        return dataBaseHelper.getUserIdByUsername(username);
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 点击确定按钮后的操作
            }
        });
        builder.setNegativeButton("取消", null); // 不需要特殊操作时可以传递null

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}