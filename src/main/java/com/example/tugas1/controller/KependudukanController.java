package com.example.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
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
	
	@RequestMapping(value="/penduduk/tambah", method=RequestMethod.POST)
	public String tambahPenduduk(@ModelAttribute PendudukModel penduduk) {
		pendudukDAO.addPenduduk(penduduk);
		return "sukses-tambah-penduduk";
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk(Model model,  
    		@PathVariable(value = "nik") String nik)
    {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		model.addAttribute("penduduk", penduduk);
        return "form-update-penduduk";
    }
	
	@RequestMapping(value="/penduduk/ubah/{nik}", method=RequestMethod.POST)
	public String updatePendudukSubmit(Model model,  
    		@PathVariable(value = "nik") String nik,
    		@ModelAttribute PendudukModel penduduk) {
		
		PendudukModel pendudukLama = pendudukDAO.selectPenduduk(nik);

		penduduk.setId(pendudukLama.getId());
		penduduk.setJenis_kelamin(pendudukLama.getJenis_kelamin());
		model.addAttribute("nik", penduduk.getNik());
		
		if(!pendudukLama.getTanggal_lahir().equals(penduduk.getTanggal_lahir()) || !pendudukLama.getId_keluarga().equals(penduduk.getId_keluarga())) {
			penduduk.setNik(pendudukDAO.generateNIK(penduduk));
		}
		
		pendudukDAO.updatePenduduk(penduduk);
		return "sukses-update-penduduk";
	}
	
	
	@RequestMapping("/penduduk")
    public String viewPenduduk (Model model,
    		 @RequestParam(value = "nik", required = false) String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
        
        if (penduduk != null) {
            model.addAttribute ("penduduk", penduduk);
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
	
	@RequestMapping("/keluarga/tambah")
    public String addKeluarga(Model model)
    {
		model.addAttribute("keluarga", new KeluargaModel());
		model.addAttribute("allKelurahan", pendudukDAO.selectAllKelurahan());
        return "form-tambah-keluarga";
    }
	
	@RequestMapping(value="/keluarga/tambah", method=RequestMethod.POST)
	public String tambahKeluarga(Model model,
			@ModelAttribute KeluargaModel keluarga) {
		pendudukDAO.addKeluarga(keluarga);
		model.addAttribute("nkk", keluarga.getNomor_kk());
		return "sukses-tambah-keluarga";
	}
	
	@RequestMapping("/kelurga/ubah/{nkk}")
    public String updateKeluarga(Model model,  
    		@PathVariable(value = "nkk") String nkk)
    {
		KeluargaModel keluarga = pendudukDAO.selectKeluarga(nkk);
		model.addAttribute("allKelurahan", pendudukDAO.selectAllKelurahan());
		model.addAttribute("keluarga", keluarga);
        return "form-update-keluarga";
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String updateKeluargaSubmit(Model model,  
    		@PathVariable(value = "nkk") String nkk,
    		@ModelAttribute KeluargaModel keluarga) {
		
		KeluargaModel keluargaLama = pendudukDAO.selectKeluarga(nkk);

		keluarga.setId_keluarga(keluargaLama.getId_keluarga());
		model.addAttribute("nkk", keluarga.getNomor_kk());
		
		pendudukDAO.updateKeluarga(keluarga);
		return "sukses-tambah-keluarga";
	}
	
	 @RequestMapping(value="penduduk/mati", method = RequestMethod.POST)
	    public String changePendudukStatus(Model model, 
	    		@RequestParam(value="nik", required=true) String nik) {
	    	PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
	    	pendudukDAO.updateStatusKematian(penduduk);
	    	
	    	model.addAttribute("nik_kematian", nik);
	    	return "sukses-mati";
	    }
	 
	 @RequestMapping(value = "/penduduk/cari")
	 public String cariPendudukKota (Model model,
			 @RequestParam(value = "kota", required = false, defaultValue = "0") String id_kota,
	         @RequestParam(value = "kecamatan", required = false, defaultValue = "0") String id_kecamatan,
	         @RequestParam(value = "kelurahan", required = false, defaultValue = "0") String id_kelurahan){
		 
		 List<KotaModel> list_kota = pendudukDAO.selectAllKota();
		 model.addAttribute("list_kota", list_kota);
		 
		 if(id_kota != null) {
			 model.addAttribute("id_kota", id_kota);
			 List<KecamatanModel> list_kecamatan = pendudukDAO.selectKecamatanByIdKota(id_kota);
			 model.addAttribute("list_kecamatan", list_kecamatan);
		 }
		 
		 if(id_kecamatan != null) {
			 model.addAttribute("id_kecamatan", id_kecamatan);
			 List<KelurahanModel> list_kelurahan = pendudukDAO.selectKelurahanByIdKecamatan(id_kecamatan);
			 model.addAttribute("list_kelurahan", list_kelurahan);
		 }
		 
		 return "cari-penduduk";
	 }
}
