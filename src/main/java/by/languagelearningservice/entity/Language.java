package by.languagelearningservice.entity;

import by.languagelearningservice.entity.courses.Level;

import java.util.List;

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

    private Level level;

    Language() {
    }

    Language(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
