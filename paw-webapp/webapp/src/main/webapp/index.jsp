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
<nav class="bg-white border-gray-200 px-2 sm:px-4 py-2.5 rounded dark:bg-gray-800">
    <div class="container flex flex-wrap justify-between items-center mx-auto">
        <a href="#" class="flex items-center">
            <img src="#" class="mr-3 h-6 sm:h-9" alt="Flowbite Logo" />
            <span class="self-center text-xl font-semibold whitespace-nowrap dark:text-white">Bandify</span>
        </a>
        <button data-collapse-toggle="mobile-menu" type="button" class="inline-flex items-center p-2 ml-3 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="mobile-menu" aria-expanded="false">
            <span class="sr-only">Open main menu</span>
            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z" clip-rule="evenodd"></path></svg>
            <svg class="hidden w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/200    0/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
        </button>
        <h1 class="text-3xl flex justify-center font-bold p-5 text-center">Welcome to Bandify!</h1>

        <div class="hidden w-full md:block md:w-auto" id="mobile-menu">

            <ul class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium">
                <li>
                    <svg

                            xmlns="http://www.w3.org/2000/svg"
                            width="24"
                            height="24"
                            viewBox="0 0 24 24"
                            fill="none"
                            stroke="currentColor"
                            stroke-width="2"
                            stroke-linecap="round"
                            stroke-linejoin="round">
                        <polyline points="22 12 16 12 14 15 10 15 8 12 2 12"></polyline>
                        <path
                                d="M5.45 5.11L2 12v6a2 2 0 0 0 2 2h16a2 2 0 0 0
				2-2v-6l-3.45-6.89A2 2 0 0 0 16.76 4H7.24a2 2 0 0 0-1.79 1.11z"></path>
                    </svg>

                    <a href="#" class="block py-2 pr-4 pl-3 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 dark:text-white" aria-current="page">Home</a>

                </li>
                <li>
                    <a href="#" class="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-gray-400 md:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700">About</a>
                </li>

            </ul>
        </div>
    </div>
</nav>

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
  </body>
</html>
