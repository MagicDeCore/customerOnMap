<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-customer" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><a class="button" href="${createLink(uri: '/upload')}"><g:message code="Import from CSV" /></a></li>
                <li><a class="button" href="${createLink(uri: '/location/index')}"><g:message code="Show all Cusctomers" /></a></li>
            </ul>
        </div>
        <div id="list-customer" class="content scaffold-list" role="main">
            <h1><g:message code="Customers List" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table class="pagination">
                <tr><td><g:form action="index" method="GET">
                            <g:textField name="name" placeholder="by Name" value="${params.name}"/>
                        </g:form>
                    </td>
                    <td><g:form action="index" method="GET">
                            <g:textField name="email" placeholder="by Email" value="${params.email}"/>
                        </g:form>
                    </td>
                    <td><g:form action="index" method="GET">
                            <g:textField name="street" placeholder="by Street" value="${params.street}"/>
                        </g:form>
                    </td>
                    <td><g:form action="index" method="GET">
                            <g:textField name="zip" placeholder="by Zipcode" value="${params.zip}"/>
                        </g:form>
                    </td>
                </tr>
            </table>
            <f:table collection="${customerList}" />

            <div class="pagination">
                <g:paginate total="${customerCount ?: 0}" />
            </div>
        </div>
    </body>
</html>