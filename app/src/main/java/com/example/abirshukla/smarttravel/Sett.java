package com.example.abirshukla.smarttravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class Sett extends AppCompatActivity {
    RadioButton dis;
    RadioButton price;
    RadioButton choose;

    RadioButton disF;
    RadioButton priceF;
    RadioButton chooseF;
    RadioButton chooseLocalF;
    RadioButton voice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);
       dis = (RadioButton) findViewById(R.id.distance);
         price = (RadioButton) findViewById(R.id.price);
         choose = (RadioButton) findViewById(R.id.choose);
        disF = (RadioButton) findViewById(R.id.distanceF);
        priceF = (RadioButton) findViewById(R.id.priceF);
        chooseF = (RadioButton) findViewById(R.id.chooseF);

        voice = (RadioButton) findViewById(R.id.voiceF);
        chooseLocalF = (RadioButton) findViewById(R.id.chooseLocalF);


        String option = TripInfo.getGasOption();
        if (option.equals("Distance")) {
            dis.setChecked(true);
            price.setChecked(false);
            choose.setChecked(false);
        }
        else if (option.equals("Price")) {
            price.setChecked(true);
            dis.setChecked(false );
            choose.setChecked(false);
        } else if (option.equals("Choose")) {
            choose.setChecked(true);
            price.setChecked(false);
            dis.setChecked(false);
        }
        option = TripInfo.getFoodOption();
        if (option.equals("Distance")) {
            disF.setChecked(true);
        }
        else if (option.equals("Price")) {
            priceF.setChecked(true);
        } else if (option.equals("Choose")) {
            chooseF.setChecked(true);
        }
        else if (option.equals("Choose Local")) {
            chooseLocalF.setChecked(true);
        }else if(option.equals("Voice")) {
            voice.setChecked(true);
        }

    }
    public void set(View view) {
        // Check which radio button was clicked
        if (dis.isChecked()) {
            TripInfo.setGasOption("Distance");
        }
        else if (price.isChecked()) {
            TripInfo.setGasOption("Price");
        }
        else if (choose.isChecked()) {
            TripInfo.setGasOption("Choose");
        }

        if (disF.isChecked()) {
            TripInfo.setFoodOption("Distance");
        }
        else if (priceF.isChecked()) {
            TripInfo.setFoodOption("Price");
        }
        else if (chooseF.isChecked()) {
            TripInfo.setFoodOption("Choose");
        }
        else if (chooseLocalF.isChecked()) {
            TripInfo.setFoodOption("Choose Local");
        }
        else if (voice.isChecked()) {
            TripInfo.setFoodOption("Voice");
        }



        Intent i = new Intent(this,Home.class);
        TripInfo.change = false;
        startActivity(i);

    }
}
