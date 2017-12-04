package bot;

import bot.database.manager.DatabaseManager;
import net.dv8tion.jda.core.entities.ISnowflake;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;

import java.io.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class I18N {
    /**
     * The name of the language and locale, ie. "English (US)"
     */
    private static final String KEY_LOCALE_NAME = "meta.locale.this";
    /**
     * If a locale specifies a fallback locale, missing keys will be looked up in the fallback.
     */
    private static final String KEY_FALLBACK_LOCALE = "meta.fallbackLocale";

    /**
     * Date format according to {@link java.time.format.DateTimeFormatter}.
     */
    private static final String KEY_DATE_FORMAT = "meta.dateFormat";

    /**
     * Time format according to {@link java.time.format.DateTimeFormatter}.
     */
    private static final String KEY_TIME_FORMAT = "meta.timeFormat";

    /**
     * Datetime format according to {@link java.time.format.DateTimeFormatter}.
     */
    private static final String KEY_DATETIME_FORMAT = "meta.datetimeFormat";

    /**
     * The default fallback locale, used if the locale does not define a fallback locale or the defined
     * fallback locale cannot be found
     */
    private static final String ROOT_LOCALE = "en";
    /**
     * The default locale
     */
    private static final String DEFAULT_LOCALE = "en_US";

    private Map<String, Properties> loadedLocales;

    private static I18N instance = null;

    public static I18N getInstance() {
        if (instance == null) {
            instance = new I18N();
        }
        return instance;
    }

    public I18N() {
        this.loadedLocales = new HashMap<>();
    }

    public String localize(DatabaseManager dbMan, MessageChannel channel, String key, Object... params) {
        String locale = dbMan.getLocale(channel);
        return localize(locale, key, params);
    }

    public String localize(String locale, String key, Object... params) {
        Properties i18n = getI18NValues(locale);
        String localized = i18n.getProperty(key);
        // Travel the chain of parent locales until finding one that provides a localisation
        while (localized == null && !ROOT_LOCALE.equals(locale)) {
            locale = i18n.getProperty(KEY_FALLBACK_LOCALE, ROOT_LOCALE);
            i18n = getI18NValues(locale);
            localized = i18n.getProperty(key);
        }
        if (localized == null) {
            // If no locale has a localisation, return the key
            System.out.println("Key miss for root locale and key " + key + ", returning key");
            return key;
        }
        return String.format(localized, params);
    }

    // date format is currently not used, but can't hurt to have it, eh?

    public String formatDate(DatabaseManager dbMan, MessageChannel channel, LocalDate date) {
        String locale = dbMan.getLocale(channel);
        return formatDate(locale, date);
    }

    public String formatDate(String locale, LocalDate date) {
        String formatString = this.localize(locale, KEY_DATE_FORMAT);
        Locale javaLocale = getJavaLocale(locale);
        DateTimeFormatter formatter = formatString == KEY_DATE_FORMAT
                ? DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(javaLocale)
                : DateTimeFormatter.ofPattern(formatString, javaLocale);
        return formatter.format(date);
    }

    // TODO: pluralization, quantification?

    private Locale getJavaLocale(String locale) {
        return Locale.forLanguageTag(locale);
    }

    private Properties getI18NValues(String locale) {
        Properties i18n = loadedLocales.get(locale);
        if (i18n == null) {
            i18n = new Properties();
            try {
                i18n.load(new FileInputStream(locale + ".lang"));
            } catch (IOException e) {
                System.out.println("Failed loading properties for locale " + locale);
            }
            loadedLocales.put(locale, i18n);
        }
        return i18n;
    }
}
