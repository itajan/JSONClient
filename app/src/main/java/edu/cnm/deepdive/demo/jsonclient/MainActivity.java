package edu.cnm.deepdive.demo.jsonclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import edu.cnm.deepdive.demo.jsonclient.entities.Greeting;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    new HttpRequestTask().execute();
  }

  private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {

    @Override
    protected Greeting doInBackground(Void... voids) {
      try {
        // "http://10.0.2.2:8080/"directory name"/"directory name"/ - location of emulator on computer
        final String url = "http://rest-service.guides.spring.io/greeting";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Greeting greeting = restTemplate.getForObject(url, Greeting.class);
        return greeting;
      } catch (Exception e) {
        Log.e("MainActivity", e.getMessage(), e);
      }
      return null;
    }
    @Override
    protected void onPostExecute(Greeting greeting) {
      TextView greetingId = (TextView) findViewById(R.id.greeting_id);
      TextView greetingContent = (TextView) findViewById(R.id.greeting_content);
      greetingId.setText(greeting.getId());
      greetingContent.setText(greeting.getContent());
    }
  }
}
