var Server = {

    save:function (headerValue,data,url,formId,caption)
    {
    jQuery.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: url,
        async: true,
        data : JSON.stringify(data),
        dataType: "json",
        headers:header,
        success: function (result) {
            $('#'+formId).find("#jqGrid").trigger('reloadGrid');
            //resetForm()
        },
        error: function (result) {
           // document.getElementById("#"+formId).reset();
            Server.getMessage(1, result.responseText, caption);
            $('#jqGrid').trigger('reloadGrid');
            Server.resetForm(formId);

        }
    });
},
    delete: function(header,url,id,formId){
        $.confirm({
            title: 'Confirm!',
            content: 'Are you sure you want to delete the leave type!',
            buttons: {
                confirm: function () {
                    $.ajax({
                        type : "POST",
                        contentType : "application/json",
                        url : url+id,
                        data : {id:id},
                        dataType : 'json',
                        headers:header,
                        success : function(result) {
                            Server.getMessage(1,result,"Leave type information");
                            $('#jqGrid').trigger( 'reloadGrid' );
                            Server.resetForm(formId);
                        },
                        error : function(e) {
                            $('#jqGrid').trigger( 'reloadGrid' );
                            alert("Error!" +e)
                        }
                    });

                },
                cancel: function () {
                }
            }
        });
    },
    getMessage: function(type,msg,header){
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

    },
    resetForm:function(formIdReset){
        $('#'+formIdReset).trigger("reset");
        $('#id').val('');
        $('#deleteButton').hide();
        $('#jqGrid').trigger( 'reloadGrid' );
        $('#saveButton').val('Save');

    },
    validator:function(formId){
        return $('#'+formId).validate();

    },
    edit:function(header,url,id,callback){
        console.log(id);
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : url+id,
            data : id,
            dataType : 'json',
            headers:header,
            success : function(result) {
                callback(result);
                $('#deleteButton').show();
            },
            error : function(e) {
                alert("Error!" +e)
            }
        });

    },
    list:function(header,url,colModel,formId,caption){
        //Start JqGrid
        $('#'+formId).find('#jqGrid').jqGrid({
            url: url,
            mtype: "GET",
            styleUI : 'Bootstrap',
            postData: header,
            contentType : "application/json",
            datatype: "json",
            colModel:colModel ,
            pager: "#jqGridPager",
            rowNum:10,
            rownumbers: true,
            rowList:[10,20,30,50,100,500],
            viewrecords: true,
            sortorder: "asc",
            caption:caption,
           /* autoWidth: true,*/
            //width:600,
            height:243,
            altRows:true,
            shrinkToFit: false,
            scrollOffset: 0,
            onSelectRow: function() {
                var myGrid = $('#jqGrid'),
                    selectedRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
                    cellValue = myGrid.jqGrid ('getCell', selectedRowId, 'id');
                edit(cellValue);
            }
        });
        $('#'+formId).find("#jqGrid").jqGrid("setLabel", "rn", "SL.");
        $(window).bind('resize', function() {
            // resize the datagrid to fit the page properly:
            $('div.grid-resize').each(function() {
                $(this).width('auto');
                $(this).find('table').width('100%');
            });
        });

    }

}