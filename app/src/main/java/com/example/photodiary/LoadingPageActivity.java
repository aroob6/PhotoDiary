package com.example.photodiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingPageActivity extends AppCompatActivity {

    View dialog;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingpage);

        /*dialog = (View) View.inflate(getApplicationContext(), R.layout.dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoadingPageActivity.this);
        //builder.setTitle("비밀번호 입력");
        builder.setView(dialog);
        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editTextPass1 = dialog.findViewById(R.id.editTextPass1);
                EditText editTextPass2 = dialog.findViewById(R.id.editTextPass2);
                EditText editTextPass3 = dialog.findViewById(R.id.editTextPass3);
                EditText editTextPass4 = dialog.findViewById(R.id.editTextPass4);
                if (editTextPass1.getText().toString().equals("1") && editTextPass2.getText().toString().equals("1") && editTextPass3.getText().toString().equals("1") && editTextPass4.getText().toString().equals("1")) {
                    startLoading();
                }
            }
        });
        builder.show();
*/
        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                finish();
            }
        }, 2000);
    }
}
