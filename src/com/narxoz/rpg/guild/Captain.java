package com.narxoz.rpg.guild;

public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String topic, String payload) {
        System.out.println("[Captain " + getName() + "] issues order: " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Captain " + getName() + "] received " + topic
                + " from " + from.getName() + ": " + payload
                + " -> adjusting battle plan.");
    }
}
