package com.ohho.valetparking.enums;

import java.util.Arrays;

public enum Department {
  FS, FD, GSC, FNB, ETC;

  public static Department findByRole(String role) {
    return Arrays.stream(Department.values())
                  .filter(x -> x.name().equals(role))
                  .findFirst().orElseThrow(IllegalArgumentException::new);
  }

}
