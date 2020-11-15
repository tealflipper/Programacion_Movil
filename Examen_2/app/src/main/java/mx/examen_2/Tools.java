package mx.examen_2;

import android.util.JsonReader;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class Tools {

    public static String imagesURL = "https://librojson.000webhostapp.com/imagenes/";
    static String jsonString;
    public Tools(){
    }



    /*
    * Converts JSON array to Arraylist <Movie>
    * */
    public static ArrayList<Movie> jsonArrayToArrayList(JSONArray jsonArray) throws JSONException {
        ArrayList<Movie> movieList = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject curObj= jsonArray.getJSONObject(i);
            Movie movie = new Movie();

            movie.setId(curObj.getInt("id"));
            movie.setTitle(curObj.getString("title"));
            movie.setGenre(curObj.getString("genre"));
            movie.setLength(curObj.getDouble("length"));
            movie.setDirector(curObj.getString("director"));
            movie.setYear(curObj.getInt("year"));
            movie.setPrice(curObj.getDouble("price"));

            movieList.add(movie);
        }
        return movieList;
    }


    /*
    * Converts ArrayList to String for printing out
    * */
    public static ArrayList<String> ArrayListToStringArray(ArrayList<Movie> movieArrayList){
        ArrayList moviesStringList= new ArrayList<String>();
        int i = 1;
        for (Movie movie : movieArrayList){
            String moviesString = "" + i + movie.toString();
            i++;
            moviesStringList.add(moviesString);
        }
        return moviesStringList;
    }

    /*
    * Writes contents of ArrayList to file in JSON format
    * */
    public static void saveJSONFile(File movieFile, ArrayList<Movie> movieList) throws IOException {
        FileWriter fileWriter = new FileWriter(movieFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("[ ");
        for(Movie m:movieList){
            JSONObject jsonMovie = new JSONObject();
            try {
                jsonMovie.put("id", m.getId());
                jsonMovie.put("title", m.getTitle().toString());
                jsonMovie.put("genre", m.getGenres().toString());
                jsonMovie.put("length", m.getLength());
                jsonMovie.put("director", m.getDirector().toString());
                jsonMovie.put("year", m.getYear());
                jsonMovie.put("price", m.getPrice());
            } catch (JSONException e){
                e.printStackTrace();
            }
            String stringMovie = jsonMovie.toString();
            bufferedWriter.write(stringMovie);
            if (m.getId()!= movieList.get(movieList.size()-1).getId()){
                bufferedWriter.write(",");
            }

        }
        bufferedWriter.write("]");
        bufferedWriter.close();

    }

    /*
    * Reads JSON format from local file
    * */
    public static JSONArray readJSONFile(File movieFile) throws IOException, JSONException {

        FileReader fileReader = new FileReader(movieFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while(line != null){
            stringBuilder.append(line).append("\n");
            line =bufferedReader.readLine();
        }
        bufferedReader.close();
        String stringMovies = stringBuilder.toString();
        JSONArray jsonMovies = new JSONArray(stringMovies);
        return jsonMovies;
    }

    /*
     * Downloads file from url
     * */
    public static JSONArray downloadJSONArray(File localFile) throws IOException, JSONException{

        // Get a non-default Storage bucket
        //singleton
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://tepitoflix.appspot.com");

        //create reference to file
        StorageReference storageRef = storage.getReference();
        StorageReference jsonFileRef = storageRef.child("moviesjson.json");

        jsonFileRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        JSONArray jsonArray = readJSONFile(localFile);
        return jsonArray;
    }

    private static void bytesToJSON(byte[] bytes){

    }
}