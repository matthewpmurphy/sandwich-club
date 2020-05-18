package com.udacity.sandwichclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //setup UI component variables, TextViews and LinearLayouts
    private TextView alsoKnownAsTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;
    private TextView placeOfOriginTv;
    private LinearLayout alsoKnownAsLl;
    private LinearLayout descriptionLl;
    private LinearLayout ingredientsLl;
    private LinearLayout placeOfOriginLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.noimagefound)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //Update populateUI to take the Sandwich model as a parameter, populate our UI views, and hide empty content areas
    private void populateUI(Sandwich sandwich) {
        descriptionTv = findViewById(R.id.description_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionLl = findViewById(R.id.description_ll);
        placeOfOriginLl = findViewById(R.id.origin_ll);
        alsoKnownAsLl = findViewById(R.id.also_known_ll);
        ingredientsLl = findViewById(R.id.ingredients_ll);

        //Load Description to UI if available
        String descriptionText = sandwich.getDescription();
        if(descriptionText == null || descriptionText.isEmpty()) {
            descriptionLl.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(descriptionText);
            descriptionLl.setVisibility(View.VISIBLE);
        }

        //Load Place of Origin to UI if available
        String placeOfOriginText = sandwich.getPlaceOfOrigin();
        if(placeOfOriginText == null || placeOfOriginText.isEmpty()) {
            placeOfOriginLl.setVisibility(View.GONE);
        } else {
            placeOfOriginTv.setText(placeOfOriginText);
            placeOfOriginLl.setVisibility(View.VISIBLE);
        }

        //Load  Also Known As to UI if available
        String alsoKnownAsText = null;
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if(alsoKnownAsList == null || alsoKnownAsList.isEmpty()) {
            alsoKnownAsLl.setVisibility(View.GONE);
        } else {
            alsoKnownAsText = TextUtils.join(",", alsoKnownAsList);
            alsoKnownAsTv.setText(alsoKnownAsText);
            alsoKnownAsLl.setVisibility(View.VISIBLE);
        }

        //Load Ingredients to UI if available
        String ingredientsText = null;
        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList == null || ingredientsList.isEmpty()) {
            ingredientsLl.setVisibility(View.GONE);
        } else {
            ingredientsText = TextUtils.join("\n\r", ingredientsList);
            ingredientsTv.setText(ingredientsText);
            ingredientsLl.setVisibility(View.VISIBLE);
        }

    }
}
