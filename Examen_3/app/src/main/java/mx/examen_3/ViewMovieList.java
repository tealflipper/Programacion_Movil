package mx.examen_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ViewMovieList extends AppCompatActivity {
    MovieDBAdapter movieDBAdapter;
    private LruCache<Integer, Bitmap> memoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie_list);
        movieDBAdapter = new MovieDBAdapter(this);
        try {
            printMovieList();
        }catch (JSONException e){
            Toast.makeText(getApplicationContext(), "Error en formato JSON"+e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error abriendo archivo"+e.toString(), Toast.LENGTH_LONG).show();
        }

        returnToMain();

    }





    public void printMovieList() throws JSONException,IOException{
        /*
        File localFile = new File(ContextCompat.getExternalFilesDirs(this,
                null)[1],"movies.json");
        JSONArray jsonMovies = Tools.readJSONFile(localFile);
        ArrayList<Movie> movieList = Tools.jsonArrayToArrayList(jsonMovies);
        ArrayList movies = Tools.ArrayListToStringArray(movieList);
*/
        ArrayList<Movie> movieList = movieDBAdapter.getMovies();
        ArrayList<MovieSingle> movies = new ArrayList<MovieSingle>();
        ArrayList<String> movieStrings = Tools.ArrayListToStringArray(movieList);

        for ( int i = 0; i < movieList.size(); i++) {
            MovieSingle movie=new MovieSingle();
            movie.movie = movieStrings.get(i);

            File image = new File(ContextCompat.getExternalFilesDirs(getApplicationContext(),
                    null)[1], movieList.get(i).getImageFile());

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            movie.image = (Bitmap.createScaledBitmap(bitmap,200,150,true));
            movies.add(movie);


        }
/*
         */
        AdapterMovie adapterMovie = new AdapterMovie(this,
                R.layout.movie_list, movies);
        ListView listViewMovies = (ListView) findViewById(R.id.listview);
        listViewMovies.setAdapter(adapterMovie);

    }


    public void returnToMain(){
        Button btn = (Button) findViewById(R.id.returnMain);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Ir a visualizar peliculas
                    Intent data = new Intent(v.getContext(), MainActivity.class);
                    setResult(RESULT_OK, data);
                    finish();
                }catch (Exception e){
                    Toast.makeText(v.getContext(), "eeeeee", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}


