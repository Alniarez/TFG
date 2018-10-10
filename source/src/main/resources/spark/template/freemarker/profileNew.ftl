<#import "template.ftl" as layout />
<@layout.template title="${lang.crearUsuario}">
    <#include "*/snipets/nav.ftl">
    <#include "*/snipets/messages.ftl">
<form class="w3-container" action="/newUser" method="post">
    <p>
        <label class="w3-label" for="login">${lang.nombreAcceso}</label>
        <input id="login" name="login" class="w3-input" type="text" required>
    </p>
    <p>
        <label class="w3-label" for="name">${lang.nombreApellidos}</label>
        <input id="name" name="name" class="w3-input" type="text" required>
    </p>
    <p>
        <label class="w3-label" for="email">${lang.eCorreo}</label>
        <input id="email" name="email" class="w3-input" type="email" required>
    </p>
    <p>
        <label class="w3-label w3-validate" for="new1">${lang.clave}</label>
        <input id="new1" name="new1" class="w3-input" type="password" required>
    </p>
    <p>
        <label class="w3-label w3-validate" for="new2">${lang.claveRepetir}</label>
        <input id="new2" name="new2" class="w3-input" type="password" required>
    </p>
    <p>
        <input type="submit" class="w3-btn w3-padding w3-teal" value="${lang.crearUsuario}"/>
    </p>
</form>
</@layout.template>