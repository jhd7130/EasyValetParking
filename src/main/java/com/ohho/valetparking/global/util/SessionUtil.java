package com.ohho.valetparking.global.util;

import com.ohho.valetparking.global.properties.RedisSessionProperty;
import javax.servlet.http.HttpSession;

/**
 *    * @author Atom
 *    * @param session 사용자의 세션
 *    * @return refreshToken Value를 반환
 */
public class SessionUtil {

  private static final String REFRESHTOKEN = "REFRESHTOKEN";

  public static String getRefreshtoken(HttpSession session) {
    return (String) session.getAttribute(REFRESHTOKEN);
  }

  public static void setRefreshtoken(HttpSession session, String refreshToken) {
    session.setAttribute(REFRESHTOKEN, refreshToken);
  }

  public static void removeToken(HttpSession session) {
    session.removeAttribute(REFRESHTOKEN);
  }

  public static void clear(HttpSession session) {
    session.invalidate();
  }

}
