package com.bridge4biz.laundry.cleanbasket_androidver2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.network.FirebaseManager;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.Review;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewActivity extends AppCompatActivity {
    private int reviewType;
    private int oid;
    private int rate = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        oid = Integer.parseInt(intent.getStringExtra("oid"));
        setType(type);

        star3stClicked();
    }


    private void setType(String type) {
        if (type.equals("pickUp")) {
            reviewType = Review.PICKUP_REVIEW;
        } else {
            reviewType = Review.DROPOFF_REVIEW;
            setDropOffUI();
        }
    }

    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.infoText)
    TextView infoText;
    @BindView(R.id.questionOne)
    TextView questionOneText;
    @BindView(R.id.opinionEdit)
    EditText opinionEdit;

    private void setDropOffUI() {
        titleText.setText(getResources().getString(R.string.dropoff_review_title));
        infoText.setText(getResources().getString(R.string.dropoff_info));
        questionOneText.setText(getResources().getString(R.string.dropoff_question));
    }

    @OnClick(R.id.complete)
    public void onCompleteClicked() {
        FirebaseManager firebaseManager = new FirebaseManager();
        if(opinionEdit.getText() != null) {
            firebaseManager.sendReviewToServer(new Review(oid, reviewType, rate, opinionEdit.getText().toString()));
        }
        else {
            firebaseManager.sendReviewToServer(new Review(oid, reviewType, rate, ""));
        }

        finish();
    }

    @BindView(R.id.star_1st) ImageView star1st;
    @BindView(R.id.star_2st) ImageView star2st;
    @BindView(R.id.star_3st) ImageView star3st;
    @BindView(R.id.star_4st) ImageView star4st;
    @BindView(R.id.star_5st) ImageView star5st;

    @OnClick({R.id.star_1st, R.id.star_2st, R.id.star_3st, R.id.star_4st, R.id.star_5st})
    public void onStartClicked(View v) {
        switch (v.getId()){
            case R.id.star_1st :
                star1stClicked();
                break;
            case R.id.star_2st :
                star2stClicked();
                break;
            case R.id.star_3st :
                star3stClicked();
                break;
            case R.id.star_4st :
                star4stClicked();
                break;
            case R.id.star_5st :
                star5stClicked();
                break;
        }
    }

    private void star1stClicked() {
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star1st);
        Picasso.with(this).load(R.drawable.ic_star).into(star2st);
        Picasso.with(this).load(R.drawable.ic_star).into(star3st);
        Picasso.with(this).load(R.drawable.ic_star).into(star4st);
        Picasso.with(this).load(R.drawable.ic_star).into(star5st);
        rate = 1;
    }

    private void star2stClicked() {
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star1st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star2st);
        Picasso.with(this).load(R.drawable.ic_star).into(star3st);
        Picasso.with(this).load(R.drawable.ic_star).into(star4st);
        Picasso.with(this).load(R.drawable.ic_star).into(star5st);
        rate = 2;
    }

    private void star3stClicked() {
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star1st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star2st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star3st);
        Picasso.with(this).load(R.drawable.ic_star).into(star4st);
        Picasso.with(this).load(R.drawable.ic_star).into(star5st);
        rate = 3;
    }

    private void star4stClicked() {
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star1st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star2st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star3st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star4st);
        Picasso.with(this).load(R.drawable.ic_star).into(star5st);
        rate = 4;
    }

    private void star5stClicked() {
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star1st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star2st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star3st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star4st);
        Picasso.with(this).load(R.drawable.ic_star_fill).into(star5st);
        rate = 5;
    }
}
