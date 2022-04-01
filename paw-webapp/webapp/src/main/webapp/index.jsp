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
<h1 class="text-3xl font-bold p-5 text-center">Welcome to Bandify!</h1>

<div class="max-w-sm mx-auto bg-white shadow py-5 px-6">
    <form>
        <label class="block">
          <span class="block text-sm font-medium text-slate-700"
          >Nombre artístico *</span
          >
            <input
                    type="text"
                    class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
            />
        </label>

        <label class="block mt-5">
            <span class="block text-sm font-medium text-slate-700">Email *</span>
            <input
                    type="email"
                    class="peer mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
            />
            <p class="mt-2 invisible peer-invalid:visible text-pink-600 text-sm">
                Ingrese un email correcto
            </p>
        </label>
        <div class="flex flex-row-reverse">
            <button
                    class="mt-2 bg-sky-600 hover:bg-sky-700 px-5 py-2 leading-5 rounded-full font-semibold text-white"
            >
                Entrar
            </button>
        </div>
    </form>
</div>

<h2 class="mt-10 mb-5 text-2xl text-center">Busquedas de artistas</h2>
<div
        class="mt-5 p-6 max-w-sm mx-auto rounded-xl shadow-lg flex items-center space-x-4"
>
    <div class="shrink-0">
        <img
                class="h-12 w-12"
                src="https://yt3.ggpht.com/ytc/AKedOLR1VOl8ziwo8xxVOl7z9Nb4bAtaDS_Gw0fsCPk0vQ=s900-c-k-c0x00ffffff-no-rj"
                alt="ChitChat Logo"
        />
    </div>
    <div>
        <div class="text-xl font-medium text-black">La Renga</div>
        <p class="text=slate-500">
            Buscando batero para nuestra gira nacional!
        </p>
    </div>
</div>
</body>
</html>
