package com.example.tugas1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KecamatanModel {
	private String idKecamatan;
	private char kode_kecamatan;
	private char id_kota;
	private String nama_kecamatan;
	List<KotaModel> kota;
}
