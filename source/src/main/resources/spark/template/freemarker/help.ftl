<#import "template.ftl" as layout />

<@layout.template title="${lang.enviarComentario}">
<#include "*/snipets/nav.ftl">
<form class="w3-container w3-section" action="/help" method="post">

    <label for="name" class="w3-label">${lang.nombreApellidos}</label>
    <input class="w3-input" type="text" id="name" name="name" required>

    <label for="email" class="w3-label">${lang.eCorreo}</label>
    <input class="w3-input" type="email" id=="email" name="email" required>

    <label for="topic" class="w3-label">${lang.asunto}</label>
    <input class="w3-input" type="text" id="topic" name="topic" required>

    <label for="subject" class="w3-label">${lang.tema}</label>
    <select class="w3-select" id="subject" name="subject">
        <#list subjects as subject>
            <option value="${subject.id}">${subject.code}</option>
        </#list>
    </select>

    <label for="text" class="w3-label">${lang.escribaComentario}</label>
    <textarea class="w3-input" id="text" name="text" rows="8" cols="60" required></textarea>

    <p><input class="w3-btn w3-teal" type="submit" value="${lang.enviar}"></p>
</form>
</@layout.template>