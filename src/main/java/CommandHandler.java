import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

class CommandHandler extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent EVENT) {
        ConfigHandler CONFIG_HANDLER = new ConfigHandler();

        if (EVENT.getAuthor().isBot() || EVENT.getChannel().getId().equals(EVENT.getGuild().getId()))
            return;

        Message MSG_OBJ = EVENT.getMessage();
        MessageChannel MSG_CHANNEL = EVENT.getChannel();
        Guild SERVER = EVENT.getGuild();
        User AUTHOR = MSG_OBJ.getAuthor();
        String MSG_RAW = MSG_OBJ.getContentRaw();
        String[] CMD;

        if (MSG_RAW.startsWith(CONFIG_HANDLER.getPrefix())) {
            CMD = MSG_RAW.substring(1).split(" ");

            switch (CMD[0]) {
                case "sban":
                    break;
                case "tban":
                    break;
                case "ban":
                    break;
                case "kick":
                    break;
                case "mute":
                    break;
                case "squelch":
                    break;
                case "warn":
                    break;
                case "help":
                    break;
                case "mains":
                    break;
                default:
            }
        }
    }
}
