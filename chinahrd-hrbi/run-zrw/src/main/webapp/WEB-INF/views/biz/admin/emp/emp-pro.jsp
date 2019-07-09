<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>用户项目配置</title>
    <link href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet"/>
    <style >
        .btn{margin-right: 20px;}
        select{padding: 3px 4px;height:19px;}
    </style>
</head>
<body>
    <div class="rightBody">
        <div class="SetUpBody">
            <div class="index-common-title bottom-title">
                <div class="index-common-title-left bottom-left"></div>
                <div class="index-common-title-text bottom-text">员工项目配置界面
                    <small>(${empDto.empName})</small>
                </div>
                <input type="hidden" id="empId" value="${empDto.empId}">
            </div>
            <div class="bottom-div clearfix">
                <div class="col-sm-12 ct-col2">
                    <button type="submit" class="btn btn-primary submitBtn">提交</button>
                    <button type="button" class="btn btn-default" id="resetBtn">返回</button>
                    <div class="ct-mCustomScrollBar height450">
                        <ul id="orgTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script>
    require(['messenger', 'ztree', 'utils', 'jquery-mCustomScrollBar'], function () {
        var webRoot = G_WEB_ROOT;
        var urls = {
            getTreeDataJsonUrl: webRoot + "/empProjectRelation/getTreeDataJson",
            addEmpProjectUrl: webRoot + "/empProjectRelation/addEmpProject",
            goUserUrl: webRoot + '/emp/list'
        }
        var treeData = [];
		var projType=null;
        $(".ct-mCustomScrollBar").mCustomScrollbar({
            axis: "yx",
            scrollButtons: {enable: true},
            scrollbarPosition: "outside",
            theme: "minimal-dark"
        });

        function getTreeDataJson() {
            $.ajax({
                url: urls.getTreeDataJsonUrl,
                type: 'post',
                dataType: 'json',
                data: {empId: $("#empId").val()},
                async: false,	//同步
                success: function (data) {
                    treeData = data.jsonData;
                    projType=data.proType;
                }
            });  
       
       }

        getTreeDataJson();

        var selectData = [];
        var treeObj;
        var setting = {
        		 view: {
    				//addHoverDom: addHoverDom,
    				//removeHoverDom: removeHoverDom,
    				addDiyDom: addDiyDom
    			}, 
    			data: {
    				key: {
    					title:"name"
    				},
    				simpleData: {
    					enable: true
    				}
    			},
             check: {
                enable: true,
//                 chkboxType: {"Y": "ps", "N": "ps"}
                chkboxType: {"Y": "s", "N": "ps"}
            }, 
            callback: {
                beforeClick: function (treeId, treeNode) {
                  if (treeNode.isParent) {
                       treeObj.expandNode(treeNode);
                        return false;
                    } 
                   
                }
            }
        };
        IDMark_A = "_a";
    	function addHoverDom( treeNode) {
    		var aObj = $("#" + treeNode.tId + IDMark_A);
			if ($("#diyBtn_"+treeNode.id).length>0) return;
			var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' >&nbsp;</span><select class='selDemo ' id='diyBtn_" +treeNode.id+ "'><option value=1>1</option><option value=2>2</option><option value=3>3</option></select>";
			aObj.after(editStr);
			var btn = $("#diyBtn_"+treeNode.id);
			if (btn) btn.bind("change", function(){alert("diy Select value="+btn.attr("value")+" for " + treeNode.name);});

		}
    	function removeHoverDom(treeId, treeNode) {
    		
			if (treeNode.parentTId && treeNode.getParentNode().id!=1) return;
			if (treeNode.id == 15) {
				$("#diyBtn1_"+treeNode.id).unbind().remove();
				$("#diyBtn2_"+treeNode.id).unbind().remove();
			} else {
				$("#diyBtn_"+treeNode.id).unbind().remove();
				$("#diyBtn_space_" +treeNode.id).unbind().remove();
			}
		}
    	function addDiyDom(treeId, treeNode) {
			if (treeNode.parentNode && treeNode.parentNode.id!=2) return;
			var aObj = $("#" + treeNode.tId + IDMark_A);
			var editStr = "<select class='selDemo' id='diyBtn_" +treeNode.id+ "'>";
			for(var i=0;i<projType.length;i++){
				if(treeNode.type==projType[i].type){
					editStr+="<option value="+projType[i].type+" selected='selected'>"+projType[i].typeName+"</option>";
				}else{
					editStr+="<option value="+projType[i].type+">"+projType[i].typeName+"</option>";
				}
				
			}
			editStr+="</select>";
				aObj.after(editStr);
				var btn = $("#diyBtn_"+treeNode.id);
				if (btn) btn.bind("change", function(){
					/*  var zTree = $.fn.zTree.getZTreeObj("orgTree");
					var nodes = zTree.getCheckedNodes(true);
					if (nodes.length == 0) {
	                    notifyInfo('请至少选择一条项目权限！', 'error');
	                    btn.prop('selectedIndex', 0);
	                    return false;
	                } */
					treeNode.type=btn.val();
					/* for(var i=0;i<nodes.length;i++){
						if(nodes[i].id==treeNode.id){
							nodes[i].type=btn.val();
						}
						
					} */
					
					//return nodes;
				});
		}

    	
        $(function () {
            var el = $("#orgTree");
           treeObj = $.fn.zTree.init(el, setting, treeData);

            $(".submitBtn").click(function () {

                // bug314 数据权限不是’开发部‘，而是’北京中人网信息咨询有限公司‘	by jxzhang
                selectData = [];
                // 获取已选的节点（已存在节点）
                var checkedNodes = treeObj.getCheckedNodes(true);
                //假如机构树没有选中的阶段，不给提交
                if (checkedNodes.length == 0) {
                    notifyInfo('请至少选择一条项目权限！', 'error');
                    return false;
                }
                $.each(checkedNodes, function (index, item) {
                    selectData.push({
                    	projectId: item.id,
                        fullPath: item.fullPath,
                        halfCheck: 1,
                    	type:item.type
                    });
                });
                var pojoDto = {
                	empId: $("#empId").val(),
                    projectDto: selectData
                }
                 $.ajax({
                    url: urls.addEmpProjectUrl,
                    type: 'post',
                    data: JSON.stringify(pojoDto),
                    dataType: 'json',
                    contentType: 'application/json;charset=utf-8',
                    success: function (data) {
                        var dataType = (data.type) ? 'success' : 'error';
                      //  notifyInfo(data.msg, dataType);
                        if (dataType == 'success') $('#resetBtn').click();
                    }
                }); 
            });

            $('#resetBtn').click(function () {
                window.location.href = urls.goUserUrl;
            });
        });
        
        function notifyInfo(msg, type) {
            Messenger().post({
                type: type,
                message: msg
            });
           
        }
    });
</script>
</body>
</html>