package com.app.games.activities.admin.ui.notifications;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.games.R;
import com.app.games.callback.FileUploadCallback;
import com.app.games.callback.MemoryImageCallback;
import com.app.games.controller.ImageController;
import com.app.games.controller.MemoryImageController;
import com.app.games.model.MemoryImage;
import com.app.games.utils.LoadingHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {


    private static final int RESULT_LOAD_IMAGES = 25;
    LoadingHelper loadingHelper;
    RecyclerView list;
    ArrayList<MemoryImage> memoryImages;
    MemoryAdapter memoryAdapter;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_memory_game, container, false);
        loadingHelper = new LoadingHelper(getActivity());

        list = root.findViewById(R.id.list);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new GridLayoutManager(getActivity(),2));

        memoryImages = new ArrayList<>();
        memoryImages.add(new MemoryImage("",""));
        memoryAdapter = new MemoryAdapter();
        list.setAdapter(memoryAdapter);

        loadingHelper.showLoading("Loading Data");
        new MemoryImageController()
                .getAll(new MemoryImageCallback() {
                    @Override
                    public void onSuccess(ArrayList<MemoryImage> designs) {
                        loadingHelper.dismissLoading();
                        memoryImages = new ArrayList<>();
                        memoryImages.add(new MemoryImage("",""));
                        memoryImages.addAll(designs);
                        memoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });


        return root;
    }



    private class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder> {


        @NonNull
        @Override
        public MemoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.memory_item,parent,false);
            return new MemoryAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MemoryAdapter.ViewHolder holder, int position) {
            MemoryImage memoryImage = memoryImages.get(position);

            if(memoryImage.getKey().equals("")){
                holder.delete.setVisibility(View.GONE);


                Picasso.get()
                        .load(R.drawable.select_image)
                        .into(holder.image);

                holder.image
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(checkReadPermission()){
                                    pickDoc();
                                }
                            }
                        });
            }else {

                holder.image.setBackground(null);
                holder.delete.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(memoryImage.getImage())
                        .into(holder.image);
            }

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LoadingHelper(getActivity())
                            .showDialog("Delete Image",
                                    "Are you sure?",
                                    "Delete", "Cancel",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            new MemoryImageController()
                                                    .delete(memoryImage, new MemoryImageCallback() {
                                                        @Override
                                                        public void onSuccess(ArrayList<MemoryImage> designs) {
                                                            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
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
            return memoryImages.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            ImageView image;
            ImageButton delete;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                delete = itemView.findViewById(R.id.delete);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkReadPermission(){
        int permissionWriteExternal = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},2);
            return false;
        }else{
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickDoc();
            }
        }
    }

    private void pickDoc(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), RESULT_LOAD_IMAGES);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGES && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            loadingHelper.showLoading("Uploading Image");
            Uri uri = data.getData();
            new ImageController().uploadImage(uri, new FileUploadCallback() {
                @Override
                public void onSuccess(String url) {
                    MemoryImage memoryImage = new MemoryImage("",url);
                    new MemoryImageController().Save(memoryImage, new MemoryImageCallback() {
                        @Override
                        public void onSuccess(ArrayList<MemoryImage> designs) {
                            //  memoryImages.add(designs.get(0));
                        }

                        @Override
                        public void onFail(String msg) {

                        }
                    });
                    loadingHelper.dismissLoading();
                }

                @Override
                public void onFail(String msg) {
                    loadingHelper.dismissLoading();
                }
            });



        }
    }
}