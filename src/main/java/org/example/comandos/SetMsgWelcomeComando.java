package org.example.comandos;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.example.dao.GuildJoinMessageDAO;
import org.example.models.Comando;
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

        if (event.getGuild() != null){
        long guildId = event.getGuild().getIdLong();
        //Verificando se o usuario é Administrador ou Mod
        Member member = event.getMember();

        if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)){
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
        //Tratamento de nullpointException :)
        OptionMapping mensagemOpt = event.getOption("user");
        OptionMapping imagemOpt = event.getOption("imagem");

        String mensagem = (mensagemOpt != null) ? mensagemOpt.getAsString() : null;// recebe a mensagem do usuario
        String imagemUrl = (imagemOpt != null) ? imagemOpt.getAsString() : null;
        dao.salvarMensagem(guildId, mensagem, imagemUrl); //salva no banco

        event.reply("Mensagem atualizada com sucesso! <a:feliz:1388200507771977832>").setEphemeral(true).queue();
    }else{
            event.reply("Este comando só pode ser usado em um servidor.").setEphemeral(true).queue();
        }
    }
}
