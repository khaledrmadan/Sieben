package de.baumann.sieben;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity4 extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean timerStarted = true;
    public TextView textView;
    private final long startTime = 30 * 1000;
    private final long interval = 1 * 100;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        setSupportActionBar(toolbar);
        setTitle(R.string.act_4);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!timerStarted) {
                    countDownTimer.start();
                    timerStarted = true;
                    fab.setImageResource(R.drawable.pause);
                    Snackbar.make(view, R.string.sn_weiter, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    countDownTimer.cancel();
                    timerStarted = false;
                    fab.setImageResource(R.drawable.play);
                    Snackbar.make(view, R.string.sn_pause, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(MainActivity4.this) {
            public void onSwipeTop() {
                countDownTimer.start();
                timerStarted = true;
                fab.setImageResource(R.drawable.pause);
                Snackbar.make(imageView, R.string.sn_weiter, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            public void onSwipeRight() {
                Intent intent_in = new Intent(de.baumann.sieben.MainActivity4.this, Pause2.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                countDownTimer.cancel();
                finish();
            }

            public void onSwipeLeft() {
                Intent intent_in = new Intent(de.baumann.sieben.MainActivity4.this, Pause4.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                countDownTimer.cancel();
                finish();
            }

            public void onSwipeBottom() {
                countDownTimer.cancel();
                timerStarted = false;
                fab.setImageResource(R.drawable.play);
                Snackbar.make(imageView, R.string.sn_pause, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new CountDownTimerActivity(startTime, interval);
        textView.setText(textView.getText() + String.valueOf(startTime / 1000));
        progressBar.setRotation(180);
        fab.setImageResource(R.drawable.pause);
        imageView.setImageResource(R.drawable.a04);
        countDownTimer.start();
        progressBar.setProgress(100);
    }


    public class CountDownTimerActivity extends CountDownTimer {

        public CountDownTimerActivity(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(0);
            Intent intent_in = new Intent(MainActivity4.this, Pause4.class);
            startActivity(intent_in);
            overridePendingTransition(0, 0);
            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText("" + millisUntilFinished/1000);
            int progress = (int) (millisUntilFinished/300);
            progressBar.setProgress(progress);
        }
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
        if (id == R.id.action_license) {
            final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.about_text)));
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(MainActivity4.this)
                    .setTitle(R.string.about_title)
                    .setMessage( s )
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
            d.show();
            ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (id == R.id.action_changelog) {
            final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.changelog_text)));
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(MainActivity4.this)
                    .setTitle(R.string.action_changelog)
                    .setMessage( s )
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
            d.show();
            ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (id == R.id.action_help) {
            final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.help_text)));
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(MainActivity4.this)
                    .setMessage( s )
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
            d.show();
            ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
    }
}