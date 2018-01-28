<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="panel">
    <div class="panel-body">
        <h3 class="title-hero">
            Employee List
        </h3>

        <div class="example-box-wrapper">
            <form class="form-horizontal bordered-row" name="leaveType" id="employeeListId" method="POST"
                  data-parsley-validate="">
                <div class="row">
                    <input type="hidden" id="csr-token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Name:</label>
                            <div class="col-sm-6">
                                <input type="text" name="searchEmployeeByName" id="searchEmployeeByName" path="searchEmployeeByName" placeholder="Name"
                                       required class="form-control">
                            </div>
                        </div>
                    </div>
                    </div>

                <div class="bg-default content-box text-center pad20A mrg10T">
                    <input id="searchButton" style="float: left; margin-left: 5px;" type="button" value="Search"
                           onclick="search()" class="btn btn-lg btn-primary"/>
                    <input style="float: left; margin-left: 5px;" type="button" value="Cancel" onclick="Server.resetForm('hrYearId')" class="btn btn-lg btn-primary"/>
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
<script type="text/javascript">
    $(document).ready(function () {
        header = {
            'X-CSRF-TOKEN': $('#csr-token').val(),
            '${_csrf.parameterName}': $('#csr-token').val()
        };
        var url = '${contextPath}/emp/list2';
        var urlmethod='POST'
        var formId = 'employeeListId';
        var colModel = [
            {label: 'id', name: 'id', key: true, width: 75, hidden: true},
            {label: 'pin', name: 'pin', width: 100},
            {label: 'Full Name', name: 'name', width: 150},
            {label: 'Email', name: 'email', width: 150},
            {label: 'Father Name', name: 'fname', width: 150},
            {label: 'Date of Joining', name: 'doj', width: 150},
            {label: 'Date of Confirmation', name: 'doc', width: 150},
            {label: 'National Id', name: 'nid', width: 150}
        ]

        Server.list(header, url, colModel, formId,"Employee List",urlmethod);


    });
    function edit(id){
        window.location.href = "${contextPath}/emp/edit/"+id;
    }




</script>
