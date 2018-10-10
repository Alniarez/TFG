<#import "template.ftl" as layout />
<@layout.template title="${lang.identificarse}">
<#include "*/snipets/nav.ftl">
<#include "*/snipets/messages.ftl">
<form class="w3-container w3-section" action="/login" method="post">
    <label for="name" class="w3-label"><i class="fa fa-male"></i> ${lang.nombreAcceso}</label>
    <input class="w3-input" type="text" id="name" name="name" value="${username!}" required>

    <label for="password" class="w3-label"><i class="fa fa-lock"></i> ${lang.clave}</label>
    <input class="w3-input" type="password" id="password" name="password" required>

    <p><input class="w3-btn w3-teal" type="submit" value="${lang.enviar}"></p>
</form>
</@layout.template>