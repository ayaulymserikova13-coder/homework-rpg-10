package com.narxoz.rpg.guild;

public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void explainHistory(String topic, String payload) {
        System.out.println("[Loremaster " + getName() + "] shares knowledge: " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Loremaster " + getName() + "] received " + topic
                + " from " + from.getName() + ": " + payload
                + " -> checking old records and cursed legends.");
    }
}