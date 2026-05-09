package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.QuestLog;
import java.util.List;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMember;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestPriority;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0;
        int messagesRouted = 0;
        int membersNotified = 0;

        System.out.println("\n=== CouncilEngine: Party Review ===");
        for (Hero hero : party) {
            System.out.println(hero);
        }

        GuildMember engineSpeaker = new GuildMember("CouncilEngine", hall) {
            @Override
            public void receive(String topic, GuildMember from, String payload) {
                System.out.println("[CouncilEngine] received " + topic + ": " + payload);
            }
        };

        System.out.println("\n=== Iterator 1: Ordered Quest Review ===");
        QuestIterator ordered = questLog.ordered();

        while (ordered.hasNext()) {
            Quest quest = ordered.next();
            questsTraversed++;

            System.out.println("Planning quest: " + quest);

            hall.dispatch("orders", engineSpeaker,
                    "Prepare plan for quest: " + quest.getTitle());
            messagesRouted++;
            membersNotified += countLastDispatch(hall);

            if (quest.isUrgent()) {
                hall.dispatch("urgent", engineSpeaker,
                        "Urgent quest needs immediate attention: " + quest.getTitle());
                messagesRouted++;
                membersNotified += countLastDispatch(hall);
            }
        }

        System.out.println("\n=== Iterator 2: High Priority Quest Review ===");
        QuestIterator highPriority = questLog.priorityAtLeast(QuestPriority.HIGH);

        while (highPriority.hasNext()) {
            Quest quest = highPriority.next();
            questsTraversed++;

            System.out.println("High priority focus: " + quest);

            hall.dispatch("supplies", engineSpeaker,
                    "Prepare extra resources for: " + quest.getTitle());
            messagesRouted++;
            membersNotified += countLastDispatch(hall);

            hall.dispatch("scouting", engineSpeaker,
                    "Scout the area before starting: " + quest.getTitle());
            messagesRouted++;
            membersNotified += countLastDispatch(hall);

            if (quest.getTitle().toLowerCase().contains("curse")) {
                hall.dispatch("curse", engineSpeaker,
                        "Need lore analysis for cursed quest: " + quest.getTitle());
                messagesRouted++;
                membersNotified += countLastDispatch(hall);
            }
        }

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }

    private int countLastDispatch(GuildMediator hall) {
        if (hall instanceof GuildHall) {
            return ((GuildHall) hall).getLastDispatchCount();
        }
        return 0;
    }
}