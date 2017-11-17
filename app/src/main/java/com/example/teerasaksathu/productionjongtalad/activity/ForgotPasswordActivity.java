package com.example.teerasaksathu.productionjongtalad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.teerasaksathu.productionjongtalad.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRestorePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initInstances();

        btnRestorePassword.setOnClickListener(this);
    }

    private void initInstances() {
        btnRestorePassword = (Button) findViewById(R.id.btnRestorePassword);
    }

    @Override
    public void onClick(View view) {
        if(view == btnRestorePassword){
            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
            builder.setTitle("กู้คืนรหัสผ่าน")
                    .setMessage("ระบบได้ทำการส่งรหัสผ่านไปที่ Email ของท่านเรียบร้อยแล้วโปรดเช็ค Email")
                    .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ForgotPasswordActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}
