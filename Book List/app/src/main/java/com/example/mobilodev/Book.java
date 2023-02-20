package com.example.mobilodev;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Book implements Serializable {
    public String Bookname;
    public String Author;
    public int Image;
    public String TextSum;

    public Book(String bookName,String author, int image, String textSum){
        this.Author = author;
        this.Bookname = bookName;
        this.Image = image;
        this.TextSum = textSum;
    }
}
