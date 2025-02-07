package com.example.ecommercebasic.entity.product;

public enum UnitType {
    KILOGRAM("kg"),
    GRAM("g"),
    LITER("L"),
    MILLILITER("mL"),
    UNIT("unit"),  // Genel tekil birim
    ITEM("item");  // Ürün bazlı sayım için

    private final String value;

    // Enum değerini dinamik olarak bulma (Factory Method)
    public static UnitType fromValue(String value) {
        for (UnitType unit : UnitType.values()) {
            if (unit.getValue().equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid unit type: " + value);
    }

    UnitType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
