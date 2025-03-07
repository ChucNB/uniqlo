package com.poliqlo.repositories;

import com.poliqlo.controllers.admin.san_pham.model.reponse.DataList;
import com.poliqlo.models.SanPham;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer>, JpaSpecificationExecutor<SanPham> {
    @Query(value = "SELECT new com.poliqlo.controllers.admin.san_pham.model.reponse.DataList(sp.id,sp.ten) FROM SanPham sp")
    List<DataList> findAllDataList();
    @Query(value = "SELECT DISTINCT sp.maSanPham FROM SanPham sp")
    List<String> findAllCode();
    List<SanPham> findAllByIsDeletedIsFalseOrderByIdDesc();

    @Query(value = """
    SELECT 
        SP.ID, 
        SP.MA_SAN_PHAM, 
        SP.TEN, 
        TH.TEN AS THUONG_HIEU, 
        CL.TEN AS CHAT_LIEU, 
        KD.TEN AS KIEU_DANG, 
        GROUP_CONCAT(DISTINCT DM.TEN ORDER BY DM.TEN SEPARATOR ', ') AS DANH_MUC, 
        SP.ANH_URL, 
        T.SO_LUONG, 
        SP.TRANG_THAI
    FROM SAN_PHAM SP
    LEFT JOIN THUONG_HIEU TH ON TH.ID = SP.THUONG_HIEU_ID
    LEFT JOIN CHAT_LIEU CL ON CL.ID = SP.CHAT_LIEU_ID
    LEFT JOIN KIEU_DANG KD ON KD.ID = SP.KIEU_DANG_ID
    LEFT JOIN SAN_PHAM_DANH_MUC SPDM ON SPDM.SAN_PHAM_ID = SP.ID
    LEFT JOIN DANH_MUC DM ON DM.ID = SPDM.DANH_MUC_ID
    LEFT JOIN (
        SELECT SPCT.SAN_PHAM_ID, CAST(COALESCE(SUM(SPCT.SO_LUONG), 0) AS UNSIGNED) AS SO_LUONG
        FROM SAN_PHAM_CHI_TIET SPCT
        GROUP BY SPCT.SAN_PHAM_ID
    ) AS T ON T.SAN_PHAM_ID = SP.ID
    WHERE SP.IS_DELETED = FALSE
    GROUP BY SP.ID, SP.MA_SAN_PHAM, SP.TEN, TH.TEN, CL.TEN, KD.TEN, SP.ANH_URL, T.SO_LUONG, SP.TRANG_THAI;
    """, nativeQuery = true)
    List<Object[]> findAllSanPham();
//    CAST(COALESCE(MAX(spct.giaBan), 0) AS double)

    @Modifying
    @Transactional
    @Query("UPDATE SanPham sp SET sp.maSanPham = :maSanPham, sp.ten = :ten, " +
            "sp.thuongHieu = (SELECT th FROM ThuongHieu th WHERE th.id = :idThuongHieu), " +
            "sp.chatLieu = (SELECT cl FROM ChatLieu cl WHERE cl.id = :idChatLieu), " +
            "sp.kieuDang = (SELECT kd FROM KieuDang kd WHERE kd.id = :idKieuDang) " +
            "WHERE sp.id = :id")
    int updateSanPham(
            @Param("id") Integer id,
            @Param("maSanPham") String maSanPham,
            @Param("ten") String ten,
            @Param("idThuongHieu") Integer idThuongHieu,
            @Param("idChatLieu") Integer idChatLieu,
            @Param("idKieuDang") Integer idKieuDang
    );
}