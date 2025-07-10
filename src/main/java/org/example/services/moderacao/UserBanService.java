package org.example.services.moderacao;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.util.exceptions.inGuildException;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class UserBanService {

    public void banUser(MessageReceivedEvent event, Guild guild, User user, int delDays, String motivo) {
        // CORRIGIR ESSA EXCEPTION PQ TA DISPARANDO MESMO COM O USER NO SERVIDOR
       /* try{
            Member member = guild.getMember(user);

            if (member == null) {

                throw new inGuildException(user.getAsMention());
            }*/


            guild.ban(user, delDays, TimeUnit.DAYS).reason(motivo).queue(
                sucess -> event.getChannel().sendMessage("Usuário " +user.getAsMention()+ " foi banido!").queue(),
                error -> event.getChannel().sendMessage("Ops, ocorreu um erro ao banir o usuário " + user.getAsMention()).queue()
        );

       /* }catch (inGuildException e){
            event.getChannel().sendMessage(e.getMessage()).queue();
        }catch (Exception e){
            event.getChannel().sendMessage("Ops ocorreu um erro inesperado <:astra_assustada:1390381186416906303>").queue();
        } */

    }
}
