<#import "template.ftl" as layout />
<@layout.template title="${lang.perfil}">
    <#include "*/snipets/nav.ftl">
    <#include "*/snipets/messages.ftl">
    <#if user??>
    <div class="w3-row">
        <div class="w3-col w3-center" style="width:200px">
            <img src="${user.gravatar}" style="width:180px">
            <#if AuthenticatedUser.admin || AuthenticatedUser.id == user.id>
                <hr>
                <a href="/profile/edit/${user.id}" class="w3-btn w3-teal"><i class="fa fa-cog"></i> ${lang.editarPerfil}</a>

            </#if>
        </div>
        <#if assignedSubjects??>
	        <div class="w3-container w3-quarter">
	        	<ul class="w3-ul">
	        		<li><h2>${lang.asignaciones}</h2></li>
	        		<#list assignedSubjects as subjectSelectedIterator>
	        		<li>${subjectSelectedIterator.code}</li>
	        		</#list>
	        	</ul>
	  		</div>
	  	</#if>
        <div class="w3-container w3-rest">
            <ul class="w3-ul">
                <li>
                    <h3>${lang.nombreApellidos}</h3>
                    <p>${user.name}</p>
                </li>
                <li>
                    <h3>${lang.nombreAcceso}</h3>
                    <p>${user.login}</p>
                </li>
                <li>
                    <h3>${lang.eCorreo}</h3>
                    <p>${user.email}</p>
                </li>
                <li>
                    <h3>${lang.estado}</h3>
                    <p>
                        <#if user.active>
                            <span class="w3-tag	 w3-green">${lang.activo}</span>
                        <#else>
                            <span class="w3-tag w3-red">${lang.inactivo}</span>
                        </#if>
                    </p>
                </li>
                <li>
                    <h3>${lang.rol}</h3>
                    <p>
                        <#if user.admin>
                            <span class="w3-tag	 w3-light-green">${lang.administrador}</span>
                        <#else>
                            <span class="w3-tag w3-light-blue">${lang.usuario}</span>
                        </#if>
                    </p>
                </li>
            </ul>
        </div>
    </div>
    </#if>
</@layout.template>