package com.example.project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.project.DatabaseTables.Quiz.TABLE_NAME;

public class FavoriteFragment extends Fragment {
    RecyclerViewItem [] items;
    ArrayAdapter<RecyclerViewItem> adapter;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    String values ="";

    public FavoriteFragment(RecyclerViewItem [] items) {
        // Required empty public constructor
        this.items = items;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize instance members.
        databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        TextView textView = view.findViewById(R.id.sqlTable);
        Button button = view.findViewById(R.id.sql_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuiz();
            }
        });
        // Inflate the layout for this fragment
        List<RecyclerViewItem> list = getQuiz();
        adapter = new ArrayAdapter<>(getContext(), R.layout.favorite_item_list, R.id.favorite_textView, list);
        ListView listView = view.findViewById(R.id.favorite_listView);
        listView.setAdapter(adapter);
        return view;
    }

    private void deleteTable(long id) {
        database.execSQL(DatabaseTables.SQL_DELETE_TABLE_QUIZ);
    }

    private void addQuiz() {
        ContentValues values = new ContentValues();
        for(int i = 0; i < items.length; i++) {
            values.put(DatabaseTables.Quiz.COLUMN_NAME_NAME, items[i].getName());
            values.put(DatabaseTables.Quiz.COLUMN_NAME_CATEGORY, items[i].getCategory());
            values.put(DatabaseTables.Quiz.COLUMN_NAME_LOCATION, items[i].getLocation());
            database.insert(TABLE_NAME, null, values);
        }
    }

    private List<RecyclerViewItem> getQuiz() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, DatabaseTables.Quiz.COLUMN_NAME_CATEGORY + " ASC");
        List<RecyclerViewItem> quizList = new ArrayList<>();
        while (cursor.moveToNext()) {
            RecyclerViewItem quiz = new RecyclerViewItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Quiz.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Quiz.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Quiz.COLUMN_NAME_CATEGORY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Quiz.COLUMN_NAME_LOCATION))
            );
            quizList.add(quiz);
        }
        cursor.close();
        return quizList;
    }
}