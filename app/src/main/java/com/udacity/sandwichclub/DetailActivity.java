package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;
    private TextView ingredientsTextView;
    private TextView placeOfOriginTextView;
    private TextView knownAsTextView;
    private TextView sandwichNameTextView;
    private TextView descriptionTextView;
    private TextView placeOfOriginTextViewLabel;
    private TextView knownAsTextViewLabel;

    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        sandwich = new Sandwich();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        initViews();
        populateUI();
        setTitle(sandwich.getMainName());
    }

    private void initViews() {
        ingredientsIv = findViewById(R.id.image_iv);
        ingredientsTextView = findViewById(R.id.textView_ingredients);
        placeOfOriginTextView = findViewById(R.id.textView_placeOfOrigin);
        placeOfOriginTextViewLabel = findViewById(R.id.textView_placeOfOrigin_label);
        knownAsTextViewLabel = findViewById(R.id.textView_knownAs_label);
        knownAsTextView = findViewById(R.id.textView_knownAs);
        sandwichNameTextView = findViewById(R.id.textview_name);
        descriptionTextView = findViewById(R.id.textview_discription);
    }

    private void populateUI() {

        if(sandwich.getImage().isEmpty() || sandwich.getImage() == null)
        {
            ingredientsIv.setVisibility(View.GONE);
        }else{
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);
        }


        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        if (sandwich.getAlsoKnownAs().isEmpty() || sandwich.getAlsoKnownAs() == null) {
            knownAsTextViewLabel.setVisibility(View.GONE);
            knownAsTextView.setVisibility(View.GONE);
        } else {
            knownAsTextView.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getPlaceOfOrigin().isEmpty() || sandwich.getPlaceOfOrigin() == null) {
            placeOfOriginTextViewLabel.setVisibility(View.GONE);
            placeOfOriginTextView.setVisibility(View.GONE);
        } else {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }

        descriptionTextView.setText(sandwich.getDescription());
        ingredientsTextView.setText(TextUtils.join(", ", sandwich.getIngredients()));
        sandwichNameTextView.setText(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
