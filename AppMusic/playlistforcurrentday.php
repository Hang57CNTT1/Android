<?php 

// * hổ trợ cho Playlist trả dữ liệu khác nhau mỗi ngày, không trùng lập
//kết nối csdl 
	require "connect.php";
	$query = "SELECT DISTINCT * FROM playlist ORDER BY rand(" .date("Ymd").") LIMIT 3";

	class PlaylistToday{
		function PlaylistToday($idplaylist,$ten,$hinh,$icon){
			$this->idPlayList = $idplaylist;
			$this->Ten = $ten;
			$this->HinhNen = $hinh;
			$this->HinhIcon = $icon;

		}
	}
		$arrayplaylistortoday = array();
		$data = mysqli_query($con,$query);
		while($row = mysqli_fetch_assoc($data)){
			array_push($arrayplaylistortoday, new PlaylistToday($row['idPlayList']
																,$row['Ten']
																,$row['HinhNen']
																,$row['HinhIcon']));
		}
		echo json_encode($arrayplaylistortoday);
	// if($query){
	// 	echo "OK";
	// }
	// else{
	// 	echo "ERROR";
	// }
	// }
 ?>