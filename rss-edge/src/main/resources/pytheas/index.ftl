<#macro body>

<#import "/layout/bootstrap/simplepage.ftl" as layout />

<@layout.pagelayout title="RSS Feeds">

<!-- Move your JavaScript code to an include file -->
<script type="text/javascript">
<#include "index.js"/>
</script>
 
<!-- Customize your styles here -->
<style>
    div.dataTables_filter label {
        float: left;
        margin-bottom: 15px;
    }
</style>

<button class="btn-primary btn feed-new" type="button" style="margin: 5px;"><i class="icon-plus icon-white"></i> New Feed</button>

<!-- Define a table -->
<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered dataTable" id="feeds-table" style="width:50%;">
</table>

<#import "/layout/bootstrap/form.ftl" as form/>
<!-- Dialog template for adding a new feed -->
<@form.modalform action="/feeds" id="feed-add-template" title="New RSS Feed" class="span10" method="POST">
    <@form.modalbody>
        <@form.text label="Feed URL"  name="feedURL" value="" />
    </@form.modalbody>

    <@form.modalfooter>
        <@form.submit "Add"/>
        <@form.cancel "Cancel"/>
    </@form.modalfooter>
</@form.modalform>

</@layout.pagelayout>

</#macro>
