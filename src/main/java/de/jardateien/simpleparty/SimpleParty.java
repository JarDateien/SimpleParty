package de.jardateien.simpleparty;

import de.jardateien.simpleparty.commands.PartyCommand;
import de.jardateien.simpleparty.listener.PartyListener;
import de.jardateien.simpleparty.party.PartyManager;
import de.jardateien.simpleparty.utils.Component;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public final class SimpleParty extends Plugin {

    @Getter
    private static SimpleParty instance;
    private PartyManager partyManager;

    @Override
    public void onEnable() {
        instance = this;

        this.partyManager = new PartyManager();

        var manager = this.getProxy().getPluginManager();
        manager.registerListener(this, new PartyListener());
        manager.registerCommand(this, new PartyCommand().registerSubCommands());

        this.showDisplay();
    }

    private void showDisplay() {
        var console = this.getProxy().getConsole();
        console.sendMessage(Component.text("§b  ____ §3 _ §9          §1     §5 _ §d     §4____  §c     §6    §e _  §f       "));
        console.sendMessage(Component.text("§b / ___)§3(_)§9          §1     §5( )§d    §4(  _ \\ §c     §6    §e( ) §f       "));
        console.sendMessage(Component.text("§b| (___ §3 _ §9_ __ ___  §1_ __ §5| |§d ___§4| |_) )§c__ _ §6_ __§e| |_§f _   _ "));
        console.sendMessage(Component.text("§b \\___ \\§3| §9( '_ ` _ `§1( '_ \\§5| |§d/ _ )§4  __/§c/ _` |§6 '__§e) __§f( ) ( )"));
        console.sendMessage(Component.text("§b ____) §3| |§9 ( ) ( ) §1| |_) §5| |§d  __§4| |  §c| (_| |§6 |  §e| |_§f| |_| |"));
        console.sendMessage(Component.text("§b(_____/§3(_|§9_) (_) (_§1| .__/§5(_)§d\\___§4(_)   §c\\__,_(§6_)  §e \\__)§f\\__, |"));
        console.sendMessage(Component.text("§b       §3   §9         §1| |   §5   §d    §4      §c      §6    §e     §f __/ |"));
        console.sendMessage(Component.text("§b       §3   §9         §1(_)   §5   §d    §4      §c      §6    §e     §f(___/`"));
        console.sendMessage(Component.SPACE);
    }

    //Config
    //
    //Blacklist Eine liste die die Party nicht mit gezogen wird und eine wo sie nicht rausgezogen wird
    //Mit gezogen: Lobby/Hub/Comunity...
    //Rausgezogen: Bedwars/SkyWars...

    //Wie viele in eine Party dürfen: (0 ist für kein Limit)
    //Limit: 5
    //


    //MySQL Connection
    //Adresse:
    //Password:
    //Username:
    //Database:


}
