<?php 
header( 'Content-Type: text/html;charset=utf-8' );



function ejecutarSQLCommand($commando){
 
  $mysqli = new mysqli("mysql5012.site4now.net", "db_a19ab1_user", "005154-g", "db_a19ab1_voto");

    /* check connection */
    if ($mysqli->connect_errno) {
        printf("Connect failed: %s\n", $mysqli->connect_error);
        exit();
    }

    if ( $mysqli->multi_query($commando)) {
        if ($resultset = $mysqli->store_result()) {
            while ($row = $resultset->fetch_array(MYSQLI_BOTH)) {
                
            }
            $resultset->free();
        }
        
    
    }
    $mysqli->close();
}


function getSQLResultSet($commando){ 
  $mysqli = new mysqli("mysql5012.site4now.net", "db_a19ab1_user", "005154-g", "db_a19ab1_voto");
  /* check connection */
  if ($mysqli->connect_errno) {
      printf("Connect failed: %s\n", $mysqli->connect_error);
      exit();
  }

  if ( $mysqli->multi_query($commando)) {
    return $mysqli->store_result();
	  
}
$mysqli->close();
}


?>



