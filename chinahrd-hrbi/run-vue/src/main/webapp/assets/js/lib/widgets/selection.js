/**
 * Created by Administrator on 2016/6/2.
 */
/* 例子
 //年或半年
 $("#managerSelect").selection({
 dateType:1,
 dateValue:[{k:'2015年上半年',v:'201501'},{k:'2015年下半年',v:'201507'}],
 dateSelected:['201507'],
 //crowdSelected:['0'],
 ok:function(event, data){
 console.log(data)
 }
 });
 //年月区间
 $("#managerSelect").selection({
 dateType:2,
 dateRange:{
 min:'2010-10-01',
 max:'2016-06-3'
 },
 dateSelected:['2015', '3', '2016', '2'],
 //dateSelectedLength:0,
 //crowdSelected:['0'],
 ok:function(event, data){
 console.log(data)
 }
 });
 //年季度区间
 $("#managerSelect").selection({
 dateType:3,
 dateRange:{
 min:'2010-10-01',
 max:'2016-06-3'
 },
 dateSelected:['2015', '3', '2016', '2'],
 //dateSelectedLength:0,
 //crowdSelected:['0'],
 ok:function(event, data){
 console.log(data)
 }
 });
 //年月日区间
 $("#managerSelect").selection({
 dateType:4,
 dateRange:{
 min:'2010-10-01',
 max:'2016-06-3'
 },
 dateSelected:['2015-10-03', '2016-05-15'],
 //dateSelectedLength:0,
 //crowdSelected:['0'],
 ok:function(event, data){
 console.log(data)
 }
 });
 //日期
 $("#managerSelect").selection({
 dateType:5,
 dateRange:{
 min:'2010-10-01',
 max:'2016-06-3'
 },
 dateSelected:['2015-10-15'],
 //crowdSelected:['0'],
 ok:function(event, data){
 console.log(data)
 }
 });
 //年+半年区间
 $("#managerSelect").selection({
 dateType:6,
 dateRange:{
 min:'2010-10-01',
 max:'2016-06-3'
 },
 dateSelected:['2014', '2', '2015', '2'],
 //dateSelectedLength:0,
 //crowdSelected:['0'],
 ok:function(event, data){
 console.log(data);
 }
 });
 //年+月
 $("#managerSelect").selection({
 dateType:7,
 dateRange:{
 min:'2010-10',
 max:'2016-06'
 },
 dateSelected:['2014-01'],
 //crowdSelected:['0'],
 ok:function(event, data){
 console.log(data);
 }
 });
 * */
define(['jquery', 'jquery-ui', 'datetimepicker'], function(){
    Date.prototype.format = function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(),    //day
            "h+" : this.getHours(),   //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1 ? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }

    $.widget("w.selection", {
        options: {
            //0没有时间选项；1单一下拉菜单；2年月区间；3年季区间；4年月日区间
            dateType:0,

            //dateType=1时有效：[{k:'2015年上半年',v:'201501'},{k:'2015年下半年',v:'201507'}]
            dateValue:[],

            //dateType>1时有效：
            dateRange:{
                min:'1900-01-01',
                max:'2099-12-31'
            },

            //时间初始化选中值
            dateSelected:['','','',''],

            //日期选择范围长度（dateType==2或3或4时有效），0没限制；dateType==2时单位为月份；dateType==3时单位为季度；dateType==4时单位为日
            dateSelectedLength:0,

            //人群初始化选中值(数组长度>0显示人群,值为0选中不限；数组长度=0不显示人群)
            crowdSelected:[],

            //添加动态行: [{name:'类别', selected:0, list:[{k:'按收入',v:'1'},{k:'按毛利',v:'0'}]}]
            dynamicRows:[],

            ok:function(event, data){}
        },
        _create: function() {
            var t=this;
            t.element.html("");
            t._initElement(function(){
                switch(t.options.dateType){
                    case 1:{ t._setDate();break; }
                    case 2:{ t._setYearMonthRegion();break; }
                    case 3:{ t._setYearQuarterRegion();break; }
                    case 4:{ t._setDayRegion();break; }
                    case 5:{ t._setDay();break; }
                    case 6:{ t._setHalfYear();break; }
                    case 7:{ t._setMonth();break; }
                    case 8:{ t._timeAndSalesProduct();break; }
                }
            }, function (){
                t._ok();
            });

        },
        _initElement:function(renderCallback, crowdCallback){
            var arr = [];
            arr.push('	<div class="fhead">');
            arr.push('		<span class="dateText"></span>');
            arr.push('		<span class="crowdText"></span>');
            arr.push('		<i class="icon-sort-down"></i>');
            arr.push('	</div>');
            arr.push('	<div class="fbody">');
            arr.push('		<table>');

            if(this.options.dateType > 0) {
                arr.push('			<tr>');
                arr.push('				<td class="cname tm">时间<i class="timeerror icon-warning-sign hide"></i></td>');
                arr.push('				<td class="timecol">');
                arr.push('				</td>');
                arr.push('			</tr>');
            }

            if(this.options.crowdSelected.length > 0) {
                arr.push('			<tr>');
                arr.push('				<td class="cname cr"><div>人群<i class="crowdserror icon-warning-sign hide"></i></div></td>');
                arr.push('				<td>');
                arr.push('					<div class="crbd">');
                arr.push('					</div>');
                arr.push('				</td>');
                arr.push('			</tr>');
            }

            arr.push('			<tr>');
            arr.push('				<td colspan="2">');
            arr.push('					<div class="opr">');
            arr.push('						<span class="oprationmsg"></span>');
            arr.push('						<button class="oprationsave">确定</button>');
            arr.push('						<button class="oprationcancel">取消</button>');
            arr.push('					</div>');
            arr.push('				</td>');
            arr.push('			</tr>');
            arr.push('		</table>');
            arr.push('	</div>');
            if (!this.element.hasClass("datecrowdselection")) {
                this.element.addClass("datecrowdselection");
            }
            if (!this.element.hasClass("closeed")) {
                this.element.addClass("closeed");
            }
            this.element.html(arr.join(''));

            //添加动态行
            var dim=this.options.dynamicRows;
            if(dim.length>0){
                var dimText=[], dimTr=[];
                $.each(dim, function(i, item){
                    dimTr.push('			<tr>');
                    dimTr.push('				<td class="cname"><div>'+item.name+'</div></td>');
                    dimTr.push('				<td>');
                    dimTr.push('					<div class="ptb dynamicRows'+i+'Div">');
                    var displayText='';
                    if(item.list.length>0) {
                        dimTr.push('					<select class="dynamicRows'+i+'select">');
                        $.each(item.list, function (j, li) {
                            dimTr.push('<option value="'+li.k+'"'+(li.k==item.selected?' selected':'')+'>'+li.v+'</option>');
                            if(j==0 || li.k==item.selected){
                                displayText=li.v;
                            }
                        });
                        dimTr.push('					</select>');
                    }
                    dimTr.push('					</div>');
                    dimTr.push('				</td>');
                    dimTr.push('			</tr>');

                    dimText.push('<span class="dimText dynamicRows'+i+'Text">'+displayText+'</span>');
                });
                this.element.find(".fhead .crowdText").after(dimText.join(''));
                this.element.find(".fbody .opr").parents("tr").before(dimTr.join(''));
            }

            //事件
            var t=this;
            t.element.find(".fhead").unbind("click").on("click", function(){
                t.element.removeClass("closeed");
                t._reset();
            });
            t.element.find(".oprationcancel").unbind("click").on("click", function(){
                t.element.addClass("closeed");
            });

            renderCallback.call(new Object());

            if(t.options.crowdSelected.length > 0) {
                t._setCrowds(crowdCallback);
            }else{
                crowdCallback.call(new Object());
            }
        },
        //打开时重置默认值
        _reset:function(){
            var t=this;
            //时间
            if (t.options.dateType > 0) {
                var eleDate = t.element.find(".fbody .timecol");
                switch (t.options.dateType) {
                    case 1:{
                        eleDate.find(".selectdate").val(t.options.dateSelected[0]);
                        break;
                    }
                    case 2:
                    case 3:{
                        eleDate.find(".fromyear").val(t.options.dateSelected[0]).change();
                        eleDate.find(".fromq").val(t.options.dateSelected[1]);
                        eleDate.find(".toyear").val(t.options.dateSelected[2]).change();
                        eleDate.find(".toq").val(t.options.dateSelected[3]);
                        break;
                    }
                    case 4:{
                        eleDate.find(".fromDay").val(t.options.dateSelected[0]);
                        eleDate.find(".toDay").val(t.options.dateSelected[1]);
                        break;
                    }
                    case 5:{
                        eleDate.find(".day").val(t.options.dateSelected[0]);
                        break;
                    }
                    case 7:{
                    	eleDate.find(".month").val(t.options.dateSelected[0]);
                    	break;
                    }
                }
            }
            //人群
            if(t.options.crowdSelected.length > 0) {
                t.element.find(".fbody .crbd .selected").removeClass("selected");
                $.each(t.options.crowdSelected, function(i, item){
                    if(item==""){
                        item="0";
                    }
                    t.element.find(".fbody .crbd div[data-id='"+item+"']").addClass("selected");
                });
            }
            //动态行
            if(t.options.dynamicRows.length>0){
                $.each(t.options.dynamicRows, function(i, item){
                    t.element.find(".fbody tr .dynamicRows"+i+'select').val(t.options.dynamicRows[i].selected);
                });
            }
        },
        _setCrowds:function(callback){
            var t=this;
            var data=[];
            $.get(G_WEB_ROOT+'/common/getPopulations', {}, function(data){
                //
                var crowdArr=[], crowdbtn=[];
                var crowds = _.filter(data, function(m){
                    var b=false;
                    $.each(t.options.crowdSelected, function(i, item){
                        if(item == m.k){
                            b=true;
                        }
                    })
                    return b;
                });
                $.each(crowds, function(i, item){
                    crowdArr.push(item.v);
                });
                var cs=crowds.length>0?crowdArr.join("、"):'不限';
                t.element.find(".fhead .crowdText").attr("title", cs).text(cs);

                //
                crowdbtn.push('<div'+(crowds.length==0?' class="selected"':'')+' data-id="0">不限</div>')
                $.each(data, function(i, item){
                    var b=false;
                    $.each(t.options.crowdSelected, function(i, m){
                        if(m == item.k){
                            b=true;
                            hasSelected=true;
                        }
                    })
                    crowdbtn.push('<div'+(b?' class="selected"':'')+' data-id="'+item.k+'">'+item.v+'</div>');
                });
                t.element.find(".fbody .crbd").html(crowdbtn.join(''));

                //事件
                t.element.find(".crbd div").unbind("click").on("click", function(){
                    var id=$(this).data("id");
                    if(id!="0") {
                        t.element.find(".crbd div[data-id='0']").removeClass("selected");
                        if ($(this).hasClass("selected")) {
                            $(this).removeClass("selected");
                            if(t.element.find(".crbd .selected").length==0){
                                t.element.find(".crbd div[data-id='0']").addClass("selected");
                            }
                        } else {
                            $(this).addClass("selected");
                        }
                    }else{
                        if (!$(this).hasClass("selected")) {
                            t.element.find(".crbd .selected").removeClass("selected");
                            $(this).addClass("selected");
                        }
                    }
                });

                if(callback){
                    callback.call(new Object());
                }
            });
        },
        _setDate:function(){
            var t=this;
            var arr=[];
            arr.push('<select class="selectdate">');
            $.each(this.options.dateValue, function (i, item) {
                arr.push('<option value="' + item.v + '"'+(t.options.dateSelected[0]==item.v?' selected':'')+'>' + item.k + '</option>');
            });
            arr.push('</select>');
            this.element.find(".fbody .timecol").html(arr.join(''));

            var dateName=[];
            var eleDate = t.element.find(".fbody .timecol");
            dateName.push(eleDate.find(".selectdate").find("option:selected").text());
            t.element.find(".fhead .dateText").text(dateName.join('')).attr("title", dateName.join(''));
        },
        _setYearMonthRegion:function(){
            var t=this;
            var mindate=new Date(t.options.dateRange.min);
            var maxdate=new Date(t.options.dateRange.max);
            var minyear=mindate.getYear()+1900;
            var maxyear=maxdate.getYear()+1900;
            var minmonth=mindate.getMonth()+1;
            var maxmonth=maxdate.getMonth()+1;

            var arr=[];
            //from年
            arr.push('<select class="fromyear">');
            for(var i=maxyear;i>=minyear;i--){
                arr.push('<option value="' + i + '">' + i + '年</option>');
            }
            arr.push('</select>&nbsp;');
            //月
            arr.push('<select class="fromq"></select>&nbsp;-&nbsp;');

            //to年
            arr.push('<select class="toyear">');
            for(var i=maxyear;i>=minyear;i--){
                arr.push('<option value="' + i + '">' + i + '年</option>');
            }
            arr.push('</select>&nbsp;');
            //月
            arr.push('<select class="toq"></select>');

            t.element.find(".fbody .timecol").html(arr.join(''));

            var min=[],max=[],all=[];
            for(var i=1;i<=12;i++){
                if(i>=minmonth){
                    min.push('<option value="' + i + '">' + i + '月</option>');
                }
                if(i<=maxmonth){
                    max.push('<option value="' + i + '">' + i + '月</option>');
                }
                all.push('<option value="' + i + '">' + i + '月</option>');
            }
            t.element.data("monthObj", {min:min,max:max,all:all});

            t.element.find(".fromyear").unbind().change(function(){
                if($(this).val()==minyear){
                    t.element.find(".fromq").html(t.element.data("monthObj").min);
                }else if($(this).val()==maxyear){
                    t.element.find(".fromq").html(t.element.data("monthObj").max);
                }else{
                    t.element.find(".fromq").html(t.element.data("monthObj").all);
                }
            });
            var selectedBeginYear= t.options.dateSelected[0];
            if(t.element.find(".fromyear option[value='"+selectedBeginYear+"']").length>0){
                t.element.find(".fromyear").val(selectedBeginYear);
            }
            t.element.find(".fromyear").change();
            var selectedBeginQ= t.options.dateSelected[1];
            if(t.element.find(".fromq option[value='"+selectedBeginQ+"']").length>0){
                t.element.find(".fromq").val(selectedBeginQ);
            }

            t.element.find(".toyear").unbind().change(function(){
                if($(this).val()==minyear){
                    t.element.find(".toq").html(t.element.data("monthObj").min);
                }else if($(this).val()==maxyear){
                    t.element.find(".toq").html(t.element.data("monthObj").max);
                }else{
                    t.element.find(".toq").html(t.element.data("monthObj").all);
                }
            });
            var selectedEndYear= t.options.dateSelected[2];
            if(t.element.find(".toyear option[value='"+selectedEndYear+"']").length>0){
                t.element.find(".toyear").val(selectedEndYear);
            }
            t.element.find(".toyear").change();
            var selectedEndQ= t.options.dateSelected[3];
            if(t.element.find(".toq option[value='"+selectedEndQ+"']").length>0){
                t.element.find(".toq").val(selectedEndQ);
            }

            var dateName=[];
            var eleDate = t.element.find(".fbody .timecol");
            dateName.push(eleDate.find(".fromyear").find("option:selected").text());
            dateName.push(eleDate.find(".fromq").find("option:selected").text());
            dateName.push("-");
            dateName.push(eleDate.find(".toyear").find("option:selected").text());
            dateName.push(eleDate.find(".toq").find("option:selected").text());
            t.element.find(".fhead .dateText").text(dateName.join('')).attr("title", dateName.join(''));
        },
        _setYearQuarterRegion:function(){
            var t=this;
            var mindate=new Date(t.options.dateRange.min);
            var maxdate=new Date(t.options.dateRange.max);
            var minyear=mindate.getYear()+1900;
            var maxyear=maxdate.getYear()+1900;
            var minmonth=mindate.getMonth()+1;
            var maxmonth=maxdate.getMonth()+1;

            var arr=[];
            //from年
            arr.push('<select class="fromyear">');
            for(var i=maxyear;i>=minyear;i--){
                arr.push('<option value="' + i + '">' + i + '年</option>');
            }
            arr.push('</select>&nbsp;');
            //月
            arr.push('<select class="fromq"></select>&nbsp;-&nbsp;');

            //to年
            arr.push('<select class="toyear">');
            for(var i=maxyear;i>=minyear;i--){
                arr.push('<option value="' + i + '">' + i + '年</option>');
            }
            arr.push('</select>&nbsp;');
            //月
            arr.push('<select class="toq"></select>');

            t.element.find(".fbody .timecol").html(arr.join(''));

            var min=[],max=[],all=[];
            for(var i=1;i<=4;i++){
                if(i*3>=minmonth){
                    min.push('<option value="' + i + '">Q' + i + '</option>');
                }
                if((i*3-3)<=maxmonth){
                    max.push('<option value="' + i + '">Q' + i + '</option>');
                }
                all.push('<option value="' + i + '">Q' + i + '</option>');
            }
            t.element.data("monthObj", {min:min,max:max,all:all});

            t.element.find(".fromyear").unbind().change(function(){
                if($(this).val()==minyear){
                    t.element.find(".fromq").html(t.element.data("monthObj").min);
                }else if($(this).val()==maxyear){
                    t.element.find(".fromq").html(t.element.data("monthObj").max);
                }else{
                    t.element.find(".fromq").html(t.element.data("monthObj").all);
                }
            });
            var selectedBeginYear= t.options.dateSelected[0];
            if(t.element.find(".fromyear option[value='"+selectedBeginYear+"']").length>0){
                t.element.find(".fromyear").val(selectedBeginYear);
            }
            t.element.find(".fromyear").change();
            var selectedBeginQ= t.options.dateSelected[1];
            if(t.element.find(".fromq option[value='"+selectedBeginQ+"']").length>0){
                t.element.find(".fromq").val(selectedBeginQ);
            }

            t.element.find(".toyear").unbind().change(function(){
                if($(this).val()==minyear){
                    t.element.find(".toq").html(t.element.data("monthObj").min);
                }else if($(this).val()==maxyear){
                    t.element.find(".toq").html(t.element.data("monthObj").max);
                }else{
                    t.element.find(".toq").html(t.element.data("monthObj").all);
                }
            });
            var selectedEndYear= t.options.dateSelected[2];
            if(t.element.find(".toyear option[value='"+selectedEndYear+"']").length>0){
                t.element.find(".toyear").val(selectedEndYear);
            }
            t.element.find(".toyear").change();
            var selectedEndQ= t.options.dateSelected[3];
            if(t.element.find(".toq option[value='"+selectedEndQ+"']").length>0){
                t.element.find(".toq").val(selectedEndQ);
            }

            var dateName=[];
            var eleDate = t.element.find(".fbody .timecol");
            dateName.push(eleDate.find(".fromyear").find("option:selected").text());
            dateName.push(eleDate.find(".fromq").find("option:selected").text());
            dateName.push("-");
            dateName.push(eleDate.find(".toyear").find("option:selected").text());
            dateName.push(eleDate.find(".toq").find("option:selected").text());
            t.element.find(".fhead .dateText").text(dateName.join('')).attr("title", dateName.join(''));
        },
        _setDayRegion:function(){
            var t=this;
            var mindate=new Date(t.options.dateRange.min);
            var maxdate=new Date(t.options.dateRange.max);
            var selectMinDate=new Date(t.options.dateSelected[0]);
            var selectMaxDate=new Date(t.options.dateSelected[1]);
            var fromDate=selectMinDate.format('yyyy-MM-dd');
            var toDate=selectMaxDate.format('yyyy-MM-dd');
            var arr=[];
            arr.push('<input type="text" class="fromDay" readonly />&nbsp;-&nbsp;<input type="text" class="toDay" readonly />');
            t.element.find(".fbody .timecol").html(arr.join(''));

            var dp={
                format:'yyyy-mm-dd',
                startDate:mindate,
                endDate:maxdate,
                autoclose:true,
                startView:2,
                minView:2,
                todayHighlight:true,
                language:'cn'
            };
            t.element.find(".fbody .timecol .fromDay").datetimepicker(dp).val(fromDate);
            t.element.find(".fbody .timecol .toDay").datetimepicker(dp).val(toDate);

            var dateName=[];
            dateName.push(selectMinDate.format('yyyy.MM.dd'));
            dateName.push('-');
            dateName.push(selectMaxDate.format('yyyy.MM.dd'));
            t.element.find(".fhead .dateText").text(dateName.join('')).attr("title", dateName.join(''));
        },
        _setDay:function(){
            var t=this;
            var mindate=new Date(t.options.dateRange.min);
            var maxdate=new Date(t.options.dateRange.max);
            var selectDate=new Date(t.options.dateSelected[0]);
            var day=selectDate.format('yyyy-MM-dd');
            var arr=[];
            arr.push('<input type="text" class="day" readonly />');
            t.element.find(".fbody .timecol").html(arr.join(''));

            var dp={
                format:'yyyy-mm-dd',
                startDate:mindate,
                endDate:maxdate,
                autoclose:true,
                startView:2,
                minView:2,
                todayHighlight:true,
                language:'cn'
            };
            t.element.find(".fbody .timecol .day").datetimepicker(dp).val(day);

            t.element.find(".fhead .dateText").text(day).attr("title", day);
        },
        _setMonth:function(){
        	var t=this;
        	var mindate=new Date(t.options.dateRange.min);
        	var maxdate=new Date(t.options.dateRange.max);
        	var selectDate=new Date(t.options.dateSelected[0]);
        	var month=selectDate.format('yyyy-MM');
        	var arr=[];
        	arr.push('<input type="text" class="month" readonly />');
        	t.element.find(".fbody .timecol").html(arr.join(''));
        	
        	var dp={
        			format:'yyyy-mm',
        			startDate:mindate,
        			endDate:maxdate,
        			autoclose:true,
        			startView:3,
        			minView:3,
//        			todayHighlight:true,
        			language:'cn'
        	};
        	t.element.find(".fbody .timecol .month").datetimepicker(dp).val(month);
        	
        	t.element.find(".fhead .dateText").text(month).attr("title", month);
        },
        _timeAndSalesProduct:function(callback){
        	var t=this;
        	var minyear=t.options.dateRange.min;
        	var maxyear=t.options.dateRange.max;
        	var selectyear=t.options.dateSelected[0];
        	var length=parseInt(maxyear)-parseInt(minyear);
        	var arr=[];
        	arr.push('<select class="yearSelect">');
        	for(var i=0; i<=length; i++) {
//        		if(parseInt(selectyear)==parseInt(maxyear)+i)
        		// 修改选中日期 update by malong 2016-12-15
    			if(parseInt(selectyear)==parseInt(maxyear) - i)
        			arr.push('<option value="'+(parseInt(maxyear)-i)+'" selected>'+(parseInt(maxyear)-i)+'年</option>');
        		else
        			arr.push('<option value="'+(parseInt(maxyear)-i)+'">'+(parseInt(maxyear)-i)+'年</option>');
        	}
        	arr.push('</select>');
        	t.element.find(".fbody .timecol").html(arr.join(''));
        	var year=selectyear+'年';
        	t.element.find(".fhead .dateText").text(year).attr("title", year);
        },
        _setHalfYear:function(){
            var t=this;
            var mindate=new Date(t.options.dateRange.min);
            var maxdate=new Date(t.options.dateRange.max);
            var minyear=mindate.getYear()+1900;
            var maxyear=maxdate.getYear()+1900;
            var minmonth=mindate.getMonth()+1;
            var maxmonth=maxdate.getMonth()+1;

            var arr=[];
            //from年
            arr.push('<select class="fromyear">');
            for(var i=maxyear;i>=minyear;i--){
                arr.push('<option value="' + i + '">' + i + '年</option>');
            }
            arr.push('</select>&nbsp;');
            //半年
            arr.push('<select class="fromq"></select>&nbsp;-&nbsp;');

            //to年
            arr.push('<select class="toyear">');
            for(var i=maxyear;i>=minyear;i--){
                arr.push('<option value="' + i + '">' + i + '年</option>');
            }
            arr.push('</select>&nbsp;');
            //半年
            arr.push('<select class="toq"></select>');

            t.element.find(".fbody .timecol").html(arr.join(''));

            var min=[],max=[],all=[];
            if(minmonth<=6){
                min.push('<option value="1">上半年</option>');
            }
            min.push('<option value="2">下半年</option>');

            max.push('<option value="1">上半年</option>');
            if(maxmonth>6){
                max.push('<option value="2">下半年</option>');
            }

            all.push('<option value="1">上半年</option>');
            all.push('<option value="2">下半年</option>');

            t.element.data("monthObj", {min:min,max:max,all:all});

            t.element.find(".fromyear").unbind().change(function(){
                if($(this).val()==minyear){
                    t.element.find(".fromq").html(t.element.data("monthObj").min);
                }else if($(this).val()==maxyear){
                    t.element.find(".fromq").html(t.element.data("monthObj").max);
                }else{
                    t.element.find(".fromq").html(t.element.data("monthObj").all);
                }
            });
            var selectedBeginYear= t.options.dateSelected[0];
            if(t.element.find(".fromyear option[value='"+selectedBeginYear+"']").length>0){
                t.element.find(".fromyear").val(selectedBeginYear);
            }
            t.element.find(".fromyear").change();
            var selectedBeginQ= t.options.dateSelected[1];
            if(t.element.find(".fromq option[value='"+selectedBeginQ+"']").length>0){
                t.element.find(".fromq").val(selectedBeginQ);
            }

            t.element.find(".toyear").unbind().change(function(){
                if($(this).val()==minyear){
                    t.element.find(".toq").html(t.element.data("monthObj").min);
                }else if($(this).val()==maxyear){
                    t.element.find(".toq").html(t.element.data("monthObj").max);
                }else{
                    t.element.find(".toq").html(t.element.data("monthObj").all);
                }
            });
            var selectedEndYear= t.options.dateSelected[2];
            if(t.element.find(".toyear option[value='"+selectedEndYear+"']").length>0){
                t.element.find(".toyear").val(selectedEndYear);
            }
            t.element.find(".toyear").change();
            var selectedEndQ= t.options.dateSelected[3];
            if(t.element.find(".toq option[value='"+selectedEndQ+"']").length>0){
                t.element.find(".toq").val(selectedEndQ);
            }

            var dateName=[];
            var eleDate = t.element.find(".fbody .timecol");
            dateName.push(eleDate.find(".fromyear").find("option:selected").text());
            dateName.push(eleDate.find(".fromq").find("option:selected").text());
            dateName.push("-");
            dateName.push(eleDate.find(".toyear").find("option:selected").text());
            dateName.push(eleDate.find(".toq").find("option:selected").text());
            t.element.find(".fhead .dateText").text(dateName.join('')).attr("title", dateName.join(''));
        },
        _ok:function(){
            var t=this;
            t.element.find(".oprationsave").unbind("click").on("click", function() {
                //日期
                var date = [];
                if (t.options.dateType > 0) {
                    var dateName = [];
                    var eleDate = t.element.find(".fbody .timecol");
                    var eleError=t.element.find(".fbody .timeerror");
                    var eleErrorMsg=t.element.find(".fbody .oprationmsg");
                    switch (t.options.dateType) {
                        case 1:{
                            date.push(eleDate.find(".selectdate").val());
                            dateName.push(eleDate.find(".selectdate").find("option:selected").text());
                            break;
                        }
                        case 2:
                        case 3:
                        case 6:{
                            var bd=eleDate.find(".fromyear").val()+''+(parseInt(eleDate.find(".fromq").val())+10);
                            var ed=eleDate.find(".toyear").val()+''+(parseInt(eleDate.find(".toq").val())+10);
                            if(bd>ed){
                                eleErrorMsg.text("开始日期必须小于或者等于结束日期");
                                eleError.removeClass("hide").attr("title", eleErrorMsg.text());
                                return;
                            }else{
                                eleErrorMsg.text("");
                                eleError.addClass("hide");
                            }
                            //判断时间跨度
                            var len=t.options.dateSelectedLength;
                            if(len>0){
                                if(t.options.dateType==2) {
                                    var begin = (parseInt(eleDate.find(".fromyear").val()) * 12) + parseInt(eleDate.find(".fromq").val());
                                    var end = (parseInt(eleDate.find(".toyear").val()) * 12) + parseInt(eleDate.find(".toq").val());
                                    if ((end - begin + 1) > len) {
                                        eleErrorMsg.text("时间跨度不能大于" + len + "个月");
                                        eleError.removeClass("hide").attr("title", eleErrorMsg.text());
                                        return;
                                    } else {
                                        eleErrorMsg.text("");
                                        eleError.addClass("hide");
                                    }
                                }
                                if(t.options.dateType==3){
                                    var begin = (parseInt(eleDate.find(".fromyear").val()) * 4) + parseInt(eleDate.find(".fromq").val());
                                    var end = (parseInt(eleDate.find(".toyear").val()) * 4) + parseInt(eleDate.find(".toq").val());
                                    if ((end - begin + 1) > len) {
                                        eleErrorMsg.text("时间跨度不能大于" + len + "个季度");
                                        eleError.removeClass("hide").attr("title", eleErrorMsg.text());
                                        return;
                                    } else {
                                        eleErrorMsg.text("");
                                        eleError.addClass("hide");
                                    }
                                }
                                if(t.options.dateType==6){
                                    var begin = (parseInt(eleDate.find(".fromyear").val()) * 2) + parseInt(eleDate.find(".fromq").val());
                                    var end = (parseInt(eleDate.find(".toyear").val()) * 2) + parseInt(eleDate.find(".toq").val());
                                    if ((end - begin + 1) > len) {
                                        eleErrorMsg.text("时间跨度不能大于" + len + "个半年");
                                        eleError.removeClass("hide").attr("title", eleErrorMsg.text());
                                        return;
                                    } else {
                                        eleErrorMsg.text("");
                                        eleError.addClass("hide");
                                    }
                                }
                            }
                            date.push(eleDate.find(".fromyear").val());
                            date.push(eleDate.find(".fromq").val());
                            date.push(eleDate.find(".toyear").val());
                            date.push(eleDate.find(".toq").val());
                            dateName.push(eleDate.find(".fromyear").find("option:selected").text());
                            dateName.push(eleDate.find(".fromq").find("option:selected").text());
                            dateName.push("-");
                            dateName.push(eleDate.find(".toyear").find("option:selected").text());
                            dateName.push(eleDate.find(".toq").find("option:selected").text());
                            break;
                        }
                        case 4:{
                            if(eleDate.find(".fromDay").val()>eleDate.find(".toDay").val()){
                                eleErrorMsg.text("开始日期必须小于或者等于结束日期");
                                eleError.removeClass("hide").attr("title", eleErrorMsg.text());
                                return;
                            }else{
                                eleErrorMsg.text("");
                                eleError.addClass("hide");
                            }
                            //判断时间跨度
                            var len=t.options.dateSelectedLength;
                            if(len>0){
                                var beginDate=new Date(eleDate.find(".fromDay").val());
                                var endDate=new Date(eleDate.find(".toDay").val());
                                var rd=(endDate.getTime()-beginDate.getTime())/(24*3600*1000)+1;
                                if (rd > len) {
                                    eleErrorMsg.text("时间跨度不能大于" + len + "个天");
                                    eleError.removeClass("hide").attr("title", eleErrorMsg.text());
                                    return;
                                } else {
                                    eleErrorMsg.text("");
                                    eleError.addClass("hide");
                                }
                            }
                            date.push(eleDate.find(".fromDay").val());
                            date.push(eleDate.find(".toDay").val());
                            dateName.push((new Date(eleDate.find(".fromDay").val()).format('yyyy.MM.dd')));
                            dateName.push("-");
                            dateName.push((new Date(eleDate.find(".toDay").val()).format('yyyy.MM.dd')));
                            break;
                        }
                        case 5:{
                            date.push(eleDate.find(".day").val());
                            dateName.push(eleDate.find(".day").val());
                            break;
                        }
                        case 7:{
                        	date.push(eleDate.find(".month").val());
                        	dateName.push(eleDate.find(".month").val());
                        	break;
                        }
                        case 8:{
                        	date.push(eleDate.find(".yearSelect option:selected").val());
                        	dateName.push(eleDate.find(".yearSelect option:selected").text());
                        	break;
                        }
                    }
                    t.element.find(".fhead .dateText").text(dateName.join('')).attr("title", dateName.join(''));

                    t.options.dateSelected=date;
                }


                //人群
                var crowd = [];
                if(t.options.crowdSelected.length > 0) {
                    var crowdsName = [];
                    var eleCrowd = t.element.find(".fbody .crbd");
                    if (eleCrowd.find("div[data-id='0']").hasClass("selected")) {
                        crowdsName.push('不限');
                    } else {
                        eleCrowd.find(".selected").each(function (i, item) {
                            crowd.push($(this).data("id"));
                            crowdsName.push($(this).text());
                        });
                    }
                    t.element.find(".fhead .crowdText").text(crowdsName.join('、')).attr("title", crowdsName.join('、'));

                    t.options.crowdSelected=crowd.length==0?['0']:crowd;
                }

                //动态行
                var dynamicRows = [];
                if(t.options.dynamicRows.length>0){
                    $.each(t.options.dynamicRows, function(i, item){
                        var selectedVal=t.element.find(".fbody tr .dynamicRows"+i+'select').val();
                        dynamicRows.push(selectedVal);
                        t.element.find(".fhead .dynamicRows"+i+'Text').text(t.element.find(".fbody tr .dynamicRows"+i+'select').find("option:selected").text());
                        t.options.dynamicRows[i].selected=selectedVal;
                    });
                }

                t._trigger("ok", null, {
                    date: date,
                    crowd: crowd,
                    dynamicRows: dynamicRows
                });

                //close
                t.element.addClass("closeed");
            });
        },
        _setOptions:function(options){
            var t=this;
            t.options= $.extend({}, t.options, options);
            t._create();
        }
    });
});