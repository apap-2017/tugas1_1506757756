package com.example.tugas1.service;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

public interface KependudukanService {
	PendudukModel selectPenduduk(String nik);
	
	KeluargaModel selectKeluarga(String nomor_kk);
	
	void addPenduduk(PendudukModel penduduk);
	
	void addKeluarga(KeluargaModel keluarga);
	
	void updatePenduduk(PendudukModel penduduk);
	
	void updateKeluarga(KeluargaModel keluarga);
	
	void updateStatusKematian(PendudukModel penduduk);
	
	KeluargaModel selectAlamat(String id_keluarga);
	
	//select berdasarkan kota,kabupaten,dll...w
}
