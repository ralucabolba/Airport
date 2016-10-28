package ds.assignment.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {
	public static Cookie findCookie(Cookie[] cookies, String name) {
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(name)) {
					return c;
				}
			}
		}
		return null;
	}
}
