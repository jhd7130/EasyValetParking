package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.entity.Member;
import com.ohho.valetparking.domains.member.domain.entity.SignIn;
import javax.servlet.http.HttpServletResponse;

/**
 * Role : Responsibility : Cooperation with :
 **/
public interface LoginService {

  Member signIn(SignIn signIn, HttpServletResponse response);

  void signOut();

}
