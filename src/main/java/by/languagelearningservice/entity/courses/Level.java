package by.languagelearningservice.entity.courses;

public enum Level {
    A1("Начальный (A1)"),
    A2("Элементарный (A2)"),
    B1("Средний (B1)"),
    B2("Выше среднего (B2)"),
    C1("C1 Продвинутый");

    private String translation;

    Level() {
    }

    Level(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

}
