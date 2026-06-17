package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.models.User;
import com.mecralife.services.DataStorage;
import com.mecralife.utils.PasswordUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersController {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> colId;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, String> colPhone;
    @FXML
    private TableColumn<User, Integer> colBonus;
    @FXML
    private TableColumn<User, String> colRole;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleCombo;
    @FXML
    private TextField bonusField;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colBonus.setCellValueFactory(new PropertyValueFactory<>("bonusBalance"));
        colRole.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRoleDisplayName()));

        roleCombo.getItems().addAll("USER", "PHARMACIST", "MANAGER", "ADMIN");
        roleCombo.setValue("USER");

        loadUsers();
    }

    private void loadUsers() {
        tableView.setItems(FXCollections.observableArrayList(DataStorage.getUsers()));
    }

    @FXML
    public void onAddUser() {
        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert("Ошибка", "Заполните email и пароль");
            return;
        }

        if (DataStorage.findUserByEmail(emailField.getText()) != null) {
            showAlert("Ошибка", "Пользователь с таким email уже существует");
            return;
        }

        int bonus = 0;
        try {
            bonus = Integer.parseInt(bonusField.getText());
        } catch (NumberFormatException e) {
        }

        User user = new User(0, emailField.getText(),
                PasswordUtils.hashPassword(passwordField.getText()),
                nameField.getText(), phoneField.getText(), bonus, roleCombo.getValue());
        DataStorage.addUser(user);
        loadUsers();
        clearForm();
        showAlert("Успех", "Пользователь добавлен");
    }

    @FXML
    public void onDeleteUser() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DataStorage.deleteUser(selected.getId());
            loadUsers();
            showAlert("Успех", "Пользователь удалён");
        }
    }

    @FXML
    public void onEditUser() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            emailField.setText(selected.getEmail());
            nameField.setText(selected.getFullName());
            phoneField.setText(selected.getPhone());
            bonusField.setText(String.valueOf(selected.getBonusBalance()));
            roleCombo.setValue(selected.getRole());
            passwordField.clear();
        }
    }

    @FXML
    public void onUpdateUser() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setEmail(emailField.getText());
            selected.setFullName(nameField.getText());
            selected.setPhone(phoneField.getText());
            selected.setBonusBalance(Integer.parseInt(bonusField.getText()));
            selected.setRole(roleCombo.getValue());

            if (!passwordField.getText().isEmpty()) {
                selected.setPasswordHash(PasswordUtils.hashPassword(passwordField.getText()));
            }

            DataStorage.updateUser(selected);
            loadUsers();
            clearForm();
            showAlert("Успех", "Пользователь обновлён");
        }
    }

    private void clearForm() {
        emailField.clear();
        nameField.clear();
        phoneField.clear();
        passwordField.clear();
        bonusField.clear();
        roleCombo.setValue("USER");
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