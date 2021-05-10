package com.example.project;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.project.DatabaseTables.Quiz.COLUMN_NAME_CATEGORY;
import static com.example.project.DatabaseTables.Quiz.TABLE_NAME;

public class FavoriteFragment extends Fragment {
    private List<RecyclerViewItem> list;
    private RecyclerViewItem [] items;
    private ArrayAdapter<RecyclerViewItem> adapter;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String selection;
    private String [] selectionArgs;
    private ListView listView;
    private SharedPreferences myPreferenceRef;
    private SharedPreferences.Editor myPreferenceEditor;

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
        myPreferenceRef = this.getActivity().getPreferences(MODE_PRIVATE);
        myPreferenceEditor = myPreferenceRef.edit();
        selection = COLUMN_NAME_CATEGORY + "= ?";
        selectionArgs = new String[]{myPreferenceRef.getString("category", "Novalue")};
        addQuiz();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        MaterialToolbar toolbar = view.findViewById(R.id.favorite_toolbar);
        toolbar.setOnMenuItemClickListener(menuListener);
        listView = view.findViewById(R.id.favorite_listView);
        listView.setOnItemClickListener(listener);
        updateAdapter();
        return view;
    }

    private void deleteTable() {
        database.execSQL(DatabaseTables.SQL_DELETE_TABLE_QUIZ);
    }

    private void createTable(){
        database.execSQL(DatabaseTables.SQL_CREATE_TABLE_QUIZ);
    }

    private void addQuiz() {
        deleteTable();
        createTable();
        ContentValues values = new ContentValues();
        for (RecyclerViewItem item : items) {
            values.put(DatabaseTables.Quiz.COLUMN_NAME_NAME, item.getName());
            values.put(DatabaseTables.Quiz.COLUMN_NAME_CATEGORY, item.getCategory());
            values.put(DatabaseTables.Quiz.COLUMN_NAME_LOCATION, item.getLocation());
            database.insert(TABLE_NAME, null, values);
        }
    }

    private List<RecyclerViewItem> getQuiz(String selection, String [] selectionArgs) {
        Cursor cursor = database.query(TABLE_NAME, null, selection, selectionArgs, null, null, COLUMN_NAME_CATEGORY + " DESC");
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

    private final AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.fragmentContainer, new QuizFragment(Integer.parseInt(list.get(position).getId())-1, items)).commit();
            Log.d("TAG", list.get(position).toString());
        }
    };

    private final Toolbar.OnMenuItemClickListener menuListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.category_item:
                        selectionArgs = new String[]{"Komedi"};
                        savePref();
                        updateAdapter();
                        break;
                    case R.id.location_item:
                        selectionArgs = new String[]{"Språk"};
                        savePref();
                        updateAdapter();
                        break;
                    case R.id.name_item:
                        selectionArgs = new String[]{"Historia"};
                        savePref();
                        updateAdapter();
                        break;
                }
                return true;
        }
    };

    private void updateAdapter(){
        list = getQuiz(selection, selectionArgs);
        //In order to update the listview.
        adapter = new ArrayAdapter<>(getContext(), R.layout.favorite_item_list, R.id.favorite_textView, list);
        listView.setAdapter(adapter);
    }

    private void savePref(){
        // Store the new preference
        myPreferenceEditor.putString("category", selectionArgs[0]);
        myPreferenceEditor.apply();
    }
}