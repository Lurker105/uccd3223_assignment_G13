package com.example.uccd3223_assignment_g13;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseRVAdapter extends RecyclerView.Adapter<ExpenseRVAdapter.ViewHolder> {

    private ArrayList<ExpenseModal> expenseModalArrayList;
    private Context context;

    ExpenseRVAdapter(ArrayList<ExpenseModal> expenseModalArrayList, Context context){
        this.expenseModalArrayList = expenseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseRVAdapter.ViewHolder holder, int position) {
        ExpenseModal modal = expenseModalArrayList.get(position);
        holder.expenseAmount.setText(modal.getAmount());
        holder.expenseCategory.setText(modal.getCategory());
        holder.expenseDescription.setText(modal.getDescription());
        holder.expenseDate.setText(modal.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UpdateExpense.class);

                i.putExtra("amount", modal.getAmount());
                i.putExtra("category",modal.getCategory());
                i.putExtra("description",modal.getDescription());
                i.putExtra("date",modal.getDate());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView expenseAmount, expenseCategory, expenseDescription, expenseDate;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            expenseAmount = itemView.findViewById(R.id.idAmount);
            expenseCategory = itemView.findViewById(R.id.idCategory);
            expenseDescription = itemView.findViewById(R.id.idDescription);
            expenseDate = itemView.findViewById(R.id.idDate);
        }
    }
}
