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
		kependudukanMapper.addPenduduk(penduduk);
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
	
}
