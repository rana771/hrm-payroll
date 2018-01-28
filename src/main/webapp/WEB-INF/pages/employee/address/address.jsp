<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel">
    <div class="panel-body">
        <h3 class="title-hero">
            Employee Address
        </h3>

        <div class="example-box-wrapper col-md-12">
            <form class="form-horizontal bordered-row"   name="employeeAddress" id="empAddFormId" method="POST"
                  data-parsley-validate="">
                <div class="row">
                    <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="id" id="id" value="">
                    <input type="hidden" name="employee.id" id="empId" value="${employee.id}">
                    <input type="hidden" name="version" id="version" value="${employeeAddress.version}">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Address Type</label>
                            <div class="col-sm-8">
                                <select class="form-control" name="addressType.id" id="addressTypeId" required>
                                    <option value=""> Select </option>
                                    <c:forEach var="addressType" items="${addressTypeList}">
                                        <option value="${addressType.id}">${addressType.column1}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Address:</label>
                            <div class="col-sm-8">
                                <textarea type="text" name="address" id="address" path="address" placeholder="Address"
                                       required class="form-control"></textarea>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">

                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Active:</label>
                            <div class="col-sm-8">
                                <label class="col-sm-3 control-label">Yes</label>
                                <input type="radio" name="isActive" id="Active" path="active" required class="form-control">
                                <label class="col-sm-3 control-label">No</label>
                                <input type="radio" name="isActive" id="InActive" path="active" required class="form-control">
                            </div>
                        </div>
                    </div>

                </div>
                <div class="bg-default content-box text-center pad20A mrg10T">
                    <input id="saveButton" style="float: left; margin-left: 5px;" type="button" value="Save"
                           onclick="saveEmpAddress()" class="btn btn-lg btn-primary"/>
                    <input style="float: left; margin-left: 5px;" type="button" value="Cancel" onclick="Server.resetForm('empEduFormId')" class="btn btn-lg btn-primary"/>
                    <div style="clear: both"></div>
                </div>

            </form>
        </div>
    </div>

</div>
<script type="text/javascript">
    function edit(empEduId) {
        var action = "${contextPath}" + "/education/edit/";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        Server.edit(header, action, empEduId,setDataToEdit);

    }
    function setDataToEdit(result) {
        console.log(result);
        //$('#empEduFormId').find("#board").val(result.board);
        $('#id').val(result.id);
        $('#board').val(result.board);
        $('#version').val(result.version);
        $('#institute').val(result.institute);
        $('#result').val(result.result);
        $('#passingYear').val(result.passing_year);
        $('#educationTitleId').val(result.education_title_id).prop('selected', true);
        //$('#educationTitleId').val(result.);
        $('#saveButton').val('Update');
    }
    function deleteEmpEducation() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var formId=$('#deleteButton').closest('form').attr('id');
        console.log("formId"+formId)
        var empEduId = $('#id').val();
        var url = '/education/delete/';
        Server.delete(header, url, empEduId, formId);
    }


</script>
