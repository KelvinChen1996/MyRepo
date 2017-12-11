package com.example.asus.pikachise.view.myfranchise.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.pikachise.R;
import com.example.asus.pikachise.model.BrochureList;
import com.example.asus.pikachise.presenter.api.apiService;
import com.example.asus.pikachise.presenter.api.apiUtils;
import com.example.asus.pikachise.presenter.session.SessionManagement;
import com.example.asus.pikachise.view.authentication.Error401;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 25/11/2017.
 */

public class DetailMyFranchiseBrochureAdapter extends RecyclerView.Adapter<DetailMyFranchiseBrochureAdapter.ViewHolder> {
    private List<BrochureList> brochureList;
    private LayoutInflater inflater;
    Context context;
    private String token;
    public DetailMyFranchiseBrochureAdapter(List<BrochureList> brochureList, Context c, String token){
        this.brochureList = brochureList;
        this.inflater = LayoutInflater.from(c);
        this.token = token;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_mybrochure, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BrochureList current = brochureList.get(position);
        Picasso.with(context)
                .load(current.getBrochure())
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(current.getId());
            }
        });

    }
    public void showDialog(final String id){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Are you want to DELETE it ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletebrochure(id);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void deletebrochure(String id){
        apiService service;
        service = apiUtils.getAPIService();
        service.deletebrochure(token, id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==401 || response.code()==400){
                            context.startActivity(new Intent(context, Error401.class));
                        }
                        else if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("Brochure deleted successfully")){
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, "Please swipe down to refresh", Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                            Toast.makeText(context, "Whoops something wrong !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with upir connection", Toast.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    public int getItemCount() {
        return brochureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemmybrochure_imageview) ImageView imageView;
        @BindView(R.id.itemmybrochure_delete) ImageView delete;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
