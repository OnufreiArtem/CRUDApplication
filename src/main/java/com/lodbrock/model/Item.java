package com.lodbrock.model;

import java.time.LocalDateTime;

/** Item class
 * This class represent the object of game item.
 * @version 1.0
 * @author Artem Onufrei
 */

public class Item {

    private String uuid;
    private String name;
    private String description;
    private String type;
    private int price;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Item() {
    }

    public Item(String uuid, String name, String description, String type, int price) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
    }

    public Item(String uuid, String name, String description, String type, int price,
                LocalDateTime created_at, LocalDateTime updated_at) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Item{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    public String getUUId() {
        return uuid;
    }

    public void setUUId(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
