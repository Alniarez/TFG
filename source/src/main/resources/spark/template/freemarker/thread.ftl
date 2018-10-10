<#import "template.ftl" as layout />
<@layout.template title="${lang.tema}: ${thread.topic}">
<#include "*/snipets/nav.ftl">
<div class="w3-container">
	<h3>
		${subject.code}
		<#if thread.open>
	    	<span class="w3-tag w3-red">${lang.abierto}</span>
	    <#else>
	        <span class="w3-tag w3-green">${lang.resuelto}</span>
	    </#if>
	</h3>
</div>
<div class="w3-container w3-padding-8">
	<div class="w3-quarter w3-container">
		<img src="${thread.gravatar}" class="w3-left w3-margin-right w3-padding-8" style="width:80px">
        <span class="w3-xlarge">${thread.name}</span><br>
        <span>${thread.email}</span>
	</div>
    <div class="w3-half w3-container">
    	<div class="w3-container w3-pale-red w3-leftbar w3-border-red">
        	<p>${thread.date}</p>
        </div>
        <div class="w3-container w3-pale-red">
            <p>${thread.text}</p>
        </div>
    </div>
</div>
<#list messages as message>
<div class="w3-container w3-padding-8">
    <div class="w3-quarter w3-container">
        <img src="${message.user.gravatar}" class="w3-left w3-margin-right w3-padding-8" style="width:80px">
        <span class="w3-xlarge">${message.user.name}</span><br>
        <span>${message.user.email}</span>
    </div>
    <div class="w3-half w3-container">
        <div class="w3-container w3-pale-blue w3-leftbar w3-border-blue">
            <p>${message.date}</p>
        </div>
        <div class="w3-container w3-pale-blue">
            <p>${message.text}</p>
        </div>
    </div>
</div>
</#list>
<#if thread.open>
<div class="w3-row w3-section">
	<div class="w3-container w3-half">
		<form action="/thread/${thread.id}/reply" method="post">
			<h4>${lang.responder}</h4>
			<textarea class="w3-input w3-border" id="text" name="text" rows="8" cols="60" required></textarea>
			<input id="replyButton" type="submit" class="hidden"/>
		</form>
		<form action="/thread/${thread.id}/close" method="post">
			<input id="closeButton" type="submit" class="hidden"/>
		</form>
		<div class="w3-container w3-section">
			<label class="w3-btn w3-teal" for="replyButton"><i class="fa fa-plus-square-o"></i> ${lang.responder}</label>
			<label class="w3-btn w3-teal" for="closeButton"><i class="fa fa-times-circle"></i> ${lang.cerrarTema}</label>
		</div>
	</div>
	<form class="w3-container w3-rest" action="/thread/${thread.id}/subject" method="post">
		<h4>${lang.cambiarTematica}</h4>
		<select class="w3-select" id="subjectIterator" name="subject">
			<#list subjects as subjectIterator>
				<#if subjectIterator.id == subject.id>
					<option value="${subjectIterator.id}" selected>${subjectIterator.code}</option>
				<#else>
					<option value="${subjectIterator.id}">${subjectIterator.code}</option>
				</#if>
			</#list>
		</select>
		<p><input class="w3-btn w3-teal" type="submit" value="${lang.cambiarTematica}"/></p>
	</form>
</div>
<#else>
<div class="w3-container w3-section w3-border w3-hover-border-red">
	<p>${lang.temaEstaCerrado}</p>
</div>
</#if>
<style>
    .hidden {
        display: none;
    }
</style>
</@layout.template>