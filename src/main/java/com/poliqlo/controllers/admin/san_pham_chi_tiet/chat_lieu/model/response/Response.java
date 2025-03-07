package com.poliqlo.controllers.admin.san_pham_chi_tiet.chat_lieu.model.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.poliqlo.models.ChatLieu}
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Response implements Serializable {
    @Positive
    private Integer id;
    private String tenSanPham;
    private String kichThuoc;
    private Double giaBan;
    private Integer soLuong;
    private String anhSanPham;
    private String tenMau;
}