<div style="display:none">
    <form action="/profile/edit/${user.id}/deactivate" method="post">
        <input id="deactivate" type="submit" class="w3-btn w3-padding w3-teal" value=""/>
    </form>
    <form action="/profile/edit/${user.id}/noadmin" method="post">
        <input id="noadmin" type="submit" class="w3-btn w3-padding w3-teal" value=""/>
    </form>
    <form action="/profile/edit/${user.id}/admin" method="post">
        <input id="admin" type="submit" class="w3-btn w3-padding w3-teal"
               value=""/>
    </form>
    <form action="/profile/edit/${user.id}/activate" method="post">
        <input id="activate" type="submit" class="w3-btn w3-padding w3-teal" value=""/>
    </form>
</div>
<div class="w3-center w3-section">
<#if user.active>
    <label class="w3-btn w3-teal" for="deactivate">${lang.desactrivarUsuario}</label>
<#else>
    <label class="w3-btn w3-teal" for="activate">${lang.activarUsuario}</label>
</#if>
<#if AuthenticatedUser.admin>
    <#if user.admin>
        <label class="w3-btn w3-teal" for="noadmin">${lang.quitarAdmin}</label>
    <#else>
        <label class="w3-btn w3-teal" for="admin">${lang.ponerAdmin}</label>
    </#if>
</#if>
</div>