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
import com.app.games.activities.admin.AddShortVideoActivity;
import com.app.games.activities.admin.QuestionTypeActivity;
import com.app.games.activities.user.UserWatchVideoActivity;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ShortVideosAdapter extends RecyclerView.Adapter<ShortVideosAdapter.ViewHolder>{

    private ArrayList<ShortVideo> companys;
    private Context context;

    public ShortVideosAdapter(ArrayList<ShortVideo> companys, Context context) {
        this.companys = companys;
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
        final ShortVideo company = companys.get(position);

        holder.name.setText(company.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.shortVideo = company;
                SharedData.question_section = 2;
                Intent intent = new Intent(context, UserWatchVideoActivity.class);
                context.startActivity(intent);
            }
        });

        holder.show_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.shortVideo = company;
                SharedData.question_section = 2;
                Intent intent = new Intent(context, QuestionTypeActivity.class);
                context.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open dialog to edit
                SharedData.shortVideo = company;
                Intent intent = new Intent(context, AddShortVideoActivity.class);
                context.startActivity(intent);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadingHelper(context)
                        .showDialog("Delete Short Video",
                                "Are You Sure?",
                                "Delete",
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new ShortVideoController()
                                                .delete(company, new ShortVideosCallback() {
                                            @Override
                                            public void onSuccess(ArrayList<ShortVideo> companys) {
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
        return companys.size();
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


            show_complaints.setVisibility(View.VISIBLE);
            if(SharedData.type == 2){
                show_complaints.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }

        }
    }
}
