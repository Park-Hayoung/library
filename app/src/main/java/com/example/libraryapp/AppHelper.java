package com.example.libraryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.libraryapp.data.Book;
import com.example.libraryapp.util.MyDate;

public class AppHelper {
    public static SQLiteDatabase database;
    public static final String TAG = "AppHelper";

    public static void openDatabase(Context context, String databaseName) {
        Log.d(TAG, "openDatabase 호출");

        if (databaseName != null) {
            database = context.openOrCreateDatabase(
                    databaseName, Context.MODE_PRIVATE, null);
            Log.d(TAG, databaseName + " 데이터베이스를 오픈했습니다.");
        }
    }

    public static void createBookTable() {
        Log.d(TAG, "createTable 호출 : book");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요.");
            return;
        }
        String sql = "CREATE TABLE IF NOT EXISTS book (" +
                        "b_code INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "b_title VARCHAR(100) NOT NULL, " +
                        "b_author VARCHAR(30) NOT NULL, " +
                        "b_publisher VARCHAR(50) NOT NULL, " +
                        "b_stat CHAR(1) NOT NULL )";

        database.execSQL(sql);
        Log.d(TAG, "book 테이블 생성 완료.");
    }

    public static void createMemberTable() {
        Log.d(TAG, "createTable 호출 : member");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요.");
            return;
        }
        String sql = "CREATE TABLE IF NOT EXISTS member (" +
                "m_id VARHCAR(20) NOT NULL PRIMARY KEY, " +
                "m_name VARCHAR(20) NOT NULL, " +
                "m_passwd VARCHAR(30) NOT NULL, " +
                "m_date VARCHAR(20) NOT NULL, " +
                "m_cnt INTEGER NOT NULL )";

        database.execSQL(sql);
        Log.d(TAG, "member 테이블 생성 완료.");
    }

    public static void createRentTable() {
        Log.d(TAG, "createTable 호출 : rent");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요.");
            return;
        }
        String sql = "CREATE TABLE IF NOT EXISTS rent (" +
                "r_code INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "m_id VARCHAR(30) NOT NULL, " +
                "b_code INTEGER NOT NULL, " +
                "r_date VARCHAR(20) NOT NULL, " +
                "r_stat CHAR(1) NOT NULL, " +
                "CONSTRAINT fk_m_id FOREIGN KEY(m_id) REFERENCES member(m_id), " +
                "CONSTRAINT fk_b_code FOREIGN KEY(b_code) REFERENCES book(b_code))";

        database.execSQL(sql);
        Log.d(TAG, "rent 테이블 생성 완료.");
    }

    public static void createRentEndTable() {
        Log.d(TAG, "createTable 호출 : rent_end");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요.");
            return;
        }
        String sql = "CREATE TABLE IF NOT EXISTS rent_end (" +
                "re_code INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "m_id VARCHAR(20) NOT NULL, " +
                "b_code INTEGER NOT NULL, " +
                "re_date VARCHAR(20) NOT NULL, " +
                "re_stat CHAR(1) NOT NULL, " +
                "CONSTRAINT fk_m_id FOREIGN KEY(m_id) REFERENCES member(m_id), " +
                "CONSTRAINT fk_b_code FOREIGN KEY(b_code) REFERENCES book(b_code))";

        database.execSQL(sql);
        Log.d(TAG, "rent_end 테이블 생성 완료.");
    }

    public static void insertBookData(Book book) {
        Log.d(TAG, "insertBookData 호출");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요.");
            return;
        }

        String sql = "INSERT INTO book(b_title, b_author, b_publisher, b_stat) VALUES(?, ?, ?, ?)";
        Object[] params = { book.getTitle(), book.getAuthor(), book.getPublisher(), book.getStat() };
        database.execSQL(sql, params);
        Log.d(TAG, "book 데이터 삽입 완료");
    }

    public static Cursor selectBookData() {
        Log.d(TAG, "selectBookData 호출");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요");
            return null;
        }

        String sql = "SELECT * FROM book";
        return database.rawQuery(sql, null);
    }

    public static void insertRentData(Context context, Book book) {
        Log.d(TAG, "insertRentData 호출");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요.");
            return;
        }

        // 아이디 불러오기
        SharedPreferences pref
                = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String m_id = pref.getString("m_id", null);

        // 로그인 상태라면 대여 프로세스 진행
        if (m_id != null) {
            int b_code = book.getCode();
            String r_date = MyDate.getDate();
            String sql = "INSERT INTO rent(m_id, b_code, r_date) VALUES(?, ?, ?)";
            Object[] params = { m_id, b_code, r_date };
            database.execSQL(sql, params);
            updateBookStat(b_code, 'B');
            String message = "'" + book.getTitle() + "' 도서를 대여하였습니다.";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            Log.d(TAG, message);
        } else {
            Toast.makeText(context, "사용자 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void updateBookStat(int b_code, char b_stat) {
        Log.d(TAG, "updateBookStat 호출");

        if (database == null) {
            Log.d(TAG, "데이터베이스를 먼저 오픈하세요.");
            return;
        }

        String sql = "UPDATE book SET b_stat = ? " +
                     "WHERE b_code = ?";
        Object[] params = { b_stat, b_code };
        database.execSQL(sql, params);

        if (b_stat == Book.AVAILABLE) {
            Log.d(TAG, "도서번호 " + b_code + "의 상태가 '대여가능' 상태로 변경되었습니다.");
        } else if (b_stat == Book.UNAVAILABLE) {
            Log.d(TAG, "도서번호 " + b_code + "의 상태가 '대여불가' 상태로 변경되었습니다.");
        }
    }
}
