package se.oscarb.klotterplanket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    /*
        Hur använder man Firebase med en Android app?
        0. Lägg till tillåtelse att använda internet i AndroidManifest.xml
            <uses-permission android:name="android.permission.INTERNET" />
        1. Lägg till Firebase i build.gradle
            compile 'com.firebase:firebase-client-android:2.5.2'
        2. Säg till Firebase vad som är vår context (se onCreate)
        3. Skapa en referens till _vår_ Firebase-databas
            3.1 Deklarera variabeln firebaseReference
            3.2 Instansisera variabeln (se onCreate)
        4. Skicka data till en Firebase-databas (se sendMessage)
        5. Läsa in data från en Firebase-databas (se onCreate)
     */


    // Instansvariabler
    private EditText editMessage;
    private TextView displayMessage;
    private TextView displayMessage2;
    private TextView displayMessage3;

    private Firebase firebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hämta Views
        editMessage = (EditText) findViewById(R.id.edit_message);
        displayMessage = (TextView) findViewById(R.id.display_message);
        displayMessage2 = (TextView) findViewById(R.id.display_message_2);
        displayMessage3 = (TextView) findViewById(R.id.display_message_3);

        // VIlken context ska Firebase ha?
        Firebase.setAndroidContext(this);

        // Skapa en referens
        firebaseReference = new Firebase("https://klotterplanket.firebaseio.com");

        // Hämta data
        firebaseReference.child("message").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Firebase", "Något barn i databasen har ändrats på!");
                displayMessage.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Hämta data från "messages"
        firebaseReference.child("messages").addValueEventListener(new ValueEventListener() {
            // Varje gång något ändras på i "messages"
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 1;
                int nrOfMessages = (int) dataSnapshot.getChildrenCount();

//                Log.i("TEST", )
                // Hämta varje barn i "messages", d.v.s varje meddelande
                for(DataSnapshot theSnapshot : dataSnapshot.getChildren()){

                    String msg = theSnapshot.getValue().toString();

                    if(i == nrOfMessages ) {
                        displayMessage.setText(msg);
                    } else if (i == nrOfMessages - 1) {
                        displayMessage2.setText(msg);
                    } else if (i == nrOfMessages - 2) {
                        displayMessage3.setText(msg);
                    }

                    i++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    // Körs när  man klickar på knappen "Klottra"
    public void sendMessage(View view) {
        // Vad ska vi skicka?
        String message = editMessage.getText().toString();

        // Skicka texten!
        firebaseReference.child("messages").push().setValue(message);

    }
}
