package com.appone.jordan.quiznow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appone.jordan.quiznow.Adapters.QuestionAnswerAdapter;
import com.appone.jordan.quiznow.Models.QuestionAnswerItem;
import com.appone.jordan.quiznow.Database.DatabaseHelper;
import com.appone.jordan.quiznow.Models.Question;

import java.util.ArrayList;
import java.util.List;

public class AllQuestionViewActivity extends AppCompatActivity {

    /**
     * This Activity displays all the questions and answers
     * to our Quiz. It extends upon our adapters and reads in the questions and answers
     * from SQLite3 database.
     *
     * As with the adapter, code from this tutorial was followed : This code comes from this tutorial : https://www.youtube.com/watch?v=hVJpWSalzbo
     */

    // Set up variables
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

    // Initialise our data

    private void initDataForView() {

        DatabaseHelper db=new DatabaseHelper(this);

        List<Question> iDatabase = db.getAllQuestions();

        // set up data
        for (int i = 0; i < 10; i++){
            /* param : question
               param : Answer
            * */
            /*
                Create our question and answer object and we iterate through the
                number of questions within our table.
             */
            QuestionAnswerItem item = new QuestionAnswerItem(iDatabase.get(i).getQuestion(),iDatabase.get(i).getAnswer(),true);
            items.add(item);

        }

        QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(items);
        list.setAdapter(adapter);
    }
}
