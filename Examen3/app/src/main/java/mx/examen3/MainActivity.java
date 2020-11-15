package mx.examen3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button downloadFile, saveFile, viewMoviesList, deleteMovies;
    ArrayList<Movie> movieList;
    ProgressDialog mProgressDialog;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    MovieDBAdapter movieDBAdapter;
    Movie movie;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Toast.makeText(this, ""+mStorageRef.getPath(), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);
        movieDBAdapter = new MovieDBAdapter(this);
        movie = new Movie();
        initViewComp();
    }

    public void initViewComp() {
        downloadFile = (Button) findViewById(R.id.downloadFile);
        downloadFile.setOnClickListener(this);
        saveFile = (Button) findViewById(R.id.saveFile);
        saveFile.setOnClickListener(this);
        viewMoviesList = (Button) findViewById(R.id.viewMoviesList);
        viewMoviesList.setOnClickListener(this);
        deleteMovies = (Button) findViewById(R.id.deleteMovie);
        deleteMovies.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent goToActivity;
        switch (v.getId()) {
            case R.id.downloadFile:
                try {
                    //TODO create AsyncTask to download and create database
                    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected()) {
                        Toast.makeText(getApplicationContext(), "Conexion ok", Toast.LENGTH_SHORT).show();
                        //download file
                        Toast.makeText(this, "Descargando Archivo ",
                                Toast.LENGTH_SHORT).show();
                        JSONArray movieJSON = Tools.downloadJSONArray();
                        Toast.makeText(this, "Archivo descargado",
                                Toast.LENGTH_SHORT).show();
                        //get file to list of movies
                        movieList = Tools.jsonArrayToArrayList(movieJSON);
                        //create database
                        for(int i =0; i < movieList.size(); i++){
                            movieDBAdapter.insertMovie(movieList.get(i));
                        }
                        Toast.makeText(getApplicationContext(), "Base de datos creada", Toast.LENGTH_SHORT).show();
                        //TODO download images
                    }else{
                        Toast.makeText(getApplicationContext(), "Error en conexion", Toast.LENGTH_SHORT).show();
                    }

                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(), "Error en formato JSON" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.saveFile:
                    try {
                        File localFile = new File(ContextCompat.getExternalFilesDirs(this,
                                null)[1],"movies.json");
                        Tools.saveJSONFile(localFile, movieList);
                        Toast.makeText(getApplicationContext(), "Archivo guardado",
                                Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        Toast.makeText(getApplicationContext(), "Error guardando el archivo",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.viewMoviesList:
                    goToActivity= new Intent(v.getContext(), ViewMovieList.class);
                    startActivityForResult(goToActivity,1);
                    break;
                case R.id.deleteMovie:
                    Intent goToActivity2= new Intent(v.getContext(), DeleteMovie.class);
                    startActivityForResult(goToActivity2,2);
                    break;
                default:
                    break;
            }

    }
    private class DownloadImage extends AsyncTask <String, Bitmap, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Download Image Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (FileOutputStream out = new FileOutputStream("AABB001.jpg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }
}

