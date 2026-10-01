import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class WeatherApiClient {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("        WEATHER REST API CLIENT");
        System.out.println("======================================");

        System.out.print("Enter latitude: ");
        double latitude = scanner.nextDouble();

        System.out.print("Enter longitude: ");
        double longitude = scanner.nextDouble();

        scanner.close();

        fetchWeather(latitude, longitude);
    }

    public static void fetchWeather(double latitude, double longitude) {

        try {

            String apiUrl =
                    "https://api.open-meteo.com/v1/forecast"
                    + "?latitude=" + latitude
                    + "&longitude=" + longitude
                    + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();

            System.out.println("\nHTTP Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                String jsonResponse = response.toString();

                System.out.println("\n----------- JSON RESPONSE -----------");
                System.out.println(jsonResponse);
                System.out.println("-------------------------------------");

                displayWeatherData(jsonResponse);

            } else {

                System.out.println(
                        "Failed to retrieve weather data."
                );

            }

            connection.disconnect();

        } catch (Exception e) {

            System.out.println(
                    "\nError while connecting to the API: "
                    + e.getMessage()
            );
        }
    }

    private static void displayWeatherData(String json) {

        try {

            String currentData = json.substring(
                    json.indexOf("\"current\"")
            );

            String temperature =
                    extractValue(currentData, "temperature_2m");

            String humidity =
                    extractValue(currentData, "relative_humidity_2m");

            String windSpeed =
                    extractValue(currentData, "wind_speed_10m");

            System.out.println("\n========== WEATHER DATA ==========");

            System.out.println(
                    "Temperature: " + temperature + " °C"
            );

            System.out.println(
                    "Relative Humidity: " + humidity + " %"
            );

            System.out.println(
                    "Wind Speed: " + windSpeed + " km/h"
            );

            System.out.println("==================================");

        } catch (Exception e) {

            System.out.println(
                    "Unable to parse weather information."
            );
        }
    }

    private static String extractValue(
            String json,
            String key) {

        String searchKey = "\"" + key + "\":";

        int startIndex = json.indexOf(searchKey);

        if (startIndex == -1) {
            return "N/A";
        }

        startIndex += searchKey.length();

        int endIndex = json.indexOf(",", startIndex);

        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }

        return json.substring(startIndex, endIndex).trim();
    }
}
