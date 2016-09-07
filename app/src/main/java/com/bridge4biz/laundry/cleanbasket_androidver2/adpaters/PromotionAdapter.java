package com.bridge4biz.laundry.cleanbasket_androidver2.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.MoneyStringUtil;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Promotion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gingeraebi on 2016. 8. 29..
 */
public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionItemViewHolder> {

    private ArrayList<Promotion> promotions;
    private Context context;

    public PromotionAdapter(Context context, ArrayList<Promotion> promotions) {
        this.promotions = promotions;
        this.context = context;
    }

    @Override
    public PromotionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_promotion, parent, false);

        return new PromotionItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PromotionItemViewHolder holder, int position) {
        Promotion promotionResult = promotions.get(position);

        if (promotionResult.getImg() != null && !promotionResult.getImg().isEmpty()) {
            Picasso.with(context).load(promotionResult.getImg()).into(holder.promotionImage);
        } else {
            Picasso.with(context).load(R.drawable.ic_promotion).into(holder.promotionImage);
        }
        holder.promotionNameText.setText(promotionResult.getName() + "");
        holder.promotionMileageText.setText("+" + MoneyStringUtil.makeMoneyString(promotionResult.getMileage()) + "점");
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    public final static class PromotionItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.promotionImage)
        ImageView promotionImage;

        @BindView(R.id.promotionName)
        TextView promotionNameText;

        @BindView(R.id.promotionMileage)
        TextView promotionMileageText;

        public PromotionItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
