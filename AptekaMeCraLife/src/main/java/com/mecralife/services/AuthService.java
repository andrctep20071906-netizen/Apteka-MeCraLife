package com.mecralife.services;

import com.mecralife.models.User;
import com.mecralife.utils.PasswordUtils;  // ← ЭТА СТРОКА ДОЛЖНА БЫТЬ
import com.mecralife.services.DataStorage;

public class AuthService {
    private static User currentUser;

    public static boolean login(String email, String password) {
        User user = DataStorage.findUserByEmail(email);
        if (user == null) return false;

        if (PasswordUtils.checkPassword(password, user.getPasswordHash())) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public static boolean register(String email, String password, String fullName, String phone) {
        if (DataStorage.findUserByEmail(email) != null) return false;

        User newUser = new User(0, email, PasswordUtils.hashPassword(password),
                fullName, phone, 0, "USER");
        DataStorage.addUser(newUser);
        return true;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAuthenticated() {
        return currentUser != null;
    }

    public static boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }

    public static boolean isManager() {
        return currentUser != null && ("ADMIN".equals(currentUser.getRole()) ||
                "MANAGER".equals(currentUser.getRole()));
    }

    public static boolean isPharmacist() {
        return currentUser != null && ("ADMIN".equals(currentUser.getRole()) ||
                "MANAGER".equals(currentUser.getRole()) ||
                "PHARMACIST".equals(currentUser.getRole()));
    }

    public static void updateCurrentUser(User user) {
        currentUser = user;
        DataStorage.updateUser(user);
    }

    public static String getRoleDisplay() {
        if (currentUser == null) return "Гость";
        return currentUser.getRoleDisplayName();
    }
}