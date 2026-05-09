package com.narxoz.rpg.guild;

public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        System.out.println("[Healer " + getName() + "] prepares aid: " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Healer " + getName() + "] received " + topic
                + " from " + from.getName() + ": " + payload
                + " -> preparing potions and recovery plan.");
    }
}
