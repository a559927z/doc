require(['jquery', 'bootstrap', 'utils'], function ($) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        save: webRoot + '/redis/save.do',
        get: webRoot + '/redis/get.do',
        del: webRoot + '/redis/del.do',
        allKeys: webRoot + '/redis/allKeys.do',
    }
    var $table = $("#table-user");

    /**
     * 保存
     */
    $("#saveBtn").click(function () {
        Tc.ajax({
            url: urls.save,
            data: {"key": $("#key").val(), "value": $("#value").val()},
            success: function (data) {
                $("#rsSaveZone").text(data);
                allKeys();
            }
        });
    });

    /**
     * 获取值
     */
    $("#getBtn").click(function () {
        Tc.ajax({
            url: urls.get,
            data: {"queryKey": $("#queryKey").val()},
            success: function (data) {
                $("#rsGetZone").text(data);
            }
        });
    });
    /**
     * 删除值
     */
    $("#delBtn").click(function () {
        Tc.ajax({
            url: urls.del,
            data: {"delKey": $("#delKey").val()},
            success: function (data) {
                $("#rsDelZone").text(data);
                allKeys();
            }
        });
    });

    /**
     * 刷新
     */
    $("#refBtn").click(function () {
        allKeys();
    });


    function allKeys() {
        Tc.ajax({
            url: urls.allKeys,
            success: function (data) {
                var html = '';
                $.each(data, function (index, item) {
                    html += '<tr><td>' + item.key + '</td><td>' + item.value + '</td></tr>';
                });
                $table.find("tbody").empty();
                $table.find("tbody").append(html);
            }
        });
    }

    allKeys();

});