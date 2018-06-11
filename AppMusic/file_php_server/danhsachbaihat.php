<?php 
	require "connect.php";

	class Baihat{
		function Baihat($idbaihat,$tenbaihat,$hinhbaihat,$tencasi,$linkbaihat,$luotthich){
			$this->idBaiHat = $idbaihat;
			$this->TenBaiHat = $tenbaihat;
			$this->HinhBaiHat = $hinhbaihat;
			$this->CaSi = $tencasi;
			$this->LinkBaiHat = $linkbaihat;
			$this->LuotThich = $luotthich;

		}
	}
	$arraydanhsachbaihat = array();

	if(strlen($_POST['idquangcao']) > 0 && isset($_POST['idquangcao'])){

		$idquangcao = $_POST['idquangcao'];
		$queryquangcao ="SELECT * FROM quangcao WHERE id = '$idquangcao'";
		$dataquangcao = mysqli_query($con,$queryquangcao);
		$rowquangcao = mysqli_fetch_assoc($dataquangcao);
		$id = $rowquangcao['idBaiHat'];
		$query = "SELECT * FROM baihat WHERE idBaiHat = '$id'";
	}
	$data = mysqli_query($con,$query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arraydanhsachbaihat, new Baihat($row['idBaiHat']
													,$row['TenBaiHat']
													,$row['HinhBaiHat']
													,$row['CaSi']
													,$row['LinkBaiHat']
													,$row['LuotThich']));
	}
	echo json_encode($arraydanhsachbaihat);

	// if($data){
	// 	echo "OK";
	// }
	// else{
	// 	echo "FAIL";
	// }

	//echo $id;
 ?>