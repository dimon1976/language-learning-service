package by.languagelearningservice.entity;

public enum Language {
    AR("Арабский"),
    SP("Испанский"),
    IT("Итальянский"),
    CH("Китайский"),
    DE("Немецкий"),
    PL("Польский"),
    PT("Португальский"),
    RU("Русский"),
    TR("Турецкий"),
    FR("Французский"),
    JA("Японский"),
    EN("Английский");

    private String translation;

    private String level;

    Language() {
    }

    Language(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
