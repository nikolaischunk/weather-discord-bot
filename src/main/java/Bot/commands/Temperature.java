/**
 * Author: Nikolai
 * Projekt: TemperatureBot
 * ClassUsage: TemperatureMethod
 */
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
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Temperature implements Command {

    /**
     * Mapt JSON Object into a Map
     * @param str
     * @return map
     */
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(str, new
                TypeToken<HashMap<String, Object>>() {
                }.getType());
        return map;
    }

    /**
     * If the Command !temperature is called, run is executed
     * @param args
     * @param event
     */
    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        //If args = Empty the getHelp Message will be sent
        if (args.isEmpty()) {
            event.getChannel().sendMessage(getHelp()).queue();
            return;
        }

        String location = args.get(0);
        String city = args.get(1);
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + location + "&APPID=" + Secret.API_KEY;

        try {
            // get ReturnValue of the API
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
            Map<String, Object> sysMap = jsonToMap(respMap.get("sys").toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());

            //Variables
            double kelvin = 273.15;
            float temp = Float.parseFloat(mainMap.get("temp").toString());
            float feels_like = Float.parseFloat(mainMap.get("feels_like").toString());
            long temperature = Math.round(temp - kelvin);
            long feelslike = Math.round(feels_like - kelvin);

            String format_city = respMap.get("name").toString();

            /*Create the Embed Builder
            Add Fields, Titel, Author, Image to the Embed
            Look at Discord to watch the Embed
            */
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(Constants.Botname);
            builder.setTitle("Temperature in " + format_city + ", " + sysMap.get("country").toString());
            builder.addField("Temperature", "" + temperature + "°", false);
            builder.addField("Feels like", "" + feelslike + "°", false);

            builder.setFooter("Author: " + Constants.Author);
            builder.setThumbnail(Constants.ThumbnailBild);

            builder.setColor(Color.CYAN);

            //Queued the Embed
            event.getChannel().sendMessage(builder.build()).queue();

        } catch (IOException e) {
            //If there is a IOException print the Trace
            e.printStackTrace();
        }

        System.out.println(args.get(0));
    }

    /**
     * Commandname = !temperature
     * @return commandname
     */
    @Override
    public String getCommand() {
        return "temp";
    }

    /**
     * @return the HelpMessage + Usage
     */
    @Override
    public String getHelp() {
        return "Gives you the current Temperature in a Specific City\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + " <countrycode> <cityname>";
    }
}
