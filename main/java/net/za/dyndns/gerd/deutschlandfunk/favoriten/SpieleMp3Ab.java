package net.za.dyndns.gerd.deutschlandfunk.favoriten;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by hanno on 2015-07-27 19:20.
 */
public class SpieleMp3Ab {
  private Activity activity;
  private Context context;
  private TextView tx1, tx2, tx3;
  private int debug;
  private Button b1, b2, b3, b4;

  private MediaPlayer mediaPlayer;
  private double startTime = 0;
  private double finalTime = 0;
  private Handler myHandler = new Handler();
  private int forwardTime = 5000;
  private int backwardTime = 5000;
  private SeekBar seekBar;
  public static int oneTimeOnly = 0;
  private Helfer h;

  SpieleMp3Ab(Activity activity, Context context, int debug) {
    this.activity = activity;
    this.context = context;
    this.debug = debug;
    h = new Helfer(context, debug);
    h.logi(4, "Pl10", "Konstruktor SpieleMp3Ab");
    h.logi(4, "Pl20", context.toString());
    h.logi(4, "Pl20", activity.getApplicationContext().toString());

  }

  public void hören(File result) {
    // http://www.tutorialspoint.com/android/android_mediaplayer.htm
    mediaPlayer = MediaPlayer.create(context, Uri.fromFile(result));
    seekBar = (SeekBar) activity.findViewById(R.id.SeekBar01);
    seekBar.setClickable(false);
    b1 = (Button) activity.findViewById(R.id.button1);
    b2 = (Button) activity.findViewById(R.id.button2);
    b3 = (Button) activity.findViewById(R.id.button3);
    b4 = (Button) activity.findViewById(R.id.button4);
    tx1 = (TextView) activity.findViewById(R.id.fortschritt);
    tx2 = (TextView) activity.findViewById(R.id.fortschritt);
    tx3 = (TextView) activity.findViewById(R.id.fortschritt);

    b2.setEnabled(false);

/*
    //b2.setEnabled(true);
    //String schild = "Tu nix ";
    // if (debug > 1) schild = "Schild-001-debug>1 " + schild;
    //b2.setText(schild);

    b2.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            String schild = "Tu garnichts ";
            /*
//          if (allentries.löscheSendungsdatei()) // Ein Klick löscht
            if (allentries.umtaufeSendungsdatei()) // Ein Klick tauft um
              schild = "Lösche " + allentries.getFilename();
            else
              schild = "Kann " + allentries.getFilename() + " nicht löschen";
            Stern/
            Toast.makeText(activity,
                schild,
                Toast.LENGTH_SHORT).show();
          }

        }
    );
*/
    b3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(activity.getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
          seekBar.setMax((int) finalTime);
          oneTimeOnly = 1;
        }
        tx2.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))
            )
        );

        tx1.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))
            )
        );

        seekBar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        b2.setEnabled(true);
        b3.setEnabled(false);
      }
    });

    b2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(activity.getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
        mediaPlayer.pause();
        b2.setEnabled(false);
        b3.setEnabled(true);
      }
    });

    b1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int temp = (int) startTime;

        if ((temp + forwardTime) <= finalTime) {
          startTime = startTime + forwardTime;
          mediaPlayer.seekTo((int) startTime);
          Toast.makeText(activity.getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(activity.getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
        }
      }
    });

    b4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int temp = (int) startTime;

        if ((temp - backwardTime) > 0) {
          startTime = startTime - backwardTime;
          mediaPlayer.seekTo((int) startTime);
          Toast.makeText(activity.getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(activity.getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private Runnable UpdateSongTime = new Runnable() {
    public void run() {
      startTime = mediaPlayer.getCurrentPosition();
      tx1.setText(String.format("%d min, %d sec",

              TimeUnit.MILLISECONDS.toMinutes((long) startTime),
              TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                  TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                      toMinutes((long) startTime))
          )
      );
      seekBar.setProgress((int) startTime);
      myHandler.postDelayed(this, 100);
    }
  };

  public void hörenV0(File result) {
    Uri myUri = Uri.fromFile(result);
    h.logi(2, "Pl00", "hören " + myUri.toString());
    h.logi(2, "Pl00", "hören " + result.toString());
    MediaPlayer mMediaPlayer = new MediaPlayer();
    try {
      mMediaPlayer.setDataSource(result.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      mMediaPlayer.prepare();
    } catch (IOException e) {
      e.printStackTrace();
    }
    mMediaPlayer.start();
  }
}


