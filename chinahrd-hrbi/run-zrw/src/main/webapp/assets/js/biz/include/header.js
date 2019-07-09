/**
 * htpeng
 */
var closeMessage;
require(['jquery', 'underscore', 'utils', 'organTreeSelector', 'message'], function ($, echarts) {
    var webRoot = G_WEB_ROOT;
    var urls = {
        memoUrl: webRoot + '/message/findMessage.do'				//查看消息信息
    };
    /*
     初始化组织架构
     */
    var topOrganId = window.currOrganId = $('#topOrganId').val();
    var topOrganName = window.currOrganTxt = $('#topOrganName').val();

    var organTreeSelector = $("#tree").organTreeSelector({
        multiple: false,
        value: {
            id: topOrganId,
            text: topOrganName
        },
        showSelectBtn: false,
        onSelect: function (ids, texts) {
            window.currOrganId = ids;
            window.currOrganTxt = texts;
            var organObject = window.organizationObject;
            if (organObject && $.isFunction(organObject)) {
                organObject(ids, texts);
            }
        }
    });

    window.setOrganTreeVal = function (organId, organTxt) {
        organTreeSelector.organTreeSelector("value", {id: organId, text: organTxt})
    }

    /*
     消息
     */
    var messageContext = null;
    var messageObj = {
        init: function (organizationId) {
            var self = this;
//    		if(organizationId == self.organizationId){
//    			return;
//    		}
            self.organizationId = organizationId;
            $.ajax({
                url: urls.memoUrl,
                type: "post",
                data: {organizationId: organizationId},
                success: function (result) {
                	  if(typeof result !="object") {
                		  result=[];
                     }
                	  messageContext = $('#message').message(self.getOption(result));	//初始化
                      $(".modal-backdrop").hide();
                   
                }
            });
        },
        getOption: function (rs) {
            var organizationId = this.organizationId;
            //参数配置
            var options = {
                id: "",
                title: '消息',
                titleSuffix: '条新消息',
                data: rs,
                parm: {organizationId: organizationId}
            }
            return options;
        }
    }

    messageObj.init(topOrganId);
    closeMessage = function () {
        if (null != messageContext) {
            $(messageContext).hide();
        }
    }
});

