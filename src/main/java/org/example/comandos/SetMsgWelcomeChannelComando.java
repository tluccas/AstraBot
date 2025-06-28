package org.example.comandos;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.GuildDAO;
import org.example.dao.GuildJoinMessageDAO;
import org.example.models.Comando;
import org.example.models.ComandoSlash;

public class SetMsgWelcomeChannelComando implements Comando {

    private final GuildJoinMessageDAO dao;

    public SetMsgWelcomeChannelComando(GuildJoinMessageDAO dao) {
        this.dao = dao;
    }



    @Override
    public String getNomeComando() {
        return "setcanalboasvindas";
    }

    @Override
    public String getDescricao() {
        return "Define o canal onde as mensagens de boas vindas serão enviadas";
    }
    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage("Esse comando só pode ser executado por slash ´ / ´").queue();

    }
    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {

        var canal = event.getOption("canal").getAsChannel();
        long guildID = event.getGuild().getIdLong();
        long canalId = canal.getIdLong();
        //Verificando se o usuario é Administrador ou Mod
        Member member = event.getMember();

        if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)){
            event.reply("Apenas um administrador pode executar esse comando " +
                    "<a:catocolorido:1388193166292942968>").setEphemeral(true).queue();
            return;
        }

        dao.salvarCanal(guildID, canalId);

        event.reply("Canal de boas-vindas definido para: " + canal.getName()).queue();

    }
}
