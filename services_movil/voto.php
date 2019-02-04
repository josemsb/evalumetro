<?php
include('functions.php');
$v_consulta=$_GET["cs"];
$v_correo=$_GET["co"];
$v_Fecha=$_GET["f"];
$v_Candidato=$_GET["c"];
$v_TipoCandidato=$_GET["t"];
$v_Periodo=$_GET["p"];


        if($resultsetid=getSQLResultSet("SELECT `id`  FROM `usuario` WHERE correo='$v_correo'"))
        {  
            $id=0;
            while($rowid = mysqli_fetch_array($resultsetid)){$id=$rowid[0]; } 

            if($id<>0)
            {
                switch ($v_consulta)
                {
                    case "RegistrarVoto":

                        if($resultset=getSQLResultSet("SELECT `idUsuario`,`Fecha`,`idCandidato`,`tipoCandidato`,`idPeriodo`  FROM `voto` WHERE idUsuario=$id and tipoCandidato='$v_TipoCandidato'  and idPeriodo=$v_Periodo")){
                            while($row = mysqli_fetch_array($resultset))
                            {
                                ejecutarSQLCommand("INSERT INTO  `votoHistorial` (idUsuario,Fecha,idCandidato,tipoCandidato,idPeriodo)
                                VALUES (
                                '$row[0]' ,
                                '$row[1]' ,
                                '$row[2]' ,
                                '$row[3]' ,
                                '$row[4]' 
                                )"); 
                            }

                            ejecutarSQLCommand("DELETE FROM `voto` WHERE idUsuario=$id and tipoCandidato='$v_TipoCandidato' and idPeriodo=$v_Periodo ");
                        
                            ejecutarSQLCommand("INSERT INTO  `voto` (idUsuario,Fecha,idCandidato,tipoCandidato,idPeriodo)
                            VALUES (
                            '$id' ,
                            '$v_Fecha' ,
                            '$v_Candidato' ,
                            '$v_TipoCandidato' ,
                            '$v_Periodo' 
                            )");

                            echo "SR"; //SI REGISTRO VOTO

                        }
                        else {echo "NR"; //NO REGISTRO VOTO
                        }
                        $resultset->free();
                   
                    break;
                    
                    case "VerificarVoto":
                        if($resultset=getSQLResultSet("SELECT `idCandidato`  FROM `voto` WHERE idUsuario=$id and idCandidato=$v_Candidato  and idPeriodo=$v_Periodo")){
                        
                            $iv=0;
                            while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC)){$iv++;}
                            if($iv<>0){   
                                echo "VA"; //SI ES EL VOTO ACTUAL
                            }
                            else {
                                echo "VN"; //NO ES EL VOTO ACTUAL
                            }
                        }
                        $resultset->free();
                    break;
                }
            }
            else {echo "NR";}      

        }
        else {echo "NR";}
        $resultsetid->free();

    
?>