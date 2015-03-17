<?php
	$userhost = "root"; 
    $passhost = ""; 
    $host = "localhost"; 
    $dbname = "letusknow";
	
	$conn = mysql_connect($host,$userhost,$passhost)or die("cannot connect");
	mysql_select_db($dbname)or die("cannot select DB");
?>