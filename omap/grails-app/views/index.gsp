<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Customers</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
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

    %{--<div id="content" role="main">--}%
        %{--<section class="row colset-2-its">--}%
            %{--<h1>Welcome to Grails</h1>--}%

            %{--<p>--}%
                %{--Congratulations, you have successfully started your first Grails application! At the moment--}%
                %{--this is the default page, feel free to modify it to either redirect to a controller or display--}%
                %{--whatever content you may choose. Below is a list of controllers that are currently deployed in--}%
                %{--this application, click on each to execute its default action:--}%
            %{--</p>--}%

            %{--<div id="controllers" role="navigation">--}%
                %{--<h2>Available Controllers:</h2>--}%
                %{--<ul>--}%
                    %{--<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">--}%
                        %{--<li class="controller">--}%
                            %{--<g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>--}%
                        %{--</li>--}%
                    %{--</g:each>--}%
                %{--</ul>--}%
            %{--</div>--}%
        %{--</section>--}%
    %{--</div>--}%

</body>
</html>
