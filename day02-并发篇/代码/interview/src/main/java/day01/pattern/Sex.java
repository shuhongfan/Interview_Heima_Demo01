package day01.pattern;

enum Sex {
    MALE, FEMALE;
}

/*final class Sex extends Enum<Sex> {
    public static final Sex MALE;
    public static final Sex FEMALE;

    private Sex(String name, int ordinal) {
        super(name, ordinal);
    }

    static {
        MALE = new Sex("MALE", 0);
        FEMALE = new Sex("FEMALE", 1);
        $VALUES = values();
    }

    private static final Sex[] $VALUES;

    private static Sex[] $values() {
        return new Sex[]{MALE, FEMALE};
    }

    public static Sex[] values() {
        return $VALUES.clone();
    }

    public static Sex valueOf(String value) {
        return Enum.valueOf(Sex.class, value);
    }
}*/
