<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel">
    <div class="panel-body">
        <h3 class="title-hero">
            Employee Education Information
        </h3>

        <div class="example-box-wrapper col-md-12">
            <form class="form-horizontal bordered-row"   name="employeeEducation" id="empEduFormId" method="POST"
                  data-parsley-validate="">
                <div class="row">
                    <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="id" id="id" value="">
                    <input type="hidden" name="employee.id" id="empId" value="${employee.id}">
                    <input type="hidden" name="version" id="version" value="${employeeEducation.version}">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Education Title</label>
                            <div class="col-sm-8">
                                <select class="form-control" name="educationTitle.id" id="educationTitleId" required>
                                    <option value=""> Select </option>
                                   <c:forEach var="educationalTitle" items="${educationalTitleList}">
                                        <option value="${educationalTitle.id}">${educationalTitle.column1}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Board:</label>
                            <div class="col-sm-8">
                                <input type="text" name="board" id="board" path="Board" placeholder="Board"
                                       required class="form-control">
                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">

                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">institute:</label>
                            <div class="col-sm-8">
                                <input type="text" name="institute" id="institute" path="institute" placeholder="Institute"
                                       required class="form-control">
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Result:</label>
                            <div class="col-sm-8">
                                <input type="text" name="result" id="result" path="result" placeholder="result"
                                       required class="form-control">
                            </div>
                        </div>
                    </div>


                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Passing Year:</label>
                            <div class="col-sm-8">
                                <input type="text" name="passingYear" id="passingYear" path="passingYear" placeholder="passingYear"
                                       required class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="col-sm-4 control-label" style="text-align: left">Upload Certificate:</label>
                            <div class="col-sm-7">
                                <input type="file" name="certificate" id="certificate" path="certificate" placeholder="certificate"
                                       required class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="bg-default content-box text-center pad20A mrg10T">
                    <input id="saveButton" style="float: left; margin-left: 5px;" type="button" value="Save"
                           onclick="saveEmpEducation()" class="btn btn-lg btn-primary"/>
                    <input id="deleteButton" style="float: left; margin-left: 5px; display: none;" type="button"
                           value="Delete" onclick="deleteEmpEducation()" class="btn btn-lg btn-primary"/>
                    <input style="float: left; margin-left: 5px;" type="button" value="Cancel" onclick="Server.resetForm('empEduFormId')" class="btn btn-lg btn-primary"/>
                    <div style="clear: both"></div>
                </div>

                <div class="bg-default content-box text-center pad20A mrg25T grid-resize">
                    <table id="jqGridEdu"></table>
                    <div id="jqGridPager"></div>
                </div>
            </form>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(document).ready(function () {
        var id= $('#employeeId').val();
        var gridId="jqGridEdu";
        console.log("Reload Employee Education Table ")
        console.log(id);
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val(),
        };

        var url = "${pageContext.request.contextPath}/education/list/?id="+id;
        var formId = 'empEduFormId';
        var caption = 'Employee Education';
        var urlmethod='POST'
        var colModel = [
            {label: 'id', name: 'id', key: true, width: 75, hidden: true},
            {label: 'Education Title', name: 'title', width: 150},
            {label: 'Board', name: 'board', width: 100},
            {label: 'Institute', name: 'institute', width: 100},
            {label: 'Result', name: 'result', width: 100},
            {label: 'Passing Year', name: 'passing_year', width: 100},
        ]
        Server.list(header,url,colModel,formId,caption,urlmethod,gridId);


    });
    function edit(empEduId) {
        var action = "${pageContext.request.contextPath}/education/edit/";
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        Server.edit(header, action, empEduId,setDataToEdit);

    }
    function setDataToEdit(result) {
        console.log(result);
        $('#id').val(result.id);
        $('#board').val(result.board);
        $('#version').val(result.version);
        $('#institute').val(result.institute);
        $('#result').val(result.result);
        $('#passingYear').val(result.passing_year);
        $('#educationTitleId').val(result.education_title_id).prop('selected', true);
        $('#saveButton').val('Update');
    }
    function deleteEmpEducation() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var gridId= "jqGridEdu"
        var formId=$('#deleteButton').closest('form').attr('id');
        console.log("formId"+formId)
        var empEduId = $('#id').val();
        var url =  "${pageContext.request.contextPath}/education/delete/";
        Server.delete(header, url, empEduId, formId,gridId);
    }
    function saveEmpEducation() {
        var frm = $('#empEduFormId');
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var caption = "Employee Education"
        var formId = "empEduFormId";
        var formData=new FormData;
        var file = $("#certificate")[0].files[0];
        formData.append("file", file);
        formData.append("id",$('#id').val());
        formData.append("version",$('#version').val());
        formData.append("board",$('#board').val());
        formData.append("institute",$('#institute').val());
        formData.append("result",$('#result').val());
        formData.append("passingYear",$('#passingYear').val());
        formData.append("employeeId", $('#employeeId').val());
        formData.append("educationTitleId", $('#educationTitleId').val());
        var action = "";
        if ($('#id').val() > 0) {
            var action = "${pageContext.request.contextPath}/education/update";
        } else {
            var action = "${pageContext.request.contextPath}/education/save";
        }
        for (var pair of formData.entries()) {
            console.log(pair[0]+ ', ' + pair[1]);
        }

        $.ajax({
            url: action,
            data: formData,
            processData: false,
            contentType: false,
            type: 'POST',
            headers:header,
            success: function (data) {
                Server.getMessage(1,data,"Empoyee Education");
                $('#'+formId).find("#jqGridEdu").trigger('reloadGrid');
                Server.resetForm(formId);
            },
            error: function (err) {


            }
        });
    }


</script>
