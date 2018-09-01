package com.piadinapp.backendpiadinapp.frags;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.piadinapp.backendpiadinapp.R;
import com.piadinapp.backendpiadinapp.adapters.OrdersAdapter;
import com.piadinapp.backendpiadinapp.model.Ordine;
import com.piadinapp.backendpiadinapp.utility.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * List of the orders fragment
 */
public class OrderListFragment extends Fragment {
    private ArrayList<Ordine> mOrderList;
    private RecyclerView mRvOrders;
    private OrdersAdapter mOrdersAdapter;

    public OrderListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_order_list,container,false);

        //Recycler view click listener
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(),"Position " + position,Toast.LENGTH_SHORT).show();
            }
        };

        mOrderList = new ArrayList<>();
        //Fill recycler view
        mRvOrders = (RecyclerView) view.findViewById(R.id.rvOrderList);
        mRvOrders.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mOrdersAdapter = new OrdersAdapter(listener);
        mRvOrders.setAdapter(mOrdersAdapter);

        return view;
    }

    public void onOrderListChanged(ArrayList<Ordine> newList)
    {
        mOrdersAdapter.updateData(newList);
    }
}
