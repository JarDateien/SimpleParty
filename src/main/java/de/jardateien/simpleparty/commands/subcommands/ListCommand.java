package de.jardateien.simpleparty.commands.subcommands;

import de.jardateien.simpleparty.commands.manager.SubCommand;
import de.jardateien.simpleparty.utils.Component;
import de.jardateien.simpleparty.SimpleParty;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ListCommand extends SubCommand {

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        var party = SimpleParty.getInstance().getPartyManager().getParty(player);
        if(party == null) {
            player.sendMessage(Component.PARTY, Component.text(""));
            return;
        }

        var leader = party.getLeader();
        player.sendMessage(Component.PARTY, Component.text("§eMitglieder der Party von §a" + leader.getName() + " §7(" + party.getSize() + ")"));
        player.sendMessage(Component.text("§8- §a" + leader.getName() + " §4(Leader) " + this.isOnline(leader.getName())));
        party.getModerators().forEach(mods -> player.sendMessage(Component.text("§8- §a" + mods.getName() + " §c(Mod) " + this.isOnline(mods.getName()))));

        for (ProxiedPlayer member : party.getMembers()) {
            if(party.isLeader(member)) continue;
            if(party.isModerator(member)) continue;
            if(party.isBanned(member)) continue;
            player.sendMessage(Component.text("§8- §a" + member.getName() + " " + this.isOnline(member.getName())));
        }

        if(!party.isLeader(player) && !party.isModerator(player)) return;
        party.getBanned().forEach(member -> player.sendMessage(Component.text("§8- §a" + member.getName() + " §4(Banned)")));
    }

    private String isOnline(String name) {
        var player = ProxyServer.getInstance().getPlayer(name);
        return  player != null ? "§a➊ §8| §7" + player.getServer().getInfo().getName() : "§c➊ §8| §7Offline";
    }
}
