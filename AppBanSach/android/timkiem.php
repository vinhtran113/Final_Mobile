<?php
include "connect.php";
$search = $_POST['search'];
if (empty($search)) {
	$arr = [
		'success' => false,
		'message' => "ko thanh cong",
	];
}

$query = "SELECT * FROM `sanphammoi` WHERE `tensp` LIKE '%".$search."%'";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)){
	$result[] = ($row);
	// code...
}
if (!empty($result)) {
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	];
}else {
	$arr = [
		'success' => false,
		'message' => "Tên sách không tồn tại",
		'result' => $result
	];
}
print_r(json_encode($arr));
?>