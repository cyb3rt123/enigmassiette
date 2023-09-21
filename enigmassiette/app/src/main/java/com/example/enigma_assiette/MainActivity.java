package com.example.enigma_assiette;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.enigma_assiette.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;


import android.view.Menu;
import android.view.MenuItem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.ContentValues;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNom, editTextCritique;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private RatingBar ratingDecoration, ratingNourriture, ratingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des composants
        editTextNom = findViewById(R.id.editTextNom);
        editTextCritique = findViewById(R.id.editTextCritique);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        ratingDecoration = findViewById(R.id.ratingDecoration);
        ratingNourriture = findViewById(R.id.ratingNourriture);
        ratingService = findViewById(R.id.ratingService);

        Button buttonEnregistrer = findViewById(R.id.buttonEnregistrer);

        buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = editTextNom.getText().toString();
                String date = String.format("%04d-%02d-%02d",
                        datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                String heure = String.format("%02d:%02d",
                        timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                float noteDecoration = ratingDecoration.getRating();
                float noteNourriture = ratingNourriture.getRating();
                float noteService = ratingService.getRating();
                String critique = editTextCritique.getText().toString();

                FirebaseApp.initializeApp(getApplicationContext());

                DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_NOM, nom);
                values.put(DatabaseHelper.COLUMN_DATE_HEURE_REPAS, date + " " + heure);
                values.put(DatabaseHelper.COLUMN_NOTE_DECORATION, noteDecoration);
                values.put(DatabaseHelper.COLUMN_NOTE_NOURRITURE, noteNourriture);
                values.put(DatabaseHelper.COLUMN_NOTE_SERVICE, noteService);
                values.put(DatabaseHelper.COLUMN_CRITIQUE, critique);

                db.insert(DatabaseHelper.TABLE_NAME, null, values);
                db.close();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Critique de restaurant");
                intent.putExtra(Intent.EXTRA_TEXT, "Nom du restaurant : " + nom +
                        "\nDate et heure du repas : " + date + " " + heure +
                        "\nNote décoration : " + noteDecoration +
                        "\nNote nourriture : " + noteNourriture +
                        "\nNote service : " + noteService +
                        "\nCritique : " + critique);

                startActivity(Intent.createChooser(intent, "Envoyer par email"));

                Toast.makeText(MainActivity.this, "Enregistrement réussi", Toast.LENGTH_SHORT).show();
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        AppBarConfiguration appBarConfiguration = null;
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}