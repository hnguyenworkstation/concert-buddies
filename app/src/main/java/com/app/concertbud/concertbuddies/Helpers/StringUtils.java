package com.app.concertbud.concertbuddies.Helpers;

/**
 * Created by hungnguyen on 2/27/18.
 */

public class StringUtils {
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String PROFILE = "public_profile";

    // <<<
    private static final String BIRTHDAY = "user_birthday";
    private static final String ABOUT = "user_about_me";

    private static String[] FB_PERMISSIONS = {EMAIL, PROFILE, BIRTHDAY, ABOUT};

    public static String[] getFbPermissions() {
        return FB_PERMISSIONS;
    }
}
