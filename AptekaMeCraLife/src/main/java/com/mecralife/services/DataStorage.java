package com.mecralife.services;

import com.mecralife.models.Order;
import com.mecralife.models.Product;
import com.mecralife.models.User;
import com.mecralife.utils.PasswordUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String DATA_DIR = "data/";
    private static final String USERS_FILE = DATA_DIR + "users.json";
    private static final String PRODUCTS_FILE = DATA_DIR + "products.json";
    private static final String ORDERS_FILE = DATA_DIR + "orders.json";

    private static List<User> users;
    private static List<Product> products;
    private static List<Order> orders;
    private static Gson gson;

    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
        createDataDirectory();
        loadAllData();

        if (users.isEmpty()) {
            initTestData();
            saveAllData();
        }
    }

    private static void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private static void loadAllData() {
        users = loadFromFile(USERS_FILE, new TypeToken<List<User>>() {
        }.getType());
        products = loadFromFile(PRODUCTS_FILE, new TypeToken<List<Product>>() {
        }.getType());
        orders = loadFromFile(ORDERS_FILE, new TypeToken<List<Order>>() {
        }.getType());

        if (users == null) users = new ArrayList<>();
        if (products == null) products = new ArrayList<>();
        if (orders == null) orders = new ArrayList<>();
    }

    private static <T> List<T> loadFromFile(String filename, Type type) {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void saveToFile(String filename, Object data) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAllData() {
        saveToFile(USERS_FILE, users);
        saveToFile(PRODUCTS_FILE, products);
        saveToFile(ORDERS_FILE, orders);
    }

    private static void initTestData() {
        // ==================== ТОВАРЫ (32 штуки) ====================

        // ОБЕЗБОЛИВАЮЩИЕ (5)
        products.add(new Product(1, "Парацетамол 500мг", "Парацетамол", "Фармстандарт", 89.50, "Таблетки", true, "Жаропонижающее и обезболивающее средство.", "Обезболивающие"));
        products.add(new Product(2, "Нурофен 200мг", "Ибупрофен", "Reckitt Benckiser", 245.00, "Таблетки", true, "Противовоспалительное, жаропонижающее.", "Обезболивающие"));
        products.add(new Product(3, "Но-шпа 40мг", "Дротаверин", "Хиноин", 220.00, "Таблетки", true, "Спазмолитическое средство.", "Обезболивающие"));
        products.add(new Product(4, "Кетанов 10мг", "Кеторолак", "Сан Фармасьютикал", 180.00, "Таблетки", true, "Сильное обезболивающее средство.", "Обезболивающие"));
        products.add(new Product(5, "Анальгин 500мг", "Метамизол натрия", "Фармстандарт", 65.00, "Таблетки", true, "Анальгезирующее средство.", "Обезболивающие"));

        // ПРОСТУДНЫЕ (5)
        products.add(new Product(6, "Амбробене 30мг", "Амброксол", "Меркле", 189.90, "Сироп", true, "Отхаркивающее средство.", "Простудные"));
        products.add(new Product(7, "Лазолван 30мг", "Амброксол", "Boehringer Ingelheim", 310.00, "Таблетки", true, "Муколитическое средство.", "Простудные"));
        products.add(new Product(8, "АЦЦ 200мг", "Ацетилцистеин", "Сандоз", 280.00, "Порошок", true, "Разжижает мокроту.", "Простудные"));
        products.add(new Product(9, "Терафлю", "Парацетамол + фенирамин", "Novartis", 450.00, "Порошок", true, "От гриппа и простуды.", "Простудные"));
        products.add(new Product(10, "Колдрекс", "Парацетамол + фенилэфрин", "Reckitt Benckiser", 420.00, "Порошок", true, "Симптомы простуды.", "Простудные"));

        // ПРОТИВОАЛЛЕРГИЧЕСКИЕ (5)
        products.add(new Product(11, "Супрастин 25мг", "Хлоропирамин", "Эгис", 140.00, "Таблетки", true, "Противоаллергическое средство.", "Аллергия"));
        products.add(new Product(12, "Зодак 10мг", "Цетиризин", "Зентива", 250.00, "Таблетки", true, "От аллергии.", "Аллергия"));
        products.add(new Product(13, "Лоратадин 10мг", "Лоратадин", "Фармстандарт", 85.00, "Таблетки", true, "Противоаллергическое средство.", "Аллергия"));
        products.add(new Product(14, "Фенистил гель", "Диметинден", "Novartis", 380.00, "Гель", true, "Противозудное средство.", "Аллергия"));
        products.add(new Product(15, "Кларитин 10мг", "Лоратадин", "Schering-Plough", 320.00, "Таблетки", true, "От сезонной аллергии.", "Аллергия"));

        // ВИТАМИНЫ (5)
        products.add(new Product(16, "Витамин С 500мг", "Аскорбиновая кислота", "Эвалар", 150.00, "Таблетки", true, "Повышает иммунитет.", "Витамины"));
        products.add(new Product(17, "Компливит", "Мультивитамины", "Фармстандарт", 320.00, "Таблетки", true, "Комплекс витаминов.", "Витамины"));
        products.add(new Product(18, "Алфавит", "Мультивитамины", "Аквион", 380.00, "Таблетки", true, "Витаминно-минеральный комплекс.", "Витамины"));
        products.add(new Product(19, "Омега-3", "Рыбий жир", "РеалКапс", 450.00, "Капсулы", true, "ПНЖК Омега-3.", "Витамины"));
        products.add(new Product(20, "Магне В6", "Магний + пиридоксин", "Санаторио", 550.00, "Таблетки", true, "Восполняет дефицит магния.", "Витамины"));

        // ЖКТ (5)
        products.add(new Product(21, "Мезим форте", "Панкреатин", "Берлин-Хеми", 280.00, "Таблетки", true, "Улучшает пищеварение.", "ЖКТ"));
        products.add(new Product(22, "Креон 10000", "Панкреатин", "Эбботт", 450.00, "Капсулы", true, "Ферментный препарат.", "ЖКТ"));
        products.add(new Product(23, "Смекта", "Смектит диоктаэдрический", "Ипсен", 320.00, "Порошок", true, "Противодиарейное средство.", "ЖКТ"));
        products.add(new Product(24, "Энтеросгель", "Метилкремниевая кислота", "Силма", 420.00, "Паста", true, "Энтеросорбент.", "ЖКТ"));
        products.add(new Product(25, "Полисорб", "Коллоидный диоксид кремния", "Полисорб", 350.00, "Порошок", true, "Энтеросорбент.", "ЖКТ"));

        // ДЕРМАТОЛОГИЯ (5)
        products.add(new Product(26, "Аквамарис", "Натрия хлорид", "Ядран", 320.00, "Спрей", true, "Увлажнение носа.", "Дерматология"));
        products.add(new Product(27, "Пантенол", "Декспантенол", "Байер", 380.00, "Спрей", true, "Заживление кожи.", "Дерматология"));
        products.add(new Product(28, "Левомеколь", "Хлорамфеникол + метилурацил", "Нижфарм", 120.00, "Мазь", true, "Антибактериальная мазь.", "Дерматология"));
        products.add(new Product(29, "Бепантен", "Декспантенол", "Байер", 420.00, "Крем", true, "Уход за кожей.", "Дерматология"));
        products.add(new Product(30, "Цинковая мазь", "Оксид цинка", "Тульская ФФ", 60.00, "Мазь", true, "Подсушивающее средство.", "Дерматология"));

        // ПРОБИОТИКИ (2)
        products.add(new Product(31, "Линекс", "Лебенин", "Sandoz", 450.00, "Капсулы", true, "Пробиотик для кишечника.", "Пробиотики"));
        products.add(new Product(32, "Бифидумбактерин", "Бифидобактерии", "Партнер", 280.00, "Порошок", true, "Нормализует микрофлору.", "Пробиотики"));

        // ==================== ПОЛЬЗОВАТЕЛИ (8 РОЛЕЙ) ====================

        // 1. Администратор (полный доступ)
        users.add(new User(1, "admin@mecralife.ru",
                PasswordUtils.hashPassword("admin123"),
                "Администратор Системы", "8 (800) 555-35-35", 0, "ADMIN"));

        // 2. Менеджер (управление товарами и пользователями)
        users.add(new User(2, "manager@mecralife.ru",
                PasswordUtils.hashPassword("manager123"),
                "Петрова Анна Сергеевна", "+7 (912) 111-22-33", 500, "MANAGER"));

        // 3. Фармацевт (консультации, просмотр заказов)
        users.add(new User(3, "pharmacist@mecralife.ru",
                PasswordUtils.hashPassword("pharm123"),
                "Смирнова Елена Викторовна", "+7 (912) 222-33-44", 300, "PHARMACIST"));

        // 4. Постоянный покупатель (VIP)
        users.add(new User(4, "vip@mail.ru",
                PasswordUtils.hashPassword("vip123"),
                "Иванов Иван Иванович", "+7 (912) 345-67-89", 1250, "USER"));

        // 5. Новый покупатель
        users.add(new User(5, "new@mail.ru",
                PasswordUtils.hashPassword("new123"),
                "Новиков Дмитрий Алексеевич", "+7 (923) 456-78-90", 0, "USER"));

        // 6. Сотрудник аптеки (читатель)
        users.add(new User(6, "staff@apteka.ru",
                PasswordUtils.hashPassword("staff123"),
                "Козлова Мария Игоревна", "+7 (934) 567-89-01", 180, "USER"));

        // 7. Пенсионер (со скидкой)
        users.add(new User(7, "pensioner@mail.ru",
                PasswordUtils.hashPassword("pension123"),
                "Сидорова Людмила Петровна", "+7 (945) 678-90-12", 450, "USER"));

        // 8. Тестовый пользователь
        users.add(new User(8, "test@test.ru",
                PasswordUtils.hashPassword("test123"),
                "Тестовый Пользователь", "+7 (900) 123-45-67", 150, "USER"));
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public static List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public static List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public static void addUser(User user) {
        int maxId = users.stream().mapToInt(User::getId).max().orElse(0);
        user.setId(maxId + 1);
        users.add(user);
        saveAllData();
    }

    public static void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
        saveAllData();
    }

    public static void deleteUser(int userId) {
        users.removeIf(u -> u.getId() == userId);
        saveAllData();
    }

    public static void addOrder(Order order) {
        orders.add(order);
        saveAllData();
    }

    public static void addProduct(Product product) {
        int maxId = products.stream().mapToInt(Product::getId).max().orElse(0);
        product.setId(maxId + 1);
        products.add(product);
        saveAllData();
    }

    public static void removeProduct(int productId) {
        products.removeIf(p -> p.getId() == productId);
        saveAllData();
    }

    public static void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                break;
            }
        }
        saveAllData();
    }

    public static User findUserByEmail(String email) {
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
    }

    public static User findUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public static List<Product> getProductsByCategory(String category) {
        if (category == null || category.equals("Все")) {
            return getProducts();
        }
        return products.stream().filter(p -> p.getCategory().equals(category)).toList();
    }

    public static List<String> getAllCategories() {
        return products.stream().map(Product::getCategory).distinct().sorted().toList();
    }

    public static List<User> getUsersByRole(String role) {
        if (role == null || role.equals("Все")) {
            return getUsers();
        }
        return users.stream().filter(u -> u.getRole().equals(role)).toList();
    }
}