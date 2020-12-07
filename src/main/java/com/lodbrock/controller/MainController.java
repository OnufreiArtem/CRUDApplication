package com.lodbrock.controller;

import com.lodbrock.model.Item;
import com.lodbrock.dao.implementations.ItemMySQLDaoImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.UUID;

public class MainController implements Initializable {
    @FXML
    public AnchorPane main_window;
    @FXML
    public Button delete_btn;
    @FXML
    public Button show_btn;
    @FXML
    public Button add_btn;
    @FXML
    public TableView<Item> result_table;
    @FXML
    public TableColumn<Item, String> name_column;
    @FXML
    public TableColumn<Item, String> desc_column;
    @FXML
    public TableColumn<Item, String> type_column;
    @FXML
    public TableColumn<Item, Integer> price_column;
    @FXML
    public TextField name_field;
    @FXML
    public TextArea desc_area;
    @FXML
    public ComboBox<String> type_combo;
    @FXML
    public TextField price_field;
    @FXML
    public Button update_btn;
    @FXML
    public TableColumn<Item, LocalDateTime> created_at_column;
    @FXML
    public TableColumn<Item, LocalDateTime> updated_at_column;
    @FXML
    public TableColumn<Item, String> uuid_column;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        uuid_column.setCellValueFactory(new PropertyValueFactory<>("UUId"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("Name"));
        desc_column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        type_column.setCellValueFactory(new PropertyValueFactory<>("Type"));
        price_column.setCellValueFactory(new PropertyValueFactory<>("Price"));
        created_at_column.setCellValueFactory(new PropertyValueFactory<>("Created_at"));
        updated_at_column.setCellValueFactory(new PropertyValueFactory<>("Updated_at"));

        name_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20) {
                name_field.setText(oldValue);
            }
        });

        desc_area.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 200) {
                desc_area.setText(oldValue);
            }
        });

        price_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || newValue.length() > 7) {
                price_field.setText(oldValue);
            }
        });


        update_btn.setDisable(true);
        add_btn.setDisable(true);

        type_combo.setItems(FXCollections.observableList(Arrays.asList(
                "None",
                "Equipable",
                "Consumable",
                "Can be placed")
        ));

        result_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String selectedId = newSelection.getUUId();
                /*
                name_field.setText(newSelection.getName());
                desc_area.setText(newSelection.getDescription());
                type_combo.getSelectionModel().select(newSelection.getType());
                price_field.setText(String.valueOf(newSelection.getPrice()));
                */
                Item item = ItemMySQLDaoImpl.getInstance().getById(selectedId);
                name_field.setText(item.getName());
                desc_area.setText(item.getDescription());
                type_combo.getSelectionModel().select(item.getType());
                price_field.setText(String.valueOf(item.getPrice()));

                update_btn.setDisable(false);

            } else{
                update_btn.setDisable(true);
            }
        });

    }

    public void showAllItems(MouseEvent mouseEvent) {
        result_table.setItems(FXCollections.observableList(ItemMySQLDaoImpl.getInstance().getAll()));
        add_btn.setDisable(false);
    }

    public void deleteItem(MouseEvent mouseEvent) {
        Item item = result_table.getSelectionModel().getSelectedItem();
        boolean deleted = ItemMySQLDaoImpl.getInstance().delete(item.getUUId());
        if(deleted){
            result_table.getItems().remove(item);
        }
        System.out.println(item);
        System.out.println(item.toString() + " deleted: " + deleted);
    }

    public void addItem(MouseEvent mouseEvent) {
        Item itemFromPanel = getItemFromPanel();
        itemFromPanel.setUUId(UUID.randomUUID().toString());
        boolean added = ItemMySQLDaoImpl.getInstance().add(itemFromPanel);
        if(added){
            result_table.setItems(FXCollections.observableList(ItemMySQLDaoImpl.getInstance().getAll()));
        }
        System.out.println(itemFromPanel.toString() + " added: " + added);
    }

    public void updateItem(MouseEvent event) {
        Item selectedItem = result_table.getSelectionModel().getSelectedItem();
        Item itemFromPanel = getItemFromPanel();
        itemFromPanel.setUUId(selectedItem.getUUId());

        boolean updated = ItemMySQLDaoImpl.getInstance().update(selectedItem.getUUId(), itemFromPanel);

        if(updated){
            result_table.setItems(FXCollections.observableList(ItemMySQLDaoImpl.getInstance().getAll()));
        }

        System.out.println(selectedItem.toString() + " updated: " + updated);

    }

    public Item getItemFromPanel(){
        String name = name_field.getText();
        String description = desc_area.getText();
        String type = type_combo.getSelectionModel().getSelectedItem();
        int price = Integer.parseInt(price_field.getText().equals("") ? "0" : price_field.getText());

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setType(type);
        item.setPrice(price);

        return item;

    }
}
