<#if error??>
<div class="w3-container w3-section w3-red">
    <span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
    <p>${error}</p>
</div>
</#if>
<#if warning??>
<div class="w3-container w3-section w3-yellow">
    <span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
    <p>${warning}</p>
</div>
</#if>
<#if success??>
<div class="w3-container w3-section w3-green">
    <span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
    <p>${success}</p>
</div>
</#if>
<#if info??>
<div class="w3-container w3-section w3-blue">
    <span onclick="this.parentElement.style.display='none'" class="w3-closebtn">&times;</span>
    <p>${info}</p>
</div>
</#if>