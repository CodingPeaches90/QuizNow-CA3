package com.appone.jordan.quiznow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class QuizItemAdapter extends RecyclerView.Adapter<QuizItemAdapter.QuizItemViewHolder>
{

    public static class QuizItemViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView quizName;
        TextView quizDesc;
        ImageView quizPhoto;

        QuizItemViewHolder(View itemView)
        {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            quizName = itemView.findViewById(R.id.quiz_name);
            quizDesc = itemView.findViewById(R.id.quiz_desc);
            quizPhoto = itemView.findViewById(R.id.quiz_photo);

        }
    }
    List<QuizItem> items;

    QuizItemAdapter(List<QuizItem> items)
    {
        this.items = items;
    }

    @Override
    public QuizItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz_item_ui,viewGroup,false);
        QuizItemViewHolder qi = new QuizItemViewHolder(v);
        /*
            Switch To Activity .... https://stackoverflow.com/questions/27083837/card-view-click-on-card-move-to-new-activity
        */
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.getContext().startActivity(new Intent(v.getContext(),QuestionActivity.class));
            }
        });
        return qi;
    }

    @Override
    public void onBindViewHolder(QuizItemViewHolder quizItemViewHolder, int i)
    {
        quizItemViewHolder.quizName.setText(items.get(i).name);
        quizItemViewHolder.quizDesc.setText(items.get(i).desc);
        quizItemViewHolder.quizPhoto.setImageResource(items.get(i).photoId);
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }
}
