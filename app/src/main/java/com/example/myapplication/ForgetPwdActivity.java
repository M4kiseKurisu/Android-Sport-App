package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ForgetPwdActivity extends AppCompatActivity {

    private EditText editPhoneNumber1;
    private Button buttonResetPassword;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        editPhoneNumber1 = findViewById(R.id.editTextPhoneNumber1);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPassword();
            }
        });
    }

    private void getPassword() {
        /*String phoneNumber = editPhoneNumber1.getText().toString().trim();
        dataBaseHelper =  new DataBaseHelper(this, "DataBase.db", null, 1);
        Cursor cursor = dataBaseHelper.getReadableDatabase().query("PasswordTB",null,"phoneNumber = phoneNumber",new String[] {String.valueOf(phoneNumber)},null,null,null);

        // 检查输入是否有效
        if (!phoneNumber.isEmpty()) {
            // 显示错误
            return;
        }*/

        // 实现重置密码逻辑（可能需要与服务器通信）
        // 这里假设直接在本地进行处理
    }
}
