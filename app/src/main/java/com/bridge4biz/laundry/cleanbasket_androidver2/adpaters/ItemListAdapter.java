package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.listeners.OnItemListClickedListener;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.ItemCode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gingeraebi on 2016. 7. 28..
 */
public class ItemListAdapter extends BaseAdapter  {

    private Context context;

    private ArrayList<ItemCode> itemCodes;
    private HashMap<Integer, Integer> itemCountMap;

    private OnItemListClickedListener onItemListClickedListener;

    public ItemListAdapter(Context context, ArrayList<ItemCode> itemCodes, HashMap<Integer, Integer> itemCountMap) {
        this.context = context;
        this.itemCodes = itemCodes;
        this.itemCountMap = itemCountMap;
    }

    public void setOnItemListClickedListener(OnItemListClickedListener onItemListClickedListener) {
        this.onItemListClickedListener = onItemListClickedListener;
    }

    @Override
    public int getCount() {
        return itemCodes.size();
    }

    @Override
    public Object getItem(int position) {
        return itemCodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ItemCode itemCode = itemCodes.get(position);

        //header일 경우
        if (itemCode.itemCode == -100) {
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_header, parent, false);
            TextView categoryName = (TextView) view.findViewById(R.id.categoryName);
            categoryName.setText(itemCode.getName());
            return view;
        }

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_itmelist, parent, false);

        TextView itemName = (TextView) view.findViewById(R.id.categoryName);
        TextView itemPrice = (TextView) view.findViewById(R.id.price);

        itemName.setText(itemCode.getName());
        itemPrice.setText(itemCode.getPrice() + "");

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.row);
        ImageView increase = (ImageView) view.findViewById(R.id.increase);
        ImageView decrease = (ImageView) view.findViewById(R.id.decrease);
        TextView itemCount = (TextView) view.findViewById(R.id.count);

        if(itemCountMap.containsKey(itemCode.itemCode)) {
            itemCount.setText("" + itemCountMap.get(itemCode.itemCode));
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListClickedListener.onIncreaseClicked(itemCode);
                ItemListAdapter.this.notifyDataSetChanged();
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListClickedListener.onIncreaseClicked(itemCode);
                ItemListAdapter.this.notifyDataSetChanged();
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListClickedListener.onDecreaseClicked(itemCode);
                ItemListAdapter.this.notifyDataSetChanged();
            }
        });

        return view;
    }



}
