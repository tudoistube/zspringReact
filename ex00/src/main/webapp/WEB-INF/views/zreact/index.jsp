<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
  <head>
    <meta charset="utf-8">
    <title>1st React Component in Springframework3.</title>
  </head>

  <body>
    <h1>Greetings from JoyWins, <h1>

    <script>
     	let zmessage = '${zmessage}';
     	if (zmessage == 'Merry Christmas^_____^!!!') {
         	alert(zmessage);
     	}
     </script>

    <div id="zroot"></div>
    <script src="/xweb/resources/built/bundle.js" type="text/javascript"></script>
  </body>
</html>
