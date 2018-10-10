<#macro template title="Welcome">
    <#setting locale="${locale}">
<!DOCTYPE html>
<html>
<head>
    <title>${title} | Simple Help Desk</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8">
    <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.0.0.min.js" integrity="sha256-JmvOoLtYsmqlsWxa7mDSLMwa6dZ9rrIdtrrVYRnDRH0=" crossorigin="anonymous"></script>
</head>
<body>
    <#include "*/snipets/headder.ftl">
<#nested />
<#include "*/snipets/footer.ftl">
</body>
</html>
</#macro>