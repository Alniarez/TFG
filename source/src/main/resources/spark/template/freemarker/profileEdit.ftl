<#import "template.ftl" as layout />
<@layout.template title="${lang.modificarUsuario}">
    <#include "*/snipets/nav.ftl">
    <#include "*/snipets/messages.ftl">
<#if user??>
<div class="w3-row">
	<div class="w3-half">
            <div class="w3-container w3-teal">
                <h2>${lang.modificarDatos}</h2>
            </div>
            <form class="w3-container" action="/profile/edit/${user.id}" method="post">
                <p>
                    <label class="w3-label" for="login">${lang.nombreAcceso}</label>
                    <input id="login" name="login" class="w3-input" type="text" value="${user.login}" required>
                </p>
                <p>
                    <label class="w3-label" for="name">${lang.nombreApellidos}</label>
                    <input id="name" name="name" class="w3-input" type="text" value="${user.name}" required>
                </p>
                <p>
                    <label class="w3-label" for="email">${lang.eCorreo}</label>
                    <input id="email" name="email" class="w3-input" type="email" value="${user.email}" required>
                </p>
                <p>
                    <div class="w3-tooltip">
                        <label class="w3-label">${lang.imagen}</label><br>
                        <img src="${user.gravatar}" class="w3-circle" style="width:60px">
                        <span class="w3-text"><a href="http://gravatar.com">${lang.visitarGravatar}</a></span>
                     </div>
                </p>
                <p>
                    <input type="submit" class="w3-btn w3-padding w3-teal" value="${lang.modificarDatos}"/>
                </p>
            </form>
        </div>
        <div class="w3-half">
            <div class="w3-container w3-teal">
                <h2>${lang.cambiarClave}</h2>
            </div>
            <form class="w3-container" action="/profile/edit/${user.id}/pass" method="post">
                <p>
                    <label class="w3-label w3-validate" for="new1">${lang.clave}</label>
                    <input id="new1" name="new1" class="w3-input" type="password" required>
                </p>
                <p>
                    <label class="w3-label w3-validate" for="new2">${lang.claveRepetir}</label>
                    <input id="new2" name="new2" class="w3-input" type="password" required>
                </p>
                <p>
                    <input type="submit" class="w3-btn w3-padding w3-teal" value="${lang.cambiarClave}"/>
                </p>
            </form>
        </div>
    </div>
    <#include "*/snipets/admin/editProfileButtoms.ftl">
	<#if AuthenticatedUser.admin>	    
	<div class="w3-container w3-teal">
		<h2>${lang.asignaciones}</h2>
	</div>
	<#if assignedSubjects??>
	<div class="w3-containe"">
		<ul class="w3-ul">
	    	<#list assignedSubjects as subjectSelectedIterator>
	        	<li>${subjectSelectedIterator.code}</li>
	        </#list>
	    </ul>
	</div>
	</#if>
	<div class="w3-container w3-row">
	<#if unassignedSubjects??>
		<form class="w3-half" action="/profile/edit/${user.id}/asign"  method="post">
			<h3>${lang.asignarTematica}</h3>
		    <select class="w3-select" name="subject">
			<#list unassignedSubjects as usubjectSelectedIterator>
				<option value="${usubjectSelectedIterator.id}">${usubjectSelectedIterator.code}</option>
			</#list>
			</select>
			<p><input class="w3-btn w3-teal" type="submit" value="${lang.asignar}"/></p>	
	    </form>
	</#if>
	<#if assignedSubjects??>
		<form class="w3-half" action="/profile/edit/${user.id}/unasign" method="post">
		<h3>${lang.quitarAsignarTematica}</h3>
			<select class="w3-select" name="subject">
			<#list assignedSubjects as subjectSelectedIterator>
				<option value="${subjectSelectedIterator.id}">${subjectSelectedIterator.code}</option>
			</#list>
			</select>
			<p><input class="w3-btn w3-teal" type="submit" value="${lang.eliminar}"/></p>	
	    </form>
	</#if>
	</div>
	</#if>
</#if>
</@layout.template>