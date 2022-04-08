<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="public/styles/navbar.css">

<nav class="navbar-nav-0">
    <div class="navbar-div-0">
        <a href="#" class="flex items-center">
            <button class="navbar-button-0">
                <svg xmlns="http://www.w3.org/2000/svg" class="navbar-svg-0" fill="none" viewBox="0 0 24 24"
                     stroke="currentColor" strokeWidth="{2}">
                    <path strokeLinecap="round" strokeLinejoin="round"
                          d="M9 19V6l12-3v13M9 19c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zm12-3c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zM9 10l12-3">
                    </path>
                </svg>
                <span class="navbar-span-0">
       (Bandify Logo)
      </span>
            </button>
        </a>
        <button data-collapse-toggle="mobile-menu" type="button" class="navbar-button-1" aria-controls="mobile-menu"
                aria-expanded="false">
            <span class="navbar-span-1">
      Open main menu
     </span>
            <svg class="navbar-svg-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
                      clip-rule="evenodd">
                </path>
            </svg>
            <svg class="navbar-svg-2" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                      clip-rule="evenodd">
                </path>
            </svg>
        </button>
        <h1 class="navbar-h1-0">
            Bienvenido a ${param.name}!
        </h1>
        <div class="navbar-div-1" id="mobile-menu">
            <ul class="navbar-ul-0">
                <li>
                    <button class="navbar-button-2">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-1 w-1" fill="none" viewBox="0 0 24 24"
                             stroke="currentColor" strokeWidth="{2}">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                  d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6">
                            </path>
                        </svg>
                        <span class="navbar-span-2">
         Inicio
        </span>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>