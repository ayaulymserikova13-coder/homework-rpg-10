package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===");

        List<Hero> party = List.of(
                new Hero("Arman", 45, 20, 12, 6, 100),
                new Hero("Mira", 35, 30, 16, 4, 80)
        );

        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Escort the merchant caravan", QuestPriority.NORMAL, 120, false));
        questLog.add(new Quest("Clear the goblin cave", QuestPriority.HIGH, 250, false));
        questLog.add(new Quest("Rescue villagers from the burning bridge", QuestPriority.URGENT, 400, true));
        questLog.add(new Quest("Investigate the cursed ruins", QuestPriority.HIGH, 350, true));
        questLog.add(new Quest("Deliver herbs to the healer", QuestPriority.LOW, 60, false));

        GuildHall hall = new GuildHall();

        Quartermaster quartermaster = new Quartermaster("Doran", hall);
        Scout scout = new Scout("Lina", hall);
        Healer healer = new Healer("Selene", hall);
        Captain captain = new Captain("Ragnar", hall);
        Loremaster loremaster = new Loremaster("Eldric", hall);

        System.out.println("\n=== Manual Mediator Demo ===");
        captain.issueOrder("orders", "All officers prepare for the next campaign.");
        scout.reportRoute("scouting", "Northern road is safe, but the forest has monster tracks.");
        quartermaster.requestSupplies("supplies", "We need arrows, rope, torches, and healing kits.");
        healer.prepareAid("healing", "I will prepare potions for wounded heroes.");
        loremaster.explainHistory("history", "The cursed ruins were sealed by ancient mages.");

        System.out.println("\n=== Manual Iterator Demo: Reverse Order ===");
        printQuests(questLog.reverse());

        System.out.println("\n=== Open/Closed Iterator Demo: Reward Sorted ===");
        printQuests(questLog.rewardSorted());

        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        System.out.println("\n=== Final Council Result ===");
        System.out.println(result);
    }

    private static void printQuests(QuestIterator iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}