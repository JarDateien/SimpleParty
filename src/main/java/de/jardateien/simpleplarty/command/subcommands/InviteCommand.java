package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.language.LanguageManager;
import de.jardateien.simpleplarty.party.Party;
import de.jardateien.simpleplarty.party.PartyManager;
import de.jardateien.simpleplarty.utils.Component;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InviteCommand extends SubCommand {

    private final LanguageManager manager;

    public InviteCommand(PartyManager partyManager) {
        super(partyManager);
        this.manager = partyManager.getManager();
    }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {

        if(args.length <= 1) {
            player.sendMessage(Component.PARTY, Component.text(this.manager.get(player, "party_invite_use")));
            return;
        }

        var party = this.partyManager.getParty(player);
        if(party == null) {
            party = new Party(player);
            this.partyManager.setParty(player, party);
        }

        if(!party.isLeader(player)) {
            player.sendMessage(Component.PARTY, Component.text(this.manager.get(player, "party_isnt_leader")));
            return;
        }

        var request = ProxyServer.getInstance().getPlayer(args[1]);
        if(request == null) {
            player.sendMessage(Component.PARTY, Component.text(this.manager.get(player, "party_isnt_online", args[1])));
            return;
        }

        if(request == player) {
            player.sendMessage(Component.PARTY, Component.text("§cDu kannst dich nicht selber in deine Party einladen!"));
            return;
        }

        var requestParty = this.partyManager.getParty(request);
        if(requestParty != null) {
            player.sendMessage(Component.PARTY, Component.text("§7Der Spieler ist bereits in einer Party"));
            return;
        }

        if(party.hasRequest(request)) {
            player.sendMessage(Component.PARTY, Component.text("§7Dieser Spieler wurde bereits in deine Party eingeladen"));
            return;
        }

        party.sendMembers("§a" + request.getName() + " §7wurde in die Party eingeladen!");
        request.sendMessage(Component.PARTY, Component.text("§a" + player.getName() + " §7hat dich in seiner Party eingeladen"));

        var accept = Component.event("§a[ANNEHMEN]").setCommand("/party accept " + player.getName()).showText("§a/party accept " + player.getName());
        var deny = Component.event("§c[ABLEHNEN]").setCommand("/party deny " + player.getName()).showText("§c/party deny " + player.getName());

        player.sendMessage(Component.text("§7[§5Party§7] §7Klicke hier zum:"), Component.SPACE, accept.toText(), Component.SPACE, deny.toText());
        party.addRequests(request);
    }

}
