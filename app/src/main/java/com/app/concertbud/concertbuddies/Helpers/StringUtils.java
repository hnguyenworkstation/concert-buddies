package com.app.concertbud.concertbuddies.Helpers;

/**
 * Created by hungnguyen on 2/27/18.
 */

public class StringUtils {
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String PROFILE = "public_profile";

    private static String[] FB_PERMISSIONS = {EMAIL, PROFILE};

    public static String[] getFbPermissions() {
        return FB_PERMISSIONS;
    }
}
