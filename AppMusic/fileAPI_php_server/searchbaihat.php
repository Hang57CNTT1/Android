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
	$mangcakhuc = array();
	if(isset($_POST['tukhoa']))
	{
		$tukhoa = $_POST['tukhoa'];
		$query = "SELECT * FROM baihat WHERE lower(TenBaiHat) LIKE '%tukhoa%'";
		$data = mysqli_query($con,$query);
		while ($row = mysqli_fetch_assoc($data)) {
			array_push($mangcakhuc, new Baihat($row['idBaiHat'],
												,$row['TenBaiHat']
												,$row['HinhBaiHat']
												,$row['CaSi']
												,$row['LinkBaiHat']
												,$row['LuotThich']));
		}

	}
	echo json_encode($mangcakhuc);
	// if($data){
	// 	echo "ok";
	// }
	// else{
	// 	echo "failed"
	// }
 ?>