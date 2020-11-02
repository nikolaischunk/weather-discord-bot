/**
 * Author: Nikolai
 * Project: TemperatureBot
 * MethodUsage: A TryOut Class to test how to implement Methods
 */
package Bot.commands;

import Bot.Command;
import Bot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Ping implements Command {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        //If Ping is called do that
        if(msg.equalsIgnoreCase(Constants.tutorialBotPrefix + "ping")){
            event.getChannel().sendMessage("Pong!").queue(message -> {
                message.editMessage(event.getJDA().getGatewayPing()+"ms").queue();
            });
            //print that in command Line
            System.out.println(Constants.pingExecuted);
        }
    }

    /**
     *
     * @return the Command Name
     */
    @Override
    public String getCommand() {
        return "ping";
    }

    /**
     *
     * @return the HelpMessage
     */
    @Override
    public String getHelp() {
        return "Gives you the Gateway Ping\n" +
                "Usage: `" + Constants.tutorialBotPrefix + getCommand() + "`";
    }
}
