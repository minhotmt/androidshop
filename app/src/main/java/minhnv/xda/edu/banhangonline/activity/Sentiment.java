package minhnv.xda.edu.banhangonline.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

import java.util.ArrayList;
import java.util.List;

import minhnv.xda.edu.banhangonline.R;

public class Sentiment extends AppCompatActivity {


    Toolbar toolbar;
    TextView textView,txtSentiment;
    EditText editText;
    Button btnse;

    private class WatsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... textToSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("Running the Watson thread");
                }
            });

            NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                    NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                    "ae9bbf46-ed1a-404a-8c18-26fcf426db44",
                    "CMGtOrukt3Uc");

            //String text = "Apples and Oranges" + "I love apples! I don't like oranges";
            String text = String.valueOf(editText.getText());

            List<String> targets = new ArrayList<>();
            targets.add("Shirt");
            targets.add("This");
            targets.add("I");
            targets.add("It");
            targets.add("So");
            targets.add("Verry");

            EmotionOptions emotion= new EmotionOptions.Builder()
                    .targets(targets)
                    .build();

            Features features = new Features.Builder()
                    .emotion(emotion)
                    .build();

            AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                    .html(text)
                    .features(features)
                    .build();

            AnalysisResults response = service
                    .analyze(parameters)
                    .execute();
            System.out.println(response);

            if (response.getEmotion().getDocument().getEmotion().getJoy()>0.5){
                return "Kết quả Sentiment Analysis Emotion:" +
                        "\nThích:"+String.valueOf(response.getEmotion().getDocument().getEmotion().getJoy())+
                        "\nGiận giữ: "+String.valueOf(response.getEmotion().getDocument().getEmotion().getAnger())+
                        "\nSợ hãi: "+String.valueOf(response.getEmotion().getDocument().getEmotion().getFear())+
                        "\nBuồn: "+String.valueOf(response.getEmotion().getDocument().getEmotion().getSadness())+
                        "\nGét:"+String.valueOf(response.getEmotion().getDocument().getEmotion().getAnger())+
                        "\nSản phẩm được khách hàng yêu thích";
            }else {
                return "Sản phẩm không được khách hàng yêu thích";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result!=null){
                textView.setText(result);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentiment);

        editText = (EditText) findViewById(R.id.edit);
        textView = (TextView) findViewById(R.id.text);
        txtSentiment = (TextView) findViewById(R.id.txtSentiment);
        btnse = (Button) findViewById(R.id.btnsent);
        toolbar = (Toolbar) findViewById(R.id.toolbarsentiment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("the text to speech: " + editText.getText());
                textView.setText("TTS: " + editText.getText());
                Sentiment.WatsonTask task = new Sentiment.WatsonTask();
                task.execute(new String[]{});
            }
        });
    }
}
