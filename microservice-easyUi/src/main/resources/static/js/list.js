
/**
 * 查询详情
 */
function loadDetails() {
    var url = contextPath+"/getDataList" + "?" + GetParams();
    $('#configTable').datagrid({url: url});
}

function resetSearch() {
    $('#searchForm')[0].reset();
}

/**
 * 搜集查询数据
 * @param formId
 * @constructor
 */
function GetParams() {
    var param = $("#searchForm").serialize();
    return param;
}

/**
 * easyUi 日期格式化
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

function dateTimeFormatter(value, row, index) {
    if (!value) {
        return "";
    }
    var date = new Date(value);
    return date.format('yyyy-MM-dd hh:mm:ss');
}

/**
 * easyUi 业务类型格式化
 */
function statusFormatter(value, row, index) {
    if (value == 'NORMAL') {
        return '正常';
    }
    return value;
}



function deleteConfig() {
    var idsArr = [];
    var rows = $('#configTable').datagrid('getSelections');
    if (!rows||rows.length==0) {
        alert('请选择数据');
        return;
    }
    for (var i = 0; i < rows.length; i++) {
        idsArr.push(rows[i].userId);
    }
    var ids=idsArr.join(',');

    $.ajax({
        type: "post",
        dataType: "json",
        url:contextPath+"/delete",
        data:{'configIds':ids},
        success: function (data) {
            alert(data.msg);
            $("#configTable").datagrid("reload",{ });
        },
        error: function (data) {
            alert("error:" + data.responseText);
        }
    });

}