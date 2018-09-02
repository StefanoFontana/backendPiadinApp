package com.piadinapp.backendpiadinapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piadinapp.backendpiadinapp.R;
import com.piadinapp.backendpiadinapp.model.Ordine;
import com.piadinapp.backendpiadinapp.utility.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
    private RecyclerViewClickListener mListener;
    private List<Ordine> mData = new ArrayList<>();

    public OrdersAdapter(RecyclerViewClickListener listener)
    {
        mListener = listener;
    }

    public void updateData(List<Ordine> dataset)
    {
        mData.clear();
        mData.addAll(dataset);
        notifyDataSetChanged();
    }

    public Ordine getOrderFromPosition(int position)
    {
        if(position < 0 || position >= mData.size())
            return null;

        return mData.get(position);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);

        return new OrderViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position)
    {
        Ordine currentOrder = mData.get(position);
        holder.setId(currentOrder.getId());
        holder.setDate(currentOrder.getRegisterDate());
        holder.setEmail(currentOrder.getEmail());
        holder.setPhone(currentOrder.getPhone());
        holder.setPiadineNumber(3); //todo get number piadine da ordine
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    //View holder custom class
    public static class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mId;
        private TextView mDate;
        private TextView mEmail;
        private TextView mPhone;
        private TextView mPiadineNum;
        private RecyclerViewClickListener mListener;

        public OrderViewHolder(View itemView, RecyclerViewClickListener listener)
        {
            super(itemView);

            mId = (TextView) itemView.findViewById(R.id.tvId);
            mDate = (TextView) itemView.findViewById(R.id.tvDate);
            mEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            mPiadineNum = (TextView) itemView.findViewById(R.id.tvNumPiadine);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        public void setId(int id)
        {
            String finaTxt = "Order ID: " + String.valueOf(id);
            mId.setText(finaTxt);
        }
        public void setDate(String date)
        {
            mDate.setText(date);
        }
        public void setEmail(String mail)
        {
            mEmail.setText(mail);
        }
        public void setPhone(String phone)
        {
            mPhone.setText(phone);
        }
        public void setPiadineNumber(int number)
        {
            String finalTxt = "Piadine: " + String.valueOf(number);
            mPiadineNum.setText(finalTxt);
        }

        @Override
        public void onClick(View v)
        {
            mListener.onClick(v,getAdapterPosition());
        }
    }
}
