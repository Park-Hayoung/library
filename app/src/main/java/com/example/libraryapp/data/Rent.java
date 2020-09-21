package com.example.libraryapp.data;

public class Rent {
    private int code;
    private String m_id;
    private int b_code;
    private String date;

    public Rent(int code, String m_id, int b_code, String date) {
        this.code = code;
        this.m_id = m_id;
        this.b_code = b_code;
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public int getB_code() {
        return b_code;
    }

    public void setB_code(int b_code) {
        this.b_code = b_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}