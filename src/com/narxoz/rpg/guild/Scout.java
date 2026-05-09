package com.narxoz.rpg.guild;

public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        System.out.println("[Scout " + getName() + "] reports route: " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Scout " + getName() + "] received " + topic
                + " from " + from.getName() + ": " + payload
                + " -> checking roads and enemy movement.");
    }
}
