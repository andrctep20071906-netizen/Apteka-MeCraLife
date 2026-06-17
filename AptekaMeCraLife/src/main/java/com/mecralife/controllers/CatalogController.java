package com.mecralife.controllers;

import com.mecralife.MainApp;
import com.mecralife.models.Product;
import com.mecralife.services.CartSingleton;
import com.mecralife.services.DataStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CatalogController {

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, String> colMnn;
    @FXML
    private TableColumn<Product, String> colManufacturer;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, String> colForm;
    @FXML
    private TableColumn<Product, Boolean> colStock;
    @FXML
    private TableColumn<Product, String> colCategory;
    @FXML
    private TextField searchField;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;
    @FXML
    private ComboBox<String> formFilter;
    @FXML
    private ComboBox<String> categoryFilter;
    @FXML
    private Label cartCountLabel;

    private ObservableList<Product> allProducts;
    private ObservableList<Product> filteredProducts;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupFilters();
        loadProducts();
        updateCartCount();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMnn.setCellValueFactory(new PropertyValueFactory<>("mnn"));
        colManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colForm.setCellValueFactory(new PropertyValueFactory<>("form"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void setupFilters() {
        formFilter.getItems().addAll("Все", "Таблетки", "Сироп", "Спрей", "Гель", "Капсулы", "Порошок", "Паста", "Мазь", "Крем");
        formFilter.setValue("Все");

        categoryFilter.getItems().addAll("Все категории");
        categoryFilter.getItems().addAll(DataStorage.getAllCategories());
        categoryFilter.setValue("Все категории");
    }

    private void loadProducts() {
        allProducts = FXCollections.observableArrayList(DataStorage.getProducts());
        filteredProducts = FXCollections.observableArrayList(allProducts);
        tableView.setItems(filteredProducts);
    }

    private void updateCartCount() {
        cartCountLabel.setText("Корзина (" + CartSingleton.getInstance().getTotalItemsCount() + ")");
    }

    @FXML
    public void onSearch() {
        applyFilters();
    }

    @FXML
    public void onResetFilters() {
        searchField.clear();
        minPriceField.clear();
        maxPriceField.clear();
        formFilter.setValue("Все");
        categoryFilter.setValue("Все категории");
        applyFilters();
    }

    private void applyFilters() {
        String query = searchField.getText().toLowerCase();
        String minPriceText = minPriceField.getText();
        String maxPriceText = maxPriceField.getText();
        String form = formFilter.getValue();
        String category = categoryFilter.getValue();

        double minPrice = minPriceText.isEmpty() ? 0 : Double.parseDouble(minPriceText);
        double maxPrice = maxPriceText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceText);

        filteredProducts.setAll(
                allProducts.filtered(p -> {
                    boolean matchesSearch = query.isEmpty() ||
                            p.getName().toLowerCase().contains(query) ||
                            p.getMnn().toLowerCase().contains(query);
                    boolean matchesPrice = p.getPrice() >= minPrice && p.getPrice() <= maxPrice;
                    boolean matchesForm = "Все".equals(form) || p.getForm().equals(form);
                    boolean matchesCategory = "Все категории".equals(category) ||
                            (p.getCategory() != null && p.getCategory().equals(category));
                    return matchesSearch && matchesPrice && matchesForm && matchesCategory;
                })
        );
    }

    @FXML
    public void onAddToCart() {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CartSingleton.getInstance().addProduct(selected, 1);
            updateCartCount();
            showAlert("Успех", selected.getName() + " добавлен в корзину");
        } else {
            showAlert("Внимание", "Выберите товар для добавления");
        }
    }

    @FXML
    public void onViewProduct() throws Exception {
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ProductCardController.setSelectedProduct(selected);
            MainApp.setRoot("ProductCardView.fxml");
        } else {
            showAlert("Внимание", "Выберите товар для просмотра");
        }
    }

    @FXML
    public void goToCart() throws Exception {
        MainApp.setRoot("CartView.fxml");
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