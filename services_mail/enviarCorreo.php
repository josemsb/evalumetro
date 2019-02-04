<?php
include("class.phpmailer.php"); //you have to upload class files "class.phpmailer.php" and "class.smtp.php"
 

function enviarCorreo($clave,$correo){
 
    $mail = new PHPMailer();
 
    $mail->IsSMTP();
    $mail->SMTPAuth = true;
    $mail->SMTPSecure = "tls";
    $mail->Host = "smtp.gmail.com";
    $mail->Port = 587;
    $mail->Username = "informes@evaluometro.pe";
    $mail->Password = "005154-gG";
    
    $mail->From = "informes@evaluometro.pe";
    $mail->FromName = "Evaluometro";
    
    $mail->AddAddress($correo,"Usuario");
    $mail->Subject = "Solo falta un paso!";

    $sURL = " href='http://evaluometro.pe/services_movil/validarUsuario.php?c=";
    $sCorte = "&cc=";
    $sCorte1 = "' ";

    $sURLFinal = $sURL .  $clave . $sCorte . $correo . $sCorte1;
    
    $shtml = file_get_contents('http://evaluometro.pe/services_mail/plantilla/index.html');
    $cuerpo = str_replace(' href="#" ',$sURLFinal,$shtml);

    $mail->Body = $cuerpo;

    $mail->WordWrap = 50;
    $mail->IsHTML(true);
    
    if(!$mail->Send()) 
    {
        //echo "Mailer Error: " . $mail->ErrorInfo;
        return "Error enviando";
    } 
    else {
        //echo "Message has been sent";
        return  "Correo enviado" ;
    }
  }


?>
