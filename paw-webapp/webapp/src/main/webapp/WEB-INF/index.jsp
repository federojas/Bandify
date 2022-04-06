<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
  var titulo="hholas";

</script>
<style>
  .form{
    max-height: 40rem;
  }
</style>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="style.css" />
    <style>
      .h1 {
        font-size: 6.5em;
        margin: 200px;
      }
    </style>
</head>
<body>
<%--<%@ include file="navbar.jsp" %>--%>
<jsp:include page="navbar.jsp">
  <jsp:param name="navItem" value="${1}" />
  <jsp:param name="name" value="Bandify" />

</jsp:include>
    <!-- CONTENT -->
    <div class="bg-gray-100 p-6 flex flex-row justify-center">
      <%--FORM DE LA BANDA      --%>
      <div class="bg-white rounded-tr-xl rounded-bl-xl shadow p-6 ml-5 form">
      <div class="mt-10 pr-3 ">
        <h6 class="px-2">¿ tenés una banda y necesitas musicos?</h6>
        <div
                class="border-solid border-2 border-sky-100 rounded-xl mt-2 max-w-sm mx-auto py-3 px-6"
        >
          <form>
            <label class="block">
                <span class="block text-sm font-medium text-slate-700"
                >Nombre de la Banda *</span
                >
              <input
                      type="text"
                      class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
              />
            </label>
            <label class="block mt-5">
                <span class="block text-sm font-medium text-slate-700"
                >Email de contacto*</span
                >
              <input
                      type="email"
                      class="peer mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
              />
              <p
                      class="mt-1 invisible peer-invalid:visible text-red-700 text-xs"
              >
                Ingrese un email correcto
              </p>
            </label>
            <label class="block mt-2">
                <span class="block text-sm font-medium text-slate-700"
                >Que tipo de musico estas buscando </span
                >
              <textarea
                      rows="4"
                      class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
              >
                </textarea>
            </label>

            <div class="flex flex-row-reverse">
              <button
                      class="mt-4 bg-sky-600 hover:bg-sky-700 px-5 py-2 leading-5 rounded-full font-semibold text-white"
              >
                Aplicar
              </button>
            </div>
          </form>
        </div>
      </div>
      </div>
      <!-- SCROLL de PUBLICACIONES -->
      <div class="flex flex-col">
        <c:forEach var="audition" items="${auditionList}" varStatus="loop">
          <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="${audition.id}" />
            <jsp:param name="postCard" value="${1}" />
            <jsp:param name="bandName" value="${audition.title}" />
            <jsp:param name="auditionDate" value="${audition.creationDate}" />
            <jsp:param name="auditionTitle" value="${audition.lookingFor}" />
            <jsp:param name="auditionDescription" value="<b>hola</b>" />
          </jsp:include>
        </c:forEach>


        <jsp:include page="postCard.jsp">
          <jsp:param name="Id" value="$asd" />
          <jsp:param name="postCard" value="${1}" />
          <jsp:param name="bandName" value="$asdsda}" />
          <jsp:param name="auditionDate" value="$asdasd}" />
          <jsp:param name="auditionTitle" value="$aasdada" />
          <jsp:param name="auditionDescription" value="<b>hola</b>" />
        </jsp:include>
        <jsp:include page="postCard.jsp">
          <jsp:param name="Id" value="$asd" />
          <jsp:param name="postCard" value="${1}" />
          <jsp:param name="bandName" value="$asdsda}" />
          <jsp:param name="auditionDate" value="$asdasd}" />
          <jsp:param name="auditionTitle" value="$aasdada" />
          <jsp:param name="auditionDescription" value="<b>hola</b>" />
        </jsp:include>
        <jsp:include page="postCard.jsp">
          <jsp:param name="Id" value="$asd" />
          <jsp:param name="postCard" value="${1}" />
          <jsp:param name="bandName" value="$asdsda}" />
          <jsp:param name="auditionDate" value="$asdasd}" />
          <jsp:param name="auditionTitle" value="$aasdada" />
          <jsp:param name="auditionDescription" value="<b>hola</b>" />
        </jsp:include>
        <jsp:include page="postCard.jsp">
          <jsp:param name="Id" value="$asd" />
          <jsp:param name="postCard" value="${1}" />
          <jsp:param name="bandName" value="$asdsda}" />
          <jsp:param name="auditionDate" value="$asdasd}" />
          <jsp:param name="auditionTitle" value="$aasdada" />
          <jsp:param name="auditionDescription" value="<b>hola</b>" />
        </jsp:include>
      </div>


    </div>

</body>
</html>
