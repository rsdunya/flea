<style type="text/css">
    html, body, .container {
        height: 100%;
    }
    .container {
        display: table;
        vertical-align: middle;
    }
    .vertical-center-row {
        display: table-cell;
        vertical-align: middle;
    }
</style>

<div class="container">
    <div class="row vertical-center-row">
        <div class="row">
            <div class="col-md-2 col-md-offset-5">
                <a href="/"><img src="/resources/img/logos/bilgi_logo_scaled.png" width="200px" height="173px"></img></a>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 col-md-offset-3">
                <form id="search_form" class="form-horizontal" role="form" action="/search" method="get">
                    <div class="form-group">
                        <div class="col-md-6">
                            <input id="q_text" class="form-control" id="search_form_text_input" name="q" type="text" placeholder="Begin Searching"/>
                        </div>
                    </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-md-offset-4">
                <div class="form-group row">
                    <div class="col-md-6">
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
        </div>
        </form>
    </div>
</div>