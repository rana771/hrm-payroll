<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="panel">
    <div class="panel-body">
        <h3 class="title-hero">
            Create Holiday
        </h3>

        <div class="example-box-wrapper">
            <form class="form-horizontal bordered-row" name="holiday" id="holidayFormId" method="post">
                <div class="row">
                    <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="id" id="id" value="">
                    <input type="hidden" name="version" id="version" value="">


                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Name</label>
                            <div class="col-md-8">
                                <input type="text" name="name" id="holidayName" path="name" placeholder="name"
                                       required class="form-control">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-5">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Date</label>
                            <div class="col-sm-7">
                                <div class="input-prepend input-group">
                                    <span class="add-on input-group-addon">
                                        <i class="glyph-icon icon-calendar"></i>
                                    </span>
                                    <input type="text" name="date" id="hdate" value="01-01-2018"
                                           class="bootstrap-datepicker form-control" required
                                           data-date-format="mm-dd-yy">
                                    <form:errors path="date" cssclass="error"></form:errors>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Repeat days</label>
                            <div class="col-md-8">
                                <%--<input type="hidden" name="isRepeated" id="" value="false" />--%>
                                <input type="checkbox" name="isRepeated" id="isRepeated" value="true" />
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Description</label>
                            <div class="col-md-8">
                                <%--<input type="text" name="note" id="description" path="name" placeholder="note"--%>
                                <%--required class="form-control">--%>
                                <textarea type="text" name="description" id="description" path="name" cols="15"
                                          rows="3"></textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="bg-default content-box text-center pad20A mrg10T">
                    <input id="saveButton" style="float: left; margin-left: 5px;" type="button" value="Save"
                           onclick="saveHoliday()" class="btn btn-lg btn-primary"/>
                    <input id="deleteButton" style="float: left; margin-left: 5px; display: none;" type="button"
                           value="Delete" onclick="deleteHoliday()" class="btn btn-lg btn-primary"/>
                    <input style="float: left; margin-left: 5px;" type="button" value="Cancel"
                           onclick="Server.resetForm('holidayFormId')" class="btn btn-lg btn-primary"/>
                    <div style="clear: both"></div>
                </div>

                <div class="bg-default content-box text-center pad20A mrg25T grid-resize">
                    <table id="jqGrid"></table>
                    <div id="jqGridPager"></div>
                </div>
            </form>
        </div>
    </div>
</div>

<style type="text/css">
    .bg-red {
        background: #cf4436;
        color: #FFFFFF;
    }

    .bg-green {
        background: #449d44;
        color: #FFFFFF;
    }

    .bg-azure {
        background: #d67520;
        color: #FFFFFF;
    }
</style>

<script type="text/javascript" src="../../static/custom/commons.js"></script>
<script type="text/javascript">
    <%--$(document).ready(function () {--%>
        <%--header = {--%>
            <%--'X-CSRF-TOKEN': $('#csr-token').val(),--%>
            <%--'${_csrf.parameterName}': $('#csr-token').val()--%>
        <%--};--%>
        <%--var caption = 'Holiday Information';--%>
        <%--var formId = 'holidayFormId';--%>
        <%--var url = '${contextPath}/holiday/list';--%>
        <%--var colModel = [--%>
            <%--{label: 'id', name: 'id', key: true, width: 75, hidden: true},--%>
            <%--// {label: 'Holiday', name: 'name', width:400},--%>
            <%--// {label: 'Date', name: 'description' , width:400 }--%>
        <%--]--%>
        <%--console.log(url);--%>
        <%--console.log(formId);--%>
        <%--console.log(colModel);--%>
        <%--Server.list(header, url, colModel, formId, caption)--%>
    <%--});--%>

    $(document).ready(function () {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var url = '${contextPath}/holiday/list';
        var formId = 'holidayFormId';
        var caption = 'Holiday Information';
        var colModel = [
            {label: 'id', name: 'id', key: true, width: 75, hidden: true},
            {label: 'Date', name: 'date', width: 250},
            {label: 'Description', name: 'description', width: 250}
            // {label: 'Status', name: 'isOpen', width: 250, editable: true, formatter:statusFomatter},
        ];
        console.log(formId);

        Server.list(header, url, colModel, formId, caption);

    });

    <%--function deleteHoliday() {--%>
        <%--header = {--%>
            <%--'X-CSRF-TOKEN': $('#csr-token').val(),--%>
            <%--'${_csrf.parameterName}': $('#csr-token').val()--%>
        <%--};--%>
        <%--var formId=$('#deleteButton').closest('form').attr('id');--%>
        <%--var content = 'Are you sure you want to delete the Holiday ?';--%>
        <%--var caption = "Holiday Information";--%>
        <%--var holidayId = $('#id').val();--%>
        <%--var url = '/holiday/delete/';--%>
        <%--Server.delete(header, url, holidayId, formId, content, caption);--%>
    <%--}--%>

    <%--function edit(holidayId) {--%>
        <%--var action = "${contextPath}" + "/holiday/edit/";--%>
        <%--header = {--%>
            <%--'X-CSRF-TOKEN': $('#csr-token').val(),--%>
            <%--'${_csrf.parameterName}': $('#csr-token').val()--%>
        <%--};--%>
        <%--// console.log();--%>
        <%--Server.edit(header, action, holidayId, setDataToEdit);--%>

    <%--}--%>

    <%--function setDataToEdit(result) {--%>
        <%--// console.log(result);--%>
        <%--$('#id').val(result.id);--%>
        <%--$('#hdate').val(result.date);--%>
        <%--$('#holidayName').val(result.name);--%>
        <%--$('#isRepeated').val(result.isRepeated);--%>
        <%--$('#description').val(result.description);--%>
        <%--$('#version').val(result.version);--%>
        <%--$('#saveButton').val('Update');--%>
    <%--}--%>

    <%--function saveHoliday() {--%>
        <%--header = {--%>
            <%--'X-CSRF-TOKEN': $('#csr-token').val(),--%>
            <%--'${_csrf.parameterName}': $('#csr-token').val()--%>
        <%--};--%>

        <%--var formId = $('#saveButton').closest('form').attr('id');--%>
        <%--var caption = "Holiday Information";--%>
        <%--var frm = $('#holidayFormId');--%>
        <%--frm.validate();--%>
        <%--var isValid = frm.valid();--%>
            <%--if(!isValid) {--%>
                <%--return false;--%>
            <%--}--%>

        <%--var isRepeated = $('#isRepeated').val();--%>
        <%--if ($('#isRepeated').is(":checked")){--%>
            <%--isRepeated = true;--%>
        <%--} else {--%>
            <%--isRepeated = false;--%>
        <%--}--%>
       <%--// console.log(dateForm);--%>

        <%--var date =new Date($('#hdate').val());--%>
        <%--var holiday= {--%>
            <%--"id": $('#id').val(),--%>
            <%--"date": date,--%>
            <%--"isRepeated": isRepeated,--%>
            <%--"name": $.trim($('#holidayName').val()),--%>
            <%--"description": $('#description').val(),--%>
            <%--"version": $('#version').val()--%>
        <%--}--%>
            <%--// console.log(holiday);--%>
        <%--var action = "";--%>
        <%--if ($('#id').val() > 0) {--%>
            <%--var action = "${contextPath}" + "/holiday/update";--%>
        <%--} else {--%>
            <%--var action = "${contextPath}" + "/holiday/save";--%>
        <%--}--%>
        <%--console.log(action);--%>
        <%--Server.save(header, holiday, action, formId, caption);--%>
        <%--Server.resetForm(formId);--%>
    <%--}--%>

</script>