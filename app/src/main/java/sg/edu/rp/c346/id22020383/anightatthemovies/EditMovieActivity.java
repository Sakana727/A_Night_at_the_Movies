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

public class EditMovieActivity extends AppCompatActivity {
    EditText etTitle, etGenre, etYear, etId;
    Spinner spinnerRating;
    Button btnUpdate, btnDelete, btnCancel;

    DBHelper dbHelper;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        spinnerRating = findViewById(R.id.spinnerRating);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.movie_ratings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(adapter);

        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DBHelper(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        if (movie != null) {
            etId.setText(String.valueOf(movie.getId()));
            etTitle.setText(movie.getTitle());
            etGenre.setText(movie.getGenre());
            etYear.setText(String.valueOf(movie.getYear()));

            selectRating(movie.getRating());
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the EditText and Spinner
                String idText = etId.getText().toString();
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearText = etYear.getText().toString();
                String rating = spinnerRating.getSelectedItem().toString();

                // Check if the ID and year fields are not empty
                if (TextUtils.isEmpty(idText) || TextUtils.isEmpty(yearText)) {
                    Toast.makeText(EditMovieActivity.this, "Please enter valid ID and year", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idText);
                int year = Integer.parseInt(yearText);

                // Create a new Movie object with the updated values
                Movie updatedMovie = new Movie(id, title, genre, year, rating);

                // Update the movie in the database
                dbHelper.updateMovie(updatedMovie);

                // Update the movie object with the new values
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setYear(year);
                movie.setRating(rating);

                Toast.makeText(EditMovieActivity.this, "Movie updated successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("edited_movie", updatedMovie);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Set onClickListener for "Delete" button to handle movie deletion
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the movie is not null
                if (movie != null) {
                    // Delete the movie from the database
                    dbHelper.deleteMovie(movie.getId());
                    Toast.makeText(EditMovieActivity.this, "Movie deleted successfully", Toast.LENGTH_SHORT).show();
                    // Set the result and finish the activity
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void selectRating(String rating) {
        int position = 0;
        switch (rating) {
            case "G":
                position = 0;
                break;
            case "PG":
                position = 1;
                break;
            case "PG13":
                position = 2;
                break;
            case "NC16":
                position = 3;
                break;
            case "M18":
                position = 4;
                break;
            case "R21":
                position = 5;
                break;
        }
        spinnerRating.setSelection(position);
    }
}