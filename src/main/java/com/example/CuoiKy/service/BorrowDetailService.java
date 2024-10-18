package com.example.CuoiKy.service;

import com.example.CuoiKy.entity.BorrowDetail;
import com.example.CuoiKy.repository.IBorrowDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BorrowDetailService {
    @Autowired
    private IBorrowDetailRepository borrowDetailRepository;

    public List<BorrowDetail> getAllBorrowDetail(){return borrowDetailRepository.findAll();}

    public List<BorrowDetail> getByBorrowId(Long id){
        return borrowDetailRepository.getByBorrowId(id);
    }

    public BorrowDetail getById(Long id){
        return borrowDetailRepository.findById(id).orElse(null);
    }

    public void addBorrowDetail(BorrowDetail borrowDetail){
        borrowDetailRepository.save(borrowDetail);
    }

    public void deleteBorrowDetailById(Long id){
        borrowDetailRepository.deleteById(id);
    }

    public void updateBorrowDetail(BorrowDetail borrowDetail){
        borrowDetailRepository.save(borrowDetail);
    }

    public void returnBook(Long id) {
        BorrowDetail detail = borrowDetailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid borrow detail ID"));
        detail.setActualReturnDate(new Date());
        borrowDetailRepository.save(detail);
    }

    public void returnBookpost(Long id) {
        BorrowDetail detail = borrowDetailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid borrow detail ID"));
        detail.setActualReturnDate(new Date());
        detail.setIsFines(true); // Cập nhật isFines thành true
        borrowDetailRepository.save(detail);
    }

    public Long getBorrowId(Long id){return borrowDetailRepository.getBorrowIdByDetailId(id);}

        public void updateFine(Long id, Double fineAmount,  String description) {
            borrowDetailRepository.findById(id).ifPresent(detail -> {
                detail.setFineAmount(fineAmount);
                detail.setIsFines(false);
                detail.setDescription(description);
                borrowDetailRepository.save(detail);
            });
        }
    public void updateIsFines(Long id) {
        BorrowDetail borrowDetail = borrowDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid borrow detail ID"));

        borrowDetail.setIsFines(true); // Cập nhật isFines thành true khi thanh toán phạt thành công

        borrowDetailRepository.save(borrowDetail);
    }
}
