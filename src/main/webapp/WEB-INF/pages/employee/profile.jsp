<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row mailbox-wrapper">

    <div class="col-md-2">

        <div class="panel-layout">
            <div class="panel-box">

                <div class="panel-content image-box">
                    <div class="ribbon">
                        <div class="bg-primary">${employee.pin}</div>
                        <input type="hidden"  id="employeeId" value="${employee.id}">
                        <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </div>
                    <div class="image-content font-white">

                        <div class="meta-box meta-box-bottom">
                            <!-- <img src="../../static/image-resources/gravatar.jpg" alt="" class="meta-image img-bordered img-circle"> -->
                            <h4 class="meta-subheading">${employee.currentDesignation}</h4>
                        </div>

                    </div>
                    <img src="../../static/images/place_holder.jpg" style="width:120px" alt="">

                </div>

            </div>
        </div>
        <div class="content-box mrg15B">

            <div class="content-box-wrapper text-center clearfix">

                <ul class="list-group row list-group-icons">
                    <li class="col-md-16 active">
                        <a href="#tab-example-4" data-toggle="tab" class="list-group-item">
                            <i class="glyph-icon font-red icon-bullhorn"></i>
                            Personal Info
                        </a>
                    </li>
                    <li class="col-md-16">
                        <a href="#tab-example-1" data-toggle="tab" class="list-group-item">
                            <i class="glyph-icon icon-dashboard" onclick="accountSettings()"></i>
                            Account Settings
                        </a>
                    </li>
                    <li class="col-md-16">
                        <a href="#tab-example-5" data-toggle="tab" class="list-group-item">
                            <i class="glyph-icon font-primary icon-camera" onclick="FetchEmpAddress()"></i>
                            Address
                        </a>
                    </li>
                    <li class="col-md-16">
                        <a href="#tab-example-2" data-toggle="tab" class="list-group-item">
                            <i class="glyph-icon font-primary icon-camera" onclick="empEducation()"></i>
                            Education
                        </a>
                    </li>
                    <li class="col-md-16">
                        <a href="#tab-example-3" data-toggle="tab" class="list-group-item">
                            <i class="glyph-icon font-blue-alt icon-globe"></i>
                            Training
                        </a>
                    </li>

                </ul>
            </div>
        </div>

    </div>
    <div class="col-md-8 profilemain">
        <div class="example-box-wrapper profilesubmain">
            <div class="tab-content">
                <div class="tab-pane fade" id="tab-example-1">
                    <%--Account Settings Start--%>
                    <div id="accountsettingsId" class="divcls">

                    </div>


                    <%--Account Settings End --%>
                </div>
                <div class="tab-pane fade" id="tab-example-5">
                    <%--Employee Education--%>
                    <div id="empAddressId">

                    </div>
                    <%--Emloyee Address End --%>
                </div>

                <div class="tab-pane fade" id="tab-example-2">
                    <%--Employee Education--%>
                    <div id="empEducationId">

                    </div>
                    <%--Emloyee Education End --%>
                </div>
                <div class="tab-pane fade" id="tab-example-3">
                    <%--Employee Training Start--%>
                    <div id="traingId">

                    </div>
                    <%--Employee Training End--%>

                </div>

                <div class="tab-pane pad0A fade active in" id="tab-example-4">
                    <div class="content-box">
                        <form method="POST" modelAttribute="employee" id="empbasicinfoId"
                                   class="form-horizontal bordered-row" data-parsley-validate="">
                            <input type="hidden" name="id" id="id" value="${employee.id}">
                            <input type="hidden" name="version" id="version" value="${employee.version}">

                            <div class="row">
                                <div class="form-group" style="margin-top: 7px">
                                    <label class="col-sm-3 control-label">PIN</label>
                                    <div class="col-sm-6">
                                        <input type="text" placeholder="Required Employee PIN" id="pin"
                                               value="${employee.pin}" required class="form-control" name="pin">
                                        <form:errors path="pin" cssclass="error"></form:errors>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Full Name</label>
                                    <div class="col-sm-6">
                                        <input type="text" value="${employee.fullName}" name="fullName" id="fullName"
                                               data-parsley-minlength="6" placeholder="At least 6 characters"
                                               required class="form-control">
                                        <form:errors path="fullName" cssclass="error"></form:errors>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Date of Birth</label>
                                    <div class="col-sm-6">
                                        <div class="input-prepend input-group">
                                    <span class="add-on input-group-addon">
                                        <i class="glyph-icon icon-calendar"></i>
                                    </span>
                                            <%--<input type="text" name="dateOfBirith" id="dob"
                                                   value="${employee.dateOfBirith}"
                                                   class="bootstrap-datepicker form-control tempDateFrom"
                                                   data-date-format="mm-dd-yy">--%>
                                            <input type="text" name="dateOfBirith" id="dob"  class=" bootstrap-datepicker form-control" value="${employee.dateOfBirith}" data-date-format="mm-dd-yy">
                                            <%--<form:errors path="dateOfBirith" cssclass="error"></form:errors>--%>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Email</label>
                                    <div class="col-sm-6">
                                        <input type="text" name="email" data-parsley-type="email" id="email"
                                               value="${employee.email}" placeholder="Email address" required
                                               class="form-control">
                                        <form:errors path="email" cssclass="error"></form:errors>
                                    </div>
                                </div>

                                    <%--<div class="form-group">
                                        <label class="col-sm-3 control-label">Gender</label>
                                        <div class="col-sm-6">
                                            <select name="gender.id" class="custom-select">
                                                <option value="">Please Select</option>
                                                <c:forEach var="gender" items="${genderList}">
                                                    <option value="${gender.id}" >${gender.column1}</option>
                                                   &lt;%&ndash; <option value="${gender.id}" ${gender.id == employee.gender.id ? 'selected="selected"' : ''}>${gender.column1}</option>&ndash;%&gt;
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>--%>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Father's Name</label>
                                    <div class="col-sm-6">
                                        <input type="text" placeholder="Required Field"
                                               value="${employee.fatherName}" required class="form-control"
                                               id="fatherName"
                                               name="fatherName">
                                        <form:errors path="fatherName" cssclass="error"></form:errors>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Mother's Name</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" name="motherName" id="motherName"
                                               value="${employee.motherName}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">Spouse's Name</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" name="spouseName" id="spouseName"
                                               value="${employee.spouseName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">National ID</label>
                                    <div class="col-sm-6">
                                        <input type="text" data-parsley-type="digits"
                                               placeholder="Enter your nid number" name="nid"
                                               value="${employee.nid}" required class="form-control">
                                    </div>
                                </div>

                                    <%--<div class="form-group">
                                        <label class="col-sm-3 control-label">Nationality</label>
                                        <div class="col-sm-6">
                                            <select name="nationality.id" class="custom-select">
                                                <option>Please Select</option>
                                                <c:forEach var="nationality" items="${nationalityList}">
                                                    <option value="${nationality.id}" ${nationality.id == employee.nationality.id ? 'selected="selected"' : ''}>${nationality.column1}</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>--%>

                                     <%--<div class="form-group">
                                         <label class="col-sm-3 control-label">Marrital Status</label>
                                         <div class="col-sm-6">
                                             <select name="marritalStatus.id" class="custom-select">
                                                 <option>Please Select</option>
                                                 <c:forEach var="marritalStatus" items="${marritalStatusList}">
                                                     <option value="${marritalStatus.id}" ${marritalStatus.id == employee.marritalStatus.id ? 'selected="selected"' : ''}>${marritalStatus.column1}</option>
                                                 </c:forEach>
                                             </select>
                                         </div>
                                     </div>--%>


                            </div>
                            <div class="bg-default content-box text-center pad20A mrg10T">
                                <input id="saveButton" style="float: left; margin-left: 5px;" type="button" value="Save"
                                       onclick="savePersonalInfo()" class="btn btn-lg btn-primary"/>
                                <input id="deleteButton" style="float: left; margin-left: 5px; display: none;"
                                       type="button"
                                       value="Delete" onclick="deleteLeaveType()" class="btn btn-lg btn-primary"/>
                                <input style="float: left; margin-left: 5px;" type="button" value="Cancel"
                                       onclick="Server.resetForm('leaveTYpeFormId')" class="btn btn-lg btn-primary"/>
                                <div style="clear: both"></div>
                            </div>
                        </form>


                    </div>
                </div>
            </div>
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
<script type="text/javascript">
    function savePersonalInfo() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var caption = "Employee Personal Information"
        var formId = $('#saveButton').closest('form').attr('id');
        var dob = new Date($('#dob').val());
        var _form_values = $('#empbasicinfoId').serializeJSON();
        var action ="${pageContext.request.contextPath}/emp/update";
        var gridId=""
        Server.save(header, _form_values, action, formId, caption,gridId);
        Server.resetForm(formId);
    }

    /*
    *Account settings
    * Return Account settings page with employee information
    */
    function accountSettings() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var id = $('#employeeId').val();
        var action ="${pageContext.request.contextPath}/emp/settings/edit/";
        console.log(id)
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: action + id,
            data: id,
            dataType: 'html',
            headers: header,
            success: function (result) {
                $('#accountsettingsId').html(result);
            },
            error: function (e) {
                alert("Error!" + e)
            }
        });


    }
    /*save employee account settings
    *check password
    * return success message
    * */
    function saveAccountSettings() {
        var frm = $('#accountSettingformId');
        var newpassword = $('#newPassword').val();
        var confirmpassword = $('#repeatPassword').val();
        if (newpassword != confirmpassword) {
            Server.getMessage(1, "Password doesnot match", "Password");
        }else{
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var caption = "Employee Account Information"
        var formId = "accountSettingformId";
        var _form_values = $('#accountSettingformId').serializeJSON();
        /*var accountsettings = {
            "id": $('#userid').val(),
            "username": $.trim($('#accusername').val()),
            "email": $('#accemail').val(),
            "password": $('#newPassword').val(),
            "fullName": $('#accFullName').val()
        }*/
        var action = "";
        var gridId=""
        var action = "${pageContext.request.contextPath}/useracc/save";
        Server.save(header, _form_values, action, formId, caption,gridId);
        Server.resetForm(formId);
        }

    }
    /*Account settings Tab
     *check password
     *on focusout field
     */
    $('div.divcls').on("blur", "div.passmatchcls input", function () {
        var newpassword = $('#newPassword').val();
        var confirmpassword = $('#repeatPassword').val();
        console.log("password check")
        if (newpassword != confirmpassword) {
            Server.getMessage(1, "Password doesnot match", "Password");

        }
    });
    /*
    * Fetch employee Data and
     * employee education page
    * */
    function empEducation() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var id = $('#employeeId').val();
        console.log("Employee Id Fetch Employee Education")
        console.log(id)
        var action = "${pageContext.request.contextPath}/emp/education/";
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: action + id,
            data: id,
            dataType: 'html',
            headers: header,
            success: function (result) {
                $('#empEducationId').html(result);

            },
            error: function (e) {
                alert("Error!" + e)
            }
        });


    }


    /*
     * Fetch Employee address and address page
     *
     * */
    function FetchEmpAddress() {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var id = $('#employeeId').val();
        var action = "${pageContext.request.contextPath}/employee/address/";
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: action + id,
            data: id,
            dataType: 'html',
            headers: header,
            success: function (result) {
                $('#empAddressId').html(result);
            },
            error: function (e) {
                alert("Error!" + e)
            }
        });


    }




</script>
    