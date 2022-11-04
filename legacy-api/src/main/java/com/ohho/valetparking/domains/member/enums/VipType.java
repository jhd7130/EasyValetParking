package com.ohho.valetparking.domains.member.enums;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum VipType implements Serializable {
  PARKCLUB, HDC, ETC;
}
