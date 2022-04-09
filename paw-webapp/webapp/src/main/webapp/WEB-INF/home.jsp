<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Bandify</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"
    />
  </head>
  <body class="bg-gray-100">
    <%--Navbar--%>
    <jsp:include page="navbar2.jsp">
      <jsp:param name="navItem" value="${1}" />
      <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <!-- Content -->
    <div class="content">
      <!-- Hero -->
      <div class="hero shadow-md border-8 border-white rounded-lg bg-white">
        <div class="hero-text">
          <div>
            <div class="font-bold text-4xl">
              <h1>Conectá con bandas y artistas cercanos</h1>
            </div>
            <p class="text-lg mt-3">
              Podés formar parte de bandas o formar la tuya!
            </p>
          </div>
          <div>
            <h5 class="text-center mt-12 text-lg">¿Qué estás buscando?</h5>
            <div class="buttons">
              <button
                type="button"
                class="post-button bg-sky-600 hover:bg-sky-700 pt-"
              >
                Artistas
              </button>
              <button
                type="button"
                class="post-button bg-sky-600 hover:bg-sky-700"
              >
                Banda
              </button>
            </div>
          </div>
        </div>
        <div class="hero-image">
          <img
            src="https://mir-s3-cdn-cf.behance.net/project_modules/2800_opt_1/a8a37687605991.5dcd4611da478.jpg"
            alt="Band"
          />
        </div>
      </div>

      <%--Formulario--%>
      <div class="post-form-container">
        <h1 class="home-h1-0">¿En busca de músicos para tu banda?</h1>
        <h2 class="home-h2-0">¡Crea una audición!</h2>
        <c:url value="/create" var="postPath" />
        <!-- Form box -->
        <div class="inner-box-form">
          <form:form
            modelAttribute="auditionForm"
            action="${postPath}"
            method="post"
          >
            <div>
              <form:label class="home-form-label" path="title">
                Titulo
              </form:label>
              <form:input type="text" class="home-form-input" path="title" />

              <form:errors path="title" element="p"> </form:errors>
            </div>
            <div>
              <form:label class="home-form-label" path="description">
                Descripción
              </form:label>
              <form:input
                class="home-form-input"
                type="text"
                path="description"
              />

              <form:errors path="description" element="p"> </form:errors>
            </div>
            <div>
              <form:label class="home-form-label" path="location">
                Localización
              </form:label>
              <form:select
                class="home-form-input"
                path="location"
                multiple="false"
              >
                <c:forEach
                  var="location"
                  items="${locationList}"
                  varStatus="loop"
                >
                  <form:option value="${location.id}">
                    ${location.name}
                  </form:option>
                </c:forEach>
              </form:select>
            </div>
            <div>
              <form:label class="home-form-label" path="email">
                Email de la banda
              </form:label>
              <form:input type="text" class="home-form-input" path="email" />

              <form:errors path="email" element="p"> </form:errors>
            </div>
            <div>
              <label class="home-form-label" for="musicGenres"> Géneros </label>
              <form:select
                class="home-form-input"
                path="musicGenres"
                multiple="true"
              >
                <c:forEach var="genre" items="${genreList}" varStatus="loop">
                  <form:option value="${genre.id}"> ${genre.name} </form:option>
                </c:forEach>
              </form:select>
            </div>
            <div>
              <label class="home-label-1" for="lookingFor"> Buscando </label>
              <form:select
                class="home-form-input"
                path="lookingFor"
                multiple="true"
              >
                <c:forEach var="role" items="${roleList}" varStatus="loop">
                  <form:option value="${role.id}"> ${role.name} </form:option>
                </c:forEach>
              </form:select>
            </div>
            <div class="post-button-div">
              <button
                type="submit"
                class="post-button bg-sky-600 hover:bg-sky-700"
              >
                Publicar
              </button>
            </div>
          </form:form>
        </div>
      </div>

      <%--Publicaciones de audiciones--%>
      <div class="posts">
        <c:forEach var="audition" items="${auditionList}" varStatus="loop">
          <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="${audition.id}" />
            <jsp:param name="postCard" value="${1}" />
            <jsp:param name="bandName" value="${audition.bandId}" />
            <jsp:param name="auditionDate" value="${audition.creationDate}" />
            <jsp:param name="auditionTitle" value="${audition.title}" />
            <jsp:param
              name="auditionLookingFor"
              value="${audition.lookingFor}"
            />
            <jsp:param
              name="auditionMusicGenres"
              value="${audition.musicGenres}"
            />
            <jsp:param
              name="auditionLocation"
              value="${audition.location.name}"
            />
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
