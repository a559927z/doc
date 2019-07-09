<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>员工管理</title>
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
            <div class="index-common-title-text bottom-text">员工管理</div>
        </div>
        <div class="col-sm-12 ct-line-col conditionBtnListBody" id="searchBox">
            <div class="dis-search">
                <input class="dis-search-input btn-margin" id="searchNameTxt" type="text" placeholder="中文名" />
                <div class="add-on dis-search-input-btn" id="searchBtnId">搜索</div>
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


<script type="text/javascript" src="${jsRoot}biz/employee/empManage.js"></script>
</body>
</html>