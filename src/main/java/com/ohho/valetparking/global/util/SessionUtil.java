package com.ohho.valetparking.global.util;

import com.ohho.valetparking.global.properties.RedisSessionProperty;
import javax.servlet.http.HttpSession;

/**
 *
 *      레디스의 키값으로 저장되는 타입입니다.
 *      spring:session:sessions:expires (string) 해당 세션의 만료키로 사용합니다.
 *      spring:session:expirations (set) expire time에 삭제될 세션 정보를 담고 있습니다.
 *      spring:session:sessions (hash) session은 map을 저장소로 사용하기 때문에 이곳에 세션 데이터가 있습니다.
 *
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
