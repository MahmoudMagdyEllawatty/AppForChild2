package com.app.games.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.games.R;
import com.app.games.activities.admin.QuestionsActivity;
import com.app.games.activities.user.UserQuestionsActivity;
import com.app.games.model.QuestionType;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class QuestionTypesAdapter extends RecyclerView.Adapter<QuestionTypesAdapter.ViewHolder>{

    private ArrayList<QuestionType> comics;
    private Context context;

    public QuestionTypesAdapter(ArrayList<QuestionType> comics, Context context) {
        this.comics = comics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.company_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final QuestionType comics = this.comics.get(position);
        holder.name.setText(comics.getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.question_type = comics;
                if(SharedData.type == 2){
                    Intent intent = new Intent(context, UserQuestionsActivity.class);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context,QuestionsActivity.class);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView name;
        private ImageButton edit,delete;
        private MaterialButton show_complaints;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            show_complaints = itemView.findViewById(R.id.show_complaints);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

            show_complaints.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
    }
}
