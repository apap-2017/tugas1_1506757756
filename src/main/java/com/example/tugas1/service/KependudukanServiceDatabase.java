package com.example.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KependudukanMapper;
import com.example.tugas1.model.KeluargaModel;
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

	private String generateNIK(PendudukModel penduduk) {
		String nik = "";
		String[] tglSplit = penduduk.getTanggal_lahir().split("-");
		nik += kependudukanMapper.selectAlamat(penduduk.getId_keluarga()).getNomor_kk().substring(0,6);
		log.info("add penduduk dengan id {}", nik);
		nik += (Integer.parseInt(tglSplit[2]) + Integer.parseInt(penduduk.getJenis_kelamin())*40) + "" + tglSplit[1] + tglSplit[0].substring(2);
		log.info("add penduduk dengan id {}", nik);
		
		PendudukModel pendudukDouble = kependudukanMapper.getNIKBefore(nik);
		
		if( pendudukDouble == null || pendudukDouble.getNik().equals(pendudukDouble.getNik())) {
			log.info("add penduduk dengan id {}", nik);
			nik += "0001";
		} else {
			int doubleNIK = Integer.parseInt(pendudukDouble.getNik()) + 1;
			nik = doubleNIK + "";
			log.info("add penduduk dengan id {}", nik);
		}
		return nik;
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStatusKematian(PendudukModel penduduk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public KeluargaModel selectAlamat(String id_keluarga) {
		return kependudukanMapper.selectAlamat(id_keluarga);
	}
	
}
