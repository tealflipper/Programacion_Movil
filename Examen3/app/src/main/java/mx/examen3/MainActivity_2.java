package mx.examen_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button downloadFile, saveFile, viewMoviesList, deleteMovies;
    ArrayList <Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    //download file
                    File localFile = new File(ContextCompat.getExternalFilesDirs(this,
                            null)[1],"tmp.json");
                    JSONArray movieJSON = Tools.downloadJSONArray(localFile);

                    Toast.makeText(this, "Archivo descargado",
                            Toast.LENGTH_SHORT).show();
                    //get file to list of movies
                    movieList = Tools.jsonArrayToArrayList(movieJSON);

                }catch (IOException e){
                    Toast.makeText(this, "Error de descarga",
                            Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "Error en formato JSON",
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



}