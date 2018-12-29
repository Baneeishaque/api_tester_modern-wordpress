package com.vogella.java.library.okhttp;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class TestMain {

    static OkHttpClient client = new OkHttpClient();

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
        TestMain example = new TestMain();
//        String getResponse = example.doGetRequest("http://www.vogella.com");
        String getResponse = example.doGetRequest("http://139.59.65.128/lottery_server/http_API/admin/get_main_agents.php");
//        System.out.println(getResponse);

        try {

            JSONArray jsonArray = new JSONArray(getResponse);
//            System.out.println("\n\njsonArray: " + jsonArray);

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

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("start_date", "2018-10-07")
                            .addFormDataPart("end_date", "2018-12-29")
                            .addFormDataPart("agent", jsonArray.getJSONObject(i).getString("username"))
                            .build();

                    Request request = new Request.Builder()
                            .url("http://139.59.65.128/lottery_server/http_API/pos/main/get_payment_main.php")
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response.body().string());

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