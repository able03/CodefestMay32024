package com.example.codefestmay32024;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codefestmay32024.models.AccountModel;
import com.example.codefestmay32024.models.AccountStaticClickedModel;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder>
{
    private Context context;
    private List<AccountModel> accountList;

    public AccountAdapter(Context context) {
        this.context = context;
    }

    public void setAccountList(List<AccountModel> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_search_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int pos = position;
        int id = accountList.get(pos).getId();
        String name = accountList.get(pos).getName();
        String address = accountList.get(pos).getAddress();
        String bdate = accountList.get(pos).getBdate();
        String contact = accountList.get(pos).getContact();
        String username = accountList.get(pos).getUsername();
        String password = accountList.get(pos).getPassword();
        String pin = accountList.get(pos).getPin();

        holder.tv_name.setText(name);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AccountStaticClickedModel(id, name, address, bdate, contact, username, password, pin);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tv_name;
        private CardView cv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tvNameRV);
            cv = itemView.findViewById(R.id.cvSearch);
        }
    }

}
