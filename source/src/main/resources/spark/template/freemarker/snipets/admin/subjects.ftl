<div class="w3-container w3-section">
    <ul class="w3-ul w3-card-4">
        <li class="w3-padding-16">
        	<form action="/subject/newSubject" method="post">
        	<i class="fa fa-tag"></i>
        		<input type="text" name="subjectCode" class="w3.input" required>
            	<input type="submit" class="w3-btn w3-teal" value="${lang.insertarCategoria}"/>
        	</form>
        </li>
    <#list allSubjects as subject>
        <li class="w3-padding-16">
        	<span class="w3-closebtn w3-padding w3-margin-right w3-medium">
        		<a style="text-decoration:none;" href="/subject/delete/${subject.id}">
        			<i class="fa fa-remove" aria-hidden="true"></i> ${lang.eliminar}
				</a>
        	</span>
            <span class="w3-xlarge">${subject.code}</span></a><br>
        </li>
    </#list>
    </ul>
</div>
