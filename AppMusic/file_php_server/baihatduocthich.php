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
	$arraycasi = array();
	$query = "SELECT * FROM baihat ORDER BY LuotThich DESC LIMIT 5";
	$data = mysqli_query($con,$query);
	while($row = mysqli_fetch_assoc($data)){
		array_push($arraycasi, new Baihat($row['idBaiHat']
										,$row['TenBaiHat']
										,$row['HinhBaiHat']
										,$row['CaSi']
										,$row['LinkBaiHat']
										,$row['LuotThich']));

	}
	echo json_encode($arraycasi);
 ?>