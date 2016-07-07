package mc.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountryJSONParser {

    /** Receives a JSONObject and returns a list */
    public List<HashMap<String,String>> parse(JSONObject jObject){

        JSONArray jNames = null;
        try {
            /** Retrieves all the elements in the 'countries' array */
            jNames = jObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getCountries with the array of json object
         * where each json object represent a country
         */
        return getNames(jNames);
    }

    private List<HashMap<String, String>> getNames(JSONArray jNames){
        int nameCount = jNames.length();
        List<HashMap<String, String>> nameList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> name = null;

        /** Taking each country, parses and adds to list object */
        for(int i=0; i<nameCount;i++){
            try {
                /** Call getCountry with country JSON object to parse the country */
                name = getName((JSONObject)jNames.get(i));
                nameList.add(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return nameList;
    }

    /** Parsing the Country JSON object */
    private HashMap<String, String> getName(JSONObject jName){

        HashMap<String, String> name = new HashMap<String, String>();
        String id = "";
        String friend_name = "";
        String friend_picture = "";

        try {

            friend_name = jName.getString("name");



            name.put("flag", friend_name);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }
}