<?php
include "connect.php";
$query = "SELECT idsp,sanphammoi.tensp, COUNT(`soluong`) As tong FROM `chitietdonhang` INNER JOIN sanphammoi ON sanphammoi.id = chitietdonhang.idsp GROUP BY `idsp`";
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
		'message' => "khong thanh cong",
		'result' => $result
	];
}
print_r(json_encode($arr));

?>