package com.jeny.hang.appmusic.Service;

import com.jeny.hang.appmusic.Model.Playlist;
import com.jeny.hang.appmusic.Model.Quangcao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Gửi các phương thức từ csdl về
public interface Dataservice  {
    @GET("songbanner.php")
    Call<List<Quangcao>> GetDataBanner();

    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();

}
