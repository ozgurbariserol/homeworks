package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobilodev.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        Book selectedBook = (Book) intent.getSerializableExtra("BookInfo");
        binding.bookName.setText(selectedBook.Bookname);
        binding.authorName.setText(selectedBook.Author);
        binding.bookImage.setImageResource(selectedBook.Image);
        binding.sumText.setText(selectedBook.TextSum);
    }
}