<#import "template.ftl" as layout />

<@layout.template title="${lang.administracion}">
    <#include "*/snipets/nav.ftl">
    <#include "*/snipets/messages.ftl">
    <div class="w3-row w3-padding-8">
        <a href="/users">
            <div class="w3-half w3-bottombar w3-hover-light-grey w3-padding"><i class="fa fa-users"></i> ${lang.usuarios}</div>
        </a>
        <a href="/subjects">
            <div class="w3-half w3-bottombar w3-hover-light-grey w3-padding"><i class="fa fa-tags"></i> ${lang.categorias}</div>
        </a>
    </div>
</@layout.template>