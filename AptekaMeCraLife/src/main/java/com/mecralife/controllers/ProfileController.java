package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.models.Order;
import com.mecralife.models.User;
import com.mecralife.services.AuthService;
import com.mecralife.services.DataStorage;
import com.mecralife.utils.PasswordUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProfileController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Label bonusLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, Integer> colOrderId;
    @FXML
    private TableColumn<Order, String> colDate;
    @FXML
    private TableColumn<Order, Double> colTotal;
    @FXML
    private TableColumn<Order, String> colStatus;

    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = AuthService.getCurrentUser();
        if (currentUser == null) {
            try {
                MainApp.setRoot("MainView.fxml");
            } catch (Exception e) {
            }
            return;
        }

        welcomeLabel.setText("Профиль: " + currentUser.getFullName());
        bonusLabel.setText("Бонусов: " + currentUser.getBonusBalance() + " ₽");
        roleLabel.setText("Роль: " + currentUser.getRoleDisplayName());
        fullNameField.setText(currentUser.getFullName());
        phoneField.setText(currentUser.getPhone());

        setupOrdersTable();
        loadOrders();
    }

    private void setupOrdersTable() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalSum"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadOrders() {
        var userOrders = DataStorage.getOrders().stream()
                .filter(o -> o.getUserId() == currentUser.getId())
                .toList();
        ordersTable.setItems(FXCollections.observableArrayList(userOrders));
    }

    @FXML
    public void onSaveProfile() {
        currentUser.setFullName(fullNameField.getText());
        currentUser.setPhone(phoneField.getText());

        if (!newPasswordField.getText().isEmpty()) {
            currentUser.setPasswordHash(PasswordUtils.hashPassword(newPasswordField.getText()));
        }

        AuthService.updateCurrentUser(currentUser);
        showAlert("Успех", "Профиль обновлён");

        welcomeLabel.setText("Профиль: " + currentUser.getFullName());
    }

    @FXML
    public void onLogout() throws Exception {
        AuthService.logout();
        MainApp.setRoot("MainView.fxml");
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