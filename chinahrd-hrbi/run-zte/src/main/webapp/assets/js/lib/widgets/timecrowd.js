/**
 * Created by dcli on 2016/5/25.
 */
/*
 例子一：年上下半年+人群
 <div id="preChangeSelect"></div>
 $("#preChangeSelect").timecrowd({
     selectedTimes:['201501'],
     selectedCrowds:[1],
     times:[{key:'201501', value:'2015年上半年'},{key:'201507', value:'2015年下半年'}],
     ok:function(event, data){
        console.log(data);
     }
 });

 例子二：年季度时间度+人群
 <div id="preUnusualSelect"></div>
 $("#preUnusualSelect").timecrowd({
     selectedTimes:['2014','Q2','2015','Q3'],
     selectedCrowds:[2],
     times:[{key:'2015', value:'2015年'},{key:'2014', value:'2014年'}],
     ok:function(event, data){
        console.log(data);
     }
 });

 例子三：年月时间段选择
 $("#managerSelect").timecrowd({
     type:1,
     selectedTimes:['2015','2','2015','6'],
     times:[{key:'2015', value:'2015年'},{key:'2016', value:'2016年'}],
     ok:function(event, data){
     console.log(data);
     }
 });
 */
define(['jquery', 'jquery-ui'], function(){
    $.widget("w.timecrowd", {
        options: {
            //type:1只有年月份
            type:0,
            //选中值
            //形式一（必须是一维数组）：['2015']
            //形式二（必须是四维数组）：['2015','Q2','2015','Q3']
            selectedTimes:[],
            //选中值:[2]
            selectedCrowds:[],
            //日期下拉菜单：[{key:'2015', value:'2015年'},{key:'2014', value:'2014年'}]
            times:[],
            ok:function(){}
        },
        _getQvalue:function(q, start){
            var timeStr='';
            if(start){
                switch(q){
                    case 'Q1':{
                        timeStr='.1.1';break;
                    }
                    case 'Q2':{
                        timeStr='.4.1';break;
                    }
                    case 'Q3':{
                        timeStr='.7.1';break;
                    }
                    case 'Q4':{
                        timeStr='.10.1';break;
                    }
                }
            }else{
                switch(q){
                    case 'Q1':{
                        timeStr='.3.31';break;
                    }
                    case 'Q2':{
                        timeStr='.6.30';break;
                    }
                    case 'Q3':{
                        timeStr='.9.30';break;
                    }
                    case 'Q4':{
                        timeStr='.12.31';break;
                    }
                }
            }
            return timeStr;
        },
        _create: function() {
            var t=this;
            if(t.element.data("isInit")){
                return;
            }else{
                t.element.data("isInit", true);
            }
            t.initElement();
            if(t.options.type==0) {
                t._setQuarterLabel();
                var qs=['Q1','Q2','Q3','Q4'];
                t._setTime(qs);
                t._setCrowds(function(){
                    t._setQuarterEvent();
                });
            } else if(t.options.type==1){
                t._setMonthLabel();
                var months=['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];
                t._setTime(months);
                t._setMonthEvent();
            }
        },
        initElement:function(){
            var arr = [];
            arr.push('	<div class="fhead">');
            arr.push('		<span class="time"></span>');
            arr.push('		<span class="crowd"></span>');
            arr.push('		<i class="icon-angle-down"></i>');
            arr.push('	</div>');
            arr.push('	<div class="fbody">');
            arr.push('		<table>');
            arr.push('			<tr>');
            arr.push('				<td class="tm">时间<i class="timeerror icon-warning-sign hide"></i></td>');
            arr.push('				<td class="timecol">');
            arr.push('				</td>');
            arr.push('			</tr>');

            if(this.options.selectedCrowds.length > 0) {
                arr.push('			<tr>');
                arr.push('				<td class="cr"><div>人群<i class="crowdserror icon-warning-sign hide"></i></div></td>');
                arr.push('				<td>');
                arr.push('					<div class="crbd">');
                arr.push('					</div>');
                arr.push('				</td>');
                arr.push('			</tr>');
            }

            arr.push('			<tr>');
            arr.push('				<td colspan="2">');
            arr.push('					<div class="opr">');
            arr.push('						<button class="oprationsave">确定</button>');
            arr.push('						<button class="oprationcancel">取消</button>');
            arr.push('					</div>');
            arr.push('				</td>');
            arr.push('			</tr>');
            arr.push('		</table>');
            arr.push('	</div>');
            if (!this.element.hasClass("filtertimecrowd")) {
                this.element.addClass("filtertimecrowd");
            }
            if (!this.element.hasClass("closeed")) {
                this.element.addClass("closeed");
            }
            this.element.html(arr.join(''));

            //事件
            var t=this;
            t.element.find(".fhead").unbind("click").on("click", function(){
                t.element.removeClass("closeed");
            });
            t.element.find(".oprationcancel").unbind("click").on("click", function(){
                t.element.addClass("closeed");
            });
        },
        _setTime:function(months){
            var t=this;
            //
            var arr=[];
            if(t.options.selectedTimes.length==1) {
                arr.push('<select class="timeselect">');
                $.each(this.options.times, function (i, item) {
                    arr.push('<option value="' + item.key + '"'+(t.options.selectedTimes[0]==item.key?' selected':'')+'>' + item.value + '</option>');
                });
                arr.push('</select>');
            }else{
                //from年
                arr.push('<select class="fromyear">');
                $.each(this.options.times, function (i, item) {
                    arr.push('<option value="' + item.key + '"'+(t.options.selectedTimes[0]==item.key?' selected':'')+'>' + item.value + '</option>');
                });
                arr.push('</select>&nbsp;');
                //Q
                arr.push('<select class="fromq">');
                $.each(months, function (i, item) {
                    arr.push('<option value="' + item.replace('月', '') + '"'+(t.options.selectedTimes[1]==item.replace('月', '')?' selected':'')+'>' + item + '</option>');
                });
                arr.push('</select>&nbsp;-&nbsp;');
                //to年
                arr.push('<select class="toyear">');
                $.each(this.options.times, function (i, item) {
                    arr.push('<option value="' + item.key + '"'+(t.options.selectedTimes[2]==item.key?' selected':'')+'>' + item.value + '</option>');
                });
                arr.push('</select>&nbsp;');
                //Q
                arr.push('<select class="toq">');
                $.each(months, function (i, item) {
                    arr.push('<option value="' + item.replace('月', '') + '"'+(t.options.selectedTimes[3]==item.replace('月', '')?' selected':'')+'>' + item + '</option>');
                });
                arr.push('</select>');
            }
            this.element.find(".fbody .timecol").html(arr.join(''));
        },
        _setCrowds:function(callback){
            var t=this;
            var data=[];
            $.get(G_WEB_ROOT+'/common/getPopulations', {}, function(data){
                //
                var crowdArr=[], crowdbtn=[];
                var crowds = _.filter(data, function(m){
                    var b=false;
                    $.each(t.options.selectedCrowds, function(i, item){
                        if(item == m.k){
                            b=true;
                        }
                    })
                    return b;
                });
                $.each(crowds, function(i, item){
                    crowdArr.push(item.v);
                });
                var cs=crowds.length>0?crowdArr.join("、"):'所有人群';
                t.element.find(".fhead .crowd").attr("title", cs).text(cs);

                //
                crowdbtn.push('<div'+(crowds.length==0?' class="selected"':'')+' data-id="0">不限</div>')
                $.each(data, function(i, item){
                    var b=false;
                    $.each(t.options.selectedCrowds, function(i, m){
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

        //type=0
        _setQuarterLabel:function(){
            var t=this;
            var time='';
            if(t.options.selectedTimes.length==1){
                var timeobj=_.find(this.options.times, function(m){
                    return m.key==t.options.selectedTimes[0];
                });
                if(timeobj){
                    time=timeobj.value;
                }
            }else{
                var timeArr=[];
                timeArr.push(this.options.selectedTimes[0]);
                timeArr.push(this._getQvalue(this.options.selectedTimes[1], true));
                timeArr.push('-');
                timeArr.push(this.options.selectedTimes[2]);
                timeArr.push(this._getQvalue(this.options.selectedTimes[3], false));
                time=timeArr.join('');
            }
            this.element.find(".fhead .time").text(time);
        },
        _setQuarterEvent:function(){
            var t=this;
            t.element.find(".oprationsave").unbind("click").on("click", function(){
                //判断
                if (t.options.selectedTimes.length > 1){
                    var from = t.element.find(".fromyear").val() + '' + t.element.find(".fromq").val().replace("Q", "");
                    var to = t.element.find(".toyear").val() + '' + t.element.find(".toq").val().replace("Q", "");
                    if (from > to) {
                        t.element.find(".fbody .timeerror").removeClass("hide").attr("title", "开始日期必须小于或者等于结束日期");
                        return;
                    }
                }
                if (t.element.find(".crbd .selected").length == 0) {
                    t.element.find(".fbody .crowdserror").removeClass("hide").attr("title", "请选择人群");
                    return;
                }
                t.element.find(".fbody .timeerror,.fbody .crowdserror").addClass("hide");

                var times = [];
                if (t.options.selectedTimes.length == 1) {
                    times.push(t.element.find(".timeselect").val());
                    //set
                    var timeStr=t.element.find(".timeselect option:selected").html();
                    t.element.find(".fhead .time").text(timeStr);
                } else {
                    times.push(t.element.find(".fromyear").val());
                    times.push(t.element.find(".fromq").val());
                    times.push(t.element.find(".toyear").val());
                    times.push(t.element.find(".toq").val());
                    //set
                    var showTime = [];
                    showTime.push(t.element.find(".fromyear").val());
                    showTime.push(t._getQvalue(t.element.find(".fromq").val(), true));
                    showTime.push('-');
                    showTime.push(t.element.find(".toyear").val());
                    showTime.push(t._getQvalue(t.element.find(".toq").val(), false));
                    t.element.find(".fhead .time").text(showTime.join(''));
                }
                var crowds = [], crowdsName = [];
                if(t.element.find(".crbd div[data-id='0']").hasClass("selected")){
                    crowds.push($(this).data("id"));
                    crowdsName.push('所有人群');
                }else {
                    t.element.find(".crbd .selected").each(function (i, item) {
                        crowds.push($(this).data("id"));
                        crowdsName.push($(this).text());
                    });
                }
                t._trigger("ok", null, {
                    times: times,
                    crowds: crowds
                });
                //set
                t.element.find(".fhead .crowd").text(crowdsName.join('、')).attr("title", crowdsName.join('、'));

                //close
                t.element.addClass("closeed");
            });
        },

        //type=1
        _setMonthLabel:function(){
            var t=this;
            var time='';
            if(t.options.selectedTimes.length==1){
                var timeobj=_.find(this.options.times, function(m){
                    return m.key==t.options.selectedTimes[0];
                });
                if(timeobj){
                    time=timeobj.value;
                }
            }else{
                var timeArr=[];
                timeArr.push(this.options.selectedTimes[0]);
                timeArr.push('.'+this.options.selectedTimes[1].replace('月',''));
                timeArr.push('-');
                timeArr.push(this.options.selectedTimes[2]);
                timeArr.push('.'+this.options.selectedTimes[3].replace('月',''));
                time=timeArr.join('');
            }
            this.element.find(".fhead .time").text(time);
        },
        _setMonthEvent:function(){
            var t=this;
            t.element.find(".oprationsave").unbind("click").on("click", function(){
                //判断
                if (t.options.selectedTimes.length > 1){
                    var from = t.element.find(".fromyear").val() + '' + (t.element.find(".fromq").val()+10);
                    var to = t.element.find(".toyear").val() + '' + (t.element.find(".toq").val()+10);
                    if (from > to) {
                        t.element.find(".fbody .timeerror").removeClass("hide").attr("title", "开始日期必须小于或者等于结束日期");
                        return;
                    }
                }
                if(t.options.selectedCrowds.length > 0) {
                    if (t.element.find(".crbd .selected").length == 0) {
                        t.element.find(".fbody .crowdserror").removeClass("hide").attr("title", "请选择人群");
                        return;
                    }
                }
                t.element.find(".fbody .timeerror,.fbody .crowdserror").addClass("hide");

                var times = [];
                if (t.options.selectedTimes.length == 1) {
                    times.push(t.element.find(".timeselect").val());
                    //set
                    var timeStr=t.element.find(".timeselect option:selected").html();
                    t.element.find(".fhead .time").text(timeStr);
                } else {
                    times.push(t.element.find(".fromyear").val());
                    times.push(t.element.find(".fromq").val());
                    times.push(t.element.find(".toyear").val());
                    times.push(t.element.find(".toq").val());
                    //set
                    var showTime = [];
                    showTime.push(t.element.find(".fromyear").val());
                    showTime.push('.'+t.element.find(".fromq").val());
                    showTime.push('-');
                    showTime.push(t.element.find(".toyear").val());
                    showTime.push('.'+t.element.find(".toq").val());
                    t.element.find(".fhead .time").text(showTime.join(''));
                }
                t._trigger("ok", null, {
                    times: times
                });

                //close
                t.element.addClass("closeed");
            });
        }
    });
});