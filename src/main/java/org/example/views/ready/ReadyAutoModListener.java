package org.example.views.ready;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.dao.AutoModDAO;
import org.example.models.entities.AutoMod;
import org.example.services.moderacao.SpamAlertaService;


public class ReadyAutoModListener extends ListenerAdapter {

    private final AutoModDAO autoModDAO;
    private final SpamAlertaService spamAlertaService;


    public ReadyAutoModListener(AutoModDAO autoModDAO) {
        this.autoModDAO = autoModDAO;
        this.spamAlertaService = new SpamAlertaService();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getGuild() == null) return;

        long guildId = event.getGuild().getIdLong();
        AutoMod autoMod = autoModDAO.obterAutoMod(guildId);

        if (autoMod != null && autoMod.getSpam_mod()) {
            spamAlertaService.processarSpam(event);
        }

    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        long guildId = event.getGuild().getIdLong();
        AutoMod autoMod = autoModDAO.obterAutoMod(guildId);

        if(autoMod != null && autoMod.getWelcome_auto_role()){
            long roleId = autoModDAO.searchWelcomeRole(guildId);
            if(roleId != 0){
                Guild guild = event.getGuild();
                Member member = event.getMember();
                Role role = guild.getRoleById(roleId);
                if(role != null){
                    guild.addRoleToMember(member, role).queue();
                }
            }
        }
    }
}
