package de.jardateien.simpleplarty.command.subcommands;

import de.jardateien.simpleplarty.command.manager.SubCommand;
import de.jardateien.simpleplarty.party.PartyManager;
import de.jardateien.simpleplarty.utils.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class HelpCommand extends SubCommand {
    public HelpCommand(PartyManager partyManager) { super(partyManager); }

    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        player.sendMessage(Component.PARTY, Component.text("§6Partyverwaltung §8(§7" + (args.length + (args.length == 0 ? +1 : +0)) +" §8/ §7" + 3 + "§8)"));
        if(args.length <= 1 || (args.length >= 2 && args[1].equalsIgnoreCase("1"))) {
            player.sendMessage(Component.text("§e /party invite <Spieler> §8| §7Lade ein Spieler in die Party ein"));
            player.sendMessage(Component.text("§e /party accept <Spieler> §8| §7Nimm eine Party-Anfrage an"));
            player.sendMessage(Component.text("§e /party deny <Spieler> §8| §7Lehn eine Party-Anfrage ab"));
            player.sendMessage(Component.text("§e /party list §8| §7Liste alle Party Mitglieder auf"));
            player.sendMessage(Component.text("§e /party leave §8| §7Verlasse eine Party"));
        } else if (args.length > 1) {
            var arg = args[1].toLowerCase();
            switch (arg) {
                case "2":
                    player.sendMessage(Component.text("§e /party jump §8| §7Spring auf den Server des Leiters!"));
                    player.sendMessage(Component.text("§e /party promode <Spieler> §8| §7Befördere ein Spieler aus der Party"));
                    player.sendMessage(Component.text("§e /party demote <Spieler> §8| §7Degradiere ein Spieler aus der Party"));
                    player.sendMessage(Component.text("§e /party kick <Spieler> §8| §7Kickt ein Spieler aus der Party"));
                    player.sendMessage(Component.text("§e /party join <Spieler> §8| §7Kickt ein Spieler aus der Party"));
                    break;
                case "3":
                    player.sendMessage(Component.text("§e /party toggle <toggle> §8| §7Spring auf den Server des Leiters!"));
                    player.sendMessage(Component.text("§e /party pull §8| §7Spring auf den Server des Leiters!"));
                    player.sendMessage(Component.text("§e /party help §8| §7Spring auf den Server des Leiters!"));
                    player.sendMessage(Component.text("§e /party chat §8| §7Spring auf den Server des Leiters!"));
                    break;
                default:

            }
        }








    }
}
