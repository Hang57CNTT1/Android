package com.jeny.hang.appmusic.Service;

import com.jeny.hang.appmusic.Model.Album;
import com.jeny.hang.appmusic.Model.BaiHat;
import com.jeny.hang.appmusic.Model.ChuDevaTheLoaiTrongNgay;
import com.jeny.hang.appmusic.Model.Playlist;
import com.jeny.hang.appmusic.Model.Quangcao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Tương tác kết nối với đường link,và ko gửi dữ liệu
//Gửi các phương thức từ csdl về
public interface Dataservice  {
    @GET("songbanner.php")
    Call<List<Quangcao>> GetDataBanner();

    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();

    @GET("chudevatheloaitrongngay.php")
    Call<ChuDevaTheLoaiTrongNgay> GetCategoryMusic();

    @GET("albumhot.php")
    Call<List<Album>> GetAlbumHot();

    @GET("baihatduocthich.php")
    Call<List<BaiHat>> GetBaiHatHot();
}
