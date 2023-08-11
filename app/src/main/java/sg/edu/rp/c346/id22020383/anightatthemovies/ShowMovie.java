package sg.edu.rp.c346.id22020383.anightatthemovies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowMovie extends AppCompatActivity {
    private static final int EDIT_MOVIE_REQUEST_CODE = 100;
    ListView listViewMovies;
    Spinner spinnerMovieRating;
    Button btnShowMoviesWithRating;
    DBHelper dbHelper;
    MovieAdapter movieAdapter;
    ArrayList<Movie> allMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie);

        listViewMovies = findViewById(R.id.listViewMovies);
        spinnerMovieRating = findViewById(R.id.spinnerMovieRating);
        btnShowMoviesWithRating = findViewById(R.id.btnShowMoviesWithRating);

        dbHelper = new DBHelper(this);

        allMovies = dbHelper.getAllMovies();

        movieAdapter = new MovieAdapter(this, allMovies);
        listViewMovies.setAdapter(movieAdapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.movie_ratings, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovieRating.setAdapter(spinnerAdapter);

        btnShowMoviesWithRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRating = spinnerMovieRating.getSelectedItem().toString();
                ArrayList<Movie> moviesWithRating = dbHelper.getMoviesByRating(selectedRating);
                movieAdapter = new MovieAdapter(ShowMovie.this, moviesWithRating);
                listViewMovies.setAdapter(movieAdapter);
            }
        });

        spinnerMovieRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRating = parent.getItemAtPosition(position).toString();
                filterMoviesByRating(selectedRating);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = allMovies.get(position);
                Intent intent = new Intent(ShowMovie.this, EditMovieActivity.class);
                intent.putExtra("movie", selectedMovie);
                startActivityForResult(intent, EDIT_MOVIE_REQUEST_CODE);
            }
        });
    }

    private void filterMoviesByRating(String rating) {
        ArrayList<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : allMovies) {
            if (movie.getRating().equals(rating)) {
                filteredMovies.add(movie);
            }
        }
        movieAdapter = new MovieAdapter(this, filteredMovies);
        listViewMovies.setAdapter(movieAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_MOVIE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh the list after returning from EditMovieActivity
            allMovies = dbHelper.getAllMovies();
            movieAdapter = new MovieAdapter(this, allMovies);
            listViewMovies.setAdapter(movieAdapter);
        }
    }

}