package com.example.teerasaksathu.productionjongtalad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teerasaksathu.productionjongtalad.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//TODO :success test register
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etSurname;
    private EditText etPhonenumber;
    private EditText etMarketName;
    private EditText etMarketAddress;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initInstances();
        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etPhonenumber = (EditText) findViewById(R.id.etPhonenumber);
        etMarketName = (EditText) findViewById(R.id.etMarketName);
        etMarketAddress = (EditText) findViewById(R.id.etMarketAddress);
        btnRegister.setOnClickListener(this);
    }

    private void initInstances() {
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    @Override
    public void onClick(View view) {
        if (view == btnRegister) {
            if (registerChecking()) {
                String name = etName.getText().toString().trim();
                String surname = etSurname.getText().toString().trim();
                String phonenumber = etPhonenumber.getText().toString().trim();
                String marketName = etMarketName.getText().toString().trim();
                String marketAddress = etMarketAddress.getText().toString().trim();

                sendDataToDatabase sendDataToDatabase = new sendDataToDatabase();
                sendDataToDatabase.execute(name, surname, phonenumber, marketName, marketAddress);



                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("ทำการสมัครเสร็จสิ้น !!")
                        .setMessage("ขอบคุณสำหรับการสมัครมากชิกทางทีมงานจะทำการติดต่อกลับไปภายใน 24 ชม.")
                        .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("โปรดกรอกข้อมูลให้ครบทุกช่อง")
                        .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
    }

    private boolean registerChecking() {
        int count = 0;
        if (etName.getText().toString().length() == 0) {
            count++;
            return false;
        }

        if (etSurname.getText().toString().length() == 0) {
            count++;
            return false;
        }

        if (etPhonenumber.getText().toString().length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("กรุณากรอกเบอร์โทรศัพท์ให้ครบ 10 หลัก")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        if (etMarketName.getText().toString().length() == 0) {
            count++;
            return false;
        }

        if (etMarketAddress.getText().toString().length() == 0) {
            count++;
            return false;
        } else
            return true;
    }

    private class sendDataToDatabase extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/phpNew/register_market_admin.php";

        @Override
        protected String doInBackground(String... value) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("name", value[0])
                    .add("surname", value[1])
                    .add("phonenumber", value[2])
                    .add("marketName", value[3])
                    .add("marketAddress", value[4])
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Not Success - code : " + response.code();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error - " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG);
        }
    }
}
