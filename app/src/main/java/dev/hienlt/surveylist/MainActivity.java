package dev.hienlt.surveylist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Question> surveyItems = DataSource.getSurvey();

        listView = findViewById(R.id.list_item);
        listView.setAdapter(new SurveyAdapter(this, surveyItems));
    }
}
