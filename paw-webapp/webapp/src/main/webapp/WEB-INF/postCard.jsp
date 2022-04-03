<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<script>
    function click()
    {
        $('#dialog').dialog({
            autoOpen: false,
            width: 250,
            height: 180,
            modal : true
        });
    }
</script>
<div class="bg-gray-100 p-6 flex flex-row justify-center">
    <div class=" bg-white p-1 flex flex-row justify-center shadow-lg rounded-lg mx-2 md:mx-auto my-5 max-w-md md:max-w-2xl "><!--horizantil margin is just for display-->
        <div class=" flex items-start px-1 py-1">
            <img class="w-12 h-12 rounded-full object-cover mr-4 shadow"src="https://yt3.ggpht.com/ytc/AKedOLR1VOl8ziwo8xxVOl7z9Nb4bAtaDS_Gw0fsCPk0vQ=s900-c-k-c0x00ffffff-no-rj" alt="avatar">
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
                <div class="flex justify-end">
                    <button
                            onclick="click()"
                            class="mt-4 bg-sky-600 hover:bg-sky-700 px-5 py-2 leading-5 rounded-full font-semibold text-white"
                    >
                        Aplicar
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>