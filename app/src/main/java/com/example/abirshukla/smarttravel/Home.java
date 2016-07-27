package com.example.abirshukla.smarttravel;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {
    SharedPreferences sharedPref;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if (sharedPref != null) {
            String option = sharedPref.getString("option", "");
            String food = sharedPref.getString("food","");
            if (!option.equals("") && TripInfo.change) {
                TripInfo.setGasOption(option);
            }
            if (!food.equals("") && TripInfo.change) {
                TripInfo.setFoodOption(food);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Smart Travel");

    }




    public void showNotification(Context context) {
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Intent prevIntent = new Intent(Home.this, MainActivity.class);
        prevIntent.setAction("Prev");
        PendingIntent piPrev = PendingIntent.getService(this, 0, prevIntent, 0);

        Intent playIntent = new Intent(this, MainActivity.class);
        playIntent.setAction("Play");
        PendingIntent piPlay = PendingIntent.getService(this, 0, playIntent, 0);

        Intent nextIntet = new Intent(this, MainActivity.class);
        nextIntet.setAction("Next");
        PendingIntent piNext = PendingIntent.getService(this, 0, nextIntet, 0);

        mBuilder.setSmallIcon(R.drawable.icon)
                .setContentTitle("Title")
                .setContentText("Text")
                .setTicker("Ticker")
                .setWhen(0)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .addAction (R.drawable.icon, "Prev", piPrev)
                .addAction (R.drawable.icon,     "Play", piPlay)
                .addAction (R.drawable.icon,     "Next", piNext);

        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent piNotify =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(piNotify);
        mNotifyMgr.notify(1223, mBuilder.build());
    }
    public void startTrip(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String ad = editText.getText().toString();
        ad = ad.replace(" ","+");
        TripInfo.setAddress(ad);
        Intent i = new Intent(this,MainActivity.class);

        startActivity(i);
    }
    public void getVoiceAddress(View view) {
        //Toast.makeText(getApplicationContext(), "Icon Pressed!", Toast.LENGTH_LONG).show();
        promptSpeechInput();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent se = new Intent(Home.this,Sett.class);
            startActivity(se);
        }

        return super.onOptionsItemSelected(item);
    }


    private void promptSpeechInput() {
        String speech_prompt = "Where do you want to go?";
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                speech_prompt);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Speech Not Supported",
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String res = "";
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    res = result.get(0);
                }
                break;
            }

        }
        res = res.replace(" ","+");
        TripInfo.setAddress(res);
        Intent i = new Intent(this,MainActivity.class);

        startActivity(i);


    }





    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("option",TripInfo.getGasOption());

        savedInstanceState.putString("food",TripInfo.getFoodOption());
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = sharedPref.edit();
        String option = TripInfo.getGasOption();
        editor.putString("option", option);
        String food = TripInfo.getFoodOption();
        editor.putString("food",food);
        editor.commit();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        mNotificationManager.cancel("RESUME",1234);
        mNotificationManager.cancel("GAS",1234);
        mNotificationManager.cancel("RESUME",1234);
        mNotificationManager.cancelAll();
        super.onDestroy();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String option = savedInstanceState.getString("option");

        String food = savedInstanceState.getString("food");
        TripInfo.setGasOption(option);
        TripInfo.setFoodOption(food);
    }

}
