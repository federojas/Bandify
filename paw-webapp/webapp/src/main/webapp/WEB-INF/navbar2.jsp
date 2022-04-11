<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  .bandify-nav {
    background-color: #1c041c;
  }
</style>
<html>
  <head> </head>
  <!-- <nav>
    <div class="nav-wrapper purple px-10">
        <a href="#" class="brand-logo">Bandify</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li class="active"><a class="nav-items" href="#">Inicio</a></li>
        </ul>
    </div>
</nav> -->

  <nav class="bandify-nav px-4 py-2.5 flex items-center">
    <div class="container flex flex-wrap justify-between items-center mx-auto">
      <a href="#" class="flex items-center">
        <img
            src="public/images/logo.png"
            class="mr-3 h-16"
            alt="Bandify Logo"
          />
        <span
          class="self-center text-xl font-semibold whitespace-nowrap text-white"
          >bandify</span
        >
      </a>

      <div class="w-full md:block md:w-auto" id="mobile-menu">
        <ul
          class="flex flex-col mt-4 md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium"
        >
          <li>
            <a
              href="#"
              class="block py-2 pr-4 pl-3 text-white rounded"
              >Home</a
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>
</html>
