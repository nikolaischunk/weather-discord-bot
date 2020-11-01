package Bot.commands;

import Bot.Command;
import Bot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Ping implements Command {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if(msg.equalsIgnoreCase(Constants.TutorialBotPrefix + "ping")){
            //event.getChannel().sendMessage("Pong!").complete().editMessage(Long.toString(event.getJDA().getGatewayPing())).queue();
            event.getChannel().sendMessage("Pong!").queue(message -> {
                message.editMessage(event.getJDA().getGatewayPing()+"ms").queue();
            });
            System.out.println("ping command excecuted");
        }
    }

    @Override
    public String getCommand() {
        return "ping";
    }

    @Override //jo
    public String getHelp() {
        return "Gives you the Gateway Ping\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + "`";
    }
}
