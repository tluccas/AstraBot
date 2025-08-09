package org.example.comandos;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.example.models.Comando;

public class LimparComando implements Comando {
    @Override
    public String getNomeComando() {
        return "limpar";
    }

    @Override
    public String getDescricao() {
        return "Limpa uma quantidade escolhida de mensagens (máx 100 mensagens)";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
        TextChannel canal = event.getChannel().asTextChannel();
        int quantidade = Integer.parseInt(args[0]);
        Member member = event.getMember();

        if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)){
            event.getChannel().sendMessage("Apenas um administrador pode executar esse comando " +
                    "<a:catocolorido:1388193166292942968>").queue();
            return;
        }

        if (quantidade > 1 && quantidade <= 100) {
        canal.getHistory().retrievePast(quantidade).queue(messages ->
                canal.deleteMessages(messages).queue(
                        sucess -> event.getChannel().sendMessage("<a:ClubPenguinDance:1388352786303025283> Mensagens deletadas!").queue(),
                        error -> event.getChannel().sendMessage("⚠️ Erro ao deletar mensagens: " + error.getMessage()).queue()
                )
        );
        } else{
            event.getChannel().sendMessage("OPS, envie uma quantidade válida (quantidade min = 2 e máx = 100) <a:cat:1388352178510630952>").queue();
        }
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        TextChannel canal = event.getChannel().asTextChannel();
        OptionMapping option = event.getOption("quantidade");
        if (option == null) {
            event.reply("Você precisa informar a quantidade!").setEphemeral(true).queue();
            return;
        }
        int quantidade = option.getAsInt();
        //Verificando se o usuario é administrador ou mod
        Member member = event.getMember();

        if (member == null || !member.hasPermission(Permission.ADMINISTRATOR)){
            event.reply("Apenas um administrador pode executar esse comando " +
                    "<a:catocolorido:1388193166292942968>").setEphemeral(true).queue();
            return;
        }
        if (quantidade > 1 && quantidade <= 100) {
            canal.getHistory().retrievePast(quantidade).queue(messages ->
                    canal.deleteMessages(messages).queue(
                            sucess -> event.reply("<a:ClubPenguinDance:1388352786303025283> Mensagens deletadas!").queue(),
                            error -> event.reply("⚠️ Erro ao deletar mensagens: " + error.getMessage()).queue()
                    )
            );
        } else{
            event.reply("OPS, envie uma quantidade válida (quantidade min = 2 e máx = 100) <a:cat:1388352178510630952>").queue();
        }


    }
}
