package sg.edu.rp.c346.id22020383.anightatthemovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
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
                showCancelConfirmationDialog();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = etId.getText().toString();
                String title = etTitle.getText().toString();
                String genre = etGenre.getText().toString();
                String yearText = etYear.getText().toString();
                String rating = spinnerRating.getSelectedItem().toString();

                if (TextUtils.isEmpty(idText) || TextUtils.isEmpty(yearText)) {
                    showAlertDialog("Error", "Please enter valid ID and year.");
                    return;
                }

                int id = Integer.parseInt(idText);
                int year = Integer.parseInt(yearText);

                Movie updatedMovie = new Movie(id, title, genre, year, rating);

                dbHelper.updateMovie(updatedMovie);

                showAlertDialog("Success", "Movie updated successfully.");
                setResultAndFinish(RESULT_OK, updatedMovie);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie != null) {
                    showDeleteConfirmationDialog();
                }
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

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }


    private void setResultAndFinish(int resultCode, Movie movie) {
        Intent intent = new Intent();
        intent.putExtra("edited_movie", movie);
        setResult(resultCode, intent);

    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Danger")
                .setMessage("Are you sure you want to delete "+ movie.getTitle() +"?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteMovie(movie.getId());
                        showAlertDialog("Success", "Movie deleted successfully.");
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void showCancelConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Danger")
                .setMessage("Do you want to discard the changes?")
                .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("Do Not Discard", null)
                .create()
                .show();
    }
}