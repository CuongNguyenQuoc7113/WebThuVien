package com.example.CuoiKy.controller;

import com.example.CuoiKy.entity.Bo;
import com.example.CuoiKy.service.BoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/bos")
@RequiredArgsConstructor
public class BoController {
    @Autowired
    private final BoService boService;


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("bo", new Bo());
        return "admin/bo/add";
    }

    @PostMapping("/add")
    public String addBo(@Valid Bo bo, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/bo/add";
        }
        boService.saveBo(bo);
        return "redirect:/bos";
    }

    @GetMapping
    public String listBo(Model model) {
        List<Bo> bos = boService.getAllBo();
        model.addAttribute("bos", bos);
        return "admin/bo/list";
    }


    // GET request to show category edit form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Bo bo = boService.getBoById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("bo", bo);
        return "admin/bo/edit";
    }
    // POST request to update category
    @PostMapping("/update/{id}")
    public String updateBo(@PathVariable("id") Long id, @Valid Bo bo,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            bo.setId(id);
            return "admin/bo/edit";
        }
        //categoryService.(category);
        model.addAttribute("bos", boService.getAllBo());
        boService.updateBo(bo);
        return "redirect:/bos";
    }
    // GET request for deleting category
    @GetMapping("/delete/{id}")
    public String deleteBo(@PathVariable("id") Long id, Model model) {
        Bo bo = boService.getBoById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:"
        //      + id));
        boService.deleteBo(id);
        model.addAttribute("bos", boService.getAllBo());
        return "redirect:/bos";
    }



}