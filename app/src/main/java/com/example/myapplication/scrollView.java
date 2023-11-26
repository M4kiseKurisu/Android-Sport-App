package com.example.myapplication;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class scrollView extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int[] imageResIds = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
        updateViewWithData(imageResIds);
    }

    private void updateViewWithData(int[] imageResIds) {
        LinearLayout verticalLayout = findViewById(R.id.verticalLayout);
        for (int i : imageResIds) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResIds[0]);
            /*Button button = new Button(this);
            button.setText("Button " + (i + 1));*/
            verticalLayout.addView(imageView);
        }
    }

    public void refreshDataAndView(int[] imageResIds) {
        updateViewWithData(imageResIds);
    }
}
