package api_tester.modern_wordpress;

import org.json.JSONArray;

import java.io.IOException;

public class Generate_SQL {

    public static void main(String[] args) {

        Generate_Payment_Reports example = new Generate_Payment_Reports();

        try {

            String getResponse = example.doGetRequest("http://139.59.65.128/lottery_server/http_API/admin/get_main_agents.php");

            JSONArray jsonArray = new JSONArray(getResponse);

//            System.out.println("\n\njsonArray: " + jsonArray);
//            System.out.println("No. of Agents : " + jsonArray.length());

            if (jsonArray.getJSONObject(0).getString("status").equals("0")) {

                for (int i = 1; i < jsonArray.length(); i++) {

                    System.out.println("INSERT INTO `payment_clear` (`id`, `agent`, `start_date`, `old_balance`) VALUES (NULL, '" + jsonArray.getJSONObject(i).getString("username") + "', '2018-10-05', '0');");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
