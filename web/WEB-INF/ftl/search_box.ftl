<div class="container-fluid header_inner" style="margin-bottom:20px;min-width: 980px;height: 59px; backgroun-color:#F1F1F1;background-size: 100% auto;background-repeat:no-repeat;)">
    <div class="col-sm-1">
        <a href="/"><img src="/resources/img/logos/bilgi_logo.png" width="48px" height="48px"></img></a>
    </div>
    <form id="search_form" class="form-horizontal" role="form" action="/search" method="get">
        <div class="form-group">
            <div class="col-md-6">
                <input id="q_text" class="form-control" id="search_form_text_input" name="q" type="text" placeholder="Begin Searching"/>
            </div>
            <div class="form-group row">
                <div class="col-md-2">
                    <select id="locale_selection" class="form-control" name="locale">
                        <option value="all">Search All Languages</option>
                    <#list locale_selections as locale>
                        <#if locale.language?has_content>
                            <option value="${locale.language}" <#if locale == .locale>selected</#if>>${locale.displayName}</option>
                        </#if>
                    </#list>
                    </select>
                </div>
                <div class="col-md-2">
                    <input id="search_form_submit" class="btn btn-default" type="submit"/>
                </div>
            </div>
        </div>
    </form>
</div>