package by.languagelearningservice.entity;

public enum InviteStatus {
    REQUEST("Заявка"),
    ACCEPTED("Принял"),
    DENIED("Отказал");

    private String translate;


    InviteStatus() {
    }

    InviteStatus(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
