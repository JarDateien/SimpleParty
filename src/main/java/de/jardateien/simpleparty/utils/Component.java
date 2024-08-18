package de.jardateien.simpleparty.utils;


import net.md_5.bungee.api.chat.*;

public class Component {

    public static final TextComponent PARTY = new TextComponent("§7[§5Party§7] ");
    public static final TextComponent SPACE = new TextComponent(" ");

    public static TextComponent text(String msg) { return new TextComponent(MultiColors.translate(msg)); }
    public static Component event(String msg) { return new Component(MultiColors.translate(msg)); }


    private TextComponent textComponent;

    private Component(String text) {
        this.textComponent = new TextComponent(MultiColors.translate(text));
    }

    public Component setCommand(String command) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        return this;
    }

    public Component showText(String text) {
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MultiColors.translate(text)).create()));
        return this;
    }

    public TextComponent toText() {
        return this.textComponent;
    }

}
