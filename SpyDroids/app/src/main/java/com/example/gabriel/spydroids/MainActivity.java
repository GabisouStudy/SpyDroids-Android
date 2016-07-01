package com.example.gabriel.spydroids;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("SpyDroids");

        //Android variables
        final Button buttonPlay = (Button) findViewById(R.id.button_play);

        //Logic variables


        /*@Override
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
        {
            Context c = view.getContext();

            TextView textViewItem = ((TextView) view.findViewById(R.id.activityMainTextViewItem));

            switch(textViewItem.getText().toString())
            {
                case "Linear Layout": ChangeActivity(c, LinearLayoutActivity.class); break;
                case "Relative Layout": ChangeActivity(c, RelativeLayoutActivity.class); break;
                case "Arkanoid": ChangeActivity(c, StartScreen.class); break;
                case "Intent": ChangeActivity(c, IntentActivity.class); break;
                case "Broadcast Receiver": ChangeActivity(c, BroadcastActivity.class); break;
                case "Notification": ChangeActivity(c, NotificationActivity.class); break;
                case "Alarm": ChangeActivity(c, AlarmActivity.class); break;
                case "Service": ChangeActivity(c, ServiceActivity.class); break;
                case "Handler": ChangeActivity(c, HandlerChild.class); break;
                case "SMS": ChangeActivity(c, SMSActivity.class); break;
                case "Audio": ChangeActivity(c, AudioActivity.class); break;
                case "Video": ChangeActivity(c, VideoActivity.class); break;
                case "Camera": ChangeActivity(c, CameraActivity.class); break;
                case "Socket TCP Client": ChangeActivity(c, CalculatorActivity.class); break;
                case "HTTP Client": ChangeActivity(c, LoginActivity.class); break;
            }
        }

        //Trash buttons logic
        /*buttonPlay.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });*/
    }


}
