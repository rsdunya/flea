<div>
    <ul class="pagination">
        <#list pagerLinks as pageLink>
            <li><a href="${pageLink}">${pageLink_index + 1}</a></li>
        </#list>
    </ul>
</div>