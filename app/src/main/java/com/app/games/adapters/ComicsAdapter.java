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
import com.app.games.activities.admin.AddComicActivity;
import com.app.games.activities.admin.QuestionTypeActivity;
import com.app.games.activities.user.UserReadComicActivity;
import com.app.games.callback.ComicsCallback;
import com.app.games.controller.ComicsController;
import com.app.games.model.Comics;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder>{

    private ArrayList<Comics> comics;
    private Context context;

    public ComicsAdapter(ArrayList<Comics> comics, Context context) {
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
        final Comics comics = this.comics.get(position);

        holder.name.setText(comics.getName());

        if(SharedData.type == 2){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedData.comics = comics;
                    Intent intent = new Intent(context, UserReadComicActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        holder.show_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.comics = comics;
                SharedData.question_section = 1;
                Intent intent = new Intent(context, QuestionTypeActivity.class);
                context.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.comics = comics;
                Intent intent = new Intent(context, AddComicActivity.class);
                context.startActivity(intent);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadingHelper(context)
                        .showDialog("Delete Comic",
                                "Are You Sure?",
                                "Delete",
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new ComicsController().delete(comics, new ComicsCallback() {
                                            @Override
                                            public void onSuccess(ArrayList<Comics> comics) {
                                                Toast.makeText(context , "Deleted!!!", Toast.LENGTH_SHORT).show();
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

            if(SharedData.type == 2){
                show_complaints.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }

        }
    }
}
