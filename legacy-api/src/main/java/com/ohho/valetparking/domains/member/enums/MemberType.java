package com.ohho.valetparking.domains.member.enums;

/**
 * Role : Responsibility : Cooperation with :
 **/
public enum MemberType {

  USER(1), ADMIN(2);
  private final int label;

  MemberType(int label) {
    this.label = label;
  }

  public int getLabel() {
    return label;
  }
}
