<div class="w3-container w3-section">
    <ul class="w3-ul w3-card-4">
        <li class="w3-padding-16">
            <a href="/newUser" class="w3-btn w3-teal"><i class="fa fa-user-plus"></i> ${lang.insertarUsuario}</a>
        </li>
    <#list allUsers as user>
        <li class="w3-padding-16">
            <span class="w3-closebtn w3-padding w3-margin-right w3-medium"><a style="text-decoration:none;" href="/profile/edit/${user.id}"><i class="fa fa-cog" aria-hidden="true"></i> ${lang.editar}</a></span>
            <img src="${user.gravatar}" class="w3-left w3-circle w3-margin-right" style="width:60px">
            <span class="w3-xlarge"><a style="text-decoration:none;" href="/profile/${user.id}">${user.name}</span></a><br>
            <span>${user.email}</span>
        </li>
    </#list>
    </ul>
</div>
