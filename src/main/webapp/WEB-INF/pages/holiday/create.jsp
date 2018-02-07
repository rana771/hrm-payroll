<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="panel">
    <div class="panel-body">
        <h3 class="title-hero">
            Create Holiday
        </h3>

        <div class="example-box-wrapper">
            <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <form class="form-horizontal bordered-row" name="holiday" id="holidayFormId" method="post">
                <div class="row">

                    <input type="hidden" name="id" id="holidayId" value="">
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
                                    <input type="text" name="date:strDate" id="holidayDate"  class="bootstrap-datepicker form-control tempDateFrom" value="" data-date-format="mm-dd-yy">
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
                <table id="jqGridHoliday" ></table>
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
<script>

</script>

<script type="text/javascript">
    $(document).ready(function () {
        $(function() { "use strict";
            $('.bootstrap-datepicker').bsdatepicker({
                format: 'mm-dd-yyyy',
            });
        });

        var id= $('#empId').val();
        var gridId= "jqGridHoliday";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val(),
        };
        var url = "${pageContext.request.contextPath}/holiday/list/?id="+id;
        var formId = 'holidayFormId';
        var caption = 'Holiday Information'
        var urlmethod='POST'
        var colModel = [
            {label: 'id', name: 'id', key: true, hidden: true},
            {label: 'Name', name: 'name',width:150},
            {label: 'Date', name: 'date',width:150},
            {label: 'Description', name: 'description',width:150},
        ]

        Server.list(header,url,colModel,formId,caption,urlmethod,gridId);
    });

    function edit(holidayId) {
        // console.log("Id : "+holidayId);
        var action = "${pageContext.request.contextPath}/holiday/edit/";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };

        Server.edit(header, action, holidayId,setDataToEdit);

    }

    function setDataToEdit(result) {
        Server.resetForm('holidayFormId');
        $('#holidayId').val(result.id);
        $('#holidayName').val(result.name);
        $('#version').val(result.version);
        $('#isRepeated').prop('checked',result.isRepeated);
        $('#holidayDate').val(result.date);
        $('#description').val(result.description);
        $('#saveButton').val('Update');
        // console.log(result.id);
    }


    function deleteHoliday() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var content = 'Are you sure you want to delete this Holiday?';
        var formId=$('#deleteButton').closest('form').attr('id');
        var caption = "Holiday Information"
        var url ="${pageContext.request.contextPath}/holiday/delete/";
        var gridId="jqGridHoliday"

        var _form_values =$('#holidayFormId').serializeJSON({
            checkboxUncheckedValue: "false" ,
            customTypes: {
                strDate: function(str) { // value is always a string
                    var strdate=new Date($('#holidayDate').val());
                    return new Date(strdate);
                },

            }
        });
        console.log(_form_values);
        Server.delete(header,url,_form_values,formId,content,caption,gridId);
        console.log(_form_values);
    }





    <%--var isRepeated = $('#isRepeated').val();--%>
        <%--if ($('#isRepeated').is(":checked")){--%>
            <%--isRepeated = true;--%>
        <%--} else {--%>
            <%--isRepeated = false;--%>
        <%--}--%>
       <%--console.log(isRepeated);--%>


    function saveHoliday() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var caption = "Holiday Information"
        var formId = $('#saveButton').closest('form').attr('id');
        var gridId= "jqGridHoliday";
        // console.log($('#holidayDate').val())
        var _form_values =$('#holidayFormId').serializeJSON({
            checkboxUncheckedValue: "false" ,
            customTypes: {
                strDate: function(str) { // value is always a string
                    var strdate=new Date($('#holidayDate').val());
                    return new Date(strdate);
                },

            }
        });

        // console.log(_form_values);
        if ($('#holidayId').val() > 0) {
            var action ="${pageContext.request.contextPath}/holiday/update";
        } else {
            var action ="${pageContext.request.contextPath}/holiday/save";
        }

        Server.save(header, _form_values, action, formId, caption,gridId);
        Server.resetForm(formId);
    }

</script>