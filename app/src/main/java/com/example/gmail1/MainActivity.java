package com.example.gmail1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gmail3.R;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

public class MainActivity extends AppCompatActivity {
    List<ContactModel> items;
    List<ContactModel> listFavorite;
    List<ContactModel> listSearch;
    Button btnFavorite;
    EditText textSearch;
    boolean clickFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++)
            items.add(new ContactModel(faker.name.name(), faker.lorem.sentence(), faker.lorem.paragraph(), "12:00"));
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);//
        recyclerView.setLayoutManager(layoutManager);

        final GmailAdapter adapter = new GmailAdapter(items);
        recyclerView.setAdapter(adapter);


        btnFavorite = findViewById(R.id.btn_favorite);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listFavorite = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    if (items.get(i).isFavorite) {
                        listFavorite.add(items.get(i));
                    }
                }
                GmailAdapter favoriteAdapter = new GmailAdapter(listFavorite);
                if (!clickFavorite) {
                    recyclerView.setAdapter(favoriteAdapter);
                } else recyclerView.setAdapter(adapter);
                clickFavorite = !clickFavorite;
            }
        });

        textSearch = findViewById(R.id.edt_search);

        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String charString = s.toString();
                if (count > 2) {
                    listSearch = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).getName().toLowerCase().indexOf(charString) != -1 || items.get(i).getSubject().toLowerCase().indexOf(charString) != -1 || items.get(i).getContent().toLowerCase().indexOf(charString) != -1)
                            listSearch.add(items.get(i));
                    }
                    GmailAdapter searchAdapter = new GmailAdapter(listSearch);
                    recyclerView.setAdapter(searchAdapter);
                } else {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}


