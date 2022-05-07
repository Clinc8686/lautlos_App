package de.clinc8686.lautlos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private AudioManager mAudioManager;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        image = findViewById(R.id.ringImage);

        //Checkt beim App-Start ob Rechte gesetzt wurden, falls nicht: Benutzer nach Erlaubnis fragen
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }
        setImage(mAudioManager.getRingerMode());
    }

    //ändert das Statusbild
    public void setImage(int mode) {
        if (mode == 1 || mode == 2) {
            image.setImageResource(R.drawable.notsilent_foreground);
        } else {
            image.setImageResource(R.drawable.silent_foreground);
        }
    }

    //ändert den Nicht stören Modus wenn Button geklickt wird
    public void changeMode(View view) {
        if (mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL || mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            setImage(0);
        } else {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            setImage(2);
        }
    }
}