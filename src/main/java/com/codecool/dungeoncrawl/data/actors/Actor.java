package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {
    protected Cell cell;
    protected int health;
    protected int attackPower;
    private List<Item> inventory;

    public Actor(Cell cell, int health, int attackPower) {
        this.cell = cell;
        this.cell.setActor(this);
        this.health = health;
        this.attackPower = attackPower;
        this.inventory = new ArrayList<>();
    }

    public abstract void move(int dx, int dy);

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public List<Item> getInventory() {
        return List.copyOf(inventory);
    }

    public void removeInventoryItem(Item item) {
        inventory.remove(item);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void addHealth(int health) {
        this.health += health;
    }

    public void setHealth(int health) {
        if (health > 0) {
            this.health = health;
        }
    }

    public void setAttackPower(int attackPower) {
        if (health > 0) {
            this.attackPower = attackPower;
        }
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
