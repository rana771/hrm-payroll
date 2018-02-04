<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel">
    <div class="panel-body">
        <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <h3 class="title-hero">
            Employee Contact
        </h3>

        <div class="example-box-wrapper col-md-12">
            <form class="form-horizontal bordered-row"  modelAttribute="employeeContact" name="employeeContact" id="empConFormId" method="POST"
                  data-parsley-validate="">
                <div class="row">
                    <input type="hidden" name="id" id="empConId" value="${employeeContact.id}">
                    <input type="hidden" name="employee[id]" id="empId" value="${employee.id}">
                    <input type="hidden" name="version" id="version" value="${employeeContact.version}">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Contact Type</label>
                            <div class="col-sm-8">
                                <select class="form-control" name="contactType[id]" id="contactTypeId" required>
                                    <option value=""> Select </option>
                                    <c:forEach var="contactType" items="${contactTypeList}">
                                        <option value="${contactType.id}">${contactType.column1}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Phone</label>
                            <div class="col-sm-8">
                                <input type="text" name="phone" id="phone" path="phone" placeholder="Phone" value="${employeeContact.phone}"
                                          required class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Mobile</label>
                            <div class="col-sm-8">
                                <input type="text" name="mobile" id="mobile" path="mobile" placeholder="Mobile" value="${employeeContact.mobile}"
                                       required class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Email</label>
                            <div class="col-sm-8">
                                <input type="text" name="email" id="email" path="email" placeholder="Email" value="${employeeContact.email}"
                                       required class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Fax</label>
                            <div class="col-sm-8">
                                <input type="text" name="fax" id="fax" path="fax" placeholder="Fax" value="${employeeContact.fax}"
                                       required class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Active</label>
                            <div class="col-sm-8">
                                <input type="checkbox" name="isActive" id="active"  data-toggle="toggle" checked value="true" path="active" required class="form-control">
                            </div>
                        </div>
                    </div>

                </div>
                <div class="bg-default content-box text-center pad20A mrg10T">
                    <input id="saveButton" style="float: left; margin-left: 5px;" type="button" value="Save"
                           onclick="saveEmpContact()" class="btn btn-lg btn-primary"/>
                    <input id="deleteButton" style="float: left; margin-left: 5px; display: none;" type="button"
                           value="Delete" onclick="deleteDesignation()" class="btn btn-lg btn-primary"/>
                    <input style="float: left; margin-left: 5px;" type="button" value="Cancel" onclick="Server.resetForm('employeeDesignationId')" class="btn btn-lg btn-primary"/>
                    <div style="clear: both"></div>
                </div>
                <div class="bg-default content-box text-center pad20A mrg25T grid-resize">
                    <table id="jqGridContact"></table>
                    <div id="jqGridPager"></div>
                </div>
            </form>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(document).ready(function () {
        $(function() { "use strict";
            $('.bootstrap-datepicker').bsdatepicker({
                format: 'mm-dd-yyyy',
            });
        });
        var id= $('#empId').val();
        var gridId= "jqGridDes";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val(),
        };
        var url = "${pageContext.request.contextPath}/designation/list/?id="+id;
        var formId = 'empdesignationId';
        var caption = 'Employee Designation Information'
        var urlmethod='POST'
        var colModel = [
            {label: 'id', name: 'id', key: true, width: 75, hidden: true},
            {label: 'Designation', name: 'name', width: 250},
            {label: 'Start Date ', name: 'start_date', width: 250},
            {label: 'End Date', name: 'end_date', width: 200},
            {label: 'Status', name: 'is_active', width: 100,hidden:true},
        ]
        Server.list(header,url,colModel,formId,caption,urlmethod,gridId);
    });
    function edit(empDesId) {
        console.log("Id : "+empDesId);
        var action = "${pageContext.request.contextPath}/designation/edit/";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        Server.edit(header, action, empDesId,setDataToEdit);

    }
    function setDataToEdit(result) {
        Server.resetForm('employeeDesignationId');
        $('#empdesId').val(result.id);
        $('#version').val(result.version);
        $('#active').prop('checked',result.is_active);
        $('#dateFrom').val(result.start_date)
        $('#endDate').val(result.end_date)
        $('#designationId').val(result.designation_id).prop('selected', true);
        $('#saveButton').val('Update');
    }
    function deleteDesignation() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var content = 'Are you sure you want to delete this designation?';
        var formId=$('#deleteButton').closest('form').attr('id');
        var caption = "Employee Designation"
        var url ="${pageContext.request.contextPath}/designation/delete/";
        var gridId="jqGridDes"
        //var _form_values = $('#employeeDesignationId').serializeJSON();
        var _form_values =$('#employeeDesignationId').serializeJSON({
            customTypes: {
                strDate: function(str) { // value is always a string
                    var strdate=new Date($('#dateFrom').val());
                    return new Date(strdate);
                },
                edate: function(str) {
                    var endDate=new Date($('#endDate').val());
                    return new Date(endDate);
                },

            }
        });
        Server.delete(header,url,_form_values,formId,content,caption,gridId);
    }

    /*
     *save Employee Address
     */
    function saveEmpContact() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var caption = "Employee Contact"
        var formId = $('#saveButton').closest('form').attr('id');
        var gridId= "jqGridContact";
        var _form_values =$('#empConFormId').serializeJSON({
            checkboxUncheckedValue: "true"
        });
        //var _form_values = $('#employeeDesignationId').serializeJSON();
        console.log(_form_values);
        if ($('#empConFormId').val() > 0) {
            var action ="${pageContext.request.contextPath}/emp/contact/update";
        } else {
            var action ="${pageContext.request.contextPath}/emp/contact/save";
        }

        Server.save(header, _form_values, action, formId, caption,gridId);
        Server.resetForm(formId);
    }



</script>
