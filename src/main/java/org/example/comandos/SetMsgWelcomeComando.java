package org.example.comandos;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.GuildJoinMessageDAO;
import org.example.models.Comando;
import org.example.models.ComandoSlash;
import org.example.models.entities.GuildJoinMessage;

public class SetMsgWelcomeComando implements Comando {
    private final GuildJoinMessageDAO dao;

    public SetMsgWelcomeComando(GuildJoinMessageDAO dao) {
        this.dao = dao;
    }

    @Override
    public String getNomeComando() {
        return "setboasvindas";
    }

    @Override
    public String getDescricao() {
        return "Define sua mensagem de boas vindas personalizada (use {user} para mensionar usuarios)" +
                " adicione uma imagem ou gif (opcional)";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage("Esse comando só pode ser executado por slash ´ / ´").queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        long guildId = event.getGuild().getIdLong();
        if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            event.reply("Apenas um administrador pode executar esse comando " +
                    "<a:catocolorido:1388193166292942968>").setEphemeral(true).queue();
            return;
        }

        GuildJoinMessage config = dao.obterPorGuildId(guildId);
        if (config == null || config.getCanalId() == 0L) {
            event.reply("<a:catocolorido:1388193166292942968> OPS, o canal de mensagem ainda não foi definido, defina em " +
                    "/setcanalboasvindas").setEphemeral(true).queue();
            return;
        }

        String mensagem = event.getOption("mensagem").getAsString(); // recebe a mensagem do usuario
        String imagemUrl = event.getOption("imagem") != null ? event.getOption("imagem").getAsString() : null;
        dao.salvarMensagem(guildId, mensagem, imagemUrl); //salva no banco

        event.reply("Mensagem atualizada com sucesso! <a:feliz:1388200507771977832>").setEphemeral(true).queue();
    }
}
