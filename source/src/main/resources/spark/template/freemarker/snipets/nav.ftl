<nav>
    <ul class="w3-navbar w3-border w3-light-grey">
	<#if AuthenticatedUser??>
        <li><a href="/thread"><i class="fa fa-file-text-o"></i> ${lang.temas}</a></li>
        <li><a href="/profile/${AuthenticatedUser.id}"><i class="fa fa-user"></i> ${lang.perfil}</a></li>
    <#if AuthenticatedUser.admin>
        <li><a href="/admin"><i class="fa fa-gears"></i> ${lang.administracion}</a></li>
    </#if>
        <li><a href="/logout"><i class="fa fa-power-off"></i> ${lang.desconectarse}</a></li>
    </#if>
		<li class="w3-right">
		<div class="w3-dropdown-hover">
			<a class="w3-light-grey"><i class="fa fa-language w3-large"></i> ${lang.idioma}</a>
            <#include "*/snipets/lang.ftl">
  		</div>
		</li>
	</ul>
</nav>
