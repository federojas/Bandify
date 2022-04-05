<%--
  Created by IntelliJ IDEA.
  User: Lolo
  Date: 4/4/2022
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<div class="bg-white rounded-tr-xl rounded-bl-xl shadow p-6 ml-5">
    <div class="mt-10 pr-3">
        <h6 class="px-2">¿ tenés una banda y necesitas musicos?</h6>
        <div
                class="border-solid border-2 border-sky-100 rounded-xl mt-2 max-w-sm mx-auto py-3 px-6"
        >
            <form>
                <label class="block">
                <span class="block text-sm font-medium text-slate-700"
                >Nombre *</span
                >
                    <input
                            id="name"
                            type="text"
                            class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                    />
                </label>
                <label class="block">
                <span class="mt-5 block text-sm font-medium text-slate-700"
                >Apellido *</span
                >
                    <input
                            id="surname"
                            type="text"
                            class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                    />
                </label>
                <label class="block mt-5">
                <span class="block text-sm font-medium text-slate-700"
                >Email de contacto*</span
                >
                    <input
                            id="email"
                            type="email"
                            class="peer mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                    />
                    <p
                            class="mt-1 invisible peer-invalid:visible text-red-700 text-xs"
                    >
                        Ingrese un email correcto
                    </p>
                </label>
                <label class="block">
                <span class="mt-1 block text-sm font-medium text-slate-700"
                >Un número de telefono</span
                >
                    <input
                            type="text"
                            class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                    />
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
</html>
