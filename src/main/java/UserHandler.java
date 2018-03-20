import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;

class UserHandler extends ListenerAdapter {
    ConfigHandler CONFIG_HANDLER = new ConfigHandler();
    public static List<Member> MEMBERS;

    @Override
    public void onGuildMemberNickChange(GuildMemberNickChangeEvent EVENT) {
        if (EVENT.getNewNick().matches("\\w+#\\d+"))
            setNickname(EVENT.getMember());
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent EVENT) {
        MEMBERS = EVENT.getGuild().getMembers();
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent EVENT) {
        MEMBERS = EVENT.getGuild().getMembers();
    }


    public void init() {
        refreshNicknames();
        timer();
    }

    private void refreshNicknames() {
        for (Member MEMBER : MEMBERS) {
            if (!MEMBER.getUser().isBot() && !MEMBER.isOwner() && !(MEMBER.getNickname() == null))
                setNickname(MEMBER);
        }
    }

    private String query(String NICKNAME) {
        String SKILL_RATING = "";

        try {
            SKILL_RATING = Jsoup.connect("https://www.overbuff.com/players/pc/" + NICKNAME.replace('#', '-')).get().select(".player-skill-rating").text();
        } catch (IOException EXCEPTION) {
            EXCEPTION.printStackTrace();
        }

        return SKILL_RATING;
    }

    private void timer() {
        try {
            Thread.sleep(300 * 1000);
            refreshNicknames();
            timer();
        } catch(InterruptedException EXCEPTION) {
            EXCEPTION.printStackTrace();
        }
    }


    public void setMemberList(List<Member> sMEMBERS) {
        MEMBERS = sMEMBERS; //test23
    }

    private void setNickname(Member MEMBER) {
        if (MEMBER.getNickname().matches("\\w+#\\d+"))
            MEMBER.getGuild().getController().setNickname(MEMBER, "[" + query(MEMBER.getNickname()) + "] " + MEMBER.getNickname()).queue();
        else if (MEMBER.getNickname().matches("\\[\\d+\\] \\w+#\\d+")) {
            String[] MEMBER_NICKNAME = MEMBER.getNickname().split(" ");

            if (query(MEMBER_NICKNAME[1]).equals(MEMBER_NICKNAME[0].substring(1, MEMBER_NICKNAME[0].length() - 1)))
                return;

            MEMBER.getGuild().getController().setNickname(MEMBER, "[" + query(MEMBER_NICKNAME[1]) + "] " + MEMBER_NICKNAME[1]).queue();
        }
    }

    public void addToMembers() {

    }

    public void removeFromMembers() {

    }
}
