<?php
include("config_db.php");

$passcode = $_GET["passcode"];


$query = mysql_query("SELECT customer.passcode FROM customer WHERE passcode ='".$passcode."'");


if($passcode == null){
	echo "No complease ";
}

else{
	if ($value = mysql_num_rows($query) > 0) {
	
	while($e = mysql_fetch_assoc($query)){
		
	$output[]=$e;
	}

	
}

print(json_encode($output));

}


	


/*$intNumRows = mysql_num_rows($query);


if ($intNumRows > 0) {
	
	echo "pass";

}else {
	echo "loginfail";
}

echo (json_encode($query));*/



?> 



