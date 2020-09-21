package com.example.libraryapp.data;

import android.util.Log;

public class Book {
    private int code;
    private String title;
    private String author;
    private String publisher;
    private char stat;

    public static final char AVAILABLE = 'A';
    public static final char UNAVAILABLE = 'B';

    public Book(int code, String title, String author, String publisher, char stat) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.stat = stat;
    }

    public void print() {
        Log.d("Book", getCode() + ", " + getTitle() + ", "
                + getAuthor() + ", " + getPublisher() + ", " + getStat());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public char getStat() {
        return stat;
    }

    public void setStat(char stat) {
        this.stat = stat;
    }

    public static char getAVAILABLE() {
        return AVAILABLE;
    }

    public static char getUNAVAILABLE() {
        return UNAVAILABLE;
    }
}
