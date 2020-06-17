package com.study.generator.util;

import java.util.StringTokenizer;

public class StringUtility {

    /**
     * Utility class. No instances allowed
     */
    private StringUtility() {
        super();
    }

    public static boolean stringHasValue(String s) {
        return s != null && s.length() > 0;
    }

    public static String composeFullyQualifiedTableName(String catalog,
            String schema, String tableName, char separator) {
        StringBuilder sb = new StringBuilder();

        if (stringHasValue(catalog)) {
            sb.append(catalog);
            sb.append(separator);
        }

        if (stringHasValue(schema)) {
            sb.append(schema);
            sb.append(separator);
        } else {
            if (sb.length() > 0) {
                sb.append(separator);
            }
        }

        sb.append(tableName);

        return sb.toString();
    }

    public static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }

    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

  

    public static String escapeStringForXml(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("&quot;"); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static boolean isTrue(String s) {
        return "true".equalsIgnoreCase(s); //$NON-NLS-1$
    }

    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }
}