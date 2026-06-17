package com.mecralife.models;

public class User {
    private int id;
    private String email;
    private String passwordHash;
    private String fullName;
    private String phone;
    private int bonusBalance;
    private boolean isAdmin;
    private boolean isManager;
    private boolean isPharmacist;
    private String role;
    private String registrationDate;

    public User() {
        this.role = "USER";
    }

    public User(int id, String email, String passwordHash, String fullName,
                String phone, int bonusBalance, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.phone = phone;
        this.bonusBalance = bonusBalance;
        this.isAdmin = isAdmin;
        this.isManager = false;
        this.isPharmacist = false;
        this.role = isAdmin ? "ADMIN" : "USER";
        this.registrationDate = java.time.LocalDate.now().toString();
    }

    public User(int id, String email, String passwordHash, String fullName,
                String phone, int bonusBalance, String role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.phone = phone;
        this.bonusBalance = bonusBalance;
        this.role = role;
        this.isAdmin = "ADMIN".equals(role);
        this.isManager = "MANAGER".equals(role);
        this.isPharmacist = "PHARMACIST".equals(role);
        this.registrationDate = java.time.LocalDate.now().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBonusBalance() {
        return bonusBalance;
    }

    public void setBonusBalance(int bonusBalance) {
        this.bonusBalance = bonusBalance;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public boolean isPharmacist() {
        return isPharmacist;
    }

    public void setPharmacist(boolean pharmacist) {
        isPharmacist = pharmacist;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        this.isAdmin = "ADMIN".equals(role);
        this.isManager = "MANAGER".equals(role);
        this.isPharmacist = "PHARMACIST".equals(role);
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRoleDisplayName() {
        switch (role) {
            case "ADMIN":
                return "Администратор";
            case "MANAGER":
                return "Менеджер";
            case "PHARMACIST":
                return "Фармацевт";
            default:
                return "Покупатель";
        }
    }
}