package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();
    private int lastDispatchCount;

    @Override
    public void register(GuildMember member) {
        addSubscriber("urgent", member);

        if (member instanceof Captain) {
            addSubscriber("orders", member);
            addSubscriber("rewards", member);
        } else if (member instanceof Quartermaster) {
            addSubscriber("supplies", member);
            addSubscriber("rewards", member);
        } else if (member instanceof Scout) {
            addSubscriber("scouting", member);
            addSubscriber("orders", member);
        } else if (member instanceof Healer) {
            addSubscriber("healing", member);
            addSubscriber("supplies", member);
        } else if (member instanceof Loremaster) {
            addSubscriber("lore", member);
            addSubscriber("curse", member);
            addSubscriber("history", member);
        }
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        lastDispatchCount = 0;
        System.out.println("\n[GuildHall] Topic: " + topic + " | From: " + from.getName());

        for (GuildMember member : subscribersFor(topic)) {
            if (member != from) {
                member.receive(topic, from, payload);
                lastDispatchCount++;
            }
        }
    }

    public int getLastDispatchCount() {
        return lastDispatchCount;
    }


    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}
