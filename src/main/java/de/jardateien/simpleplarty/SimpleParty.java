package de.jardateien.simpleplarty;

import de.jardateien.simpleplarty.command.PartyCommand;
import de.jardateien.simpleplarty.listener.LanguageListener;
import de.jardateien.simpleplarty.listener.PartyListener;
import de.jardateien.simpleplarty.utils.ColorUtil;
import de.jardateien.simpleplarty.utils.Component;
import de.jardateien.simpleplarty.utils.ControllManager;
import de.jardateien.simpleplarty.utils.UpdateChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class SimpleParty extends Plugin {

    private ControllManager controls;

    @Override
    public void onEnable() {
        var checker = new UpdateChecker(this, "100-sale-⏳-1-8-1-19-4-⭐-simpleparty-⚡-custom-unlimited-languages-✨", 109313);

        checker.getVersion(version -> {
            var logger = this.getLogger();

            if (this.getDescription().getVersion().equals(version)) {
                logger.info("There is not a new update available.");
                return;
            }

            logger.info("There is a new update available.");
            logger.info(checker.getURL());
            checker.update();
        });

        this.controls = new ControllManager(this);

        var proxy = this.getProxy();
        this.showDisplay(proxy);

        var pluginManger = proxy.getPluginManager();
        pluginManger.registerCommand(this, new PartyCommand(this.controls));
        pluginManger.registerListener(this, new PartyListener(this.controls));
        pluginManger.registerListener(this, new LanguageListener(this.controls));
    }

    @Override
    public void onDisable() {
        this.controls.getLanguageManager().save();
    }

    private void showDisplay(ProxyServer proxy) {
        var console = proxy.getConsole();
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
}
