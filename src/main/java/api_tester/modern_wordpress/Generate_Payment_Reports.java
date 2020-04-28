package api_tester.modern_wordpress;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Generate_Payment_Reports {

    static OkHttpClient client = new OkHttpClient.Builder()
            // .connectTimeout(660, TimeUnit.SECONDS)
            // .writeTimeout(660, TimeUnit.SECONDS)
            .readTimeout(660, TimeUnit.SECONDS)
            .build();

    // code request code here
    String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // post request code here

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    // test data
    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] args) throws IOException {

        // issue the Get request
        Generate_Payment_Reports example = new Generate_Payment_Reports();
//        String getResponse = example.doGetRequest("http://www.vogella.com");
        String getResponse = example.doGetRequest("http://139.59.65.128/lottery_server/http_API/admin/get_main_agents.php");
//        System.out.println(getResponse);

        try {

            JSONArray jsonArray = new JSONArray(getResponse);
//            System.out.println("\n\njsonArray: " + jsonArray);
            System.out.println("No. of Agents : " + jsonArray.length());

            if (jsonArray.getJSONObject(0).getString("status").equals("0")) {

                for (int i = 1; i < jsonArray.length(); i++) {

//                    HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.github.help").newBuilder();
//                    urlBuilder.addQueryParameter("v", "1.0");
//                    urlBuilder.addQueryParameter("user", "vogella");
//                    String url = urlBuilder.build().toString();
//
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .build();
//
//                    client.newCall(request).enqueue(new Callback() {
//
//                        @Override
//                        public void onFailure(Request request, IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onResponse(Response response) throws IOException {
//                            if (!response.isSuccessful()) {
//                                throw new IOException("Unexpected code " + response);
//                            } else {
//                                // do something wih the result
//                            }
//                        }
//                    });

//                    RequestBody requestBody = new MultipartBody.Builder()
//                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("start_date", "2018-10-06")
//                            .addFormDataPart("end_date", "2018-12-29")
//                            .addFormDataPart("agent", jsonArray.getJSONObject(i).getString("username"))
//                            .build();
//
//                    Request request = new Request.Builder()
//                            .url("http://139.59.65.128/lottery_server/http_API/pos/main/get_payment_main.php")
//                            .post(requestBody)
//                            .build();
//
//                    Response response = client.newCall(request).execute();
//                    System.out.println(response.body().string());

                    ProcessBuilder builder = new ProcessBuilder(
                            "cmd.exe", "/c", "http --timeout=360 --ignore-stdin --form POST http://139.59.65.128/lottery_server/http_API/pos/main/get_payment_main.php start_date=2018-10-06 end_date=2018-12-30 agent="+jsonArray.getJSONObject(i).getString("username")+"");
                    builder.redirectErrorStream(true);
                    Process p = builder.start();
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while (true) {
                        line = r.readLine();
                        if (line == null) { break; }
                        System.out.println(line);
                    }

                }

            } else {

                System.out.println("No Entries...");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // issue the post request

//        String json = example.bowlingJson("Jesse", "Jake");
//        String postResponse = example.doPostRequest("http://www.roundsapp.com/post", json);
//        System.out.println(postResponse);
    }
}