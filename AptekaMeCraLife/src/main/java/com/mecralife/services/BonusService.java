package com.mecralife.services;

import com.mecralife.models.User;

public class BonusService {

    public static int calculateBonusToEarn(double orderSum) {
        return (int) (orderSum * 0.05);
    }

    public static int calculateFinalTotal(double cartSum, int bonusToSpend, int currentBalance) {
        int maxBonusToSpend = (int) Math.min(cartSum, currentBalance);
        int actualBonusToSpend = Math.min(bonusToSpend, maxBonusToSpend);
        return (int) (cartSum - actualBonusToSpend);
    }

    public static int calculateActualBonusToSpend(int requestedBonus, double cartSum, int currentBalance) {
        int maxBySum = (int) cartSum;
        int maxByBalance = currentBalance;
        return Math.min(requestedBonus, Math.min(maxBySum, maxByBalance));
    }

    public static void applyBonusTransaction(User user, double orderSum, int bonusSpent) {
        int bonusEarned = calculateBonusToEarn(orderSum);
        int newBalance = user.getBonusBalance() - bonusSpent + bonusEarned;
        user.setBonusBalance(newBalance);
        DataStorage.updateUser(user);
    }
}