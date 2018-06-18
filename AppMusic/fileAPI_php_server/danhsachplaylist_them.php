<?php 
	require "connect.php";

	$query = "SELECT * FROM playlist";
	$data = mysqli_query($con,$query);

	class Danhsachplaylist{
		//chỉ gọi lại từ khóa của Model Playlist
		function Danhsachplaylist($idplaylist,$ten,$hinh,$icon){
			$this->idPlayList = $idplaylist;
			$this->Ten = $ten;
			$this->HinhNen = $hinh;
			$this->HinhIcon = $icon;
		}
	}

	$arrayplaylist = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrayplaylist, new Danhsachplaylist($row['idPlayList'],
														$row['Ten'],
														$row['HinhNen'],
														$row['HinhIcon']));
	}
	echo json_encode($arrayplaylist);
 ?>