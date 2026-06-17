package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.models.Product;
import com.mecralife.services.DataStorage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController {

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TextField nameField;
    @FXML
    private TextField mnnField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField formField;
    @FXML
    private ComboBox<String> categoryCombo;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private CheckBox inStockCheck;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

        categoryCombo.getItems().addAll(
                "Обезболивающие", "Простудные", "Аллергия",
                "Витамины", "ЖКТ", "Дерматология", "Пробиотики", "Другое"
        );
        categoryCombo.setValue("Другое");
        inStockCheck.setSelected(true);

        loadProducts();
    }

    private void loadProducts() {
        tableView.setItems(FXCollections.observableArrayList(DataStorage.getProducts()));
    }

    @FXML
    public void onAddProduct() {
        try {
            Product product = new Product();
            product.setName(nameField.getText());
            product.setMnn(mnnField.getText());
            product.setManufacturer(manufacturerField.getText());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setForm(formField.getText());
            product.setDescription(descriptionArea.getText());
            product.setInStock(inStockCheck.isSelected());
            product.setCategory(categoryCombo.getValue());

            DataStorage.addProduct(product);
            loadProducts();
            clearForm();
            showAlert("Успех", "Товар добавлен в категорию " + product.getCategory());
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректная цена");
        }
    }

    @FXML
    public void onDeleteProduct() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DataStorage.removeProduct(selected.getId());
            loadProducts();
            showAlert("Успех", "Товар удалён");
        }
    }

    @FXML
    public void onEditProduct() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            nameField.setText(selected.getName());
            mnnField.setText(selected.getMnn());
            manufacturerField.setText(selected.getManufacturer());
            priceField.setText(String.valueOf(selected.getPrice()));
            formField.setText(selected.getForm());
            descriptionArea.setText(selected.getDescription());
            categoryCombo.setValue(selected.getCategory());
            inStockCheck.setSelected(selected.isInStock());
        }
    }

    @FXML
    public void onUpdateProduct() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setName(nameField.getText());
                selected.setMnn(mnnField.getText());
                selected.setManufacturer(manufacturerField.getText());
                selected.setPrice(Double.parseDouble(priceField.getText()));
                selected.setForm(formField.getText());
                selected.setDescription(descriptionArea.getText());
                selected.setInStock(inStockCheck.isSelected());
                selected.setCategory(categoryCombo.getValue());

                DataStorage.updateProduct(selected);
                loadProducts();
                clearForm();
                showAlert("Успех", "Товар обновлён");
            } catch (NumberFormatException e) {
                showAlert("Ошибка", "Некорректная цена");
            }
        }
    }

    private void clearForm() {
        nameField.clear();
        mnnField.clear();
        manufacturerField.clear();
        priceField.clear();
        formField.clear();
        descriptionArea.clear();
        categoryCombo.setValue("Другое");
        inStockCheck.setSelected(true);
    }

    @FXML
    public void goBack() throws Exception {
        MainApp.setRoot("MainView.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}