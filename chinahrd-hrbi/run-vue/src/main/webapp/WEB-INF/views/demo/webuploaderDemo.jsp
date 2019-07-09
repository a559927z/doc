<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/include/top.jsp"%>
<title>webupload上传demo</title>
<link rel="stylesheet" href="${ctx}/assets/css/lib/webuploader.css">
<!--引入JS-->
</head>
<body>
	<div class="page-content">
		<!-- 上传 -->
		<div id="uploader" class="">
			<!--用来存放文件信息-->
			<div id="thelist" class="uploader-list"></div>
			<div class="btns">
				<div id="picker">选择文件</div>
				<button id="ctlBtn" class="btn btn-default">开始上传</button>
			</div>
		</div>
	</div>
    <!-- include -->
<!-- 	<div id="uploader2"> -->
<!--         <div class="queueList"> -->
<!--             <div id="dndArea" class="placeholder"> -->
<!--                 <div id="filePicker"></div> -->
<!--                 <p>或将照片拖到这里，单次最多可选300张</p> -->
<!--             </div> -->
<!--         </div> -->
<!--         <div class="statusBar" style="display:none;"> -->
<!--             <div class="progress"> -->
<!--                 <span class="text">0%</span> -->
<!--                 <span class="percentage"></span> -->
<!--             </div> -->
<!--             <div class="info"></div> -->
<!--             <div class="btns"> -->
<!--                 <div id="filePicker2"></div> -->
<!--                 <div class="uploadBtn">开始上传</div> -->
<!--             </div> -->
<!--         </div> -->
<!--     </div> -->
<script>
	require(['jquery','webuploader'], function($,WebUploader) {
		var webRoot = G_WEB_ROOT;
		var uploader = WebUploader.create({
		    // swf文件路径
		    swf: webRoot + '/assets/js/lib/webuploader/Uploader.swf',
		    // 文件接收服务端。
		    server: webRoot + '/demo/doUpload.do',
		    // 选择文件的按钮。可选。
		    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
		    pick: '#picker',
// 		    accept: {
//                 title: 'excel',
//                 extensions: 'xls,xlsx'
//             },

		    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
		    resize: false
		});
		
		//由于webuploader不处理UI逻辑，所以需要去监听fileQueued事件来实现。
		// 当有文件被添加进队列的时候
		uploader.on( 'fileQueued', function( file ) {
		    $('#thelist').append( '<div id="' + file.id + '" class="item">' +
		        '<h4 class="info">' + file.name + '</h4>' +
		        '<p class="state">等待上传...</p>' +
		    '</div>' );
		});
		
		//文件上传中，Web Uploader会对外派送uploadProgress事件，其中包含文件对象和该文件当前上传进度。
		// 文件上传过程中创建进度条实时显示。
		uploader.on( 'uploadProgress', function( file, percentage ) {
		    var $li = $( '#'+file.id ),
		        $percent = $li.find('.progress .progress-bar');

		    // 避免重复创建
		    if ( !$percent.length ) {
		        $percent = $('<div class="progress progress-striped active">' +
		          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
		          '</div>' +
		        '</div>').appendTo( $li ).find('.progress-bar');
		    }

		    $li.find('p.state').text('上传中');

		    $percent.css( 'width', percentage * 100 + '%' );
		});
		
		//文件上传失败会派送uploadError事件，成功则派送uploadSuccess事件。不管成功或者失败，在文件上传完后都会触发uploadComplete事件
		uploader.on( 'uploadSuccess', function( file ) {
		    $( '#'+file.id ).find('p.state').text('已上传');
		});

		uploader.on( 'uploadError', function( file ) {
		    $( '#'+file.id ).find('p.state').text('上传出错');
		});

		uploader.on( 'uploadComplete', function( file ) {
		    $( '#'+file.id ).find('.progress').fadeOut();
		});
		var state = 'pending', $btn = $('#ctlBtn');
		
		$btn.on( 'click', function() {
		        if ( state === 'uploading' ) {
		            uploader.stop();
		        } else {
		            uploader.upload();
		        }
	     });
		
		/**上传图片
		-------------------------------------------------------------------*/
		/**
		// 实例化
        var uploader2 = WebUploader.create({
            pick: {
                id: '#filePicker',
                label: '点击选择图片'
            },
            formData: {
                uid: 123
            },
            dnd: '#dndArea',//拉拽区域div的id
            paste: '#uploader',//黏贴区域
            swf: '/common/plugin/webuploader/Uploader.swf',
            chunked: false,
            chunkSize: 512 * 1024,
            server: '/sys/uploadHeadPic/',//上传的URL
            // runtimeOrder: 'flash',
             accept: {
                 title: 'Images',
                 extensions: 'gif,jpg,jpeg,bmp,png,jar'
             },
            // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
            disableGlobalDnd: true,
            fileNumLimit: 300,
            fileSizeLimit: 200 * 1024 * 1024,    // 200 M
            fileSingleSizeLimit: 50 * 1024 * 1024    // 50 M
        });
        uploader2.on( 'uploadSuccess', function( type ) {
           alert(11000);
        });
        */
	})
</script>
</body>
</html>