package com.appone.jordan.quiznow.Adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appone.jordan.quiznow.Models.QuestionAnswerItem;
import com.appone.jordan.quiznow.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.List;

/**
 * This class contains several classes that handle our View for displaying the Quiz's
 * questions. This code comes from this tutorial : https://www.youtube.com/watch?v=hVJpWSalzbo
 */


// This class is actually not used as we are using views with children ( Question -> Answer )
class ViewWithoutChild extends RecyclerView.ViewHolder
{
    // Variables
    TextView textView;

    ViewWithoutChild(View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);
    }
}

class ViewWithChild extends RecyclerView.ViewHolder
{

    TextView textView, textViewChild;
    RelativeLayout btn;
    ExpandableLinearLayout expandableLayout;

    ViewWithChild(View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);

        textViewChild = itemView.findViewById(R.id.textViewChild);

        btn = itemView.findViewById(R.id.buttonQA);

        expandableLayout = itemView.findViewById(R.id.expandableLayout);

    }
}

public class QuestionAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    List<QuestionAnswerItem> qaItems;
    Context c;
    SparseBooleanArray expandState = new SparseBooleanArray();

    public QuestionAnswerAdapter(List<QuestionAnswerItem> qaItems) {
        this.qaItems = qaItems;

        for (int i = 0; i < qaItems.size(); i++)
            expandState.append(i, false);
    }

    @Override
    public int getItemViewType(int position) {

        if (qaItems.get(position).isExpandable())

            return 1;
        else
            return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        this.c = viewGroup.getContext();

        if (i == 0){
            // no item
            LayoutInflater inflater = LayoutInflater.from(c);
            View view = inflater.inflate(R.layout.question_answer_without_child,viewGroup, false);
            return new ViewWithoutChild(view);

        }else{
            // no item
            LayoutInflater inflater = LayoutInflater.from(c);
            View view = inflater.inflate(R.layout.question_answer_with_child,viewGroup, false);
            return new ViewWithChild(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {

        switch (viewHolder.getItemViewType())
        {
            case 0:
            {
                ViewWithoutChild holder = (ViewWithoutChild) viewHolder;
                QuestionAnswerItem item = qaItems.get(i);
                holder.setIsRecyclable(false);
                holder.textView.setText(item.getQuestion());
            }
            break;

            case 1:
            {
                ViewWithChild holder = (ViewWithChild) viewHolder;
                QuestionAnswerItem item = qaItems.get(i);
                holder.setIsRecyclable(false);
                holder.textView.setText(item.getQuestion());

                holder.expandableLayout.setInRecyclerView(true);
                holder.expandableLayout.setExpanded(expandState.get(i));
                holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                    @Override
                    public void onPreOpen() {
                        changeRotate(holder.btn, 0f, 180f).start();
                        expandState.put(i, true);
                    }

                    @Override
                    public void onPreClose() {
                        changeRotate(holder.btn, 180f, 0f).start();
                        expandState.put(i, false);
                    }
                });

                holder.btn.setRotation(expandState.get(i)?180f:0f);
                holder.btn.setOnClickListener(v -> holder.expandableLayout.toggle());

                holder.textViewChild.setText(qaItems.get(i).getAnswer());
            }
            break;
            default:
                break;
        }
    }

    // animator
    private ObjectAnimator changeRotate(RelativeLayout button, float from, float to){
        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @Override
    public int getItemCount() {

        return qaItems.size();
    }
}
