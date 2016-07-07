package mc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class newActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
//        ListView listViewExample = (ListView)findViewById(R.id.lv_name);
    }

    public void showUp(JSONObject strJson){

        Log.d("JG", "ha...");

        Log.d("JG", "ha...2");
        /** The parsing of the xml data is done in a non-ui thread */
        ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
        Log.d("JG", "ha...3");
        /** Start parsing xml data */
        listViewLoaderTask.execute(strJson.toString());
        Log.d("JG", "ha...4");
    }



    public class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter>{


        JSONObject jObject;

        /** Doing the parsing of xml data in a non-ui thread */
        @Override
        protected SimpleAdapter doInBackground(String... strJson) {
            try{
                Log.d("JG","hana");
                jObject = new JSONObject(strJson[0]);
                Log.d("JG2",jObject.toString());
                Log.d("JG","dool");
//                CountryJSONParser countryJsonParser = new CountryJSONParser();
//                countryJsonParser.parse(jObject);
            }catch(Exception e){
                Log.d("JSON Exception1",e.toString());
            }

            CountryJSONParser countryJsonParser = new CountryJSONParser();
            Log.d("JG","dool");
            List<HashMap<String, String>> countries = null;

            try{
                /** Getting the parsed data as a List construct */
                countries = countryJsonParser.parse(jObject);
                Log.d("JG3",countries.toString());
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            Log.d("JG","dool");
            /** Keys used in Hashmap */
            String[] from = { "flag"};

            /** Ids of views in listview_layout */
            int[] to = { R.id.iv_flag};
            Log.d("JG","dool1");
            /** Instantiating an adapter to store each items
             *  R.layout.listview_layout defines the layout of each item
             */
            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), countries, R.layout.lv_layout, from, to);
            Log.d("JG","dool2");
            return adapter;
        }

        /** Invoked by the Android system on "doInBackground" is executed completely */
        /** This will be executed in ui thread */
        @Override
        public void onPostExecute(SimpleAdapter adapter) {

            /** Getting a reference to listview of main.xml layout file */
            ListView listView = ( ListView ) findViewById(R.id.lv_name);
            Log.d("JG","dool3");
            /** Setting the adapter containing the country list to listview */
            listView.setAdapter(adapter);
        }
    }

    public void onbackButtonClicked(View v)
    {
        Toast.makeText(getApplicationContext(), "뒤로 돌아감", Toast.LENGTH_LONG).show();
        finish();
    }




}
