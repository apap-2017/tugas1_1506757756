package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.KecamatanModel;
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
	
	String generateNIK(PendudukModel penduduk);
	
	String generateNKK(KeluargaModel keluarga);
	
	List<KelurahanModel> selectAllKelurahan();
	
	List<KelurahanModel> selectKelurahanByIdKecamatan(String id_kecamatan);
	
	List<KecamatanModel> selectAllKecamatan();
	
	List<KecamatanModel> selectKecamatanByIdKota(String id_kota);
	
	List<KotaModel> selectAllKota();
	
	KotaModel selectKota(String id_kota);
	
	List<PendudukModel> selectPendudukByIdKelurahan(String id_kelurahan);
	
	PendudukModel pendudukTermuda(String id_kelurahan);
	
	PendudukModel pendudukTertua(String id_kelurahan);
}
