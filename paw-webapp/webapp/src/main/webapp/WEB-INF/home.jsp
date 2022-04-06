<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
contentType="text/html;charset=UTF-8" language="java" %>
<style>
  .form{
    max-height: 50rem;
  }
</style>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
  </head>
  <body class="bg-gray-100 ">
    <%--Navbar--%>
    <jsp:include page="navbar.jsp">
      <jsp:param name="navItem" value="${1}" />
      <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <!-- Content -->
    <div class="bg-gray-100 px-5 py-10 flex flex-row justify-evenly form">
      <%--Formulario--%>
      <div class="bg-white rounded-tr-xl rounded-bl-xl shadow p-6 form">
        <div class="max-w-sm mx-auto">
          <div>
            <h1 class="font-bold text-lg text-center">¿En busca de músicos para tu banda?</h1>
                <h2 class="font-semibold text-lg text-center">¡Crea una audición!</h2>
          </div>
          <c:url value="/create" var="postPath" />
          <!-- Form box -->
          <div
            class="border-solid border-2 border-sky-100 rounded-xl mt-2 max-w-sm mx-auto py-3 px-6"
          >
            <form:form
              modelAttribute="auditionForm"
              action="${postPath}"
              method="post"
            >
              <div>
                <form:label
                  class="block text-sm font-medium text-slate-700"
                  path="title"
                  >Titulo
                </form:label>
                <form:input
                  type="text"
                  class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                  path="title"
                />
                <form:errors path="title" element="p" />
              </div>
              <div>
                <form:label
                  class="block mt-5 text-sm font-medium text-slate-700"
                  path="description"
                  >Descripción
                </form:label>
                <form:input
                  class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                  type="description"
                  path="description"
                />
                <form:errors path="description" element="p" />
              </div>
              <div>
                <form:label
                  class="block mt-5 text-sm font-medium text-slate-700"
                  path="location"
                  >Ubicación
                </form:label>
                <form:input
                  type="location"
                  class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                  path="location"
                />
                <form:errors path="location" element="p" />
              </div>
              <div>
                <label
                  class="block mt-5 text-sm font-medium text-slate-700"
                  for="musicGenres"
                  >Géneros
                </label>
                <form:select
                  class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                  path="musicGenres"
                  multiple="true"
                >
                  <form:option value="rock">Rock</form:option>
                  <form:option value="cumbia">Cumbia</form:option>
                </form:select>
              </div>
              <div>
                <label
                  class="block mt-5 text-sm font-medium text-slate-700"
                  for="lookingFor"
                  >Buscando
                </label>
                <form:select
                  class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                  path="lookingFor"
                  multiple="true"
                >
                  <form:option value="guitarrista">Guitarrista</form:option>
                  <form:option value="baterista">Baterista</form:option>
                </form:select>
              </div>
              <div class="flex flex-row-reverse">
                <button
                  type="submit"
                  class="mt-4 bg-sky-600 hover:bg-sky-700 px-5 py-2 leading-5 rounded-full font-semibold text-white"
                >
                  Publicar
                </button>
              </div>
            </form:form>
          </div>
        </div>
      </div>

      <%--Publicaciones de audiciones--%>
      <div class="flex flex-col">
        <c:forEach var="audition" items="${auditionList}" varStatus="loop">
          <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="${audition.id}" />
            <jsp:param name="postCard" value="${1}" />
            <jsp:param name="bandName" value="${audition.bandId}" />
            <jsp:param name="auditionDate" value="${audition.creationDate}" />
            <jsp:param name="auditionTitle" value="${audition.title}" />
            <jsp:param name="auditionLookingFor" value="${audition.lookingFor}" />
            <jsp:param name="auditionMusicGenres" value="${audition.musicGenres}" />
            <jsp:param name="auditionLocation" value="${audition.location}" />
            <jsp:param
              name="auditionDescription"
              value="${audition.description}"
            />
          </jsp:include>
        </c:forEach>
      </div>
    </div>
  </body>
</html>
