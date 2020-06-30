package com.vertical.commerce.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String PAYPAL_CLIENT_ID = "AfcFPXfelT4JVXiooBdxmYOCLTuHyPl-rMS9k__pU8IIySV3pzqG46WLDcuwQox4t2wFnCaFGS9PbkTB";
    public static final String PAYPAL_CLIENT_SECRET = "EIMnEf-B29pks8RkMSy6HbWGeTKIHSP7PRcYGUfD8d3KMzMT9L6GElDN8ZdJzqM2xiBHWSvchInfN0vT";
    public static final String STRIPE_PUBLIC_KEY = "pk_test_ThH63aYHIpd2qDzNdEA6fbHW00vwByCJPA";
    public static final String STRIPE_SECRET_KEY = "sk_test_VweO8sXMgLDpvzE0CJbBHGcN00UJNvFoXj";

    private Constants() {
    }
}
