package config;

import presentation.Language;

import java.io.IOException;
import java.util.*;

/**
 * Created by Jorge.
 */
public class LanguageConfig {

    private final static String LANG_FILE = "/%s.properties";
    private static Properties langprop = new Properties();

    private static String[] locales;
    private static List<Language> languages;
    private static Map<String, Map<String, String>> languageMap = new HashMap<>();

    public static void init() throws IOException {
        locales = AppConfig.get("languages").split(",");
        for (String lang : locales)
            loadLanguage(lang);

        languages = new LinkedList<>();
        for (String locale : locales) {
            languages.add(new Language(locale, getLanguage(locale).get("meta.idioma")));
        }
    }

    private static void loadLanguage(String language) throws IOException {
        String file = String.format(LANG_FILE, language);

        langprop.load(MailConfig.class.getResourceAsStream(file));

        Map<String, String> strings = new HashMap<>();
        for (final Map.Entry<Object, Object> entry : langprop.entrySet()) {
            strings.put((String) entry.getKey(), (String) entry.getValue());
        }
        languageMap.put(language, strings);

    }

    public static Map<String, String> getLanguage(String locale) {
        return languageMap.get(locale);
    }

    public static List<String> getLocales() {
        return Arrays.asList(locales);
    }

    public static List<Language> getLanguages() {
        return languages;
    }

    public static String getDefaultLocale() {
        return locales[0];
    }

}
