package mx.examen_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class UpdateMovie extends AppCompatActivity {
    private ArrayList<Movie> movieList;
    private EditText title, genre, length, director, year, price, movieId,image;
    Movie updatedMovie;
    MovieDBAdapter movieDBAdapter;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);
        movieDBAdapter = new MovieDBAdapter(this);
        initViewComp();
        lookupMovieId();
        updateMovieId();
        returnToMain();
    }

    public void initViewComp() {
        movieId = (EditText) findViewById(R.id.movieId);
        title = (EditText) findViewById(R.id.title);
        genre = (EditText) findViewById(R.id.genre);
        length = (EditText) findViewById(R.id.length);
        director = (EditText) findViewById(R.id.director);
        length = (EditText) findViewById(R.id.length);
        year = (EditText) findViewById(R.id.year);
        price = (EditText) findViewById(R.id.price);
        image = (EditText) findViewById(R.id.image);
    }

    public void lookupMovieId() {
        Button btn = (Button) findViewById(R.id.search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAttributes();
            }
        });
    }

    public void updateMovieId(){
        Button btn = (Button) findViewById(R.id.updateMovie);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAttributes();
            }
        });
    }

    public void setAttributes(){
        try {
            updatedMovie.setTitle(this.title.getText().toString());
            updatedMovie.setGenre(this.genre.getText().toString());
            updatedMovie.setDirector(this.director.getText().toString());
            double aux = Double.parseDouble(this.length.getText().toString());
            updatedMovie.setLength(aux);
            int year = Integer.parseInt(this.year.getText().toString());
            updatedMovie.setYear(year);
            aux = Double.parseDouble(this.price.getText().toString());
            updatedMovie.setPrice(aux);
            updatedMovie.setImageFile(this.image.getText().toString());

            movieDBAdapter.updateMovie(updatedMovie);
            Toast.makeText(this, "Pelicula actualizada", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getAttributes(){
        id=Integer.parseInt(movieId.getText().toString());
        updatedMovie = movieDBAdapter.searchById(id);

            //show movie atributes
        title.setText(updatedMovie.getTitle().toString(), TextView.BufferType.EDITABLE);
        genre.setText(updatedMovie.getGenres().toString(),TextView.BufferType.EDITABLE);
        length.setText(""+updatedMovie.getLength(), TextView.BufferType.EDITABLE);
        director.setText(updatedMovie.getDirector().toString(), TextView.BufferType.EDITABLE);
        length.setText(""+updatedMovie.getLength(), TextView.BufferType.EDITABLE);
        year.setText(""+updatedMovie.getYear(), TextView.BufferType.EDITABLE);
        price.setText(""+updatedMovie.getPrice(), TextView.BufferType.EDITABLE);
        image.setText(""+updatedMovie.getImageFile(), TextView.BufferType.EDITABLE);


    }
    public void returnToMain() {
        Button btn = (Button) findViewById(R.id.returnMain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ir a visualizar peliculas
                Intent data = new Intent(v.getContext(), MainActivity.class);
                //Exportar objeto a segunda actividad
                data.putExtra("movieList", movieList);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}