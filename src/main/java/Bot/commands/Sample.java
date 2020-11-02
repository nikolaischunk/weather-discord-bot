/**
 * Author: Nikolai
 * Project: TemperatureBot
 * ClassUsage: SampleClass for a COmmand to Showcase the build
 */
package Bot.commands;

import Bot.Command;
import Bot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Sample implements Command {

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Sample").queue();
    }

    @Override
    public String getCommand() {
        return "sample";
    }

    @Override
    public String getHelp() {
        return "This is a Example Command\n" +
                "Usage: `" + Constants.tutorialBotPrefix + getCommand() + "`";
    }
}
