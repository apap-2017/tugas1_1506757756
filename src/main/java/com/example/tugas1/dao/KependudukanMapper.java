package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KependudukanMapper {
	
	@Select("select * from penduduk join keluarga join kelurahan join kecamatan join kota" + 
			" on penduduk.id_keluarga = keluarga.id and keluarga.id_kelurahan = kelurahan.id" + 
			" and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id"
			+ " where nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select * from penduduk where id_keluarga = #{id}")
	List<PendudukModel> selectTablePenduduk(@Param("id") String id);
	
	@Select("select * " +
    		"from penduduk join keluarga " +
    		"on penduduk.id_keluarga = keluarga.id " +
    		"where nomor_kk = #{nkk}")
    		List<PendudukModel> selectPenduduks (@Param("nkk") String nkk);
	
	@Select("select * from keluarga join kelurahan join kecamatan join kota" + 
			" on keluarga.id_kelurahan = kelurahan.id" + 
			" and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id"
			+ " where nomor_kk = #{nkk}")
    @Results(value = {
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="rt"),
    		@Result(property="rw", column="rw"),
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="nama_kecamatan", column="nama_kecamatan"),
    		@Result(property="nama_kota", column="nama_kota"),
    		@Result(property="penduduks", column="nomor_kk",
    		javaType = List.class,
    		many=@Many(select="selectPenduduks"))
    		})
    KeluargaModel selectKeluarga (@Param("nkk") String nkk);
	
	 @Select("select * from keluarga join kelurahan on id_kelurahan=kelurahan.id"
		 		+ " join kecamatan on kecamatan.id=id_kecamatan join kota on kota.id=id_kota "
		 		+ "where keluarga.id = #{id_keluarga}")
		    @Results(value= {
		    		@Result(property="nomor_kk", column="nomor_kk"),
		    		@Result(property="alamat", column="alamat"),
		    		@Result(property="rt", column="rt"),
		    		@Result(property="rw", column="rw"),
		    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
		    		@Result(property="nama_kecamatan", column="nama_kecamatan"),
		    		@Result(property="nama_kota", column="nama_kota"),
		    		@Result(property="is_tidak_berlaku", column="is_tidak_berlaku"),
		    		@Result(property="penduduks", column="id",
		    		javaType=List.class, 
		    		many=@Many(select="selectTablePenduduk"))
		    })
	KeluargaModel selectAlamat(String id_keluarga);
	
	@Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat)"
    		+ "values ('${nik}', '${nama}', '${tempat_lahir}', '${tanggal_lahir}', ${jenis_kelamin}, ${is_wni}, '${id_keluarga}', '${agama}', "
    		+ "'${pekerjaan}', '${status_perkawinan}', '${status_dalam_keluarga}', '${golongan_darah}', '${is_wafat}')")
    void addPenduduk (PendudukModel penduduk);
	
	@Select("select * from penduduk where nik like CONCAT(#{nik},'%') order by nik desc limit 1")
	PendudukModel getNIKBefore(String nik);
}
