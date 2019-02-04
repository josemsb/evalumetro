<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<body>
<?php
include("class.phpmailer.php"); //you have to upload class files "class.phpmailer.php" and "class.smtp.php"
 
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
 
$mail->AddAddress("josemsb@gmail.com","Usuario");
$mail->Subject = "Solo falta un paso!";

$sURL = " href='http://www.google.com.pe' ";

$shtml = file_get_contents('plantilla/index.html');
$cuerpo = str_replace(' href="#" ',$sURL,$shtml);

$mail->Body = $cuerpo;

$mail->WordWrap = 50;
$mail->IsHTML(true);
 
if(!$mail->Send()) {
echo "Mailer Error: " . $mail->ErrorInfo;
} else {
echo "Message has been sent";
}
?>
</body>
</html>