package catering_service;

public abstract class Menucard {
    private int itemId;
    private String name;
    private double price;

    public Menucard(int itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    // Getters and setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public abstract String getType();
}

class Starter extends Menucard {
    public Starter(int itemId, String name, double price) {
        super(itemId, name, price);
    }

    @Override
    public String getType() {
        return "Starter";
    }
}

class MainCourse extends Menucard {
    public MainCourse(int itemId, String name, double price) {
        super(itemId, name, price);
    }

    @Override
    public String getType() {
        return "Main Course";
    }
}

class Dessert extends Menucard {
    public Dessert(int itemId, String name, double price) {
        super(itemId, name, price);
    }

    @Override
    public String getType() {
        return "Dessert";
    }
}
