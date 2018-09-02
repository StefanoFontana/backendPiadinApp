package com.piadinapp.backendpiadinapp.frags;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private RecyclerView mRvOrders;
    private OrdersAdapter mOrdersAdapter;
    private ContentRequestListener mContentListener;
    private SwipeRefreshLayout mSwipeRefresh;

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
                //Toast.makeText(view.getContext(),"Position " + position,Toast.LENGTH_SHORT).show();
                Ordine selected = mOrdersAdapter.getOrderFromPosition(position);
                if(selected == null)
                    return;

                mContentListener.onOrderSelected(selected);
            }
        };

        //Fill recycler view
        mRvOrders = (RecyclerView) view.findViewById(R.id.rvOrderList);
        mRvOrders.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mOrdersAdapter = new OrdersAdapter(listener);
        mRvOrders.setAdapter(mOrdersAdapter);

        //Setup swipe refresh view
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.srlOrderRefresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mContentListener.onOrderListRefreshRequest();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mContentListener = (ContentRequestListener) activity;
    }

    public void onOrderListChanged(ArrayList<Ordine> newList)
    {
        mOrdersAdapter.updateData(newList);
        mSwipeRefresh.setRefreshing(false);
    }
}
