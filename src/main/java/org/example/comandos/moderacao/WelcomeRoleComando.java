package org.example.comandos.moderacao;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.example.dao.AutoModDAO;
import org.example.models.Comando;
import org.example.models.entities.AutoMod;

public class WelcomeRoleComando implements Comando {
    private final AutoModDAO autoModDAO;

    public WelcomeRoleComando(AutoModDAO autoModDAO) {
        this.autoModDAO = autoModDAO;
    }

    @Override
    public String getNomeComando() {
        return "autorole";
    }

    @Override
    public String getDescricao() {
        return "Ativa/Desativa o cargo automático que os usuários irão receber ao entrar no servidor";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Esse comando está disponível como Slash -> /");
        eb.setDescription("Use /autorole");
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {

        event.deferReply().queue();

        OptionMapping opt = event.getOption("cargo");

        if (opt != null && opt.getType() == OptionType.ROLE) {
            try {
                Role selectedRole = opt.getAsRole();
                long roleId = selectedRole.getIdLong();
                long guildId = event.getGuild().getIdLong();

                AutoMod autoModExistente = autoModDAO.obterAutoMod(guildId);
                boolean isAutoModEnabled = false;

                if (autoModExistente != null) {
                    isAutoModEnabled = !autoModExistente.getWelcome_auto_role();
                    autoModExistente.setWelcome_auto_role(isAutoModEnabled);
                    autoModDAO.salvarAutoMod(autoModExistente);
                } else {
                    isAutoModEnabled = true;
                    AutoMod autoMod = new AutoMod(guildId, false, isAutoModEnabled);
                    autoModDAO.salvarAutoMod(autoMod);
                }

                autoModDAO.salvarWelcomeRole(guildId, roleId);

                if (isAutoModEnabled) {
                    event.getHook().editOriginal("<a:check:1389613917956870164> Cargo Automático de entrada definido para <@&" + roleId + ">").queue();
                } else {
                    event.getHook().editOriginal("<a:check:1389613917956870164> Cargo Automático desativado").queue();
                }

            } catch (IllegalStateException e) {
                event.getHook().editOriginal("<:astra_triste:1390823447227142204> Ops, parece que ocorreu um erro ao escolher seu cargo. O cargo pode não existir mais.").queue();
            }
        } else {
            event.getHook().editOriginal("<:astra_triste:1390823447227142204> Ops, parece que ocorreu um erro ao escolher seu cargo.").queue();
        }
    }
}