package org.example.views;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.dao.GuildDAO;
import org.example.models.entities.GuildModel;
import org.example.services.registro.SlashCommandRegistryService;


public class ReadyListener extends ListenerAdapter {

    private final GuildDAO guildDAO = new GuildDAO();
    @Override
    public void onReady(ReadyEvent event) {


        for (Guild guild : event.getJDA().getGuilds()) {
            GuildModel guildModel = new GuildModel(guild.getIdLong(), guild.getName());
            guildDAO.salvarGuild(guildModel);
        } //atualiza os servidores em que Astra entrou

        System.out.println("Servidores salvos no banco.");
        event.getJDA().getPresence().setActivity(Activity.playing("*help \uD83E\uDD16"));
        System.out.println("Bot está pronto e com presença atualizada!");
       /* Guild guild = event.getJDA().getGuildById("1113547243593678979"); // seu servidor de testes

        if (guild != null) {*/

        // Carrega ou atualiza os comandos slash
        event.getJDA().updateCommands().addCommands(SlashCommandRegistryService.getComandos()).queue();

    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) { // Salva o servidor no banco sempre que astra entrar
        Guild guild = event.getGuild();
        guildDAO.salvarGuild(new GuildModel(guild.getIdLong(), guild.getName()));
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild guild = event.getGuild();
        guildDAO.deletarGuild(new GuildModel(guild.getIdLong(), guild.getName()).getId());
        System.out.println("Fui removida do servidor: " + guild.getName());

    }
}
