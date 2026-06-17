package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.models.Product;
import com.mecralife.services.CartSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class ProductCardController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label mnnLabel;
    @FXML
    private Label manufacturerLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label formLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label bonusLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private TextField quantityField;

    private static Product selectedProduct;

    public static void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    @FXML
    public void initialize() {
        if (selectedProduct != null) {
            nameLabel.setText(selectedProduct.getName());
            mnnLabel.setText("МНН: " + selectedProduct.getMnn());
            manufacturerLabel.setText("Производитель: " + selectedProduct.getManufacturer());
            priceLabel.setText(String.format("Цена: %.2f ₽", selectedProduct.getPrice()));
            formLabel.setText("Форма выпуска: " + selectedProduct.getForm());
            stockLabel.setText(selectedProduct.isInStock() ? "В наличии" : "Нет в наличии");
            descriptionLabel.setText(selectedProduct.getDescription());
            categoryLabel.setText("Категория: " + selectedProduct.getCategory());
            bonusLabel.setText("Бонусов за покупку: " + (int) (selectedProduct.getPrice() * 5 / 100) + " ₽");
            quantityField.setText("1");
        }
    }

    @FXML
    public void onAddToCart() {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > 0) {
                CartSingleton.getInstance().addProduct(selectedProduct, quantity);
                showAlert("Успех", "Товар добавлен в корзину");
            } else {
                showAlert("Ошибка", "Количество должно быть больше 0");
            }
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректное количество");
        }
    }

    @FXML
    public void goBack() throws Exception {
        MainApp.setRoot("CatalogView.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}