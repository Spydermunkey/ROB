import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

class Main {
    public static void main(String[] ARGS) {
        ConfigHandler CONFIG_HANDLER = new ConfigHandler();
        UserHandler USER_HANDLER = new UserHandler();

        try {
            JDA API = new JDABuilder(AccountType.BOT).setToken(CONFIG_HANDLER.getToken()).buildBlocking();
            USER_HANDLER.setMemberList(API.getGuildById(CONFIG_HANDLER.getServerID()).getMembers());

            API.addEventListener(new CommandHandler());
            API.addEventListener(new UserHandler());

            USER_HANDLER.init();

            API.getPresence().setGame(Game.of(Game.GameType.DEFAULT, "I'm on a Bot!"));
        } catch (LoginException | InterruptedException EXCEPTION) {
            EXCEPTION.printStackTrace();
        }
    }
}
