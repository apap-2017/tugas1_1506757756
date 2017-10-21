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

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public String tambahPenduduk(Model model,
			@ModelAttribute PendudukModel penduduk) {
			pendudukDAO.addPenduduk(penduduk);
			model.addAttribute("nik", penduduk.getNik());
			return "sukses-tambah-penduduk";
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk(Model model,  
    		@PathVariable(value = "nik") String nik)
    {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		if(penduduk != null) {
			model.addAttribute("penduduk", penduduk);
	        return "form-update-penduduk";
		}
        return "error/not-found";
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
            return "error/not-found";
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
            return "error/not-found";
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
	
	@RequestMapping("/keluarga/ubah/{nkk}")
    public String ubahKeluarga(Model model, 
            @PathVariable(value = "nkk") String nkk)
    {
		KeluargaModel keluarga = pendudukDAO.selectKeluarga(nkk);
		
		if(keluarga != null ) {
			model.addAttribute("allKelurahan", pendudukDAO.selectAllKelurahan());
			model.addAttribute("keluarga", keluarga);
	        return "form-update-keluarga";
		}
		return "error/not-found";
    }
	

	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk,
    		@ModelAttribute KeluargaModel keluarga)
    {
		KeluargaModel keluargaLama = pendudukDAO.selectKeluarga(nkk);
		keluarga.setNomor_kk(keluargaLama.getNomor_kk());
		keluarga.setId_keluarga(keluargaLama.getId_keluarga());
		keluarga.setIs_tidak_berlaku(keluargaLama.getIs_tidak_berlaku());

		if(!keluargaLama.getId_kelurahan().equals(keluarga.getId_kelurahan())) {
			keluarga.setNomor_kk(pendudukDAO.generateNKK(keluarga));
		}
		
		pendudukDAO.updateKeluarga(keluarga);
		
		model.addAttribute("nkk", keluargaLama.getNomor_kk());
        return "sukses-update-keluarga";
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
			 @RequestParam(value = "id_kota", required = false) String id_kota,
	         @RequestParam(value = "id_kecamatan", required = false) String id_kecamatan,
	         @RequestParam(value = "id_kelurahan", required = false) String id_kelurahan){
		 
		 List<KotaModel> list_kota = pendudukDAO.selectAllKota();
		 model.addAttribute("list_kota", list_kota);
		 model.addAttribute("id_kota", id_kota);
		 
		 List<KecamatanModel> list_kecamatan = pendudukDAO.selectKecamatanByIdKota(id_kota);
		 model.addAttribute("list_kecamatan", list_kecamatan);
		 model.addAttribute("id_kecamatan", id_kecamatan);
		 
		 List<KelurahanModel> list_kelurahan = pendudukDAO.selectKelurahanByIdKecamatan(id_kecamatan);
		 model.addAttribute("list_kelurahan", list_kelurahan);
		 model.addAttribute("id_kelurahan", id_kelurahan);
		 
		 if (id_kota != null && id_kecamatan != null && id_kelurahan != null) {
				List<PendudukModel> list_penduduk = pendudukDAO.selectPendudukByIdKelurahan(id_kelurahan);
				model.addAttribute("penduduk", list_penduduk);

				log.info("Input {}", pendudukDAO.pendudukTermuda(id_kelurahan));
				
				PendudukModel penduduk_termuda = pendudukDAO.pendudukTermuda(id_kelurahan);
				model.addAttribute("termuda", penduduk_termuda);
				
				PendudukModel penduduk_tertua = pendudukDAO.pendudukTertua(id_kelurahan);
				model.addAttribute("tertua", penduduk_tertua);
			}
		 return "cari-penduduk";
	 }
}
