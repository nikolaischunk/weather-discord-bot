package Bot.commands;

import Bot.Command;
import Bot.Constants;
import Bot.Secret;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wetter implements Command {

    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(str, new
                TypeToken<HashMap<String, Object>>() {
                }.getType());
        return map;
    }


    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        if (args.isEmpty()) {
            event.getChannel().sendMessage(getHelp()).queue();
            return;
        }

        String location = args.get(0);
        String city = args.get(1);
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + location + "&APPID=" + Secret.API_KEY;

        try { //273,15 -> Kelvin
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();

            Map<String, Object> respMap = jsonToMap(result.toString());
            String format_city = respMap.get("name").toString();
            Map<String, Object> sysMap = jsonToMap(respMap.get("sys").toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            System.out.println(respMap.get("weather"));
            Map<String, Object> weatherMap = jsonToMap(respMap.get("weather").toString());
            Map<String, Object> zeroMap = jsonToMap(weatherMap.get("0").toString());


            double kelvin = 273.15;
            float temp = Float.parseFloat(mainMap.get("temp").toString());
            float feels_like = Float.parseFloat(mainMap.get("feels_like").toString());
            long temperature = Math.round(temp - kelvin);
            long feelslike = Math.round(feels_like - kelvin);
            String description = zeroMap.get("description").toString();

            
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("WeatherBot");
            builder.setTitle("Weather in " + format_city + ", " + sysMap.get("country").toString());
            builder.addField("Temperature", "" + temperature + "°", false);
            builder.addField("Feels like", "" + feelslike + "°", false);
            builder.addField("Weather", "" + description, false);

            builder.setFooter("Author: " + Constants.Author);
            builder.setThumbnail(Constants.ThumbnailBild);

            builder.setColor(Color.CYAN);

            event.getChannel().sendMessage(builder.build()).queue();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(args.get(0));
    }

    @Override
    public String getCommand() {
        return "weather";
    }

    @Override
    public String getHelp() {
        return "Gives you a random meme!\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + "` <countrycode> <cityname>";
    }
}
