package mx.examen_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button downloadFile, viewMoviesList, updateMovie;
    ProgressBar progressBar;
    TextView textView;
    private LruCache<String, Bitmap> memoryCache;
    ArrayList<Movie> movieList;
    int cont = 0;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    MovieDBAdapter movieDBAdapter;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        setContentView(R.layout.activity_main);
        movieDBAdapter = new MovieDBAdapter(this);
        movie = new Movie();
        initViewComp();
    }

    public void initViewComp() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        downloadFile = (Button) findViewById(R.id.downloadFile);
        downloadFile.setOnClickListener(this);
        viewMoviesList = (Button) findViewById(R.id.viewMoviesList);
        viewMoviesList.setOnClickListener(this);
        updateMovie = (Button) findViewById(R.id.updateMovie);
        updateMovie.setOnClickListener(this);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }

    @Override
    public void onClick(View v){
        Intent goToActivity;
        switch (v.getId()) {

            case R.id.downloadFile:

                new Download().execute(Tools.imagesURL);






                /*    */
                break;
            case R.id.viewMoviesList:
                goToActivity= new Intent(v.getContext(), ViewMovieList.class);
                startActivityForResult(goToActivity,1);
                break;
            case R.id.updateMovie:
                Intent goToActivity2= new Intent(v.getContext(), DeleteMovie.class);
                startActivityForResult(goToActivity2,2);
                break;
            default:
                break;
        }

    }
    private class Download extends AsyncTask <String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setIndeterminate(true);

                }
            });

        }
        @Override
        protected String doInBackground(String... URL) {
            String imageURL = URL[0];
            publishProgress("Empezando descarga");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //download files and create database
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()) {
                //download file
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                File localFile = new File(ContextCompat.getExternalFilesDirs(getApplicationContext(),
                        null)[1],"movies.json");

                //get file to list of movies
                try {
                    JSONArray movieJSON = Tools.downloadJSONArray(localFile);
                    movieList = Tools.jsonArrayToArrayList(movieJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //create database
                for(int i = 0; i < movieList.size(); i++){
                    try {
                        movieDBAdapter.insertMovie(movieList.get(i));
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }


            }else{
                publishProgress("Error de conexion");
            }


            try {

                publishProgress("Descargando imagenes");
                int i=0;
                ArrayList<String> imageFileNames = movieDBAdapter.getMovieImages();
                for(String imageFileName : imageFileNames){
                    String localUrl = imageURL+imageFileName;
                    InputStream input = new java.net.URL(localUrl).openStream();
                    // Decode Bitmap
                    Bitmap bitmap  = BitmapFactory.decodeStream(input);
                    addBitmapToMemoryCache(imageFileName,bitmap);
                    File localFile = new File(ContextCompat.getExternalFilesDirs(getApplicationContext(),
                            null)[1],imageFileName);
                    FileOutputStream out = new FileOutputStream(localFile);
                    boolean compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);// bmp is your Bitmap instance
                    publishProgress("Descargando imagen: "+(++i)+"/"+imageFileNames.size(),imageFileName);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Descarga exitosa";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Bitmap image = null;
            textView.setText(values[0]);
/*
            if(values.length == 2){
                image = getBitmapFromMemCache(values[1]);
                File localFile = new File(ContextCompat.getExternalFilesDirs(getApplicationContext(),
                        null)[1],values[1]);
                try  {
                    FileOutputStream out = new FileOutputStream(localFile);
                    boolean compress = image.compress(Bitmap.CompressFormat.PNG, 100, out);// bmp is your Bitmap instance
// PNG is a lossless format, the compression factor (100) is ignored
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
*/
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.post(new Runnable() {
                @Override
                public void run() {
                    progressBar.setIndeterminate(false);
                }
            });
            textView.setText("");

            // Close progressdialog
            Toast.makeText(getApplicationContext(), "Descarga completa", Toast.LENGTH_SHORT).show();
        }
    }


}

