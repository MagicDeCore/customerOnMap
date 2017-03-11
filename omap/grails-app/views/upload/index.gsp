<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'upload.label', default: 'Upload')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-upload" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                %{--<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>--}%
            </ul>
        </div>
        <div id="list-upload" class="content scaffold-list" role="main">
            <h1><g:message code="Choose file for upload" args="[entityName]" /> </h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>


            <g:form action="upload" method="post" enctype="multipart/form-data" useToken="true" >
                <table style="margin: auto; border-top: none!important; margin-bottom: 70px;">
                    <tr>
                        <td>&nbsp;</td>
                        <td valign="top" class="name">
                            <label for="payload">File</label>
                        </td>
                        <td valign="top">
                            <input type="file" id="payload" name="filecsv"/>
                        </td>
                        <td valign="top">
                            <input type="submit" class="upload" value="submit"/>
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </g:form>





            <f:table collection="${uploadList}" />

        %{--<div class="pagination">--}%
        %{--<g:paginate total="${uploadCount ?: 0}" />--}%
        %{--</div>--}%
        </div>
    </body>
</html>