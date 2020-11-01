package Bot;

import Bot.commands.Ping;
import Bot.reactions.FriendlyGreeting;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {

        JDA WetterBot = JDABuilder.createDefault(Secret.WetterBotToken)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)

                //set Activity
                .setActivity(Activity.watching("dich an L O L"))
                .addEventListeners(new Listener())
                .build().awaitReady();
    }
}
