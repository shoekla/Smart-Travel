package com.example.abirshukla.smarttravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Hub extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
    }
    public void gas(View view) {
        Intent i = new Intent(this, Gas.class);
        startActivity(i);
    }
    public void food(View view) {
        Intent i = new Intent(this, Food.class);
        startActivity(i);
    }
    public void end(View view) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }
}
