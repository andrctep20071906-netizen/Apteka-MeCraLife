package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.services.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private VBox guestPanel;
    @FXML
    private VBox userPanel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label bonusLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private VBox adminPanel;
    @FXML
    private VBox managerPanel;
    @FXML
    private VBox pharmacistPanel;

    @FXML
    public void initialize() {
        updateUI();
    }

    private void updateUI() {
        if (AuthService.isAuthenticated()) {
            guestPanel.setVisible(false);
            userPanel.setVisible(true);
            welcomeLabel.setText("Добро пожаловать, " + AuthService.getCurrentUser().getFullName() + "!");
            bonusLabel.setText("Бонусов: " + AuthService.getCurrentUser().getBonusBalance() + " ₽");
            roleLabel.setText("Роль: " + AuthService.getRoleDisplay());

            // Показываем панели в зависимости от роли
            adminPanel.setVisible(AuthService.isAdmin());
            managerPanel.setVisible(AuthService.isManager());
            pharmacistPanel.setVisible(AuthService.isPharmacist());
        } else {
            guestPanel.setVisible(true);
            userPanel.setVisible(false);
        }
    }

    @FXML
    public void openCatalog() throws Exception {
        MainApp.setRoot("CatalogView.fxml");
    }

    @FXML
    public void openCart() throws Exception {
        MainApp.setRoot("CartView.fxml");
    }

    @FXML
    public void openProfile() throws Exception {
        MainApp.setRoot("ProfileView.fxml");
    }

    @FXML
    public void openAdmin() throws Exception {
        if (AuthService.isAdmin()) {
            MainApp.setRoot("AdminView.fxml");
        }
    }

    @FXML
    public void openUsers() throws Exception {
        if (AuthService.isManager()) {
            MainApp.setRoot("UsersView.fxml");
        }
    }

    @FXML
    public void logout() throws Exception {
        AuthService.logout();
        MainApp.setRoot("MainView.fxml");
    }
}