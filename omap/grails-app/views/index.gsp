<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Customers</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<content tag="nav">
    <li class="dropdown">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <div class="nav" id="loginHeader">
                <a class="home" href="${createLink(uri: '/user/index')}"><g:message code="Manage Users"/></a>
            </div>
        </sec:ifAnyGranted>
    </li>
    <li class="dropdown">
        <sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_USER">
            <div class="nav" id="loginHeader">
                <a class="home" href="${createLink(uri: '/customer/index')}"><g:message code="Manage Customers"/></a>
            </div>
        </sec:ifAnyGranted>
    </li>
</content>

<div class="svg" role="presentation">
    <div class="grails-logo-container">
        <asset:image src="reid-4.png" class="grails-logo"/>
    </div>
</div>

</body>
</html>
