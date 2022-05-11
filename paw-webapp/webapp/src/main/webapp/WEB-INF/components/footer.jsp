<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .languages-buttons{
            color: white; font-size: large;
            margin: 0 10px;
        }
        footer.page-footer {
            padding: 20px;
            background-color: #1c041c;
        }
        footer.page-footer .footer-copyright {
            color: rgba(255, 255, 255, 0.8);
        }
    </style>
</head>
<body>
<footer class="page-footer">
    <div class="footer-copyright">
        <div class="flex flex-row justify-between px-10">
            © 2022 Copyright: Bandify
            <div >
                <a class="languages-buttons right" href="<c:url value="?lang=es"/>">ES</a>
                <a class="languages-buttons right" href="<c:url value="?lang=en"/>">EN</a>
            </div>
        </div>
    </div>
</footer>
</body>
</html>
