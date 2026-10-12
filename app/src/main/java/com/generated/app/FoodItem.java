package com.generated.app;

public class FoodItem {
    public enum Compatibility {
        BENEFICIAL, // מועיל מאוד
        NEUTRAL,    // ניטרלי
        AVOID       // מומלץ להימנע
    }

    private final String id;
    private final String name;
    private final int calories;
    private final String portion;
    private final boolean isShabbatFood;
    private final Compatibility compatO;
    private final Compatibility compatA;
    private final Compatibility compatB;
    private final Compatibility compatAB;
    private final String tip;

    public FoodItem(String id, String name, int calories, String portion, boolean isShabbatFood,
                    Compatibility compatO, Compatibility compatA, Compatibility compatB, Compatibility compatAB,
                    String tip) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.portion = portion;
        this.isShabbatFood = isShabbatFood;
        this.compatO = compatO;
        this.compatA = compatA;
        this.compatB = compatB;
        this.compatAB = compatAB;
        this.tip = tip;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public String getPortion() { return portion; }
    public boolean isShabbatFood() { return isShabbatFood; }
    public String getTip() { return tip; }

    public Compatibility getCompatibility(String bloodType) {
        if (bloodType == null) return Compatibility.NEUTRAL;
        switch (bloodType.toUpperCase()) {
            case "A":
                return compatA;
            case "B":
                return compatB;
            case "AB":
                return compatAB;
            case "O":
            default:
                return compatO;
        }
    }
}
