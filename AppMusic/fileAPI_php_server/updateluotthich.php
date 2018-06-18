<?php 
	require "connect.php";

	$luotthich = $_POST['luotthich'];
	$idbaihat = $_POST['idbaihat'];

	$query = "SELECT LuotThich FROM baihat WHERE idBaiHat = '$idbaihat'";
	$dataluotthich = mysqli_query($con,$query);
	$row = mysqli_fetch_assoc($dataluotthich);
	//Lấy tổng lượt thích từ server
	$tongluotthich = $row['LuotThich'];  

	if (isset($luotthich)) {
		$tongluotthich = $tongluotthich + $luotthich;
		$querysum = "UPDATE baihat SET LuotThich = '$tongluotthich' WHERE idBaiHat = '$idbaihat'";
		$dataupdate = mysqli_query($con,$querysum);
		if ($dataupdate) {
			echo "Successed!";
		}
		else{
			echo "Failed!";
		}
	}
 ?>