<?php  
	//lấy ra từ manager database đã tạo
	$hostname = "localhost";
	$username = "id6089340_hangandroid96";
	$password = "NTHangit08";
	$databasename = "id6089340_mp3zing";
	//câu lệnh truy vấn dữ liệu
	$con = mysqli_connect($hostname,$username,$password,$databasename);
	mysqli_query($con,"SET NAMES 'utf8'");
	// if($con ){
	// 	echo "Thanh cong";
	// }
	// else{
	// 	echo "Loi!";
	// }
?>