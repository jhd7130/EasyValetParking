package com.ohho.valetparking.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Getter
public enum ErrorCode {


  // Member
  EMAIL_DUPLICATION(400, "M001", "중복된 이메일이 존재합니다."),
  FAIL_MEMBER_REGISTRATION(400, "M001", "중복된 이메일이 존재합니다."),
  EMAIL_NOT_FOUND(400, "M002", "존재하지 않는 이메일입니다."),
  LOGIN_INPUT_INVALID(400, "M003", "로그인 정보가 없습니다."),
  NOT_MATCH_PASSWORD(400, "M004", "비밀번호를 다시 입력해주세요."),

  // VIP
  FAIL_REGISTER_VIP(400, "VIP001", "VIP등록에 실패했습니다."),
  FAIL_UPDATE_VIP(400, "VIP002", "VIP 수정에 실패했습니다."),
  NOT_FOUND_VIP(400, "VIP003", "찾을 수 없는 VIP입니다."),

  // Exit
  REJECTED_EXIT_REQUEST(400, "E001", "반려된 요청은 완료 처리할 수 없습니다."),
  FAIL_REGISTER_EXIT(400, "E002", "출차등록에 실패했습니다."),
  FAIL_UPDATE_EXIT(400, "E003", "출차상태 수정에 실패했습니다."),
  NOT_FOUND_EXIT(400, "E004", "VIP 수정에 실패했습니다."),

  // Parking


  // Token
  INVALID_TOKEN(400, "T001", "유효하지않은 토큰입니다."),
  // Common
  INVALID_ARGUMENT(400, "C001", "잘못된 입력값입니다."),
  METHOD_NOT_ALLOWED(405, "C002", "기능 사용 권한이 없습니다."),
  HANDLE_ACCESS_DENIED(403, "C003", "권한이 없습니다."),
  FAIL_REGISTER(400, "C004", "등록에 실패했습니다."),
  FAIL_UPDATE(400, "C005", "수정에 실패했습니다."),
  DATA_DUPLICATE(400, "C006", "중복된 데이터가 존재하거나 데이터를 조회할 수 없습니다."),
  NOT_FOUND(400, "C006", "찾을 수 없는 데이터입니다.")
  ;
  private final int status;
  private final String code;
  private final String message;


  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }
}
