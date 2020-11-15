package mx.examen_3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterMovie extends ArrayAdapter<MovieSingle> {
    private Context mContext;
    private ArrayList<MovieSingle> moviesList = new ArrayList();
    private static LayoutInflater inflater = null;


    public AdapterMovie(@NonNull Context context, int id , ArrayList<MovieSingle> list) {
        super(context, id , list);
        mContext = context;
        moviesList = list;
    }




    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_number;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.movie_image_list,parent,false);

        MovieSingle currentMovie = moviesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageBitmap(currentMovie.image);

        TextView name = (TextView) listItem.findViewById(R.id.movie);
        name.setText(currentMovie.movie);

        return listItem;
    }
}
