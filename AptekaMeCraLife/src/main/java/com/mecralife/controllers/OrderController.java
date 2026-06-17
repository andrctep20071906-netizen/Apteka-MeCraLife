package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.models.Order;
import com.mecralife.models.OrderItem;
import com.mecralife.models.User;
import com.mecralife.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OrderController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<String> pickupPointCombo;
    @FXML
    private Label cartSumLabel;
    @FXML
    private Label bonusBalanceLabel;
    @FXML
    private TextField bonusToSpendField;
    @FXML
    private Label finalTotalLabel;
    @FXML
    private Button confirmButton;

    private User currentUser;
    private double cartSum;
    private int actualBonusSpent;

    @FXML
    public void initialize() {
        currentUser = AuthService.getCurrentUser();
        cartSum = CartSingleton.getInstance().getTotalPrice();

        fullNameField.setText(currentUser.getFullName());
        phoneField.setText(currentUser.getPhone());

        pickupPointCombo.getItems().addAll(
                "Аптека №1 (ул. Строителей, 15)",
                "Аптека №2 (пр. Ленина, 78)",
                "Аптека №3 (ул. Гагарина, 42)"
        );
        pickupPointCombo.setValue(pickupPointCombo.getItems().get(0));

        cartSumLabel.setText(String.format("Сумма заказа: %.2f ₽", cartSum));
        bonusBalanceLabel.setText("Бонусный баланс: " + currentUser.getBonusBalance() + " ₽");

        updateFinalTotal();
    }

    @FXML
    public void onBonusSpendChange() {
        updateFinalTotal();
    }

    private void updateFinalTotal() {
        int requestedBonus = 0;
        try {
            requestedBonus = Integer.parseInt(bonusToSpendField.getText());
        } catch (NumberFormatException e) {
        }

        actualBonusSpent = BonusService.calculateActualBonusToSpend(
                requestedBonus, cartSum, currentUser.getBonusBalance());

        double finalTotal = cartSum - actualBonusSpent;
        finalTotalLabel.setText(String.format("Итого: %.2f ₽ (списывается %d бонусов)",
                finalTotal, actualBonusSpent));
    }

    @FXML
    public void onConfirmOrder() {
        if (fullNameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            showAlert("Ошибка", "Заполните все поля");
            return;
        }

        confirmButton.setDisable(true);

        int newOrderId = DataStorage.getOrders().size() + 1;
        Order order = new Order(newOrderId, currentUser.getId(),
                cartSum - actualBonusSpent, actualBonusSpent,
                BonusService.calculateBonusToEarn(cartSum),
                pickupPointCombo.getValue());

        for (var cartItem : CartSingleton.getInstance().getItems()) {
            OrderItem item = new OrderItem(
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice()
            );
            order.getItems().add(item);
        }

        DataStorage.addOrder(order);
        BonusService.applyBonusTransaction(currentUser, cartSum, actualBonusSpent);
        CartSingleton.getInstance().clear();

        showAlert("Успех", "Заказ №" + newOrderId + " успешно оформлен!");

        try {
            MainApp.setRoot("MainView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack() throws Exception {
        MainApp.setRoot("CartView.fxml");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}