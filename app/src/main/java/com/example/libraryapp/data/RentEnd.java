package com.example.libraryapp.data;

public class RentEnd {
    private int code;
    private int rm_code;
    private String date;

    public RentEnd(int code, int rm_code, String date) {
        this.code = code;
        this.rm_code = rm_code;
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public int getRm_code() {
        return rm_code;
    }

    public void setRm_code(int rm_code) {
        this.rm_code = rm_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
