<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel">
    <div class="panel-body">
        <h3 class="title-hero">
            Employee Address
        </h3>

        <div class="example-box-wrapper col-md-12">
            <form class="form-horizontal bordered-row"  modelAttribute="addressInfo" name="employeeAddress" id="empAddFormId" method="POST"
                  data-parsley-validate="">
                <div class="row">
                    <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="id" id="employeeAddressId" value="${employeeAddress.id}">
                    <input type="hidden" name="employee.id" id="empId" value="${employee.id}">
                    <input type="hidden" name="version" id="version" value="${employeeAddress.version}">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Address Type</label>
                            <div class="col-sm-8">
                                <select class="form-control" name="addressType.id" id="addressTypeId" required>
                                    <option value=""> Select </option>
                                    <c:forEach var="address" items="${addressType}">
                                        <option value="${address.id}">${address.column1}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">

                        <div class="col-md-8">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">Address:</label>
                            <div class="col-sm-8">
                                <textarea type="text" name="address" id="address" path="address" placeholder="Address" value="${employeeAddress.address}"
                                       required class="form-control"></textarea>
                            </div>
                        </div>
                        </div>
                    </div>

                </div>
                <div class="row">

                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Active:</label>
                            <div class="col-sm-8">
                                <input type="radio" name="isActive" id="active" value="true" path="active" required class="form-control" >
                            </div>
                        </div>
                    </div>

                </div>
                <div class="bg-default content-box text-center pad20A mrg10T">
                    <input id="saveButton" style="float: left; margin-left: 5px;" type="button" value="Save"
                           onclick="saveEmpAddress()" class="btn btn-lg btn-primary"/>
                    <input id="deleteButton" style="float: left; margin-left: 5px; display: none;" type="button"
                           value="Delete" onclick="deleteEmpAdd()" class="btn btn-lg btn-primary"/>
                    <input style="float: left; margin-left: 5px;" type="button" value="Cancel" onclick="Server.resetForm('empEduFormId')" class="btn btn-lg btn-primary"/>
                    <div style="clear: both"></div>
                </div>
                <div class="bg-default content-box text-center pad20A mrg25T grid-resize">
                    <table id="jqGridAdd"></table>
                    <div id="jqGridPager"></div>
                </div>
            </form>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(document).ready(function () {
        var id= $('#empId').val();
        var gridId= "jqGridAdd";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val(),
        };
        var url = "${pageContext.request.contextPath}/address/list/?id="+id;
        var formId = 'empAddFormId';
        var caption = 'Employee Address Information'
        var urlmethod='POST'
        var colModel = [
            {label: 'id', name: 'id', key: true, width: 75, hidden: true},
            {label: 'Address Type', name: 'addresstype', width: 150},
            {label: 'Address', name: 'address', width: 200},
            {label: 'Status', name: 'status', width: 200},
            {label: 'version', name: 'version', width: 100,hidden:true},
        ]
        Server.list(header,url,colModel,formId,caption,urlmethod,gridId);
    });
    function edit(empAddId) {
        var action = "${pageContext.request.contextPath}/address/edit/";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        Server.edit(header, action, empAddId,setDataToEdit);

    }
    function setDataToEdit(result) {
        console.log(result);
        //$('#empEduFormId').find("#board").val(result.board);
        $('#employeeAddressId').val(result.id);
        $('#address').val(result.address);
        $('#version').val(result.version);
        $('input[name=isActive]').prop('checked',result.is_active);
        $('#addressTypeId').val(result.address_type_id).prop('selected', true);
        $('#saveButton').val('Update');
    }
    function deleteEmpAdd() {
        console.log("Hellow world");
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var content = 'Are you sure you want to delete the this address?';
        var formId=$('#deleteButton').closest('form').attr('id');
        var caption = "Employee Address"
        var url ="${pageContext.request.contextPath}/employee/address/delete/";
        var addressInfo = {
            "id": $('#employeeAddressId').val(),
            "employeeId": $('#empId').val(),
            "addressTypeId": $('#addressTypeId').val(),
            "address": $('#address').val(),
            "isActive" : $('#active').val(),
            "version" : $('#version').val(),
        }
        $.confirm({
            title: 'Confirm!',
            content: content,
            buttons: {
                confirm: function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: url,
                        async: true,
                        data : JSON.stringify(addressInfo),
                        dataType: "json",
                        headers:header,
                        success : function(result) {
                            Server.getMessage(1,result.responseText,caption);
                            $('#jqGridAdd').trigger( 'reloadGrid' );
                            Server.resetForm(formId);
                        },
                        error : function(result) {
                            Server.getMessage(1,result.responseText,caption);
                            $('#jqGridAdd').trigger( 'reloadGrid' );
                            Server.resetForm(formId);
                        }
                    });

                },
                cancel: function () {
                }
            }
        });

    }

    /*
       *save Employee Address
     */
    function saveEmpAddress() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var caption = "Employee Address"
        var formId = $('#saveButton').closest('form').attr('id');
        var gridId= "jqGridAdd";
        var addressInfo = {
            "id": $('#employeeAddressId').val(),
            "employeeId": $('#empId').val(),
            "addressTypeId": $('#addressTypeId').val(),
            "address": $('#address').val(),
            "isActive" : $('#active').val(),
            "version" : $('#version').val(),
        }
        if ($('#employeeAddressId').val() > 0) {
            var action ="${pageContext.request.contextPath}/employee/address/update";
        } else {
            var action ="${pageContext.request.contextPath}/employee/address/save";
        }

        Server.save(header, addressInfo, action, formId, caption,gridId);
        Server.resetForm(formId);
    }


</script>
