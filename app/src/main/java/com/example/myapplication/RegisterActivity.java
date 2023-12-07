package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.zip.CRC32C;


public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPhoneNumber;
    private Button buttonRegister2;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // 创建一个新的布局文件

        ImageButton retButton = findViewById(R.id.register_return_button);
        retButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonRegister2 = findViewById(R.id.buttonRegister2);
        buttonRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String phoneNumber = editTextPhoneNumber.getText().toString();
                registerUser(username, password,phoneNumber);
            }
        });
    }

    private void registerUser(String username, String password, String phoneNumber) {
        dataBaseHelper =  new DataBaseHelper(this, "DataBase.db", null, 1);
        ContentValues values = new ContentValues();
        values.put("name",username);
        values.put("password",password);
        values.put("phoneNumber",phoneNumber);
        ContentValues values1 = new ContentValues();
        values1.put("phoneNumber",phoneNumber);
        values1.put("password",password);
        ContentValues values2 = new ContentValues();
        values2.put("name",username);
        values2.put("password",password);
        ContentValues values3 = new ContentValues();
        long result = dataBaseHelper.getWritableDatabase().insert("User",null,values);
        long result2 = dataBaseHelper.getWritableDatabase().insert("PasswordTB",null,values1);
        long result3 = dataBaseHelper.getWritableDatabase().insert("LoginTB",null,values2);
        values3.put("name",username);
        values3.put("id",result);
        long result4 = dataBaseHelper.getWritableDatabase().insert("IdTB",null,values3);
        if (result != -1 && result2 != -1 && result3 != -1 && result4 != -1) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            showAlert("注册失败","用户名或手机号重复");
        }
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

