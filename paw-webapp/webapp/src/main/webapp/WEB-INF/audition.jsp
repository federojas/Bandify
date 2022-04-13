<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
  <head>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Questrial"
    />
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><spring:message code="home.title"/></title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css" />
    <link rel="stylesheet" href="public/styles/postCard.css" />
    <link rel="stylesheet" href="public/styles/audition.css" />
    <style>
      body {
        display: flex;
        flex-direction: column;
        background-color: #f3f4f6;
      }
      .audition-content {
        justify-content: center;
        align-items: center;

        padding: 2rem 0;
      }
      .card-extern {
        width: auto;
        margin: 1.5rem auto;
        max-width: 48rem /* 768px */;
      }
      .card-content {
        background-color: #ffffff;
        box-shadow: 0 10px 15px -3px #1c041c1a, 0 4px 6px -2px #1c041c0d;
        padding: 1rem;
      }
      .card-header {
        padding: 1.25rem;
      }
      .card-header > h3 {
        font-size: 2.25rem;
        line-height: 2.5rem;
        font-weight: 600;
      }
      .card-body {
        padding: 1.25rem;
        font-size: 1.25rem;
        line-height: 1.5rem;
        font-weight: 300;
      }
      .even-columns {
        display: flex;
      }
      .even-columns > * {
        flex-basis: 100%;
      }
      .card-info {
        padding: 0.5rem;
        font-weight: 500;
      }

      li.info-item:not(:last-child) {
        margin-bottom: 1rem;
      }
      .tag {
        --tw-bg-opacity: 1;
        background-color: rgb(
          243 244 246 / var(--tw-bg-opacity)
        ); /* gray-100 */
        padding: 0.5rem;
        margin: 0.5rem;
        border-radius: 0.375rem;
        justify-content: center;
        width: fit-content;
      }
      .tag-list {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
      }
      .description {
        word-break: break-all;
        white-space: normal;
      }

    </style>
  </head>
  <body class="flex flex-col">
    <%--Navbar--%>
    <jsp:include page="navbar.jsp">
      <jsp:param name="navItem" value="${2}" />
      <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <div class="audition-content">
      <div class="card-extern">
        <!--content-->
        <div class="card-content">
          <!--header-->
          <div class="card-header">
            <h3>
              <c:out value="${audition.title}" />
            </h3>
          </div>
          <!--body-->
          <div class="card-body">
            <div class="even-columns">
              <!-- Left body part (info) -->
              <div class="card-info">
                <ul>
                    <li class="info-item">
                      <b> <spring:message code="audition.about"/> </b>
                      <br />
                      <p class="description">
                        <c:out value="${audition.description}" />
                      </p>
                    </li>
                  <li class="info-item">
                    <b> <spring:message code="audition.location"/> </b>
                    <br />
                    <div class="tag">
                      <c:out value="${audition.location.name}" />
                    </div>
                  </li>
                  <li class="info-item">
                    <b> <spring:message code="audition.desired"/> </b>
                    <br />
                    <div class="tag-list">
                      <c:forEach
                        var="item"
                        items="${audition.lookingFor}"
                        varStatus="loop"
                      >
                        <div class="tag">${item.name}</div>
                      </c:forEach>
                    </div>
                  </li>
                  <li class="info-item">
                    <b> <spring:message code="audition.genres"/> </b>
                    <br />
                    <div class="tag-list">
                      <c:forEach
                        var="item"
                        items="${audition.musicGenres}"
                        varStatus="loop"
                      >
                        <div class="tag">${item.name}</div>
                      </c:forEach>
                    </div>
                  </li>
                </ul>
              </div>
              <!-- Right body part (form) -->
              <div>
                <jsp:include page="applicationForm.jsp">
                  <jsp:param name="auditionForm" value="${1}" />
                  <jsp:param name="auditionEmail" value="${audition.email}" />
                  <jsp:param name="auditionFormId" value="${audition.id}" />
                </jsp:include>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
