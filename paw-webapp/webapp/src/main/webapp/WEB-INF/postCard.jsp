<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    function toggleModal(modalID){
        document.getElementById(modalID).classList.toggle("hidden");
        document.getElementById(modalID + "-backdrop").classList.toggle("hidden");
        document.getElementById(modalID).classList.toggle("flex");
        document.getElementById(modalID + "-backdrop").classList.toggle("flex");
    }
</script>
<div class="bg-gray-100 p-6 flex flex-row justify-center">
    <div class=" bg-white p-1 flex flex-row justify-center shadow-lg rounded-lg mx-2 md:mx-auto my-5 max-w-md md:max-w-2xl "><!--horizantil margin is just for display-->
        <div class=" flex items-start px-1 py-1">
            <div class="">
                <div class="flex items-center justify-between">
                    <h2 class="text-lg font-semibold text-gray-900 -mt-1"><b> <c:out value="${param.bandName}" /> </b> </h2>
                    <small class="text-sm text-gray-700">22h ago</small>
                </div>
                <p class="text-gray-700">${param.auditionDate}</p>
                <h1><b> <c:out value="${param.auditionTitle}" /> </b></h1>
                <p class="mt-3 text-gray-700 text-sm">
                    <c:out value="${param.auditionDescription}" />

                </p>
                <div class="flex flex-row-reverse"><button class="justify-end mt-4 bg-sky-600 hover:bg-sky-700 px-5 py-2 leading-5 rounded-full font-semibold text-white" type="button" onclick="toggleModal('modal_${param.Id}')">
                    Aplicar
                </button></div>
                

            </div>
        </div>
    </div>

</div>

<div class="hidden overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none justify-center items-center" id="modal_${param.Id}">
    <div class="relative w-auto my-6 mx-auto max-w-3xl">
        <!--content-->
        <div class="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
            <!--header-->
            <div class="flex items-start justify-between p-5 border-b border-solid border-slate-200 rounded-t">
                <h3 class="text-3xl font-semibold">
                    <c:out value="${param.bandName}"/>

                </h3>
                <button class="p-1 ml-auto bg-transparent border-0 text-black opacity-5 float-right text-3xl leading-none font-semibold outline-none focus:outline-none" onclick="toggleModal('modal_${param.Id}')">
          <span class="bg-transparent text-black opacity-5 h-6 w-6 text-2xl block outline-none focus:outline-none">
            ×
          </span>
                </button>
            </div>
            <!--body-->
            <div class="relative p-6 flex-auto">
                <p class="my-4 text-slate-500 text-lg leading-relaxed">
                    <c:out value="${param.auditionDescription}"/>
                </p>

                <%--AUDITION FORM--%>
                <div class="flex justify-center">
                    <jsp:include page="oldAuditionForm.jsp">
                        <jsp:param name="auditionForm" value="${1}" />
                        <jsp:param name="auditionFormId" value="${param.Id}" />
                        <jsp:param name="bandName" value="${param.bandName}" />
                    </jsp:include>
                </div>
            </div>
            <!--footer-->
            <div class="flex flex-row justify-end p-6 border-t border-solid border-slate-200 rounded-b">
                <button class=" mt-4 text-red-500 background-transparent font-bold uppercase px-5 py-2 text-sm outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150" type="button" onclick="toggleModal('modal_${param.Id}')">
                    cerrar
                </button>
                <button class="mt-4 bg-sky-600 hover:bg-sky-700 px-5 py-2 leading-5 rounded-full font-semibold text-white" onclick="toggleModal('modal_${param.Id}')">
                    Enviar
                </button>
            </div>
        </div>
    </div>
</div>
<div class="hidden opacity-25 fixed inset-0 z-40 bg-black" id="modal-id-backdrop"></div>

