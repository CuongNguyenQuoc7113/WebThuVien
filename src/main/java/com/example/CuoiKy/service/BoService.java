package com.example.CuoiKy.service;

import com.example.CuoiKy.entity.Bo;
import com.example.CuoiKy.repository.IBoRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoService {
    @Autowired
    private IBoRepository boRepository;

    public List<Bo> getAllBo(){
        return boRepository.findAll();
    }

    public Bo getBoById(Long id){
        return boRepository.findById(id).orElse(null);
    }

    public Bo saveBo(Bo bo){
        return boRepository.save(bo);
    }

    public void updateBo(@NotNull Bo bo) {
        Bo existingBo = boRepository.findById(bo.getId()).orElseThrow(() -> new IllegalStateException("Bo with ID " + bo.getId() + " does not exist."));
        existingBo.setName(bo.getName());
        boRepository.save(existingBo);
    }
    public void deleteBo(Long id){
        boRepository.deleteById(id);
    }
}