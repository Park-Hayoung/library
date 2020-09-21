package com.example.libraryapp;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.data.Book;
import com.example.libraryapp.data.BookAdapter;
import com.example.libraryapp.data.OnBookItemClickListener;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    BookAdapter adapter;

    TextView tv_count;

    Book curItem;
    int curPosition;

    int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        tv_count = root.findViewById(R.id.tv_count);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookAdapter();

        Cursor cursor = AppHelper.selectBookData();
        count = cursor.getCount();
        tv_count.setText(String.valueOf(count));

        if (count == 0) {
            for (int i = 0; i < 20; i++) {
                AppHelper.insertBookData(new Book(i + 1, "제목 " + (i + 1), "저자", "출판사", 'A'));
            }
        }

        for (int i = 0; i < count; i++) {
            cursor.moveToNext();
            int b_code = cursor.getInt(0);
            String b_title = cursor.getString(1);
            String b_author = cursor.getString(2);
            String b_publisher = cursor.getString(3);
            String b_stat = cursor.getString(4);

            Book book = new Book(b_code, b_title, b_author, b_publisher, b_stat.charAt(0));
            adapter.addItem(book);

            book.print();
        }

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnBookItemClickListener() {
            @Override
            public void onItemClick(int position) {
                curItem = adapter.getItems().get(position);
                curPosition = position;
                if (isBookAvailable()) {
                    showRentDialog();
                } else {
                    Toast.makeText(getContext(), "현재 대여할 수 없는 도서입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void showRentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("도서 대여")
            .setMessage("'" + curItem.getTitle() + "' 도서를 대여하시겠습니까?");

        builder.setPositiveButton("네", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                AppHelper.insertRentData(getContext(), curItem);
                adapter.getItem(curPosition).setStat('B');
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getContext(), "취소.", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private boolean isBookAvailable() {
        return curItem.getStat() == Book.AVAILABLE;
    }
}
