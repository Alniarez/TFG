<div class="w3-dropdown-content w3-border" style="right:0">
	<#list languages as language>
		<#if language.locale == locale>
		<a class="languageLink" href="/language/${language.locale}"><i class="fa fa-toggle-on"></i> ${language.language}</a>
		<#else>
		<a class="languageLink" href="/language/${language.locale}"><i class="fa fa-toggle-off"></i> ${language.language}</a>
		</#if>
	</#list>
</div>
<script>
$(document).ready(function() {
       $('.languageLink').click(function(e) {
		e.preventDefault()
		//console.log($(this).attr("href"))
		$.ajax({
			type: "POST",
			url: $(this).attr("href"),
            success: function(){    
				location.reload();   
            }
        })
	})
})
</script>
