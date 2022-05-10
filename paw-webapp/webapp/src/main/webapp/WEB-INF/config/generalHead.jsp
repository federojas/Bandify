<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<meta charset="UTF-8"/>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="<c:url value="https://cdn.tailwindcss.com"/>"></script>
<script type = "text/javascript"
        src = "<c:url value="https://code.jquery.com/jquery-2.1.1.min.js"/>"></script>
<link rel="icon" type="image/x-icon"
      href="<c:url value="/resources/images/favi.ico"/>"/>
<link
        rel="stylesheet"
        href="<c:url value="https://fonts.googleapis.com/css?family=Questrial"/>"
/>
<style>
    body {
        font-family: "Questrial", sans-serif;
    }
</style>
<script src="<c:url value="/resources/js/jquery.timeago.js"/>" type="text/javascript"></script>
<script>
    $(document).ready(function(){
        if ($('#langspan').text() == 'ES') {
            (function (factory) {
                if (typeof define === 'function' && define.amd) {
                    define(['jquery'], factory);
                } else if (typeof module === 'object' && typeof module.exports === 'object') {
                    factory(require('jquery'));
                } else {
                    factory(jQuery);
                }
            }(function (jQuery) {
                // Spanish
                jQuery.timeago.settings.strings = {
                    prefixAgo: "hace",
                    prefixFromNow: "dentro de",
                    suffixAgo: "",
                    suffixFromNow: "",
                    seconds: "menos de un minuto",
                    minute: "un minuto",
                    minutes: "unos %d minutos",
                    hour: "una hora",
                    hours: "%d horas",
                    day: "un d\u00eda",
                    days: "%d d\u00edas",
                    month: "un mes",
                    months: "%d meses",
                    year: "un año",
                    years: "%d años"
                };
            }));
        }
    });
</script>
<title><spring:message code="title.generalHead"/> </title>
