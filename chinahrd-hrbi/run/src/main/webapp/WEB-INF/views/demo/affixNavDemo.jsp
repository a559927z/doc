<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
<%@include file="/WEB-INF/views/include/top.jsp"%>
<title>附加导航Demo</title>
</head>
<body>
	<div class="page-content" >
		<div class="container">
			<div class="page-header">
				<h1>附加导航demo</h1>
			</div>
			<div class="row" id="myScrollspy">
				<div class="col-xs-3">
					<ul class="nav u-affix-nav" id="myNav">
						<li class="active"><a href="#section-1">第一部分</a></li>
						<li><a href="#section-2">第二部分</a></li>
						<li><a href="#section-3">第三部分</a></li>
						<li><a href="#section-4">第四部分</a></li>
						<li><a href="#section-5">第五部分</a></li>
					</ul>
				</div>
				<div class="col-xs-9">
					<div id="accordion" class="accordion-style1 panel-group">
						<div class="panel panel-default">
							<div class="panel-heading" id="section-1">
								<h3 class="panel-title">
									<a class="accordion-toggle" data-toggle="collapse"
										href="#collapseOne"> <i class="icon-angle-down bigger-110"
										data-icon-hide="icon-angle-down"
										data-icon-show="icon-angle-right"></i> &nbsp;第一部分
									</a>
								</h3>
							</div>
							<div class="panel-collapse collapse in" id="collapseOne">
								<div class="panel-body">
									<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
										Nam eu sem tempor, varius quam at, luctus dui. Mauris magna
										metus, dapibus nec turpis vel, semper malesuada ante.
										Vestibulum id metus ac nisl bibendum scelerisque non non
										purus. Suspendisse varius nibh non aliquet sagittis. In
										tincidunt orci sit amet elementum vestibulum. Vivamus
										fermentum in arcu in aliquam. Quisque aliquam porta odio in
										fringilla. Vivamus nisl leo, blandit at bibendum eu, tristique
										eget risus. Integer aliquet quam ut elit suscipit, id interdum
										neque porttitor. Integer faucibus ligula.</p>
									<p>Vestibulum quis quam ut magna consequat faucibus.
										Pellentesque eget nisi a mi suscipit tincidunt. Ut tempus
										dictum risus. Pellentesque viverra sagittis quam at mattis.
										Suspendisse potenti. Aliquam sit amet gravida nibh, facilisis
										gravida odio. Phasellus auctor velit at lacus blandit, commodo
										iaculis justo viverra. Etiam vitae est arcu. Mauris vel congue
										dolor. Aliquam eget mi mi. Fusce quam tortor, commodo ac dui
										quis, bibendum viverra erat. Maecenas mattis lectus enim, quis
										tincidunt dui molestie euismod. Curabitur et diam tristique,
										accumsan nunc eu, hendrerit tellus.</p>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading" id="section-2">
								<h3 class="panel-title">
									<a class="accordion-toggle" data-toggle="collapse"
										href="#collapseTwo"> <i class="icon-angle-down bigger-110"
										data-icon-hide="icon-angle-down"
										data-icon-show="icon-angle-right"></i> &nbsp;第二部分
									</a>
								</h3>
							</div>
							<div class="panel-collapse collapse in" id="collapseTwo">
								<div class="panel-body">
									<p>Nullam hendrerit justo non leo aliquet imperdiet. Etiam
										in sagittis lectus. Suspendisse ultrices placerat accumsan.
										Mauris quis dapibus orci. In dapibus velit blandit pharetra
										tincidunt. Quisque non sapien nec lacus condimentum facilisis
										ut iaculis enim. Sed viverra interdum bibendum. Donec ac
										sollicitudin dolor. Sed fringilla vitae lacus at rutrum.
										Phasellus congue vestibulum ligula sed consequat.</p>
									<p>Vestibulum consectetur scelerisque lacus, ac fermentum
										lorem convallis sed. Nam odio tortor, dictum quis malesuada
										at, pellentesque vitae orci. Vivamus elementum, felis eu
										auctor lobortis, diam velit egestas lacus, quis fermentum
										metus ante quis urna. Sed at facilisis libero. Cum sociis
										natoque penatibus et magnis dis parturient montes, nascetur
										ridiculus mus. Vestibulum bibendum blandit dolor. Nunc orci
										dolor, molestie nec nibh in, hendrerit tincidunt ante. Vivamus
										sem augue, hendrerit non sapien in, mollis ornare augue.</p>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading" id="section-3">
								<h3 class="panel-title">
									<a class="accordion-toggle" data-toggle="collapse"
										href="#collapseThree"> <i
										class="icon-angle-down bigger-110"
										data-icon-hide="icon-angle-down"
										data-icon-show="icon-angle-right"></i> &nbsp;第三部分
									</a>
								</h3>
							</div>
							<div class="panel-collapse collapse in" id="collapseThree">
								<div class="panel-body">
									<p>Integer pulvinar leo id risus pellentesque vestibulum.
										Sed diam libero, sodales eget sapien vel, porttitor bibendum
										enim. Donec sed nibh vitae lorem porttitor blandit in nec
										ante. Pellentesque vitae metus ipsum. Phasellus sed nunc ac
										sem malesuada condimentum. Etiam in aliquam lectus. Nam vel
										sapien diam. Donec pharetra id arcu eget blandit. Proin
										imperdiet mattis augue in porttitor. Quisque tempus enim id
										lobortis feugiat. Suspendisse tincidunt risus quis dolor
										fringilla blandit. Ut sed sapien at purus lacinia porttitor.
										Nullam iaculis, felis a pretium ornare, dolor nisl semper
										tortor, vel sagittis lacus est consequat eros. Sed id pretium
										nisl. Curabitur dolor nisl, laoreet vitae aliquam id,
										tincidunt sit amet mauris.</p>
									<p>Phasellus vitae suscipit justo. Mauris pharetra feugiat
										ante id lacinia. Etiam faucibus mauris id tempor egestas. Duis
										luctus turpis at accumsan tincidunt. Phasellus risus risus,
										volutpat vel tellus ac, tincidunt fringilla massa. Etiam
										hendrerit dolor eget ante rutrum adipiscing. Cras interdum
										ipsum mattis, tempus mauris vel, semper ipsum. Duis sed dolor
										ut enim lobortis pellentesque ultricies ac ligula.
										Pellentesque convallis elit nisi, id vulputate ipsum
										ullamcorper ut. Cras ac pulvinar purus, ac viverra est.
										Suspendisse potenti. Integer pellentesque neque et elementum
										tempus. Curabitur bibendum in ligula ut rhoncus.</p>
									<p>Quisque pharetra velit id velit iaculis pretium. Nullam
										a justo sed ligula porta semper eu quis enim. Pellentesque
										pellentesque, metus at facilisis hendrerit, lectus velit
										facilisis leo, quis volutpat turpis arcu quis enim. Nulla
										viverra lorem elementum interdum ultricies. Suspendisse
										accumsan quam nec ante mollis tempus. Morbi vel accumsan diam,
										eget convallis tellus. Suspendisse potenti.</p>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading" id="section-4">
								<h3 class="panel-title">
									<a class="accordion-toggle" data-toggle="collapse"
										href="#collapseFour"> <i
										class="icon-angle-down bigger-110"
										data-icon-hide="icon-angle-down"
										data-icon-show="icon-angle-right"></i> &nbsp;第四部分
									</a>
								</h3>
							</div>
							<div class="panel-collapse collapse in" id="collapseFour">
								<div class="panel-body">
									<p>Suspendisse a orci facilisis, dignissim tortor vitae,
										ultrices mi. Vestibulum a iaculis lacus. Phasellus vitae
										convallis ligula, nec volutpat tellus. Vivamus scelerisque
										mollis nisl, nec vehicula elit egestas a. Sed luctus metus id
										mi gravida, faucibus convallis neque pretium. Maecenas quis
										sapien ut leo fringilla tempor vitae sit amet leo. Donec
										imperdiet tempus placerat. Pellentesque pulvinar ultrices nunc
										sed ultrices. Morbi vel mi pretium, fermentum lacus et,
										viverra tellus. Phasellus sodales libero nec dui convallis,
										sit amet fermentum sapien auctor. Vestibulum ante ipsum primis
										in faucibus orci luctus et ultrices posuere cubilia Curae; Sed
										eu elementum nibh, quis varius libero.</p>
									<p>Vestibulum quis quam ut magna consequat faucibus.
										Pellentesque eget nisi a mi suscipit tincidunt. Ut tempus
										dictum risus. Pellentesque viverra sagittis quam at mattis.
										Suspendisse potenti. Aliquam sit amet gravida nibh, facilisis
										gravida odio. Phasellus auctor velit at lacus blandit, commodo
										iaculis justo viverra. Etiam vitae est arcu. Mauris vel congue
										dolor. Aliquam eget mi mi. Fusce quam tortor, commodo ac dui
										quis, bibendum viverra erat. Maecenas mattis lectus enim, quis
										tincidunt dui molestie euismod. Curabitur et diam tristique,
										accumsan nunc eu, hendrerit tellus.</p>
									<p>Phasellus fermentum, neque sit amet sodales tempor, enim
										ante interdum eros, eget luctus ipsum eros ut ligula. Nunc
										ornare erat quis faucibus molestie. Proin malesuada consequat
										commodo. Mauris iaculis, eros ut dapibus luctus, massa enim
										elementum purus, sit amet tristique purus purus nec felis.
										Morbi vestibulum sapien eget porta pulvinar. Nam at quam diam.
										Proin rhoncus, felis elementum accumsan dictum, felis nisi
										vestibulum tellus, et ultrices risus felis in orci. Quisque
										vestibulum sem nisl, vel congue leo dictum nec. Cras eget est
										at velit sagittis ullamcorper vel et lectus. In hac habitasse
										platea dictumst. Etiam interdum iaculis velit, vel
										sollicitudin lorem feugiat sit amet. Etiam luctus, quam sed
										sodales aliquam, lorem libero hendrerit urna, faucibus rhoncus
										massa nibh at felis. Curabitur ac tempus nulla, ut semper
										erat. Vivamus porta ullamcorper sem, ornare egestas mauris
										facilisis id.</p>
									<p>Ut ut risus nisl. Fusce porttitor eros at magna luctus,
										non congue nulla eleifend. Aenean porttitor feugiat dolor sit
										amet facilisis. Pellentesque venenatis magna et risus commodo,
										a commodo turpis gravida. Nam mollis massa dapibus urna
										aliquet, quis iaculis elit sodales. Sed eget ornare orci, eu
										malesuada justo. Nunc lacus augue, dictum quis dui id, lacinia
										congue quam. Nulla sem sem, aliquam nec dolor ac, tempus
										convallis nunc. Interdum et malesuada fames ac ante ipsum
										primis in faucibus. Nulla suscipit convallis iaculis. Quisque
										eget commodo ligula. Praesent leo dui, facilisis quis eleifend
										in, aliquet vitae nunc. Suspendisse fermentum odio ac massa
										ultricies pellentesque. Fusce eu suscipit massa.</p>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading" id="section-5">
								<h3 class="panel-title">
									<a class="accordion-toggle" data-toggle="collapse"
										href="#collapseFive"> <i
										class="icon-angle-down bigger-110"
										data-icon-hide="icon-angle-down"
										data-icon-show="icon-angle-right"></i> &nbsp;第五部分
									</a>
								</h3>
							</div>
							<div class="panel-collapse collapse in" id="collapseFive">
								<div class="panel-body">
									<p>Nam eget purus nec est consectetur vehicula. Nullam
										ultrices nisl risus, in viverra libero egestas sit amet. Etiam
										porttitor dolor non eros pulvinar malesuada. Vestibulum sit
										amet est mollis nulla tempus aliquet. Praesent luctus
										hendrerit arcu non laoreet. Morbi consequat placerat magna, ac
										ornare odio sagittis sed. Donec vitae ullamcorper purus.
										Vivamus non metus ac justo porta volutpat.</p>
									<p>Vivamus mattis accumsan erat, vel convallis risus
										pretium nec. Integer nunc nulla, viverra ut sem non,
										scelerisque vehicula arcu. Fusce bibendum convallis augue sit
										amet lobortis. Cras porta urna turpis, sodales lobortis purus
										adipiscing id. Maecenas ullamcorper, turpis suscipit
										pellentesque fringilla, massa lacus pulvinar mi, nec dignissim
										velit arcu eget purus. Nam at dapibus tellus, eget euismod
										nisl. Ut eget venenatis sapien. Vivamus vulputate varius
										mauris, vel varius nisl facilisis ac. Nulla aliquet justo a
										nibh ornare, eu congue neque rutrum.</p>
								</div>
							</div>
						</div>

						<!-- 第六部分为非展开模式demo -->
						<!-- <div class="panel panel-default">
						<div class="panel-heading" id="section-6">
							<h3 class="panel-title">
								<a class="accordion-toggle collapsed" data-toggle="collapse" href="#collapseSix">
									<i class="icon-angle-right bigger-110" data-icon-hide="icon-angle-down" data-icon-show="icon-angle-right"></i>
									&nbsp;第六部分
								</a>
							</h3>
						</div>
						<div class="panel-collapse collapse" id="collapseSix">
							<div class="panel-body">
								<p>Nam eget purus nec est consectetur vehicula. Nullam ultrices nisl risus, in viverra libero egestas sit amet. Etiam porttitor dolor non eros pulvinar malesuada. Vestibulum sit amet est mollis nulla tempus aliquet. Praesent luctus hendrerit arcu non laoreet. Morbi consequat placerat magna, ac ornare odio sagittis sed. Donec vitae ullamcorper purus. Vivamus non metus ac justo porta volutpat.</p>
							</div>
						</div>
					</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		require([ 'bootstrap', 'ace','jquery-pin' ], function() {

			var winScrollObj = {
				navId : '#myNav',
				isClick: false,
				init: function(){
					var self = this;
					var fOffsetTop = 0;

					var _navObj = $(self.navId);
					_navObj.width(_navObj.parent().width() * 0.9);
					var _navLi = _navObj.children('li');
					var frames = window.parent.document.getElementById("mainFrame");

					_navLi.click(function() {
						var _this = $(this);
						self.setLiClass(_this);
						self.isClick = true;
						if(frames){
							var href = _this.children().attr('href');
							if(href.indexOf('#')!=-1) {//判断是否是锚点而不是链接
								//父窗口滚动
								$(window.parent).scrollTop($(href).offset().top + $(frames).offset().top);
							}
						}
					});

					if (!frames) {	//父级iframe不存在则使用bootstrap的pin插件
						_navObj.pin({
							containerSelector : '#myScrollspy',
							padding:{
								top: 1
							}
						});
					}else{
						fOffsetTop = $(frames).offset().top;
					}
					var winObj = !frames ? window : window.parent;
					$(winObj).scroll(function() {
						var liArr = self.getScrollOffset(_navLi);
						var _this = $(this);

						var top = _this.scrollTop();
						if(frames){		//父级iframe存在则添加margin-top样式
							//计算第一个导航距离头部的高度
							var spaceTop = liArr[0] + fOffsetTop;
							if (top <= spaceTop) {
								_navObj.addClass('affix-top').removeClass('affix');
								_navObj.css('marginTop', 1);
							} else if (top > spaceTop) {
								_navObj.addClass('affix').removeClass('affix-top');
								_navObj.css('marginTop', top - fOffsetTop - 30);
							}
						}

						if(self.isClick){
							self.isClick = false;
							return;
						}


						var wOffsetTop = top - fOffsetTop;
						var currObj;
						if((frames && (top+1 >= $(_this[0].document).height() - _this.height()))
								|| (wOffsetTop >= liArr[liArr.length-1])){
							currObj = _navLi[_navLi.length-1];
						}else if(wOffsetTop <= liArr[0]){
							currObj = _navLi[0];
						}else{
							$.each(liArr,function(i,num){
								var prevNum;
								if(i - 1 >= 0){
									var prevNum = liArr[i-1];
									if(prevNum <= wOffsetTop && wOffsetTop < num){
										currObj = _navLi[i-1];
										return true;
									}
								}
							});
						}
						self.setLiClass($(currObj));
					});
				},
				setLiClass: function(_obj){
					_obj.siblings().removeClass('active');
					_obj.addClass('active');
				},
				getScrollOffset: function(objs){
					var newArr = [];
					$.each(objs, function(i, obj) {
						var linkObj = $(obj).children().attr('href');

						newArr.push(Math.round($(linkObj).offset().top));
					});
					return newArr;
				}
			}

			winScrollObj.init();
		});
	</script>
</body>
</html>