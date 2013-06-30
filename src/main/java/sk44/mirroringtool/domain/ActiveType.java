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

    ACTIVE(1), DISABLED(2);
    private final int typeValue;

    public Integer getTypeValue() {
        return typeValue;
    }

    public static ActiveType activeTypeOfValue(Integer value) {
        for (ActiveType t : ActiveType.values()) {
            if (t.getTypeValue().equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("value " + value + " is not exists.");
    }

    private ActiveType(int typeValue) {
        this.typeValue = typeValue;
    }
}
