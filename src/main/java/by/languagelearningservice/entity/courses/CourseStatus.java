package by.languagelearningservice.entity.courses;

public enum CourseStatus {
    DRAFT("Черновик"),
    PRIVATE("Приватный"),
    INACTIVE("Неактивный"),
    OPEN("Открытый");


    private String translation;

    CourseStatus() {
    }

    CourseStatus(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

}
