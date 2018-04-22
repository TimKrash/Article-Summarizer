package edu.illinois.cs.cs125.mp7_numbers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String API_KEY = "BF578331EC";
    /**
     * Logging tag for messages in the main activity.
     */
    private static final String TAG = "MP7: Summary API";

    /**
     * Request key.
     * @param requestQueue - get the API
     */
    private static RequestQueue requestQueue;

    /**
     * Run when this activity comes to foreground.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton getSummary = findViewById(R.id.getSummary);
        getSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startAPICall();
            }
        });
    }

    void startAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.smmry.com/&SM_API_KEY=" + API_KEY
                    + "&SM_URL=https://www.nytimes.com/2018/04/21/us/barbara-bush-funeral" +
                            ".html?rref=collection%2Fsectioncollection%2Fus&action" +
                            "=click&contentCollection=us&region=rank&module=package" +
                            "&version=highlights&contentPlacement=2&pgtype=sectionfront",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                Log.d(TAG, response.toString(2));
                            } catch (JSONException ignored) {
                            }
                            /**
                             * This part does not seem to be working...
                             * not sure how to correctly parse this array
                             */
                            try {
                                JSONArray smArray = new JSONArray();
                                String text = "";
                                for (int i = 0; i < smArray.length(); i++) {
                                    JSONObject obj = (JSONObject) smArray.get(i);
                                    text = obj.get("sm_api_content").toString();
                                }
                                TextView summary = findViewById(R.id.summary);
                                summary.setText(text);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
