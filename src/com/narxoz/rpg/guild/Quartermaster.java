package com.narxoz.rpg.guild;


public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String topic, String payload) {
        System.out.println("[Quartermaster " + getName() + "] requests supplies: " + payload);
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Quartermaster " + getName() + "] received " + topic
                + " from " + from.getName() + ": " + payload
                + " -> preparing gear and rewards.");
    }
}
