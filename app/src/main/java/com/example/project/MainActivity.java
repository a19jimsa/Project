package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewItem [] recyclerViewItems;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String values ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=a19jimsa");
        // Initialize instance members.
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(listener);
        TextView textView = findViewById(R.id.sqlTable);
        Button button = findViewById(R.id.sql_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuiz();
            }
        });
        Button readButton = findViewById(R.id.sql_read_button);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values = "";
                List<RecyclerViewItem> items = getQuiz();
                for(int i = 0; i < items.size(); i++){
                    values += items.get(i).toString();
                }
                textView.setText(values);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_menu_items, menu);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            Log.d("TAG", json);
            Gson gson = new Gson();
            //Skicka till vilken fragment som helst! HÄr finns all data sparad och samlad!
            recyclerViewItems = gson.fromJson(json, RecyclerViewItem[].class);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment(recyclerViewItems)).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new HomeFragment(recyclerViewItems);
                    break;
                case R.id.nav_about:
                    fragment = new HomeFragment(recyclerViewItems);
                    break;
                case R.id.nav_favorites:
                    fragment = new FavoriteFragment(recyclerViewItems);
                    break;
                case R.id.nav_quiz:
                    fragment = new HomeFragment(recyclerViewItems);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            return true;
        }
    };

    private int deleteFish(long id) {
        String selection = DatabaseTables.Quiz.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        return database.delete(DatabaseTables.Quiz.TABLE_NAME, selection, selectionArgs);
    }

    private void addQuiz() {
        ContentValues values = new ContentValues();
        for(int i = 0; i < recyclerViewItems.length; i++) {
            values.put(DatabaseTables.Quiz.COLUMN_NAME_NAME, recyclerViewItems[i].getName());
            values.put(DatabaseTables.Quiz.COLUMN_NAME_CATEGORY, recyclerViewItems[i].getCategory());
            values.put(DatabaseTables.Quiz.COLUMN_NAME_LOCATION, recyclerViewItems[i].getLocation());
            database.insert(DatabaseTables.Quiz.TABLE_NAME, null, values);
        }
    }

    private List<RecyclerViewItem> getQuiz() {
        Cursor cursor = database.query(DatabaseTables.Quiz.TABLE_NAME, null, null, null, null, null, DatabaseTables.Quiz.COLUMN_NAME_LOCATION + " DESC");
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