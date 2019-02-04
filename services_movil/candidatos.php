<?php
include('functions.php');
$v_consulta=$_GET["c"];
$v_Tipo=$_GET["t"];
$v_idTipo=$_GET["i"];
$v_idPeriodo=$_GET["p"];

switch ($v_consulta){
    case "getListado":
        if($v_Tipo=="R"){
            if($resultset=getSQLResultSet("CALL pa_ListarCandidatosMovilRegion($v_idTipo, $v_idPeriodo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="P"){  
            if($resultset=getSQLResultSet("CALL pa_ListarCandidatosMovilProvincia($v_idTipo, $v_idPeriodo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="D"){
            if($resultset=getSQLResultSet("CALL pa_ListarCandidatosMovilDistrito($v_idTipo, $v_idPeriodo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="C"){  
            if($resultset=getSQLResultSet("CALL pa_ListarDatosCandidato($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="EU"){  
            if($resultset=getSQLResultSet("CALL pa_ListarDatosEducacion($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="ENU"){  
            if($resultset=getSQLResultSet("CALL pa_ListarDatosEducacionNoUni($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="ET"){  
            if($resultset=getSQLResultSet("CALL pa_ListarDatosEducacionTecnica($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="SP"){  
            if($resultset=getSQLResultSet("CALL pa_ListarSentenciaPenal($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="SNO"){  
            if($resultset=getSQLResultSet("CALL pa_ListarSentenciaNoObligatoria($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="EX"){  
            if($resultset=getSQLResultSet("CALL pa_ListarExperienciaLaboral($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="CEP"){  
            if($resultset=getSQLResultSet("CALL pa_ListarDatosEleccionPopular($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="CP"){  
            if($resultset=getSQLResultSet("CALL pa_ListarDatosPartidario($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
        if($v_Tipo=="RE"){  
            if($resultset=getSQLResultSet("CALL pa_ListarDatosRenuncia($v_idTipo)")){
                $rawdata = array(); 
                $i=0;
                while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
                {
                    $rawdata[$i] = $row;
                    $i++;
                }
                echo json_encode($rawdata);
            }
        }
    break;

    case "CantidadVotosxCandidato":
    if($resultset=getSQLResultSet("CALL pa_CantidadVotosxCandidato($v_idTipo, $v_idPeriodo,'$v_Tipo')")){
       
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
