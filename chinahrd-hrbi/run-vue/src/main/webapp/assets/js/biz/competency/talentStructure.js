require(
		[ 'jquery', 'vue2x', 'blockComp', 'barComp','topBlockComp','gaugeComp','compUtils', 'jgGrid', 'bootstrap',
				'underscore', 'utils', 'organTreeSelector', 'vernierCursor',
				'timeLine2', 'searchBox3', "jquery-mCustomScrollBar",
				'messenger' ],
		function($, Vue,blockComp,BarComponent,topBlockComp,gaugeComp,compUtils) {
			var webRoot = G_WEB_ROOT, win = top != window ? top.window : window;

			var urls = {
				memoUrl : webRoot + '/memo/findMemo.do', // 查看备忘录信息
				addMemoUrl : webRoot + '/memo/addMemo.do', // 添加备忘录信息
				structureBudgetAnalyse : webRoot
						+ '/talentStructure/getBudgetAnalyse.do', // 编制分析
				structureGetConfigWarnVal : webRoot
						+ '/talentStructure/getConfigWarnVal.do', // 加载使用率
				structureSetConfigWarnVal : webRoot
						+ '/talentStructure/setConfigWarnVal.do', // 保存使用率
				// structureGetTalentStuctureData: webRoot +
				// '/talentStructure/getTalentStuctureData.do', //人力结构数据
				// structureGetAbEmpCountBarBySeqId: webRoot +
				// '/talentStructure/getAbEmpCountBarBySeqId.do', //职位序列分布 职级分布

				getManageEmp4OrganUrl : webRoot
						+ '/talentStructure/getManageEmp4Organ.do', // 当前机构管理者员工分布
				getManageToSubOrganUrl : webRoot
						+ '/talentStructure/getManageToSubOrgan.do', // 下级机构管理者员工分布
				getAbility4OrganUrl : webRoot
						+ '/talentStructure/getAbility4Organ.do', // 当前机构职级分布
				getAbilityToSubOrganUrl : webRoot
						+ '/talentStructure/getAbilityToSubOrgan.do', // 下级机构职级分布
				getSequence4OrganUrl : webRoot
						+ '/talentStructure/getSequence4Organ.do', // 职位序列分布
				getSequenceAbilityCountUrl : webRoot
						+ '/talentStructure/getSequenceAbilityCount.do', // 职位序列职级统计
				getSubOrganCountUrl : webRoot
						+ '/talentStructure/getSubOrganCount.do', // 组织分布
				getWorkplaceCountUrl : webRoot
						+ '/talentStructure/getWorkplaceCount.do', // 工作地分布
				getDegree4OrganUrl : webRoot
						+ '/talentStructure/getDegree4Organ.do', // 当前机构学历分布
				getDegreeToSubOrganUrl : webRoot
						+ '/talentStructure/getDegreeToSubOrgan.do', // 下级机构学历分布
				getCompanyAge4OrganUrl : webRoot
						+ '/talentStructure/getCompanyAge4Organ.do', // 当前机构司龄分布
				getCompanyAgeToSubOrganUrl : webRoot
						+ '/talentStructure/getCompanyAgeToSubOrgan.do', // 下级机构司龄分布
			};
			$(win.document.getElementById('tree')).next().show();
			if (win.setCurrNavStyle)
				win.setCurrNavStyle();

			var reqOrgId = win.currOrganId;

			var showErrMsg = function(content) {
				Messenger().post({
					message : content,
					type : 'error',
					hideAfter : 3
				});
			};

			$(".ct-mCustomScrollBar").mCustomScrollbar({
				axis : "yx",
				scrollButtons : {
					enable : true
				},
				scrollbarPosition : "outside",
				theme : "minimal-dark"
			});

			/**
			 * 重新加载表格
			 * 
			 * @param gridId
			 */
			function resizeGrid(gridId) {
				var $this = $('#gbox_' + gridId.split('#')[1]);
				var thColumn = $this
						.find('table.ui-jqgrid-htable th.ui-th-column');
				var w = $this.parent().width() > thColumn.length * 100 ? $this
						.parent().width() - 2 : thColumn.length * 100;
				$(gridId).setGridWidth(w);
			}

			function formatNum(value) {
				if (!value)
					return 0;
				return value;
			}

			$(window).resize(function() {

			});

			// 获取机构数据
			win.organizationObject = function(organId, organTxt) {
				$(".rightSetUpLeft").click();
				reqOrgId = organId;

			};

			/**
			 * 管理建议与备忘
			 * 
			 * @type {{init: timeLineObj.init, getOption:
			 *       timeLineObj.getOption}}
			 */
			var timeLineObj = {
				init : function(organId) {
					var self = this;
					self.organizationId = organId;
					$('#timeLine').timeLine(self.getOption()); // 初始化
				},
				getOption : function() {
					var organizationId = this.organizationId;
					var quotaId = $('#quotaId').val();
					// 参数配置
					var options = {
						title : '管理建议与备忘',
						titleSuffix : '条未读',
						quotaId : quotaId,
						organId : organizationId
					}
					return options;
				}
			}
			timeLineObj.init(reqOrgId);
		
			
			new Vue({
				el : '#top',
				data:{
					organId:reqOrgId,
					gaugeData:null,
					rate:'',
					text:'',
					color:'',
					number:'',
					empCount:'',
					usableEmpCount:''
				},
				components : {
					'gaugeComponent':gaugeComp,
					'topBlockComponent':topBlockComp
				},
				created:function(){
					var _this=this;
					 $.post(urls.structureBudgetAnalyse, {"organId": this.organId}, function (data) {
						 data.hasBudgetPer=true;
						 if (data.hasBudgetPer) {
			                    var value = Tc.formatFloat(data.budgetPer * 100);
			                    var greenValue = data.normal * 100;
			                    var yellowVlue = data.risk * 100;
			                    //图表
			                    var maxValue = value > 100 ? parseInt(value / 10) * 10 + 10 : 100;
			                    var g = greenValue / maxValue;
			                    var y = yellowVlue / maxValue;
			                    if (g > 1) {
			                       greenValue = yellowVlue = 1;
			                    } else {
			                        greenValue = g;
			                        yellowVlue = y > 1 ? 1 : y;
			                    }
			                    _this.gaugeData=compUtils.dataPacket(
					                    {
					                    	max:maxValue,
					                    	value:value,
					                    	color: [[greenValue, "rgb(106, 175, 43)"], [yellowVlue, "rgb(240, 166, 4)"], [1, "rgb(211, 82, 26)"]]
					                    });
			                    //文字说明
			                    var index = 2;
			                    if (value <= greenValue * 100) {
			                        index = 0;
			                    } else if (value <= yellowVlue * 100) {
			                        index = 1;
			                    }
			                    var color=["green", "yellow", "red"];
			                    
			                    _this.color=color[index];
			                    _this.rate=value+"%";
			                    var text=["招兵买马，弹药充足！", "人手富足，大展宏图！", "余粮有限，注意节制！"];
			                    _this.text=text[index];
			                } else {
			                    $("#structureRateChart,#structureRateText").hide();
			                    $("#structureRateNoData").removeClass("hide");
			                }
						 _this.number=data.hasBudgetPer ? data.number : "-";
						 _this.empCount=data.empCount;
						 _this.usableEmpCount=data.hasBudgetPer ? data.usableEmpCount : "-";
			            });
					
				}
				
				
			});
			
			new Vue({
				el : '#app',
				data:{
					organId:reqOrgId,
					positionSequence:null,
					positionRank:null,
				},
				computed:{
					listenSequence:function(){
						return {
							type:'click',
							call:this.listen
						}
					}
				},
				 methods:{
		     		 render:function(){
		     			this._renderPositionSequence();
		     			this._renderPositionRank();
		     		 },
		     		 
		     		 _renderPositionSequence:function(){
		     			var _this=this;
		     			$.get(urls.getSequence4OrganUrl, {
							organId : this.organId
						}, function(data) {
							if (_.isNull(data))
								return;
							_this.chartData=data;
							_this.renderSequence(data);

						});
		     		 },
		     		_renderPositionRank:function(sequenceId){
		     			var _this=this;
		     			$.get(urls.getAbility4OrganUrl, {
							organId : this.organId,
							sequenceId : sequenceId
						}, function(data) {
							if (_.isNull(data))
								return;
							var self = this, xAxisData = [], seriesData = [];
							var total = 0;
							$.each(data, function(idx, obj) {
								xAxisData.push(obj.k + "\n" + obj.v + "人");
								total += parseInt(obj.v);
							});
							$.each(data, function(idx, obj) {
								seriesData.push(Tc.formatFloat(parseInt(obj.v) / total
										* 100));
							});
							_this.positionRank=compUtils.dataPacket(
				                    {
				                        xAxis:xAxisData,
				                        data: seriesData
				                    });
						});
		     		 },
		     		 renderSequence:function(data){
		     			var yaxisData = [], seriesData = [];
						var total = 0;
						$.each(data, function(idx, obj) {
							yaxisData.push(obj.itemName);
							seriesData.push({
								value : obj.empCount,
								seqId : obj.itemId,
								cateName : obj.itemName
							});
							total += obj.empCount;
						});
						this.positionSequence=compUtils.dataPacket(
			                    {
			                        yAxis:yaxisData,
			                        data: seriesData
			                    });
		     		 },
		     		listen:function(param){
		     			if (!this.isReturn) {    //钻取数据
		                    var seqId = param.data.seqId;
		                    if (seqId == "-1") {
		                        console.log("其它序列不能钻取！");
		                        return;
		                    }
		     				this.isReturn = true;
		                    var obj = [];
		                    obj.push({empCount: param.value, itemId: seqId, itemName: param.name});
		                    this.renderSequence(obj);
			     			this._renderPositionRank(seqId);
		                } else {//返回上一级
		                	this.isReturn = false;
		                    this.renderSequence(this.chartData);

		                    this._renderPositionRank();
		                }
		     		}
		     	  },
		    	 created:function(){
		    		 this.render();
		    	 },
				components : {
					'blockLayout':blockComp,
					'BarComponent':BarComponent
				}
			});
	

		});