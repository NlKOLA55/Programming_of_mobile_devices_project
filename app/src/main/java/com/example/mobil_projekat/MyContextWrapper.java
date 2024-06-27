package com.example.mobil_projekat;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

@SuppressWarnings("deprecation")
public class MyContextWrapper {
    // NEMAM POJMA KAKO OVO RADI ALI ZBOG OVOGA SE JEZICI DOBRO PRIKAZUJU U CELOJ APLIKACIJI
    public static Context wrap(Context context, String language) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }
        if (!language.isEmpty() && !sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, locale);
            } else {
                setSystemLocaleLegacy(config, locale);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(config);
        } else {
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
        return context;
    }

    @SuppressWarnings("deprecation")
    private static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    private static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    private static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    private static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }
}
