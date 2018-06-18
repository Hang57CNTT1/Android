<?php 
	require "connect.php";

	class Theloai{
		function Theloai($idtheloai,$idkeychude,$tentheloai,$hinhtheloai){
			$this->idTheLoai = $idtheloai;
			$this->idChuDe = $idkeychude;
			$this->TenTheLoai = $tentheloai;
			$this->HinhTheLoai = $hinhtheloai;
		}
	}

	class Chude{
		function Chude($idchude,$tenchude,$hinhchude){
			$this->idChuDe = $idchude;
			$this->TenChuDe =  $tenchude;
			$this->HinhChuDe = $hinhchude;

		}
	}

	$arraytheloai = array();
	$arraychude = array();

	$querytheloai = "SELECT DISTINCT * FROM theloai ORDER BY rand(".date("Ymd").") LIMIT 4";
	$datatheloai = mysqli_query($con,$querytheloai);
	while ($row = mysqli_fetch_assoc($datatheloai)){
		array_push($arraytheloai, new Theloai($row['idTheLoai']
											,$row['idChuDe']
											,$row['TenTheLoai']
											,$row['HinhTheLoai']));
	}


	$querychude = "SELECT DISTINCT * FROM chude ORDER BY rand(".date("Ymd").") LIMIT 4";
	$datachude = mysqli_query($con,$querychude);
	while ($row = mysqli_fetch_assoc($datachude)){
		array_push($arraychude, new Chude($row['idChuDe']
											,$row['TenChuDe']
											,$row['HinhChuDe']));
	}

	$arraychudetheloai = array('Theloai'=>$arraytheloai,'Chude'=>$arraychude);

	echo json_encode($arraychudetheloai);
	//Tạo cấu trúc dưới dạng Json

	// if($datatheloai){
	// 	echo "OK";
	// }
	// else{
	// 	echo "FALSE";
	// }
 ?>