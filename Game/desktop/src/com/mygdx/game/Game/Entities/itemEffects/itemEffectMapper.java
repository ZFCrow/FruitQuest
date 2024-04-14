package com.mygdx.game.Game.Entities.itemEffects;

import com.mygdx.game.Game.Entities.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class itemEffectMapper {
    private static final Map<String, Consumer<Player>> effectsMap = new HashMap<>(); // consumer is used as if i were to use a player object, this mapping would be unique to each player object instead of general players object

    static{
        effectsMap.put("icecream", player -> {
            player.decreaseLives();
            player.decreaseSpeed();
            player.decreaseJumpHeight();
            player.increaseScore(2);
        });
        effectsMap.put("watermelon", player -> {
            player.increaseSpeed();
            player.increaseLives();
            player.increaseJumpHeight();
            player.increaseScore();
        });
        effectsMap.put("burger", player -> {
            player.decreaseScore();
            player.decreaseLives();
            player.decreaseSpeed();
        });
        effectsMap.put("banana", player -> {
            player.increaseScore(5);
            player.increaseLives();
            player.increaseSpeed();
            player.increaseJumpHeight();
        });
        effectsMap.put("bacon", player -> {
            player.decreaseScore(2);
            player.decreaseLives();
            player.increaseSpeed();
            player.decreaseJumpHeight();
        });
        effectsMap.put("bread", player -> {
            player.increaseScore(3);
            player.increaseLives();
            player.increaseSpeed();
            player.increaseJumpHeight();
        });
        effectsMap.put("default", player -> {
            player.decreaseScore();
        });
    }

    public static void applyEffect(String itemType, Player player){
        String itemTypeLowerCase = itemType.toLowerCase();
        if (effectsMap.containsKey(itemTypeLowerCase)){
            effectsMap.get(itemTypeLowerCase).accept(player);
        }else {
            System.out.println("No effect found for item type: " + itemType);
            effectsMap.get("default").accept(player); // if no effect is found, apply the default effect
        }
    }





}
