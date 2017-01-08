<html>
    <#include "base.ftl"/>
    <body>
        <#include "search_box.ftl"/>
        <div class="container-fluid">
            <div style="margin-bottom:20px">
                <small>Approximately ${totalHits} results found</small>
            </div>
            <#if searchResults??>
                <#list searchResults as searchResult>
                    <div style="background-color: #b1f7ff; margin-bottom:20px; padding-bottom: 20px">
                        <a href="${searchResult.url}">${searchResult.title!searchResult.url}</a>
                        <#if searchResult.preview??>${searchResult.preview}</#if>
                    </div>
                </#list>
            </#if>
            <#include "pager.ftl"/>
        </div>
    </body>
</html>