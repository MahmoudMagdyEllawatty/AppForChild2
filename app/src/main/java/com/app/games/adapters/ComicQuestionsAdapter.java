package com.app.games.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.games.R;
import com.app.games.activities.admin.AddQuestionActivity;
import com.app.games.activities.user.UserQuestionActivity;
import com.app.games.callback.ComicsCallback;
import com.app.games.controller.ComicsController;
import com.app.games.model.Comics;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ComicQuestionsAdapter extends RecyclerView.Adapter<ComicQuestionsAdapter.ViewHolder>{

    private ArrayList<Comics.Question> comics;
    private Context context;

    public ComicQuestionsAdapter(ArrayList<Comics.Question> comics, Context context) {
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
        final Comics.Question comics = this.comics.get(position);

        holder.name.setText(comics.getQuestion());

        if(SharedData.type == 2){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedData.comicQuestion = comics;
                    Intent intent = new Intent(context, UserQuestionActivity.class);
                    context.startActivity(intent);
                }
            });

        }


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.comicQuestion = comics;
                Intent intent = new Intent(context, AddQuestionActivity.class);
                context.startActivity(intent);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadingHelper(context)
                        .showDialog("Delete Question",
                                "Are You Sure?",
                                "Delete",
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ArrayList<Comics.Question> questions = SharedData.comics.getQuestions();
                                        if(questions == null){
                                            questions = new ArrayList<>();
                                        }

                                        int index = 0;
                                        for (Comics.Question question : questions){
                                            if(question.getQuestion().equals(comics.getQuestion())){
                                                break;
                                            }
                                            index++;
                                        }


                                        questions.remove(index);

                                        SharedData.comics.setQuestions(questions);

                                        new ComicsController().Save(SharedData.comics, new ComicsCallback() {
                                            @Override
                                            public void onSuccess(ArrayList<Comics> categories) {
                                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onFail(String msg) {

                                            }
                                        });

                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
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
            if(SharedData.type == 2){
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
        }
    }
}
