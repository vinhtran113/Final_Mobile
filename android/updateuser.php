<?php
include "connect.php";
$email = $_POST['email'];
$pass = $_POST['pass'];
$username = $_POST['username'];
$mobile = $_POST['mobile'];
$id = $_POST['id'];

$query = 'UPDATE `user` SET `email`="'.$email.'",`pass`="'.$pass.'",`username`="'.$username.'",`mobile`="'.$mobile.'" WHERE `id`='.$id;
$data = mysqli_query($conn,$query);
if ($data == true ) {
	// code...
	$arr = [
		'success' => true,
		'message' => "Thành công"	
		];
}else{
	$arr = [
		'success' => false,
		'message' => "Không thành công"	
		];
	}
print_r(json_encode($arr));
?>