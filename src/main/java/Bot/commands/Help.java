package Bot.commands;

import Bot.Command;
import Bot.Constants;
import Bot.Manager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class Help implements Command {
    public final Manager manager;

    public Help(Manager m) {
        this.manager = m;
    }

    @Override
    public void run(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty()) {
            EmbedBuilder e = new EmbedBuilder()
                    .setTitle("A List of all my Commands");
            StringBuilder desc = e.getDescriptionBuilder();
            manager.getCommands().forEach(command -> {
                desc.append("`").append(command.getCommand()).append("`\n");
            });
            event.getChannel().sendMessage(e.build()).queue();
        }
        Command command = manager.getCommand(String.join("", args)); //!help nonexistingCommand
        if (command == null) {
            event.getChannel().sendMessage("Sorry, the command `" + String.join("", args) + "` doesnt exist\n" +
                    "Use `" + Constants.TutorialBotPrefix + command.getCommand() + "` for a list of all my commands!");
            return;
        }
        event.getChannel().sendMessage("Command help for `" + command.getCommand() + "`\n" +
                command.getHelp()).queue();
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Gives you a List of existing Commands on this Bot\n" +
                "Usage: `" + Constants.TutorialBotPrefix + getCommand() + "`";
    }
}
