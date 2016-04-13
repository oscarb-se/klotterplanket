package se.oscarb.klotterplanket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /*
        Hur använder man Firebase med en Android app?
        1. Lägg till Firebase i build.gradle
            compile 'com.firebase:firebase-client-android:2.5.2'
}
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
