package com.example.user.emi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Creating references of both the buttons---------
        Button cal =(Button)findViewById(R.id.calculate);
        Button reset =(Button)findViewById(R.id.reset);
        //Setting constraints to the RESET Button---------
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText loan =(EditText)findViewById(R.id.loan);
                EditText rate =(EditText)findViewById(R.id.rate);
                EditText time =(EditText)findViewById(R.id.time);
                loan.setText("");
                rate.setText("");
                time.setText("");
            }
        });
        //Calling function openActivity()---------
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
    }
    //Calculation part and Creation of Intent for passing values and calling Second
    public void openActivity2() {
        //References of 3 EditText
        EditText loan = (EditText) findViewById(R.id.loan);
        EditText rate = (EditText) findViewById(R.id.rate);
        EditText time = (EditText) findViewById(R.id.time);
        String l = loan.getText().toString();
        String r = rate.getText().toString();
        String t = time.getText().toString();
        //Checking if the EditText Fields are Empty------
        if (TextUtils.isEmpty(l)) {
            loan.setError("Enter Principal Amount");
            loan.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(r)) {
            rate.setError("Enter Interest Rate");
            rate.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(t)) {
            time.setError("Enter Time Period");
            time.requestFocus();
            return;
        }
        float p = Float.parseFloat(l);
        float rt = Float.parseFloat(r);
        float tm = Float.parseFloat(t);
        //Checking for Zero values----------
        if (p == 0 || rt ==0 || tm == 0) {
            Toast.makeText(this, "Zero's are not allowed", Toast.LENGTH_LONG).show();
        } else {
            //Calculation of EMI-------------
            float principal = p;
            float roi = calInt(rt);
            float month = calMonth(tm);
            float numerator = calnumerator(roi, month);
            float denominator = caldenominator(numerator);
            float emi = calres(numerator, denominator, roi, principal);
            float tpi = emi * month;

            TextView textView3 = (TextView)findViewById(R.id.tv6);
            textView3.setText(String.valueOf(emi)+" "+"per month");
        }
    }
    //Functions used for Calculation---------
    public float calInt(float r){
        return (float)(r/12/100);
    }
    //Calculating Month
    public float calMonth(float t){
        return (float)(t*12);
    }
    //Numerator Calculation
    public float calnumerator(float roi, float month){
        return (float)(Math.pow(1+roi,month));
    }
    //Denominator Calculation
    public float caldenominator(float numerator){
        return (float)(numerator-1);
    }
    //Final result of EMI
    public float calres(float numerator, float denominator, float roi, float principal){
        return (float)(principal*roi*(numerator/denominator));
    }
}