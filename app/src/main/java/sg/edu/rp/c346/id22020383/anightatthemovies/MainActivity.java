package sg.edu.rp.c346.id22020383.anightatthemovies;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etTitle, etGenre, etYear;
    Spinner spinnerRating;
    Button btnInsert, btnShowList;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinnerRating = findViewById(R.id.spinnerRating);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        dbHelper = new DBHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.movie_ratings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(adapter);

        dbHelper = new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearText = etYear.getText().toString();
                String rating = spinnerRating.getSelectedItem().toString();

                // Check if the year field is empty
                if (TextUtils.isEmpty(yearText)) {
                    Toast.makeText(MainActivity.this, "Please enter a valid year", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if the year input contains only digits
                if (!TextUtils.isDigitsOnly(yearText)) {
                    Toast.makeText(MainActivity.this, "Please enter a valid year", Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(yearText);

                long id = dbHelper.insertMovie(title, genre, year, rating);
                if (id != -1) {
                    Toast.makeText(MainActivity.this, "Movie inserted successfully", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to insert movie", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowMovie.class);
                startActivity(intent);
            }
        });
    }

    private void clearInputFields() {
        etTitle.setText("");
        etGenre.setText("");
        etYear.setText("");
        spinnerRating.setSelection(0);
    }

}