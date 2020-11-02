/**
 * Author: Nikolai
 * Project: TemperatureBot
 * ClassUsage: Signalise if the Bot is online.
 * Could also be used to Shutdown
 */
package Bot;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

import static Bot.Constants.*;

public class Listener extends ListenerAdapter {
    public final Manager m = new Manager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        System.out.println("BOT IS NOW ONLINE");

    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase(tutorialBotPrefix + "shutdown") &&
                event.getAuthor().getIdLong() == Constants.ownerID) {
            event.getChannel().sendMessage(botIsOffline).complete();
            System.out.println(isOffline);
            event.getJDA().shutdown();
            System.exit(0);

        }
        m.run(event);
    }
}
