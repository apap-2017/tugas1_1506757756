package com.example.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KotaModel {
	private String idKota;
	private char kode_kota;
	private String nama_kota;
}
