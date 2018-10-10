package presentation;

public class Language {

    private String locale;
    private String language;

    public Language(String locale, String language) {
        super();
        this.locale = locale;
        this.language = language;
    }

    public String getLocale() {
        return locale;
    }

    public String getLanguage() {
        return language;
    }
}
