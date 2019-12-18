package openhab.rest.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResults = (TextView) findViewById(R.id.textViewResults);
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            Examples examples = new Examples();

            Examples.IEventListener eventListener = bindings -> bindingsResponse(bindings);

            examples.getBindings(eventListener);
        }
        catch(IOException ioe) {
            tvResults.setText(ioe.toString());
        }
    }

    private void bindingsResponse(List<Examples.Binding> bindings) {
        String result = "";

        if (bindings == null || bindings.size() == 0) {
            result = "No bindings";
        }
        else {
            for (Examples.Binding binding : bindings) {
                result += "Name: " + binding.name  + "\n" +
                "Author: " + binding.author + "\n" +
                "Description: " + binding.description + "\n" +
                "Id: " + binding.id + "\n\n";
            }
        }

        tvResults.setText(result);
        }
}
