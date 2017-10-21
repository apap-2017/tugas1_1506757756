package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KependudukanMapper {
	
	//Penduduk
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
	
	@Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat)"
    		+ "values ('${nik}', '${nama}', '${tempat_lahir}', '${tanggal_lahir}', ${jenis_kelamin}, ${is_wni}, '${id_keluarga}', '${agama}', "
    		+ "'${pekerjaan}', '${status_perkawinan}', '${status_dalam_keluarga}', '${golongan_darah}', '${is_wafat}')")
    void addPenduduk (PendudukModel penduduk);
	
	@Update("update penduduk set nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, "
			+ "jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} "
			+ "where id = #{id}")
    void updatePenduduk (PendudukModel penduduk);
	
	 @Update("update penduduk set is_wafat = 1 where nik = #{nik}")
	   void updateStatusKematian(@Param("nik") String nik);
	 
	 @Select("select * from penduduk where nik like CONCAT(#{nik},'%') order by nik desc limit 1")
		PendudukModel getNIKBefore(String nik);
	 
	 @Select("select nik, nama, jenis_kelamin from penduduk join keluarga on penduduk.id_keluarga = keluarga.id join "
				+ "kelurahan on keluarga.id_kelurahan = kelurahan.id join kecamatan on kelurahan.id_kecamatan = kecamatan.id join "
				+ "kota on kecamatan.id_kota = kota.id where keluarga.id_kelurahan = #{id_kelurahan}")
		List<PendudukModel> selectPendudukByIdKelurahan(@Param("id_kelurahan") String id_kelurahan);
	
	 @Select("select distinct nama, nik, tanggal_lahir, (YEAR(CURDATE())-YEAR(tanggal_lahir)) as umur from penduduk join "
				+ "keluarga on penduduk.id_keluarga = keluarga.id where keluarga.id_kelurahan = #{id_kelurahan} order by umur asc limit 1")
		PendudukModel pendudukTermuda(@Param("id_kelurahan") String id_kelurahan); 
	 
	 @Select("select distinct nama, nik, tanggal_lahir, (YEAR(CURDATE())-YEAR(tanggal_lahir)) as umur from penduduk join "
				+ "keluarga on penduduk.id_keluarga = keluarga.id where keluarga.id_kelurahan = #{id_kelurahan} order by umur desc limit 1")
		PendudukModel pendudukTertua(@Param("id_kelurahan") String id_kelurahan); 
	 
	 //Keluarga
	 
	@Select("select * from keluarga, penduduk where penduduk.id_keluarga = keluarga.id and #{id_keluarga} = penduduk.id_keluarga")
    @Results(value = {
        	@Result(property="nik", column="nik"),
        	@Result(property="nama", column="nama"),
        	@Result(property="tempat_lahir", column="tempat_lahir"),
        	@Result(property="tanggal_lahir", column="tanggal_lahir"),
        	@Result(property="jenis_kelamin", column="jenis_kelamin"),
        	@Result(property="is_wni", column="is_wni"),
        	@Result(property="id_keluarga", column="id_keluarga"),
        	@Result(property="agama", column="agama"),
        	@Result(property="pekerjaan", column="pekerjaan"),
        	@Result(property="status_perkawinan", column="status_perkawinan"),
        	@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
        	@Result(property="golongan_darah", column="golongan_darah"),
        	@Result(property="is_wafat", column="is_wafat")
        })
    List<PendudukModel> selectAnggotaKeluarga(String id_keluarga);
	
	@Select("select * from keluarga join kelurahan join kecamatan join kota" + 
			" on keluarga.id_kelurahan = kelurahan.id" + 
			" and kelurahan.id_kecamatan = kecamatan.id and kecamatan.id_kota = kota.id"
			+ " where nomor_kk = #{nkk}")
    @Results(value = {
    		@Result(property="id_keluarga", column="id"),
    		@Result(property="nomor_kk", column="nomor_kk"),
    		@Result(property="alamat", column="alamat"),
    		@Result(property="rt", column="rt"),
    		@Result(property="rw", column="rw"),
    		@Result(property="nama_kelurahan", column="nama_kelurahan"),
    		@Result(property="kode_kecamatan", column="kode_kecamatan"),
    		@Result(property="nama_kecamatan", column="nama_kecamatan"),
    		@Result(property="nama_kota", column="nama_kota"),
    		@Result(property="penduduks", column="nomor_kk",
    		javaType = List.class,
    		many=@Many(select="selectPenduduks"))
    		})
    KeluargaModel selectKeluarga (@Param("nkk") String nkk);
	
	@Update("update keluarga set alamat = #{alamat}, nomor_kk = #{nomor_kk}, rt = #{rt}, rw = #{rw}, "
			+ "id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} "
			+ "where id = #{id_keluarga}")
    void updateKeluarga (KeluargaModel keluarga);
	
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
	
	
	@Insert("insert into keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku)"
    		+ "values ('${nomor_kk}', '${alamat}', '${rt}', '${rw}', '${id_kelurahan}', ${is_tidak_berlaku})")
    void addKeluarga (KeluargaModel keluarga);
	
	 @Update("update keluarga set is_berlaku = 1 where id = #{id}")
	   void updateStatusKeluarga(@Param("id") String id);
	
	@Select("select * from keluarga where nomor_kk like CONCAT(#{nomor_kk},'%') order by nomor_kk desc limit 1")
	KeluargaModel getNKKBefore(String nomor_kk);
	
	
	//Kelurahan
	@Select("select * from kelurahan join kecamatan join kota"
			+ " on kelurahan.id_kecamatan = kecamatan.id "
			+ " and kecamatan.id_kota = kota.id"
			)
    @Results(value = {
    		@Result(property="id", column="id")
    })
    List<KelurahanModel> selectAllKelurahan();
	
	@Select("select * from kelurahan where id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahanByIdKecamatan (@Param("id_kecamatan") String id_kecamatan);
	
	//Kecamatan
	@Select("select * from kecamatan")
    List<KecamatanModel> selectAllKecamatan();
	
	@Select("select * from kecamatan where id_kota = #{id_kota}")
	List<KecamatanModel> selectKecamatanByIdKota (@Param("id_kota") String id_kota);
	
	
	//Kota
	@Select("select * from kota")
    List<KotaModel> selectAllKota();
	
	@Select("select nama_kota from kota where id = #{id_kota}")
    KotaModel selectKota(@Param("id_kota") String id_kota);
}
