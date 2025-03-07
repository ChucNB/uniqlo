package com.poliqlo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "KHACH_HANG_ID", nullable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHIEU_GIAM_GIA_ID")
    private PhieuGiamGia phieuGiamGia;

    @Size(max = 255)
    @NotNull
    @Column(name = "PHUONG_THUC_THANH_TOAN", nullable = false)
    private String phuongThucThanhToan;

    @Column(name = "PHI_VAN_CHUYEN", precision = 15, scale = 2)
    private BigDecimal phiVanChuyen;

    @NotNull
    @Column(name = "TONG_TIEN", nullable = false, precision = 15, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "GIAM_MA_GIAM_GIA", precision = 15, scale = 2)
    private BigDecimal giamMaGiamGia;

    @Size(max = 20)
    @NotNull
    @Column(name = "SO_DIEN_THOAI", nullable = false, length = 20)
    private String soDienThoai;

    @Size(max = 255)
    @NotNull
    @Column(name = "DIA_CHI", nullable = false)
    private String diaChi;

    @Size(max = 255)
    @NotNull
    @Column(name = "TEN_NGUOI_NHAN", nullable = false)
    private String tenNguoiNhan;

    @Column(name = "NGAY_NHAN_HANG")
    private LocalDate ngayNhanHang;

    
    @Column(name = "GHI_CHU",columnDefinition = "TEXT")
    private String ghiChu;

    @Size(max = 255)
    @Column(name = "LOAI_HOA_DON")
    private String loaiHoaDon;

    @Size(max = 255)
    @Column(name = "TRANG_THAI")
    private String trangThai;

    @NotNull
    @ColumnDefault("b'0'")
    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted = false;

}