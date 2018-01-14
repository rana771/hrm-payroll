<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="panel">
    <div class="panel-body">
        <h3 class="title-hero">
            Create Leave Type
        </h3>

        <div class="example-box-wrapper">
            <form class="form-horizontal bordered-row" name="item" id="leaveTYpeFormId" method="POST"   data-parsley-validate="">
                <div class="row">
                    <input type="hidden" id="csr-token"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden"   name="id" id="id" value="">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Name:</label>
                            <div class="col-sm-6">
                                <input type="text"  name="name" id="leavetypeName" path="name" placeholder="Name" required class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="bg-default content-box text-center pad20A mrg10T" >
                    <input id="saveButton" style="float: left; margin-left: 5px;" type="button" value="Save" onclick="saveLeaveType()" class="btn btn-lg btn-primary"/>
                    <input id="deleteButton" style="float: left; margin-left: 5px; display: none;"type="button" value="Delete" onclick="deleteLeaveType()" class="btn btn-lg btn-primary"/>
                    <input style="float: left; margin-left: 5px;" type="button" value="Cancel" onclick="resetForm()" class="btn btn-lg btn-primary"/>
                    <div style="clear: both"></div>
                </div>

                <div class="bg-default content-box text-center pad20A mrg25T">
                    <table id="jqGrid"></table>
                    <div id="jqGridPager"></div>
                </div>
            </form>
        </div>
    </div>

</div>


<style type="text/css">
    .bg-red{
        background: #cf4436;
        color: #FFFFFF;}
    .bg-green{
        background: #449d44;
        color: #FFFFFF;}
    .bg-azure{
        background: #d67520;
        color: #FFFFFF;}

</style>


<script type="text/javascript">
    function getSuccessMessage(type,msg,header ){
        var color = '';
        if(type==1) {
            var color = 'bg-green';
        }else if(type==2){
            var color = 'bg-red';
        }else if(type==3){
            var color = 'bg-azure';
        }
        $.jGrowl(""+ msg +"", {
            header: header,
            sticky: false,
            position: 'bottom-right',
            theme: color
        });
    }
    $( document ).ready(function() {
        //Start JqGrid
        $("#jqGrid").jqGrid({
            url: '${contextPath}/leavetype/list',
            mtype: "POST",
            styleUI : 'Bootstrap',
            postData: { 'X-CSRF-TOKEN': $('#csr-token').val(),
                '${_csrf.parameterName}': $('#csr-token').val()},
            contentType : "application/json",
            datatype: "json",
            colModel: [
                { label: 'id', name: 'id', key: true, width: 75,hidden:true },
                { label:'Leave Type', name: 'name', width: 150 },


            ],
            pager: "#jqGridPager",
            rowNum:10,
            rownumbers: true,
            rowList:[10,20,30,50,100,500],
            viewrecords: true,
            sortorder: "asc",
            caption:"Leave Type Information",
            autoWidth: true,
            width:900,
            height:243,
            altRows:true,
            scrollOffset: 0,
            onSelectRow: function() {
                var myGrid = $('#jqGrid'),
                        selectedRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
                        cellValue = myGrid.jqGrid ('getCell', selectedRowId, 'id');
                edit(cellValue);
            }
        });

        //End JqGrid
        $("#jqGrid").jqGrid("setLabel", "rn", "SL.");

    });

    function deleteLeaveType(){
        $.confirm({
            title: 'Confirm!',
            content: 'Are you sure you want to delete the leave type!',
            buttons: {
                confirm: function () {
                    var leaveTypeId = $('#id').val();
                    $.ajax({
                        type : "POST",
                        contentType : "application/json",
                        url : '${contextPath}/leavetype/delete/'+leaveTypeId,
                        data : {leaveTypeId:leaveTypeId},
                        dataType : 'json',
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader('X-CSRF-TOKEN', $('#csr-token').val());
                            xhr.setRequestHeader('${_csrf.parameterName}', $('#csr-token').val());
                        },
                        success : function(result) {
                            getSuccessMessage(1,result,"Leave type information");
                            resetForm()
                        },
                        error : function(e) {
                            alert("Error!" +e)
                        }
                    });
                },
                cancel: function () {
                }
            }
        });
    }

    function edit(leaveTypeId){
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : '${contextPath}/leavetype/edit/'+leaveTypeId,
            data : leaveTypeId,
            dataType : 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', $('#csr-token').val());
                xhr.setRequestHeader('${_csrf.parameterName}', $('#csr-token').val());
            },
            success : function(result) {
                setDataToEdit(result)
                $('#deleteButton').show();
            },
            error : function(e) {
                alert("Error!" +e)
            }
        });
    }
    function setDataToEdit(result){
        $('#id').val(result.id);
        $('#leavetypeName').val(result.name);
        $('#saveButton').val('Update');
    }
    function validator(){
        return $('leaveTYpeFormId').validate();
    }

    function resetForm(){
        $('#id').val('');
        $('#leavetypeName').val('');
        $('#deleteButton').hide();
        $('#jqGrid').trigger( 'reloadGrid' );
        $('#saveButton').val('Save');
    }
    function saveLeaveType (){
        var frm = $('#leaveTYpeFormId')
        frm.validate();
        var isValid = frm.valid();
        if(!isValid){
            return false;
        }
        var leavetype={
            "id":$('#id').val(),
            "name":$('#leavetypeName').val(),
        }
        var action = "";
        if($('#id').val()>0){
            var action ="${contextPath}/leavetype/update";
        }else{
            var action ="${contextPath}/leavetype/save";
        }
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : action,
            data : JSON.stringify( $('#leaveTYpeFormId').serialize()),
            dataType : 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', $('#csr-token').val());
                xhr.setRequestHeader('${_csrf.parameterName}', $('#csr-token').val());
            },
            success : function(data) {
                //getSuccessMessage(1,"Data has been saved successfully!","Item Type");
                $('#jqGrid').trigger( 'reloadGrid' );
                resetForm()
            },
            error : function(data) {
                document.getElementById("leaveTYpeFormId").reset();
                if(data.responseText=="unique"){
                    getSuccessMessage(2,"Name has to be unique","Leave Type");
                }
                else {
                    getSuccessMessage(1,"Data has been saved successfully!","Leave Type");
                }

                $('#jqGrid').trigger( 'reloadGrid' );
                resetForm();

            }
        });
    }

</script>
