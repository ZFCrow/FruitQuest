package com.mygdx.game.Game.LevelConfigs;

import java.util.List;

public class LevelConfig {
    private int level;

    private String mapPath;
    private List<Item> items;

    private RandomItems randomItems;
    private List<Wall> walls;
    private int scoreToUnlock;


    // Getters and setters for each field

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public String getMapPath() {
        return mapPath;
    }

    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public RandomItems getRandomItems() {
        return randomItems;
    }

    public void setRandomItems(RandomItems randomItems) {
        this.randomItems = randomItems;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public int getScoreToUnlock() {
        return scoreToUnlock;
    }

    public void setScoreToUnlock(int scoreToUnlock) {
        this.scoreToUnlock = scoreToUnlock;
    }

    public static class Item {
        private String type;
        private int quantity;

        // Getters and setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class RandomItems {
        private int quantity;

        // Getters and setters

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class Wall {
        private String type;
        private float x, y, width, height;

        // Getters and setters

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }
    }

    // Getters and Setters for LevelConfig fields
}

