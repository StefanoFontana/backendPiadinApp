package com.piadinapp.backendpiadinapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piadinapp.backendpiadinapp.R;
import com.piadinapp.backendpiadinapp.model.OrdineItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    private List<OrdineItem> mData = new ArrayList<>();

    public OrderItemAdapter()
    {}

    public void updateData(List<OrdineItem> dataset)
    {
        mData.clear();
        mData.addAll(dataset);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_element_item,parent,false);

        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position)
    {
        OrdineItem currentItem = mData.get(position);
        holder.setName(currentItem.getName());
        holder.setType(currentItem.getType());
        holder.setImpasto(currentItem.getImpasto());
        holder.setIngredients(currentItem.getIngredientList());
        holder.setPrice(currentItem.getPrice());
        holder.setQuantity(currentItem.getQuantity());
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    //View holder custom class
    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mType;
        private TextView mImpasto;
        private TextView mIngredients;
        private TextView mPrice;
        private TextView mQuantity;

        public OrderItemViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tvName);
            mType = itemView.findViewById(R.id.tvType);
            mImpasto = itemView.findViewById(R.id.tvImpasto);
            mIngredients = itemView.findViewById(R.id.tvIngredients);
            mPrice = itemView.findViewById(R.id.tvPrice);
            mQuantity = itemView.findViewById(R.id.tvQuantity);
        }

        public void setName(String name)
        {
            mName.setText(name);
        }
        public void setType(String type)
        {
            mType.setText(type);
        }
        public void setImpasto(String impasto)
        {
            mImpasto.setText(impasto);
        }
        public void setIngredients(String ingredients)
        {
            mIngredients.setText(ingredients);
        }
        public void setPrice(float price)
        {
            String str = String.valueOf(price) + " €";
            mPrice.setText(str);
        }
        public void setQuantity(int quantity)
        {
            String str = "Num: " + String.valueOf(quantity);
            mQuantity.setText(str);
        }
    }
}
