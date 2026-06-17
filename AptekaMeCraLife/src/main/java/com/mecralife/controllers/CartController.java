package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.models.CartItem;
import com.mecralife.services.AuthService;
import com.mecralife.services.BonusService;
import com.mecralife.services.CartSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CartController {

    @FXML
    private TableView<CartItem> tableView;
    @FXML
    private TableColumn<CartItem, String> colName;
    @FXML
    private TableColumn<CartItem, Double> colPrice;
    @FXML
    private TableColumn<CartItem, Integer> colQuantity;
    @FXML
    private TableColumn<CartItem, Double> colTotal;
    @FXML
    private Label totalLabel;
    @FXML
    private Label bonusBalanceLabel;
    @FXML
    private TextField bonusToSpendField;
    @FXML
    private Label finalTotalLabel;

    private ObservableList<CartItem> cartItems;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadCart();
        updateTotals();
        if (AuthService.isAuthenticated()) {
            bonusBalanceLabel.setText("Ваш баланс: " + AuthService.getCurrentUser().getBonusBalance() + " ₽");
        } else {
            bonusBalanceLabel.setText("Войдите для использования бонусов");
            bonusToSpendField.setDisable(true);
        }
    }

    private void setupTableColumns() {
        colName.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduct().getName()));
        colPrice.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject());
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());
    }

    private void loadCart() {
        cartItems = FXCollections.observableArrayList(CartSingleton.getInstance().getItems());
        tableView.setItems(cartItems);
    }

    private void updateTotals() {
        double total = CartSingleton.getInstance().getTotalPrice();
        totalLabel.setText(String.format("Сумма: %.2f ₽", total));
        updateFinalTotal();
    }

    private void updateFinalTotal() {
        double cartSum = CartSingleton.getInstance().getTotalPrice();
        int bonusToSpend = 0;

        if (bonusToSpendField.getText() != null && !bonusToSpendField.getText().isEmpty()) {
            try {
                bonusToSpend = Integer.parseInt(bonusToSpendField.getText());
            } catch (NumberFormatException e) {
                bonusToSpend = 0;
            }
        }

        int currentBalance = AuthService.isAuthenticated() ?
                AuthService.getCurrentUser().getBonusBalance() : 0;

        int actualBonusSpent = BonusService.calculateActualBonusToSpend(bonusToSpend, cartSum, currentBalance);
        double finalTotal = cartSum - actualBonusSpent;

        finalTotalLabel.setText(String.format("Итого к оплате: %.2f ₽ (списывается бонусов: %d ₽)",
                finalTotal, actualBonusSpent));
    }

    @FXML
    public void onRemoveSelected() {
        CartItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CartSingleton.getInstance().removeProduct(selected.getProduct().getId());
            loadCart();
            updateTotals();
        }
    }

    @FXML
    public void onBonusSpendChange() {
        updateFinalTotal();
    }

    @FXML
    public void onCheckout() throws Exception {
        if (CartSingleton.getInstance().isEmpty()) {
            showAlert("Ошибка", "Корзина пуста");
            return;
        }

        if (!AuthService.isAuthenticated()) {
            showAlert("Внимание", "Для оформления заказа необходимо войти в систему");
            return;
        }

        MainApp.setRoot("OrderView.fxml");
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