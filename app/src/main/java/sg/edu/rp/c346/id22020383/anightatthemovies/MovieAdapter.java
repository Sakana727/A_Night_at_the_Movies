package sg.edu.rp.c346.id22020383.anightatthemovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private ArrayList<Movie> movieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        super(context, 0, movieList);
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
        }

        Movie currentMovie = movieList.get(position);

        TextView tvTitle = listItemView.findViewById(R.id.tvTitle);
        TextView tvGenre = listItemView.findViewById(R.id.tvGenre);
        TextView tvYear = listItemView.findViewById(R.id.tvYear);
        ImageView imgRating = listItemView.findViewById(R.id.imgRating);

        tvTitle.setText(currentMovie.getTitle());
        tvGenre.setText(currentMovie.getGenre());
        tvYear.setText(String.valueOf(currentMovie.getYear()));
        imgRating.setImageResource(getRatingImageResource(currentMovie.getRating()));

        return listItemView;
    }

    private int getRatingImageResource(String rating) {
        int resourceId = R.drawable.rating_g; // Default resource for "G" rating
        switch (rating) {
            case "G":
                resourceId = R.drawable.rating_g;
                break;
            case "PG":
                resourceId = R.drawable.rating_pg;
                break;
            case "PG13":
                resourceId = R.drawable.rating_pg13;
                break;
            case "NC16":
                resourceId = R.drawable.rating_nc16;
                break;
            case "M18":
                resourceId = R.drawable.rating_m18;
                break;
            case "R21":
                resourceId = R.drawable.rating_r21;
                break;
        }
        return resourceId;
    }
}
