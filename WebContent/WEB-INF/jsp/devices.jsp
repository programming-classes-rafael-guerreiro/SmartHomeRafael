<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>SmartHome - Devices</title>

		<link href="<c:url value='/css/style.css' />" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<c:forEach items="${data}" var="userDevice">
			<p class="${userDevice.even ? 'even' : 'odd'}">${userDevice.formattedRow}</p>
		</c:forEach>
	</body>
</html>