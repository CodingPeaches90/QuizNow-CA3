package com.appone.jordan.quiznow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appone.jordan.quiznow.Adapters.QuestionAnswerAdapter;
import com.appone.jordan.quiznow.Models.QuestionAnswerItem;
import com.appone.jordan.quiznow.questionManager.DatabaseHelper;
import com.appone.jordan.quiznow.questionManager.Question;

import java.util.ArrayList;
import java.util.List;

public class AllQuestionViewActivity extends AppCompatActivity {

    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    List<QuestionAnswerItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_question_view);

        // set up
        list = findViewById(R.id.questionAnswerRecycler);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        initDataForView();
    }

    private void initDataForView() {
        DatabaseHelper db=new DatabaseHelper(this);

        List<Question> iDatabase = db.getAllQuestions();

        // set up data
        for (int i = 0; i < 10; i++){
            /* param : question
               param : Answer
            * */
            QuestionAnswerItem item = new QuestionAnswerItem(iDatabase.get(i).getQuestion(),iDatabase.get(i).getAnswer(),true);
            items.add(item);

        }

        QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(items);
        list.setAdapter(adapter);
    }
}
