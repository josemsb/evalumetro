<?php
include('functions.php');
$v_clave=$_GET["c"];
$v_correo=$_GET["cc"];

if($resultset=getSQLResultSet("CALL pa_ActivarUsuario('$v_clave', '$v_correo')")){
  $rawdata = array(); 
  $i=0;
  while($row = mysqli_fetch_array($resultset,MYSQLI_ASSOC))
  {
      $rawdata[$i] = $row;
      $i++;
  }
}

?>
    
    
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>Bienvenido a EVALUOMETRO</title>
      <style type="text/css">
      body {padding-top: 0 !important;padding-bottom: 0 !important;padding-top: 0 !important;padding-bottom: 0 !important;margin:0 !important;width: 100% !important;-webkit-text-size-adjust: 100% !important;-ms-text-size-adjust: 100% !important;-webkit-font-smoothing: antialiased !important;}
      .tableContent img {border: 0 !important;display: block !important;outline: none !important;}
      a{color:#382F2E;}
      p, h1{color:#382F2E;margin:0;}
      p{text-align:left;color:#999999;font-size:14px;font-weight:normal;line-height:19px;}
      a.link1{color:#382F2E;}
      a.link2{font-size:16px;text-decoration:none;color:#ffffff;}
      h2{text-align:left;color:#222222; font-size:19px;font-weight:normal;}
      div,p,ul,h1{margin:0;}
      .bgBody{background: #ffffff;}
      .bgItem{background: #ffffff;}
	
      @media only screen and (max-width:480px)
      {
      table[class="MainContainer"], td[class="cell"] {width: 100% !important;height:auto !important; }
      td[class="specbundle"] {width:100% !important;float:left !important;font-size:13px !important;line-height:17px !important;display:block !important;padding-bottom:15px !important;}
      td[class="spechide"] {display:none !important;}
      img[class="banner"] {width: 100% !important;height: auto !important;}
      td[class="left_pad"] {padding-left:15px !important;padding-right:15px !important;}
      }
	
      @media only screen and (max-width:540px) 
      {	
      table[class="MainContainer"], td[class="cell"] {width: 100% !important;height:auto !important; }
      td[class="specbundle"] {width:100% !important;float:left !important;font-size:13px !important;line-height:17px !important;display:block !important;padding-bottom:15px !important;}
      td[class="spechide"] {display:none !important;}
      img[class="banner"] {width: 100% !important;height: auto !important;}
      .font {font-size:18px !important;line-height:22px !important;}
      .font1 {font-size:18px !important;line-height:22px !important;}
      }

    </style>
<script type="colorScheme" class="swatch active">
{
    "name":"Default",
    "bgBody":"ffffff",
    "link":"382F2E",
    "color":"999999",
    "bgItem":"ffffff",
    "title":"222222"
}
</script>
  </head>

  <body paddingwidth="0" paddingheight="0"   style="padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;" offset="0" toppadding="0" leftpadding="0">
    <table bgcolor="#ffffff" width="100%" border="0" cellspacing="0" cellpadding="0" class="tableContent" align="center"  style='font-family:Helvetica, Arial,serif;'>
      <tbody>
        <tr>
          <td>
            <table width="600" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#ffffff" class="MainContainer">
              <tbody>
                <tr>
                  <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tbody>
                          <tr>
                            <td valign="top" width="40">&nbsp;</td>
                            <td>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tbody>
                                <!-- =============================== Header ====================================== -->   
                                  <tr>
                                    <td height='25' class="spechide"></td>
                                      
                                      <!-- =============================== Body ====================================== -->
                                  </tr>
                                  <tr>
                                    <td class='movableContentContainer ' valign='top'>
                                      <div class="movableContent" style="border: 0px; padding-top: 0px; position: relative;">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                          <tbody>
                                            <tr>
                                              <td>
                                                <table  border="0" cellspacing="0" cellpadding="0">
                                                  <tbody>
                                                    <tr>
                                                      <td valign="top" align="center" class="specbundle">
                                                        <div class="contentEditableContainer contentTextEditable">
                                                            <div class="contentEditable">
                                                                <p style='text-align:center;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#222222;'>
                                                                    <span class="specbundle2"><span class="font1">Ahora ya eres parte de &nbsp;</span></span>
                                                                </p>
                                                            </div>
                                                        </div>
                                                      </td>
                                                      <td valign="top" class="specbundle">
                                                        <div class="contentEditableContainer contentTextEditable">
                                                            <div class="contentEditable">
                                                              <p style='text-align:center;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#2b5e20;'>
                                                                <span class="font">EVALUOMETRO</span> 
                                                              </p>
                                                            </div>
                                                        </div>
                                                      </td>
                                                    </tr>
                                                  </tbody>
                                                </table>
                                              </td>
                                                  </tr>
                                                </tbody>
                                              </table>
                                                      </div>
       
        <div class="movableContent" style="border: 0px; padding-top: 0px; position: relative;">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                          <tr><td height='35'></td></tr>
                          <tr>
                            <td align='left'>
                              <div class="contentEditableContainer contentTextEditable">
                                <div class="contentEditable" align='center'>
                                  <h2 >Ya puedes volver a tu aplicación e iniciar sesión.</h2>
                                </div>
                              </div>
                            </td>
                          </tr>

                          <tr>
                            <td align='left'>
                              <div class="contentEditableContainer contentTextEditable">
                                <div class="contentEditable" align='center'>
                                <img src="http://evaluometro.pe/services_mail/plantilla/images/inicio.png" width='200' height='355' alt='facebook icon' data-default="placeholder" data-max-width="200" data-customIcon="true">
                                </div>
                              </div>
                            </td>
                          </tr>
                          <tr><td height='20'></td></tr>
                        </table>
        </div>
        <div class="movableContent" style="border: 0px; padding-top: 0px; position: relative;">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody>
    <tr>
      <td  style='border-bottom:1px solid #DDDDDD;'></td>
    </tr>
    <tr><td height='25'></td></tr>
    <tr>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody>
    <tr>
      <td valign="top" class="specbundle"><div class="contentEditableContainer contentTextEditable">
                                      <div class="contentEditable" align='center'>
                                        <p  style='text-align:left;color:#CCCCCC;font-size:12px;font-weight:normal;line-height:20px;'>
                                          <span style='font-weight:bold;'>[Siguenos en FACEBOOK]</span>
                                        </p>
                                      </div>
                                    </div></td>
      <td valign="top" width="30" class="specbundle">&nbsp;</td>
      <td valign="top" class="specbundle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody>
    <tr>
      <td valign='top' width='52'>
                                    <div class="contentEditableContainer contentFacebookEditable">
                                      <div class="contentEditable">
                                        <a target='_blank' href="https://www.facebook.com/evaluometro/"><img src="http://evaluometro.pe/services_mail/plantilla/images/facebook.png" width='52' height='53' alt='facebook icon' data-default="placeholder" data-max-width="52" data-customIcon="true"></a>
                                      </div>
                                    </div>
                                  </td>
      <td valign="top" width="16">&nbsp;</td>
    </tr>
  </tbody>
</table>
</td>
    </tr>
  </tbody>
</table>
</td>
    </tr>
  </tbody>
</table>

        </div>
        
        <!-- =============================== footer ====================================== -->
      
      </td>
    </tr>
  </tbody>
</table>
</td>
      <td valign="top" width="40">&nbsp;</td>
    </tr>
  </tbody>
</table>
</td>
    </tr>
  </tbody>
</table>
</td>
    </tr>
  </tbody>
</table>


 
      </body>
      </html>