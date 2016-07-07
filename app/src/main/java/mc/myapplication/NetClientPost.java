package mc.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetClientPost {

    // http://localhost:8080/RESTfulExample/json/product/post
    public static void main(String[] args) {

        try {

            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn 에 웹페이지 URL 을 연결시킨 것이다.
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            //요청 방식은 POST
            conn.setRequestProperty("Content-Type", "application/json");
            //Request body 를 application/json 로 서버에 전달

            JSONObject job = new JSONObject();
            try {
                job.put("phoneNum", "01000000000");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                job.put("name", "test name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                job.put("address", "test address");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OutputStream os = conn.getOutputStream();
            //Request body 를 Data 에 담기 위해 OutputStream 객체를 생성
            os.write(job.toString().getBytes());
            //Request body 에 Data 를 셋팅
            os.flush();
            //Request body 에 Data 입력

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}