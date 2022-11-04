package com.ohho.valetparking.domains.member.domain.dto;

import com.ohho.valetparking.domains.member.enums.VipType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.Objects;
import org.hibernate.validator.constraints.Length;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VipRequest {

  @NotBlank(message = "NOT NAME")
  private String name;
  @NotBlank(message ="NOT CAR NUMBER")
  private String car_number;
  @NonNull
  private VipType type;
  private String uniqueness;

}
