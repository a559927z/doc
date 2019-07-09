

require(['jquery','vue2x','vuex',  'blockComp',
     	'pieComp', 'annularComp', 'barComp', 'constellationComp', 'underscore', 'compUtils', 'bootstrap'
], function ($,Vue,vuex,blockComp,
		PieComponent,
		AnnularComponent,
		BarComponent,
		ConstellationComponent,_,compUtils) {
    var webRoot = G_WEB_ROOT;
    var win = top != window ? top.window : window;
    var urls = {
        getTeamImgDataUrl: webRoot + '/teamImg/getTeamImgData.do'
    };
  
    $(win.document.getElementById('tree')).next().show();
    if (win.setCurrNavStyle) win.setCurrNavStyle();
    var reqOrgId = win.currOrganId;
    var reqOrgText = win.currOrganTxt;


    //获取机构数据
    win.organizationObject = function (organId, organTxt) {
    	vm.organId = organId;
      //  teamImgObj.getRequestData(organId);
    };

    
    var vm=new Vue({
    	 el:"#app",
    	 data:{
    		 organId:reqOrgId,
    		 list:{}
    	 },
    	 watch: {
     	    'organId': function (val, oldVal) {
     	    	this.organId=val;
     	    	this.list={};
     	    	this.render(); 
     	    },
     	  },
     	  methods:{
     		 render:function(){
     			var _this=this;
       		 $.post(urls.getTeamImgDataUrl, {'organId': this.organId}, function (rs) {
       			 var _list={};
       			 // 职级
                 var abilityLegend = [];
                 var abilityData= [];
                 $.each(rs[0], function (a, b) {
                	 abilityData.push({value: b.v, name: b.k});
                	 abilityLegend.push(b.k);
                 });
                 _list.ability=compUtils.dataPacket({
                 		data:abilityData,
                 		legend:abilityLegend
                 });
           
                 //工作地点
       			    var workLocationLegend = [];
                    var workLocationData = [];
                    $.each(rs[1], function (a, b) {
                    	workLocationData.push({value: b.v, name: b.k});
                        workLocationLegend.push(b.k);
                    });
                  
                    _list.workLocation=compUtils.dataPacket({
                    		data:workLocationData,
                    		legend:workLocationLegend
                    })
                   //性别
                    var sexLegend = [];
                    var sexData = [];
                    $.each(rs[2], function (a, b) {
                    	sexData.push({value: b.v, name: b.k});
                    	sexLegend.push(b.k);
                    });
                    _list.sex =compUtils.dataPacket({
                    		data: sexData,
                    		legend: sexLegend
                        });
                    
                    // 婚姻状况
                    var marryData=[];
                    marryData.push(rs[3].unIsMarry);
                    marryData.push(rs[3].isMarry);
                    _list.marry =compUtils.dataPacket({
                    		data: marryData,
                    		xAxis: ['未婚','已婚']
                        });
                    // 年龄
                    _list.age =compUtils.dataPacket({
                        xAxis: rs[4].xAxisData,
                        data: rs[4].seriesData
                    });
                   //司龄
                    _list.companyAge =compUtils.dataPacket(
                    {
                    		xAxis: rs[5].xAxisData,
                    		data: rs[5].seriesData
                    });
                    
                    
                 // 血型
                    var bloodLegend = [];
                    var bloodData = [];
                    $.each(rs[6], function (a, b) {
                        bloodData.push({value: b.v, name: b.k});
                        bloodLegend.push(b.k);
                    });
                    _list.blood =compUtils.dataPacket({
                    		data: bloodData,
                    		legend: bloodLegend
                        });
                    
                    //星座
                    _list.constellation =compUtils.dataPacket(
                    {
                        xAxis: rs[7].xAxisData,
                        data: rs[7].seriesData
                    });
                    
                    // 性格
                  var personalityLegend = [];
                    var personalityData = [];
                  $.each(rs[8], function (a, b) {
                	  personalityData.push({value: b.value, name: b.name, emps: b.empNames});
                	  personalityLegend.push(b.name);
                  });

                    _list.personality=compUtils.dataPacket({
                    		data:personalityData,
                    		legend:personalityLegend
                    })
                    _this.list=_list;
       		 });
     		 }
     	  },
    	 created:function(){
    		 this.render();
    	 },
    	 components:{
    		 'blockLayout':blockComp,
				'PieComponent':PieComponent,
				'AnnularComponent':AnnularComponent,
				'BarComponent':BarComponent,
				'ConstellationComponent':ConstellationComponent
    	 }
    
    });
});