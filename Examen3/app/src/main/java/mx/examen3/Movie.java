package mx.examen_2;

import java.util.ArrayList;
import java.util.Date;

public class Movie extends Video {
    private String director;
    private int id;
    public Movie(String title, String genres, int release, double length, double price,String director,int id) {
        super(title, genres, release, length, price);
        this.director=director;
    }

    public Movie() {

    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return  "\tID: "        + this.id           + "\n" +
                "\tTITULO: "    + this.getTitle()   + "\n" +
                "\tGENERO: "    + this.getGenres()  + "\n" +
                "\tDURACIÓN: "  + this.getLength()  + "\n" +
                "\tDIRECTOR: "  + this.getDirector()+ "\n" +
                "\tAÑO: "       + this.getYear()    + "\n" +
                "\tPRECIO: "    + this.getPrice()   + "\n" ;
    }
}

