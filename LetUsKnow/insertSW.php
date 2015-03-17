<?php

include("config_db.php");

$comment = $_POST["comment"];


$query = mysql_query("INSERT INTO notification (comment) VALUES('$comment')");

  if($query){
        $json = array("1");
		printf("insert corect");
    }else{
        $json = array("0");
		printf("insert incorect");
              
    }


print(json_encode($json));
?>


