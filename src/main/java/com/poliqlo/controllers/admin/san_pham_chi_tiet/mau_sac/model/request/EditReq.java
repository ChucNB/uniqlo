package com.poliqlo.controllers.admin.san_pham_chi_tiet.mau_sac.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.poliqlo.models.MauSac}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EditReq implements Serializable {
    @NotNull
    @Positive
    Integer id;
    @NotNull(message = "Tên không được trống")
    @Size(max = 255)
    @NotBlank(message = "Tên không được trống")
    private String ten;
    @Pattern(regexp = "#\\w{6}",message = "Sai định dạng mã màu")
    private String code;
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean isDeleted;
}