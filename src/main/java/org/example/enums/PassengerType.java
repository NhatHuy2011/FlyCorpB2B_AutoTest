package org.example.enums;

public enum PassengerType {
    ADULT("adult"),
    CHILD("children"),
    INFANT("infant");

    private final String prefix;

    PassengerType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
