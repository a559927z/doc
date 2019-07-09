<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>用户管理</title>
    <style>
        .panel-body { max-height: 350px; overflow-y: auto; }
        #searchEmpTable { min-height: 310px;}
        .dis-search{width: 100%;font-size: 12px !important;}
		.dis-search-input{line-height:2 !important;font-size: 12px !important;float: left;width: 200px;height: 35px;margin-top: 1px;border: 1px solid #e0e0e0;font-size: 12px;padding-top: 4px;padding-left: 12px;}
		.dis-search-input-btn{cursor:pointer;font-size: 14px !important;float: left;width: 80px;height: 35px;line-height:35px;border: 1px solid #e0e0e0;margin-left: 20px;text-align: center;border-radius: 5px;}
		.add-on{background-color: #EDA100;color: #FFFFFF;display: inline-block;width: 100px;height: 30px;margin-top: 3px;line-height: 30px;text-align: center;}
    </style>
</head>
<body>
<div class="rightBody">
    <div class="SetUpBody">
        <div class="index-common-title bottom-title">
            <div class="index-common-title-left bottom-left"></div>
            <div class="index-common-title-text bottom-text">用户管理</div>
        </div>
        <div class="col-sm-12 ct-line-col conditionBtnListBody" id="searchBox">
            <div class="dis-search">
                <input class="dis-search-input btn-margin" id="searchNameTxt" type="text" placeholder="中文名" />
                <div class="btn btn-warning" id="searchBtnId"><span class="glyphicon glyphicon-search"></span>搜索</div>
                <div class="btn-group">
				  <button type="button" id="dimEmpOper" class="btn btn-success"><span class="glyphicon glyphicon-user"></span>添加员工</button>
				   <button class="btn btn-success dropdown-toggle" type="button" data-toggle="dropdown">
					    &nbsp; <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu">
				  
				    <li role="presentation"><a role="menuitem" id="emprowadd" tabindex="-1" href="javascript:void(0)"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加员工</a></li>
				    <li role="presentation"><a role="menuitem" id="emprowedit" tabindex="-1" href="javascript:void(0)"><span class="glyphicon glyphicon-edit"></span>&nbsp;修改员工</a></li>
				  </ul>
				</div>
            </div>
        </div>
		<div style="clear:both"></div>
        <div class="bottom-div bottom-div2 clearfix">
            <div class="col-sm-12 ct-col1">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
        </div>
    </div>
</div>

<!-- 功能(Modal) -->
<div class="modal fade" id="userRoleModal" tabindex="-1" role="dialog"
     aria-labelledby="userRoleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <input type="hidden" id="roleUserId" name="userId">
                <h3 class="modal-title" id="userRoleModalLabel">用户角色配置
                    <small>&nbsp;</small>
                </h3>
            </div>
            <div class="modal-body">
                <div class="row" style="margin: 0;" id="modalPanel">
                    <div class="col-xs-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">未选角色</h3>
                            </div>
                            <div class="panel-body">
                                <div class="list-group" id="notSelectPanel"></div>
                            </div>
                        </div>
                    </div>
                    <div class=" col-xs-6">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">已选角色</h3>
                            </div>
                            <div class="panel-body">
                                <div class="list-group" id="isSelectPanel"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="confirmRoleBtn">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>

        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 搜索弹出框 -->
<div id="searchModal" class="modal fade" data-backdrop="static" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <input type="hidden" id="searchUserId">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h3 class="modal-title">绑定员工</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-7">
                        <div class="input-group">
                            <input type="text" class="form-control search-query" id="searchTxt"
                                   placeholder="请输入员工ID/姓名"/>
                            <span class="input-group-btn">
                                    <button type="button" class="btn btn-white" id="searchBtn">
                                        	查询
                                        <i class="icon-search icon-on-right bigger-110"></i>
                                    </button>
                                </span>
                        </div>
                    </div>
                    <div class="col-xs-5"><label>* 姓名支持模糊查询</label></div>
                </div>
                <div class="row">
                    <div class="col-xs-12 column" id="searchEmpTable">
                        <table id="searchEmpGrid"></table>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<script src="${ctx}/assets/js/biz/admin/userList.js"></script>
</body>
</html>