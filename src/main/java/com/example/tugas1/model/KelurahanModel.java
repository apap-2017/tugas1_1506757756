package com.example.tugas1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KelurahanModel {
	private String idKelurahan;
	private char kode_kelurahan;
	private char id_kecamatan;
	private String nama_kecamatan;
	private char kode_pos;
	List<KecamatanModel> kecamatan;
}
