<?php
include('functions.php');
$v_consulta=$_GET["c"];
$v_idRegion=$_GET["r"];
$v_idProvincia=$_GET["p"];

switch ($v_consulta){

    case "getRegionActivo":
    if($resultset=getSQLResultSet("SELECT id as i,Nombre as n FROM `region` WHERE Activo='S'")){
       
        $rawdata = array(); 
        $i=0;
        while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
        {
            $rawdata[$i] = $row;
            $i++;
        }
        echo json_encode($rawdata);
    }

    break;

    case "getProvinciaActivo":
    if($resultset=getSQLResultSet("SELECT p.Id as i,p.Nombre as n FROM `provincia` p inner join `region` r on p.idRegion=r.id WHERE r.id=$v_idRegion and p.Activo='S'")){
        $rawdata = array(); 
        $i=0;
        while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
        {
            $rawdata[$i] = $row;
            $i++;
        }
        echo json_encode($rawdata);
    }
    break;

    case "getDistritoActivo":
    if($resultset=getSQLResultSet("SELECT d.Id as i,d.Nombre as n FROM `distrito` d inner join `provincia` p on d.idProvincia=p.id WHERE p.id=$v_idProvincia and d.Activo='S'")){
        $rawdata = array(); 
        $i=0;
        while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
        {
            $rawdata[$i] = $row;
            $i++;
        }
        echo json_encode($rawdata);
    }
    break;

   
}

?>
