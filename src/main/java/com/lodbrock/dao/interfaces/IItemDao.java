package com.lodbrock.dao.interfaces;

import com.lodbrock.model.Item;

import java.util.List;

public interface IItemDao {

    List<Item> getAll();
    Item getById(String uuid);
    boolean add(Item item);
    boolean update(String uuid, Item item);
    boolean delete(String uuid);

}
