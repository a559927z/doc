<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- jsp文件头和头部 -->
    <%@include file="/WEB-INF/views/include/top.jsp" %>
    <title>团队画像</title>
    <link rel="stylesheet" href="${ctx}/assets/css/ztree/zTreeStyle/zTreeStyle.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/biz/teamImg2.css"/>
</head>
<body>

<div class="teamimg" id="app">
    <div class="rightBody">
        <div class="rightbodypage">
            <div class="row ct-row">
				<block-layout lable="星座分布" clazz="height100pc" >
					<constellation-component  :list="list.constellation"  >
				</block-layout>
                <block-layout lable="性格" clazz="height100pc" >
                	<annular-component  :list="list.personality" />
                </block-layout>
                <block-layout lable="职级" clazz="height100pc" >
                	<pie-component  :list="list.ability" />
                </block-layout>
                <block-layout lable="工作地点" clazz="height100pc" >
                	<pie-component  :list="list.workLocation"  />
                </block-layout>
                <block-layout lable="男女比例" clazz="height100pc">
                	<annular-component :list="list.sex" />
                </block-layout>
                <block-layout lable="婚姻状况" clazz="height100pc">
                	<bar-component  :list="list.marry"  />
                </block-layout>
                <block-layout lable="年龄" clazz="height100pc">
                	<bar-component  :list="list.age" />
                </block-layout>
                <block-layout lable="司龄" clazz="height100pc">
                	<bar-component  :list="list.companyAge"  />
                </block-layout>
                <block-layout lable="血型" clazz="height100pc">
                	<pie-component  :list="list.blood"  />
                </block-layout>
            </div>
        </div>
    </div>
</div>


<script src="${ctx}/assets/js/lib/echarts/echarts.js"></script>
<script src="${ctx}/assets/js/biz/teamImgVue.js"></script>
</body>
</html>
