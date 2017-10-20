package com.example.tugas1.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KependudukanMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class KependudukanServiceDatabase implements KependudukanService{
	
	@Autowired
    private KependudukanMapper kependudukanMapper;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		// TODO Auto-generated method stub
		log.info("Penduduk selected");
		return kependudukanMapper.selectPenduduk(nik);
	}

	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info("Keluarga selected");
		return kependudukanMapper.selectKeluarga(nkk);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("Penduduk added");
		penduduk.setNik(generateNIK(penduduk));
		penduduk.setIs_wafat(0);
		kependudukanMapper.addPenduduk(penduduk);
	}



	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		log.info("Keluarga added");
		keluarga.setNomor_kk(generateNKK(keluarga));
		kependudukanMapper.addKeluarga(keluarga);
	}

	private String generateNKK(KeluargaModel keluarga) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String[] tanggalSplit = date.split("-");
		String tahun = tanggalSplit[0].substring(2);
		String bulan = tanggalSplit[1];
		String tanggal = tanggalSplit[2];
	
		String nkk = keluarga.getKode_kecamatan().substring(0, 6) + tanggal + bulan + tahun;
		
		KeluargaModel keluargaDouble = kependudukanMapper.getNKKBefore(nkk);
		
		if(keluargaDouble == null) {
			nkk += "0001";
		} else {
			long doubleNKK = Long.parseLong(keluargaDouble.getNomor_kk()) + 1;
			nkk = doubleNKK + "";
		}
		return nkk;
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		log.info("Penduduk updated");
		kependudukanMapper.updatePenduduk(penduduk);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		kependudukanMapper.updateKeluarga(keluarga);
	}

	@Override
	public void updateStatusKematian(PendudukModel penduduk) {
    	String idKeluarga = penduduk.getId_keluarga();
    	List<PendudukModel> anggota = kependudukanMapper.selectAnggotaKeluarga(idKeluarga);
    	
    	int wafat = 0;
    	for(int i = 0; i < anggota.size(); i++) {
    		if(anggota.get(i).getIs_wafat() == 1) {
    			wafat++;
    		}
    	}
    	if(wafat == anggota.size()) {
    		kependudukanMapper.updateStatusKeluarga(idKeluarga);
    	}
		kependudukanMapper.updateStatusKematian(penduduk.getNik());
	}

	@Override
	public List<KelurahanModel> selectAllKelurahan() {
		log.info("Select kelurahan");
		return kependudukanMapper.selectAllKelurahan();
	}

	@Override
	public List<KecamatanModel> selectAllKecamatan() {
		return kependudukanMapper.selectAllKecamatan();
	}

	@Override
	public List<KotaModel> selectAllKota() {
		return kependudukanMapper.selectAllKota();
	}

	@Override
	public String generateNIK(PendudukModel penduduk) {
		log.info("NIK generated");
		String nik = "";
		String[] tglSplit = penduduk.getTanggal_lahir().split("-");
		nik += kependudukanMapper.selectAlamat(penduduk.getId_keluarga()).getNomor_kk().substring(0,6);
		nik += (Integer.parseInt(tglSplit[2]) + Integer.parseInt(penduduk.getJenis_kelamin())*40) + "" + tglSplit[1] + tglSplit[0].substring(2);
		
		PendudukModel pendudukDouble = kependudukanMapper.getNIKBefore(nik);
		
		if(pendudukDouble == null) {
			nik += "0001";
		} else {
			log.info("Input {}", pendudukDouble.getNik());
			long doubleNIK = Long.parseLong(pendudukDouble.getNik()) + 1;
			nik = doubleNIK + "";
		}
		return nik;
	}

	@Override
	public KotaModel selectKota(String id_kota) {
		return kependudukanMapper.selectKota(id_kota);
	}

	@Override
	public List<KecamatanModel> selectKecamatanByIdKota(String id_kota) {
		return kependudukanMapper.selectKecamatanByIdKota(id_kota);
	}

	@Override
	public List<KelurahanModel> selectKelurahanByIdKecamatan(String id_kecamatan) {
		return kependudukanMapper.selectKelurahanByIdKecamatan(id_kecamatan);
	}
}
