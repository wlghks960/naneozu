package nnz.adminservice.service;

import nnz.adminservice.dto.AskedShowDTO;
import nnz.adminservice.dto.ReportDTO;
import nnz.adminservice.vo.AskedShowStatusVO;
import nnz.adminservice.vo.ReportStatusVO;
import nnz.adminservice.vo.ShowVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminService {
    List<AskedShowDTO> findAskedShowList();

    void handleAskedShow(AskedShowStatusVO askedShowStatusVO);

    List<ReportDTO> findReportList();

    void handleReport(ReportStatusVO reportStatusVO);

    void registBanners(List<MultipartFile> files, List<Long> showIDsVO);

    void createShow(ShowVO showVO, MultipartFile file);
}
