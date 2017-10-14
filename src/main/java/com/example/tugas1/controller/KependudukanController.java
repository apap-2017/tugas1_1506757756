package com.example.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.KependudukanService;


@Controller
public class KependudukanController {
	
	@Autowired
    KependudukanService pendudukDAO;
	
	@RequestMapping("/")
    public String index ()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk/tambah")
    public String addPenduduk()
    {
        return "form-tambah-penduduk";
    }
	
	@RequestMapping("/keluarga/tambah")
    public String addKeluarga()
    {
        return "form-tambah-keluarga";
    }
	
	
	@RequestMapping("/penduduk")
    public String viewPenduduk (Model model,
    		 @RequestParam(value = "nik", required = false) String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
        
        if (penduduk != null) {
            model.addAttribute ("penduduk", penduduk);
            if(penduduk.getIs_wni() == 1) {
            	penduduk.setKewarganegaraan("WNI");
            }
            else {
            	penduduk.setKewarganegaraan("WNA");
            }
            
            if(penduduk.getIs_wafat() == 1) {
            	penduduk.setKematian("Wafat");
            }
            else {
            	penduduk.setKematian("Hidup");
            }
            return "viewPenduduk";
        } else {
            model.addAttribute ("penduduk", penduduk);
            return "index";
        }
    }
	
	@RequestMapping("/keluarga")
    public String viewKeluarga (Model model,
    		 @RequestParam(value = "nkk", required = false) String nkk)
    {
        KeluargaModel keluarga = pendudukDAO.selectKeluarga(nkk);
        
        if (keluarga != null) {
            model.addAttribute ("keluarga", keluarga);
            return "viewKeluarga";
        } else {
            model.addAttribute ("keluarga", keluarga);
            return "index";
        }
    }
}
