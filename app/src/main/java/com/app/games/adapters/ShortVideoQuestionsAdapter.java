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
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ComicsController;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.Comics;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

 public class ShortVideoQuestionsAdapter extends RecyclerView.Adapter<ShortVideoQuestionsAdapter.ViewHolder>{

     private ArrayList<ShortVideo.Question> companys;
     private Context context;

     public ShortVideoQuestionsAdapter(ArrayList<ShortVideo.Question> companys, Context context) {
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
         final ShortVideo.Question company = companys.get(position);

         holder.name.setText(company.getQuestion());


         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SharedData.shortVideoQuestion = company;
                 Intent intent = new Intent(context, UserQuestionActivity.class);
                 context.startActivity(intent);
             }
         });

         holder.edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //open dialog to edit
                 SharedData.shortVideoQuestion = company;
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
                                         ArrayList<ShortVideo.Question> questions = SharedData.shortVideo.getQuestions();
                                         if(questions == null){
                                             questions = new ArrayList<>();
                                         }

                                         int index = 0;
                                         for (ShortVideo.Question question : questions){
                                             if(question.getQuestion().equals(company.getQuestion())){
                                                 break;
                                             }
                                             index++;
                                         }


                                         questions.remove(index);

                                         SharedData.shortVideo.setQuestions(questions);

                                         new ShortVideoController().Save(SharedData.shortVideo, new ShortVideosCallback() {
                                             @Override
                                             public void onSuccess(ArrayList<ShortVideo> shortVideos) {
                                                 Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show();
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


             show_complaints.setVisibility(View.GONE);

             if(SharedData.type == 2){
                 edit.setVisibility(View.GONE);
                 delete.setVisibility(View.GONE);
             }

         }
     }
 }
