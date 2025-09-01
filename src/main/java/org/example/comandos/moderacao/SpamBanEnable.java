package org.example.comandos.moderacao;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.AutoModDAO;
import org.example.models.Comando;

import org.example.models.entities.AutoMod;
import org.example.util.exceptions.NoGuildException;

public class SpamBanEnable implements Comando {

    private final AutoModDAO dao = new AutoModDAO();

    @Override
    public String getNomeComando() {
        return "antispam";
    }

    @Override
    public String getDescricao() {
        return "Bane usuários automaticamente ao detectar spam";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {

        try {

            Member member = event.getMember();

            if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)){
                event.getChannel().sendMessage("Apenas um administrador pode executar esse comando " +
                        "<a:catocolorido:1388193166292942968>").queue();
                return;
            }
                AutoMod autoMod = dao.obterAutoMod(event.getGuild().getIdLong());

                if (autoMod == null) {
                    autoMod = new AutoMod(event.getGuild().getIdLong(), true, false);
                }

                if (!autoMod.getSpam_mod()) {
                    autoMod.setSpam_mod(true);
                } else {
                    autoMod.setSpam_mod(false);
                }

                dao.salvarAutoMod(autoMod);

                String status = autoMod.getSpam_mod() ? "Ativado  <a:check:1389613917956870164>" : "Desativado  <a:check:1389613917956870164>";
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Anti-Spam");
                eb.setDescription(status);
                event.getChannel().sendMessageEmbeds(eb.build()).queue();

        } catch (NoGuildException e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {

        try {

            if (event.getGuild() == null) {
                throw new NoGuildException(event.getUser().getAsMention());
            }

            Member member = event.getMember();

            if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)){
                event.getChannel().sendMessage("Apenas um administrador pode executar esse comando " +
                        "<a:catocolorido:1388193166292942968>").queue();
                return;
            }
                AutoMod autoMod = dao.obterAutoMod(event.getGuild().getIdLong());

                if (autoMod == null) {
                    autoMod = new AutoMod(event.getGuild().getIdLong(), true, false);
                }

                if (!autoMod.getSpam_mod()) {
                    autoMod.setSpam_mod(true);
                } else {
                    autoMod.setSpam_mod(false);
                }

                dao.salvarAutoMod(autoMod);

                String status = autoMod.getSpam_mod() ? "Ativado  <a:check:1389613917956870164>" : "Desativado  <a:check:1389613917956870164>";
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Anti-Spam");
                eb.setDescription(status);
                event.replyEmbeds(eb.build()).queue();

        } catch (NoGuildException e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}

