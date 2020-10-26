package Bot.commands;

import Bot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class Ping extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if(msg.equalsIgnoreCase(Constants.TutorialBotPrefix + "ping")){
            event.getChannel().sendMessage("Pong!").queue();
            System.out.println("hi");
        }
    }
}
