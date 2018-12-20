package com.appone.jordan.quiznow.questionManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String dbName = "QuizNow";
    private SQLiteDatabase myDatabase;
    private static final int dVersion = 1;

    /*TABLE NAMES*/
    static final String tbName = "AiQuestionTable";
    static final String questionID = "questionID";
    static final String questonText = "questonText";
    static final String questionAnswer = "questionAnswer";
    static final String questionAnswer1 = "questionAnswer1";
    static final String questionnAnswer2 = "questionnAnswer2";
    static final String questionAnswer3 = "questionAnswer3";


    public DatabaseHelper(Context c)
    {
        super(c, dbName, null, dVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        myDatabase = db;

        String sql = "CREATE TABLE IF NOT EXISTS " + tbName + " ( "
                + questionID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + questonText
                + " TEXT, " + questionAnswer+ " TEXT, "+questionAnswer1 +" TEXT, "
                +questionnAnswer2 +" TEXT, "+questionAnswer3+" TEXT)";
        db.execSQL(sql);
        makeQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tbName);
        onCreate(db);
    }

    /* Construct our table now !*/
    public void makeQuestions()
    {
        /*
            Question(int qId, String question, String optionA, String optionB, String optionC, String answer)
        * */
        Question q1 = new Question("What does AI stand for?", "Artificial Intelligence", "Absolute Integrity", "Any Idea", "artificial intelligence");
        this.addQ(q1);
        Question q2 = new Question("What does TSP stand for?", "Travelling Sales Man Problem", "Ive no idea", "Observes through sensors and acts upon them", "Travelling Sales Man Problem");
        this.addQ(q2);
        Question q3 = new Question("What year did Deep Blue win a Human?", "1920", "1997", "Any Idea", "1997");
        this.addQ(q3);
        Question q4 = new Question("Is depth first a searching algorithm?", "Yes", "No", "Answer", "Yes");
        this.addQ(q4);
        Question q5 = new Question("Is A* an algorithm?", "Yes", "No", "Answer", "Yes");
        this.addQ(q5);
        Question q6 = new Question("What does KR stand for? ", "Knowledge Representation", "No", "Answer", "Knowledge Representation");
        this.addQ(q6);
        Question q7 = new Question("Before the center of thought was discovered to be in the brain, where was it thought to be?", "Heart", "No", "Answer", "Heart");
        this.addQ(q7);
        Question q8 = new Question("What test assesses a machines ability to exhibit intelligent behaviour equilivant to that of a human? ", "Turing Test", "No", "Answer", "Turing Test");
        this.addQ(q8);
        Question q9 = new Question("An intelligent agent has actuators and sensors, true or false?", "True", "False", "Answer", "True");
        this.addQ(q9);
        Question q10 = new Question("What measures the complexity of algorithms? ", "Big O Notation", "No", "Answer", "Big O Notation");
        this.addQ(q10);
    }

    /* Add to our table now*/
    public void addQ(Question q)
    {
        ContentValues values = new ContentValues();
        values.put(questonText, q.getQuestion());
        values.put(questionAnswer, q.getAnswer());
        values.put(questionAnswer1, q.getOptionA());
        values.put(questionnAnswer2, q.getOptionB());
        values.put(questionAnswer3, q.getOptionC());
        myDatabase.insert(tbName, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + tbName;
        myDatabase=this.getReadableDatabase();
        Cursor cursor = myDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.setqId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));
                q.setOptionA(cursor.getString(3));
                q.setOptionB(cursor.getString(4));
                q.setOptionC(cursor.getString(5));
                quesList.add(q);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + tbName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }
}
