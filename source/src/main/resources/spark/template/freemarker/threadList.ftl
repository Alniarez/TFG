<#import "template.ftl" as layout />
<@layout.template title="${lang.temas}">
    <#include "*/snipets/nav.ftl">
    <#include "*/snipets/messages.ftl">
<div id="mainTableContainer" class="w3-container w3-section w3-responsive">
    <table id="mainTable" class="w3-table w3-bordered w3-striped w3-border w3-hoverable">
        <thead>
        <th>${lang.id}</th>
        <th>${lang.asunto}</th>
        <th>${lang.usuario}</th>
        <th>${lang.estado}</th>
        <th></th>
        <th>${lang.fecha}</th>
        <th>${lang.acciones}</th>
        </thead>
        <tbody>
            <#list threads as thread>
            <tr>
                <td>${thread.id}</td>
                <td>${thread.topic}</td>
                <td>${thread.name} - ${thread.email}</td>
                <#if thread.open>
                    <td><span class="w3-tag w3-red">${lang.abierto}</span></td>
                    <td><span class="w3-tag w3-red">0</span></td>
                <#else>
                    <td><span class="w3-tag w3-green">${lang.resuelto}</span></td>
                    <td><span class="w3-tag w3-red">1</span></td>
                </#if>
                <td>${thread.date}</td>
                <td><a href="/thread/${thread.id}" class="w3-btn w3-teal"><i
                        class="fa fa-search"></i> ${lang.verDetalles}</a>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
    <div class="w3-center">
        <ul id="w3Pag" class="w3-pagination w3-border">
        </ul>
    </div>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script>
        var curretToRed = function(){
            $('.paginate_button').removeClass("w3-teal")
            $('.current').addClass("w3-teal")
        }

        var createPagination = function(){
            $.each( $('#datatablePagination').children() , function() {
                $('#w3Pag').append($("<li>").html(this))
                curretToRed()
            })
        }

        $(document).ready(function() {
            $('#mainTable').DataTable({
            	"dom": '<"w3-row"<"w3-container w3-third"i><"w3-container w3-third"l><"w3-container w3-third"<"w3-right"f>>>t<"#datatablePagination.hidden"p>',
            	"order": [[ 4, 'asc' ],[ 5, 'desc' ]],
            	"columnDefs": [{
		                "targets": [ 4 ],
		                "visible": false,
		                "searchable": false
		        }],
		        "language" : {
					"sProcessing" : "${lang.sProcessing}",
					"sLengthMenu" : "${lang.sLengthMenu}",
					"sZeroRecords" : "${lang.sZeroRecords}",
					"sEmptyTable" : "${lang.sEmptyTable}",
					"sInfo" : "${lang.sInfo}",
					"sInfoEmpty" : "${lang.sInfoEmpty}",
					"sInfoFiltered" : "${lang.sInfoFiltered}",
					"sInfoPostFix" : "${lang.sInfoPostFix}",
					"sSearch" : "${lang.sSearch}",
					"sUrl" : "${lang.sUrl}",
					"sInfoThousands" : "${lang.sInfoThousands}",
					"sLoadingRecords" : "${lang.sLoadingRecords}",
					"oPaginate" : {
						"sFirst" : "${lang.sFirst}",
						"sLast" : "${lang.sLast}",
						"sNext" : "${lang.sNext}",
						"sPrevious" : "${lang.sPrevious}"
					},
					"oAria" : {
						"sSortAscending" : ": ${lang.sSortAscending}",
						"sSortDescending" : ": ${lang.sSortDescending}"
					}
				}
            })

            createPagination()

            $("#mainTableContainer").click(function () {
                curretToRed()
            });
        } )

    </script>
</div>
</@layout.template>