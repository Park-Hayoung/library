package com.example.libraryapp.data;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraryapp.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
                    implements OnBookItemClickListener {
    private ArrayList<Book> items = new ArrayList<>();

    private static OnBookItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.book_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Book item) {
        items.add(item);
    }

    public Book getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Book item) {
        items.set(position, item);
    }

    public ArrayList<Book> getItems() {
        return items;
    }

    public void setOnItemClickListener(OnBookItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(int position) {
        if (listener != null) {
            listener.onItemClick(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author;
        TextView tv_publisher;
        TextView tv_b_code;
        TextView tv_b_stat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author = itemView.findViewById(R.id.tv_author);
            tv_publisher = itemView.findViewById(R.id.tv_publisher);
            tv_b_code = itemView.findViewById(R.id.tv_b_code);
            tv_b_stat = itemView.findViewById(R.id.tv_b_stat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void setItem(Book item) {
            tv_title.setText(item.getTitle());
            tv_author.setText(item.getAuthor());
            tv_publisher.setText(item.getPublisher());
            tv_b_code.setText(String.valueOf(item.getCode()));
            if (item.getStat() == 'A') {
                tv_b_stat.setText("대출가능");
                tv_b_stat.setTextColor(Color.BLUE);
            } else {
                tv_b_stat.setText("대출불가");
                tv_b_stat.setTextColor(Color.RED);
            }
        }
    }
}
