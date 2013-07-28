/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk44.mirroringtool.domain;

/**
 *
 * @author sk
 */
public enum ActiveType {

    ACTIVE(1, true), DISABLED(2, false);
    private final int typeValue;
    private final boolean active;

    public Integer getTypeValue() {
        return typeValue;
    }

    public boolean isActive() {
        return active;
    }

    public static ActiveType activeTypeOfValue(Integer value) {
        for (ActiveType t : ActiveType.values()) {
            if (t.getTypeValue().equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("value " + value + " is not exists.");
    }

    private ActiveType(int typeValue, boolean active) {
        this.typeValue = typeValue;
        this.active = active;
    }
}
