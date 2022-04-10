<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ page
contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" href="public/styles/postCard.css" />
    <style>
      .even-columns {
        display: flex;
      }
      .even-columns > * {
        flex-basis: 100%;
      }
    </style>
    <script>
      function toggleModal(modalID) {
        document.getElementById(modalID).classList.toggle("hidden");
        document
          .getElementById(modalID + "-backdrop")
          .classList.toggle("hidden");
        document.getElementById(modalID).classList.toggle("flex");
        document.getElementById(modalID + "-backdrop").classList.toggle("flex");
      }
    </script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  </head>
  <body>
    <div class="postCard-div-0 shadow-lg">
      <div class="postCard-div-1">
        <h2 class="postCard-h2-0">
          <b>
            <c:out value="${param.auditionTitle}" />
          </b>
        </h2>
        <p class="postCard-p-0">
          <c:out value="${param.auditionDate}" />
        </p>
      </div>

      <ul>
        <li class="flex flex-row">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            width="14"
            height="14"
            class="mr-2"
          >
            <path
              d="M13.987,6.108c-.039.011-7.228,2.864-7.228,2.864a2.76,2.76,0,0,0,.2,5.212l2.346.587.773,2.524A2.739,2.739,0,0,0,12.617,19h.044a2.738,2.738,0,0,0,2.532-1.786s2.693-7.165,2.7-7.2a3.2,3.2,0,0,0-3.908-3.907ZM15.97,9.467,13.322,16.51a.738.738,0,0,1-.692.49c-.1-.012-.525-.026-.675-.378l-.908-2.976a1,1,0,0,0-.713-.679l-2.818-.7a.762.762,0,0,1-.027-1.433l7.06-2.8a1.149,1.149,0,0,1,1.094.32A1.19,1.19,0,0,1,15.97,9.467ZM12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,22A10,10,0,1,1,22,12,10.011,10.011,0,0,1,12,22Z"
            />
          </svg>
          <c:out value="${param.auditionLocation}" />
        </li>
        <li class="flex flex-row mb-10 flex-wrap">
          <c:forEach
            var="item"
            items="${requestScope.lookingFor}"
            varStatus="loop"
          >
            <div class="bg-gray-200 p-2 rounded-md justify-center tag m-2">
                ${item.name}
            </div>

          </c:forEach>
        </li>
      </ul>
      <div class="postCard-div-3">
        <button
          class="postCard-button-0 hover:bg-sky-700"
          type="button"
          onclick="toggleModal('modal_${param.Id}')"
        >
          Ver más
        </button>
      </div>
    </div>
    <div
      class="hidden overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none justify-center items-center"
      id="modal_${param.Id}"
    >
      <div class="relative w-auto my-6 mx-auto max-w-3xl">
        <!--content-->
        <div class="postCard-div-6">
          <!--header-->
          <div class="postCard-div-7">
            <h3 class="postCard-h3-0">
              Aplicación para Banda
              <c:out value="${param.bandName}" />
            </h3>
          </div>
          <!--body-->
          <div class="postCard-div-8">
            <div class="even-columns">
              <div>
                <ul>
                  <li>
                    <b> Ubicación </b>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <c:out value="${param.auditionLocation}" />
                  </li>
                  <li>
                    <b> Instrumentos deseados </b>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <c:forEach
                      var="item"
                      items="${requestScope.lookingFor}"
                      varStatus="loop"
                    >
                      ${item.name}
                    </c:forEach>
                  </li>
                  <li>
                    <b> Interes en géneros </b>
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <c:forEach
                      var="item"
                      items="${requestScope.musicGenres}"
                      varStatus="loop"
                    >
                      ${item.name}
                    </c:forEach>
                  </li>
                </ul>
              </div>
              <div>
                <jsp:include page="applicationForm.jsp">
                  <jsp:param name="auditionForm" value="${1}" />

                  <jsp:param name="auditionFormId" value="${param.Id}" />

                  <jsp:param name="bandName" value="${param.bandName}" />
                </jsp:include>
              </div>
            </div>
          </div>
          <!--footer-->
          <div class="postCard-div-9">
            <button
              class="postCard-button-1"
              type="button"
              onclick="toggleModal('modal_${param.Id}')"
            >
              Cerrar
            </button>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
