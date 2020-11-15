package mx.examen3;

import android.graphics.Bitmap;

public class Movie extends Video {
    private String director;
    private long id;
    private  String imageFile;
    private Bitmap image;
    public Movie(String title, String genres, int release, double length, double price,String director,long id, String image) {
        super(title, genres, release, length, price);
        this.id=id;
        this.director=director;
        this.imageFile = image;
    }

    public Movie() {

    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String image) {
        this.imageFile = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString(){
        return  " \tID: "        + this.id           + "\n" +
                " \tTITULO: "    + this.getTitle()   + "\n" +
                " \tGENERO: "    + this.getGenres()  + "\n" +
                " \tDURACIÓN: "  + this.getLength()  + "\n" +
                " \tDIRECTOR: "  + this.getDirector()+ "\n" +
                " \tAÑO: "       + this.getYear()    + "\n" +
                " \tPRECIO: "    + this.getPrice()   + "\n" +
                " \tIMAGEN: "    + this.getImageFile()   + "\n" ;
    }
}

