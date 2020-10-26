package student.univ_bejaia;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Currency {

    private static HttpURLConnection connection;


    public boolean getData() {
        String line;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL("http://www.floatrates.com/daily/usd.json");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();

            if (status > 299) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                     BufferedWriter errFile = new BufferedWriter(new FileWriter("error.log"))) {
                    while ((line = bufferedReader.readLine()) != null) {
                        errFile.write(line);
                    }
                }
                return false;
            } else {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                     BufferedWriter jasonFile = new BufferedWriter(new FileWriter("rate.json"))) {
                    while ((line = bufferedReader.readLine()) != null) {
                        jasonFile.write(line);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return true;
    }

    public double parsJSON(String currency) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("rate.json")) {
            Object obj = jsonParser.parse(reader);
            if (currency.equals("usd")){
                return 1;
            }
            JSONObject jsonObject = (JSONObject) ((JSONObject) obj).get(currency);
            return (double) jsonObject.get("rate");

        } catch (IOException | org.json.simple.parser.ParseException e) {
            return -1;
        }
    }
    public String parsJSON(String currency, String json2) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("rate.json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) ((JSONObject) obj).get(currency);
            return (String) jsonObject.get(json2);

        } catch (IOException | org.json.simple.parser.ParseException e) {
            return null;
        }
    }

    public double convert(String from, String to, double money){
        return  (parsJSON(to) / parsJSON(from)) * money;
    }

}
