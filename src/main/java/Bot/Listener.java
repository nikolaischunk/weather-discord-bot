/**
 * Author: Nikolai
 * Projekt: WeatherBot
 * ClassUsage: Signalise if the Bot is online.
 * Could also be used to Shutdown
 */
package Bot;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class Listener extends ListenerAdapter {
    public final Manager m = new Manager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        System.out.println("BOT IS NOW ONLINE");
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase(Constants.TutorialBotPrefix + "shutdown") &&
                event.getAuthor().getIdLong() == Constants.OWNERID) {
            event.getJDA().shutdown();
            System.exit(0);
        }
        m.run(event);
    }
}
