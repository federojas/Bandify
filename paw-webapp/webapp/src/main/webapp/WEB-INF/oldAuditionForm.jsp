<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <div
            class="border-solid border-2 border-sky-100 rounded-xl mt-2 max-w-sm mx-auto py-3 px-6"
    >
        <form  id="${param.auditionFormId}">
            <div class="flex">
                <label class="block">
                    <span class="mt-5 block text-sm font-medium text-slate-700">Nombre *</span>
                        <input
                                id="name_${param.auditionFormId}"
                                type="text"
                                class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                        />
                    </label>
                    <label class="block pl-6" >
                    <span class="mt-5 block text-sm font-medium text-slate-700"
                    >Apellido *</span
                    >
                        <input
                                id="surname_${param.auditionFormId}"
                                type="text"
                                class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                        />
                    </label>
            </div>
            <label class="block mt-5">
            <span class="block text-sm font-medium text-slate-700"
            >Email *</span
            >
                <input
                        id="email_${param.auditionFormId}"
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
            >Número de telefono</span
            >
                <input
                        id="phoneNumber_${param.auditionFormId}"
                        type="tel"
                        pattern="[0-9]{1,10}"
                        maxlength="15"
                        class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                />
                <p
                        class="mt-1 invisible peer-invalid:visible text-red-700 text-xs"
                >
                    Ingrese un número correcto
                </p>
            </label>
            <label class="block mt-2">
            <span class="block text-sm font-medium text-slate-700"
            >Contale a la banda  tu experiencia</span
            >
                <textarea
                        id="experience_${param.auditionFormId}"
                        rows="4"
                        class="mt-1 block w-full px-3 py-2 bg-white border border-slate-300 rounded-md text-sm shadow-sm placeholder-slate-400 focus:outline-none focus:border-sky-500 focus:ring-1 focus:ring-sky-500 disabled:bg-slate-50 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none invalid:border-pink-500 invalid:text-pink-600 focus:invalid:border-pink-500 focus:invalid:ring-pink-500"
                >
            </textarea>
            </label>

            <div class="flex flex-row-reverse">
                <button
                        id="formSubmit_${param.auditionFormId}"
                        type="button"
                        onclick="formSender(${param.auditionFormId})"
                        class="mt-4 bg-sky-600 hover:bg-sky-700 px-5 py-2 leading-5 rounded-full font-semibold text-white"
                >
                    Aplicar
                </button>



            </div>
        </form>
    </div>
</html>
