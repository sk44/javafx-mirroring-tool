package sk44.mirroringtool.domain;

/**
 * Mirroring results.
 *
 * @author sk
 */
public enum ResultType {

    NONE(1, ""), SUCCESS(2, "SUCCESS"), ERROR(3, "ERROR"), STOP(4, "STOP");
    private final int typeValue;
    private final String description;

    public static ResultType resultTypeOfValue(Integer value) {
        for (ResultType t : ResultType.values()) {
            if (t.getTypeValue().equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("value " + value + " is not exists.");
    }

    public Integer getTypeValue() {
        return typeValue;
    }

    public String getDescription() {
        return description;
    }

    private ResultType(int typeValue, String description) {
        this.typeValue = typeValue;
        this.description = description;
    }
}
