<?php
include('functions.php');
include("../services_mail/enviarCorreo.php");
$v_consulta=$_GET["c"];
$v_Edad=$_GET["e"];
$v_Region=$_GET["r"];
$v_Provincia=$_GET["p"];
$v_Distrito=$_GET["d"];
$v_Correo=$_GET["cor"];
$v_Contrasena=$_GET["con"];
$v_Sexo=$_GET["s"];

switch ($v_consulta){
    case "registrarUsuario":

        $v_PassCifrado = password_hash($v_Contrasena,PASSWORD_DEFAULT);

        if($resultset=getSQLResultSet("CALL pa_RegistrarUsuario('$v_Edad', $v_Region,$v_Provincia,$v_Distrito,'$v_Correo','$v_PassCifrado','$v_Sexo')")){
            $rawdata = array(); 
            $i=0;
            while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
            {
                $rawdata[$i] = $row;
                $i++;
            }
            if($i>0)
            {
                if(strnatcmp ($row[0],"Registro satisfactorio")){
                    $sRespuestaEnvio=enviarCorreo($v_PassCifrado,$v_Correo);
                }
            }
            //echo $sRespuestaEnvio;
            echo json_encode($rawdata);
        }
    break; 
    case "validarUsuario":

        if($resultset=getSQLResultSet("CALL pa_ValidarCorreo('$v_Correo')")){
            $rawdataVacio = array(); 
            $i=0;
            while($row = mysqli_fetch_array($resultset))
            {
                if(password_verify($v_Contrasena,$row[0]))
                {
                    $i++;
                } 
            }
            if($i>0)
            {
                if($resultset1=getSQLResultSet("CALL pa_ValidarUsuario('$v_Correo')")){
                    $rawUsuario = array(); 
                    $j=0;
                    while($row1 = mysqli_fetch_array($resultset1,MYSQLI_ASSOC))
                    {
                        $rawUsuario[$j] = $row1;
                        $j++;
                    }
                    echo json_encode($rawUsuario);
                }
            }
            else{ echo json_encode($rawdataVacio);}
           
        }
    break; 

}

?>

