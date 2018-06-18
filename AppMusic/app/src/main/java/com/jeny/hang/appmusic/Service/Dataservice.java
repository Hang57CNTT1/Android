package com.jeny.hang.appmusic.Service;

import com.jeny.hang.appmusic.Model.Album;
import com.jeny.hang.appmusic.Model.BaiHat;
import com.jeny.hang.appmusic.Model.ChuDevaTheLoaiTrongNgay;
import com.jeny.hang.appmusic.Model.Chude;
import com.jeny.hang.appmusic.Model.Playlist;
import com.jeny.hang.appmusic.Model.Quangcao;
import com.jeny.hang.appmusic.Model.Theloai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    //Sử dụng phương thức POST để lấy và trả về dữ liệu từ phía server
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    //từ khóa gửi lên và từ khóa trong server phải trùng với nhau @idplaylist
    Call<List<BaiHat>> GetDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachplaylist_them.php")
    Call<List<Playlist>> GetDanhsachcacplaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihattheotheloai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<Chude>> GetAllChuDe();

    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<Theloai>> GetTheloaitheochude(@Field("idchude")String idchude);

    @GET("tatcaalbum.php")
    Call<List<Album>> GetAllalbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> Getdanhsachbaihattheoalbum(@Field("idalbum")String idalbum);

    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> UpdateLuotthich(@Field("luotthich")String luotthich,@Field("idbaihat")String idbaihat);
}
