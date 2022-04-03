<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

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
  <jsp:param name="perro" value="fico" />

</jsp:include>
    <!-- CONTENT -->
    <div class="bg-gray-100 p-6 flex flex-row justify-center">
      <!-- SCROLL de PUBLICACIONES -->
      <div class="mr-5">
        <div
          class="bg-white mt-5 p-6 max-w-sm mx-auto rounded-xl shadow-lg flex items-center space-x-4 hover:bg-gray-50"
        >
          <div class="shrink-0">
            <img
              class="h-12 w-12"
              src="https://yt3.ggpht.com/ytc/AKedOLR1VOl8ziwo8xxVOl7z9Nb4bAtaDS_Gw0fsCPk0vQ=s900-c-k-c0x00ffffff-no-rj"
              alt="La Renga"
            />
          </div>
          <div>
            <div class="text-xl font-medium text-black">
              Baterista experimentado
            </div>
            <p class="text-slate-500">La Renga</p>
          </div>
        </div>
      </div>
      <!-- INFO de PUBLICACION -->
      <div class="bg-white rounded-tr-xl rounded-bl-xl shadow p-6 ml-5">
        <!-- Titulo -->
        <div class="text-xl font-bold text-black">Baterista experimentado</div>
        <!-- Info adicional -->
        <div class="text-base text-black">
          <span>La Renga</span>
          <span> - </span>
          <span class="text-red-500">Hace 10 horas</span>
        </div>
        <ul class="mt-3">
          <li>
            <div class="text-base font-normal">
              <span class="font-bold">Instrumento: </span>
              <span>Bateria</span>
            </div>
          </li>
          <li>
            <div class="text-base font-normal">
              <span class="font-bold">Genero: </span>
              <span>Rock</span>
            </div>
          </li>
          <li>
            <div class="text-base font-normal">
              <span class="font-bold">Ciudad: </span>
              <span>Cordoba, Argentina</span>
            </div>
          </li>
        </ul>
        <!-- Postularse -->
        <div class="mt-10">
          <h6 class="px-2">Estas interesado?</h6>
          <div
            class="border-solid border-2 border-sky-100 rounded-xl mt-2 max-w-sm mx-auto py-3 px-6"
          >
            <form>
              <label class="block">
                <span class="block text-sm font-medium text-slate-700"
                  >Nombre artistico *</span
                >
                <input
                  type="text"
                  class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                />
              </label>
              <label class="block mt-5">
                <span class="block text-sm font-medium text-slate-700"
                  >Email *</span
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
                  >Contanos de vos</span
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

    </div>
<!-- POST CARD -->
<jsp:include page="postCard.jsp">
  <jsp:param name="postCard" value="${1}" />
  <jsp:param name="bandName" value="${auditionList[0].title}" />
  <jsp:param name="auditionDate" value="${auditionList[0].creationDate}" />
  <jsp:param name="auditionTitle" value="${auditionList[0].lookingFor}" />
  <jsp:param name="auditionDescription" value="${auditionList[0].description}" />
</jsp:include>



</body>
</html>
