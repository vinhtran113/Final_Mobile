<?php
include "connect.php";

$id = $_POST['id'];

$query = 'DELETE FROM `user` WHERE `id`='.$id;
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
		'message' => "Xóa Không thành công"	
		];
	}
print_r(json_encode($arr));
?>