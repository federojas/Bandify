<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="public/styles/oldAuditionForm.css">
</head>
<div class="oldAuditionForm-div-0">
    <form id="${param.auditionFormId}">
        <div class="oldAuditionForm-div-1">
            <label class="oldAuditionForm-label-0">
      <span class="oldAuditionForm-span-0">
       Nombre *
      </span>
                <input id="name_${param.auditionFormId}" type="text" class="oldAuditionForm-input-0"/>
            </label>
            <label class="oldAuditionForm-label-1">
      <span class="oldAuditionForm-span-1">
       Apellido *
      </span>
                <input id="surname_${param.auditionFormId}" type="text" class="oldAuditionForm-input-1"/>
            </label>
        </div>
        <label class="oldAuditionForm-label-2">
     <span class="oldAuditionForm-span-2">
      Email *
     </span>
            <input id="email_${param.auditionFormId}" type="email" class="oldAuditionForm-input-2"/>
            <p class="oldAuditionForm-p-0">
                Ingrese un email correcto
            </p>
        </label>
        <label class="oldAuditionForm-label-3">
     <span class="oldAuditionForm-span-3">
      Número de telefono
     </span>
            <input id="phoneNumber_${param.auditionFormId}" type="tel" pattern="[0-9]{1,10}" maxlength="15"
                   class="oldAuditionForm-input-3"/>
            <p class="oldAuditionForm-p-1">
                Ingrese un número correcto
            </p>
        </label>
        <label class="oldAuditionForm-label-4">
     <span class="oldAuditionForm-span-4">
      Contale a la banda  tu experiencia
     </span>
            <textarea id="experience_${param.auditionFormId}" rows="4" class="oldAuditionForm-textarea-0">
     </textarea>
        </label>
        <div class="oldAuditionForm-div-2">
            <button id="formSubmit_${param.auditionFormId}" type="button" onclick="formSender(${param.auditionFormId})"
                    class="oldAuditionForm-button-0">
                Aplicar
            </button>
        </div>
    </form>
</div>
</html>
