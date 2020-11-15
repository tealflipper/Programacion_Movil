package mx.examen3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.StrictMode;
import android.util.JsonReader;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class Tools {
    private static String jsonFile = "https://tepitoflix.000webhostapp.com/movieJson3.json";
    public static String imagesURL = "https://librojson.000webhostapp.com/imagenes/";
    private static String jsonString;
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

            movie.setId(curObj.getLong("id"));
            movie.setTitle(curObj.getString("title"));
            movie.setGenre(curObj.getString("genre"));
            movie.setLength(curObj.getDouble("length"));
            movie.setDirector(curObj.getString("director"));
            movie.setYear(curObj.getInt("year"));
            movie.setPrice(curObj.getDouble("price"));
            movie.setImageFile(curObj.getString("image"));


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
            moviesStringList.add(""+(i++)+movie.toString());
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
                jsonMovie.put("image", m.getImageFile());
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

    private static JSONArray readJSONFile(InputStream inputStream) throws IOException, JSONException {
        //creating an InputStreamReader object
        InputStreamReader isReader = new InputStreamReader(inputStream);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        System.out.println(sb.toString());
        String stringMovies = sb.toString();
        JSONArray jsonMovies = new JSONArray(stringMovies);
        return jsonMovies;
    }


    public static JSONArray downloadJSONArray(){
        HttpURLConnection conn = null;
        JSONArray jsonArray = new JSONArray();

        try{
            URL url = new URL(jsonFile);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                jsonArray = readJSONFile(conn.getInputStream());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                conn.disconnect();
            }
        }
        return jsonArray;
    }

}

