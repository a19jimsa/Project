package com.example.project;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizFragment extends Fragment {
    // Add RecyclerView member
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerViewItem [] item;
    private List<Answer> items;
    private Button button;
    private int nextQuestion;

    public QuizFragment(){

    }

    public QuizFragment(RecyclerViewItem [] item){
        this.item = item;
        this.nextQuestion = 0;
        this.items = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.setAdapter(adapter);
        createQuestion();
        button = view.findViewById(R.id.question_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("Next Question");
                createQuestion();
                nextQuestion(view);
                adapter.notifyDataSetChanged();
            }
        });

        TextView textView = view.findViewById(R.id.title);
        textView.setText("Starta Quizzet!");
        button.setText("Starta");
        return view;
    }

    public void nextQuestion(View view){
        TextView textView = view.findViewById(R.id.title);
        if(nextQuestion < item.length){
            items.clear();
            textView.setText(item[nextQuestion].getAuxdata().getQuestion());
            items.addAll(Arrays.asList(item[nextQuestion].getAuxdata().getAnswer()));
            adapter.notifyDataSetChanged();
            nextQuestion++;
        }else{
            items.clear();
            textView.setText("Ditt resultat blev");
            adapter.notifyDataSetChanged();
            button.setText("Spela igen?");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private void createQuestion(){
        adapter = new RecyclerViewAdapter(getContext(), items, listener);
        recyclerView.setAdapter(adapter);
    }

    private RecyclerViewAdapter.OnClickListener listener = new RecyclerViewAdapter.OnClickListener() {
        @Override
        public void onClick(Answer item, LinearLayout card) {
            if(item.isCorrect()){
                card.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.correct));
            }else {
                card.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.incorrect));
            }
        }
    };
}