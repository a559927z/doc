/*
 RequireJS 2.1.15 Copyright (c) 2010-2014, The Dojo Foundation All Rights Reserved.
 Available via the MIT or new BSD license.
 see: http://github.com/jrburke/requirejs for details
*/
var requirejs,require,define;
(function(ba){function G(b){return"[object Function]"===K.call(b)}function H(b){return"[object Array]"===K.call(b)}function v(b,c){if(b){var d;for(d=0;d<b.length&&(!b[d]||!c(b[d],d,b));d+=1);}}function T(b,c){if(b){var d;for(d=b.length-1;-1<d&&(!b[d]||!c(b[d],d,b));d-=1);}}function t(b,c){return fa.call(b,c)}function m(b,c){return t(b,c)&&b[c]}function B(b,c){for(var d in b)if(t(b,d)&&c(b[d],d))break}function U(b,c,d,e){c&&B(c,function(c,g){if(d||!t(b,g))e&&"object"===typeof c&&c&&!H(c)&&!G(c)&&!(c instanceof
RegExp)?(b[g]||(b[g]={}),U(b[g],c,d,e)):b[g]=c});return b}function u(b,c){return function(){return c.apply(b,arguments)}}function ca(b){throw b;}function da(b){if(!b)return b;var c=ba;v(b.split("."),function(b){c=c[b]});return c}function C(b,c,d,e){c=Error(c+"\nhttp://requirejs.org/docs/errors.html#"+b);c.requireType=b;c.requireModules=e;d&&(c.originalError=d);return c}function ga(b){function c(a,k,b){var f,l,c,d,e,g,i,p,k=k&&k.split("/"),h=j.map,n=h&&h["*"];if(a){a=a.split("/");l=a.length-1;j.nodeIdCompat&&
Q.test(a[l])&&(a[l]=a[l].replace(Q,""));"."===a[0].charAt(0)&&k&&(l=k.slice(0,k.length-1),a=l.concat(a));l=a;for(c=0;c<l.length;c++)if(d=l[c],"."===d)l.splice(c,1),c-=1;else if(".."===d&&!(0===c||1==c&&".."===l[2]||".."===l[c-1])&&0<c)l.splice(c-1,2),c-=2;a=a.join("/")}if(b&&h&&(k||n)){l=a.split("/");c=l.length;a:for(;0<c;c-=1){e=l.slice(0,c).join("/");if(k)for(d=k.length;0<d;d-=1)if(b=m(h,k.slice(0,d).join("/")))if(b=m(b,e)){f=b;g=c;break a}!i&&(n&&m(n,e))&&(i=m(n,e),p=c)}!f&&i&&(f=i,g=p);f&&(l.splice(0,
g,f),a=l.join("/"))}return(f=m(j.pkgs,a))?f:a}function d(a){z&&v(document.getElementsByTagName("script"),function(k){if(k.getAttribute("data-requiremodule")===a&&k.getAttribute("data-requirecontext")===i.contextName)return k.parentNode.removeChild(k),!0})}function e(a){var k=m(j.paths,a);if(k&&H(k)&&1<k.length)return k.shift(),i.require.undef(a),i.makeRequire(null,{skipMap:!0})([a]),!0}function n(a){var k,c=a?a.indexOf("!"):-1;-1<c&&(k=a.substring(0,c),a=a.substring(c+1,a.length));return[k,a]}function p(a,
k,b,f){var l,d,e=null,g=k?k.name:null,j=a,p=!0,h="";a||(p=!1,a="_@r"+(K+=1));a=n(a);e=a[0];a=a[1];e&&(e=c(e,g,f),d=m(r,e));a&&(e?h=d&&d.normalize?d.normalize(a,function(a){return c(a,g,f)}):-1===a.indexOf("!")?c(a,g,f):a:(h=c(a,g,f),a=n(h),e=a[0],h=a[1],b=!0,l=i.nameToUrl(h)));b=e&&!d&&!b?"_unnormalized"+(O+=1):"";return{prefix:e,name:h,parentMap:k,unnormalized:!!b,url:l,originalName:j,isDefine:p,id:(e?e+"!"+h:h)+b}}function s(a){var k=a.id,b=m(h,k);b||(b=h[k]=new i.Module(a));return b}function q(a,
k,b){var f=a.id,c=m(h,f);if(t(r,f)&&(!c||c.defineEmitComplete))"defined"===k&&b(r[f]);else if(c=s(a),c.error&&"error"===k)b(c.error);else c.on(k,b)}function w(a,b){var c=a.requireModules,f=!1;if(b)b(a);else if(v(c,function(b){if(b=m(h,b))b.error=a,b.events.error&&(f=!0,b.emit("error",a))}),!f)g.onError(a)}function x(){R.length&&(ha.apply(A,[A.length,0].concat(R)),R=[])}function y(a){delete h[a];delete V[a]}function F(a,b,c){var f=a.map.id;a.error?a.emit("error",a.error):(b[f]=!0,v(a.depMaps,function(f,
d){var e=f.id,g=m(h,e);g&&(!a.depMatched[d]&&!c[e])&&(m(b,e)?(a.defineDep(d,r[e]),a.check()):F(g,b,c))}),c[f]=!0)}function D(){var a,b,c=(a=1E3*j.waitSeconds)&&i.startTime+a<(new Date).getTime(),f=[],l=[],g=!1,h=!0;if(!W){W=!0;B(V,function(a){var i=a.map,j=i.id;if(a.enabled&&(i.isDefine||l.push(a),!a.error))if(!a.inited&&c)e(j)?g=b=!0:(f.push(j),d(j));else if(!a.inited&&(a.fetched&&i.isDefine)&&(g=!0,!i.prefix))return h=!1});if(c&&f.length)return a=C("timeout","Load timeout for modules: "+f,null,
f),a.contextName=i.contextName,w(a);h&&v(l,function(a){F(a,{},{})});if((!c||b)&&g)if((z||ea)&&!X)X=setTimeout(function(){X=0;D()},50);W=!1}}function E(a){t(r,a[0])||s(p(a[0],null,!0)).init(a[1],a[2])}function I(a){var a=a.currentTarget||a.srcElement,b=i.onScriptLoad;a.detachEvent&&!Y?a.detachEvent("onreadystatechange",b):a.removeEventListener("load",b,!1);b=i.onScriptError;(!a.detachEvent||Y)&&a.removeEventListener("error",b,!1);return{node:a,id:a&&a.getAttribute("data-requiremodule")}}function J(){var a;
for(x();A.length;){a=A.shift();if(null===a[0])return w(C("mismatch","Mismatched anonymous define() module: "+a[a.length-1]));E(a)}}var W,Z,i,L,X,j={waitSeconds:7,baseUrl:"./",paths:{},bundles:{},pkgs:{},shim:{},config:{}},h={},V={},$={},A=[],r={},S={},aa={},K=1,O=1;L={require:function(a){return a.require?a.require:a.require=i.makeRequire(a.map)},exports:function(a){a.usingExports=!0;if(a.map.isDefine)return a.exports?r[a.map.id]=a.exports:a.exports=r[a.map.id]={}},module:function(a){return a.module?
a.module:a.module={id:a.map.id,uri:a.map.url,config:function(){return m(j.config,a.map.id)||{}},exports:a.exports||(a.exports={})}}};Z=function(a){this.events=m($,a.id)||{};this.map=a;this.shim=m(j.shim,a.id);this.depExports=[];this.depMaps=[];this.depMatched=[];this.pluginMaps={};this.depCount=0};Z.prototype={init:function(a,b,c,f){f=f||{};if(!this.inited){this.factory=b;if(c)this.on("error",c);else this.events.error&&(c=u(this,function(a){this.emit("error",a)}));this.depMaps=a&&a.slice(0);this.errback=
c;this.inited=!0;this.ignore=f.ignore;f.enabled||this.enabled?this.enable():this.check()}},defineDep:function(a,b){this.depMatched[a]||(this.depMatched[a]=!0,this.depCount-=1,this.depExports[a]=b)},fetch:function(){if(!this.fetched){this.fetched=!0;i.startTime=(new Date).getTime();var a=this.map;if(this.shim)i.makeRequire(this.map,{enableBuildCallback:!0})(this.shim.deps||[],u(this,function(){return a.prefix?this.callPlugin():this.load()}));else return a.prefix?this.callPlugin():this.load()}},load:function(){var a=
this.map.url;S[a]||(S[a]=!0,i.load(this.map.id,a))},check:function(){if(this.enabled&&!this.enabling){var a,b,c=this.map.id;b=this.depExports;var f=this.exports,l=this.factory;if(this.inited)if(this.error)this.emit("error",this.error);else{if(!this.defining){this.defining=!0;if(1>this.depCount&&!this.defined){if(G(l)){if(this.events.error&&this.map.isDefine||g.onError!==ca)try{f=i.execCb(c,l,b,f)}catch(d){a=d}else f=i.execCb(c,l,b,f);this.map.isDefine&&void 0===f&&((b=this.module)?f=b.exports:this.usingExports&&
(f=this.exports));if(a)return a.requireMap=this.map,a.requireModules=this.map.isDefine?[this.map.id]:null,a.requireType=this.map.isDefine?"define":"require",w(this.error=a)}else f=l;this.exports=f;if(this.map.isDefine&&!this.ignore&&(r[c]=f,g.onResourceLoad))g.onResourceLoad(i,this.map,this.depMaps);y(c);this.defined=!0}this.defining=!1;this.defined&&!this.defineEmitted&&(this.defineEmitted=!0,this.emit("defined",this.exports),this.defineEmitComplete=!0)}}else this.fetch()}},callPlugin:function(){var a=
this.map,b=a.id,d=p(a.prefix);this.depMaps.push(d);q(d,"defined",u(this,function(f){var l,d;d=m(aa,this.map.id);var e=this.map.name,P=this.map.parentMap?this.map.parentMap.name:null,n=i.makeRequire(a.parentMap,{enableBuildCallback:!0});if(this.map.unnormalized){if(f.normalize&&(e=f.normalize(e,function(a){return c(a,P,!0)})||""),f=p(a.prefix+"!"+e,this.map.parentMap),q(f,"defined",u(this,function(a){this.init([],function(){return a},null,{enabled:!0,ignore:!0})})),d=m(h,f.id)){this.depMaps.push(f);
if(this.events.error)d.on("error",u(this,function(a){this.emit("error",a)}));d.enable()}}else d?(this.map.url=i.nameToUrl(d),this.load()):(l=u(this,function(a){this.init([],function(){return a},null,{enabled:!0})}),l.error=u(this,function(a){this.inited=!0;this.error=a;a.requireModules=[b];B(h,function(a){0===a.map.id.indexOf(b+"_unnormalized")&&y(a.map.id)});w(a)}),l.fromText=u(this,function(f,c){var d=a.name,e=p(d),P=M;c&&(f=c);P&&(M=!1);s(e);t(j.config,b)&&(j.config[d]=j.config[b]);try{g.exec(f)}catch(h){return w(C("fromtexteval",
"fromText eval for "+b+" failed: "+h,h,[b]))}P&&(M=!0);this.depMaps.push(e);i.completeLoad(d);n([d],l)}),f.load(a.name,n,l,j))}));i.enable(d,this);this.pluginMaps[d.id]=d},enable:function(){V[this.map.id]=this;this.enabling=this.enabled=!0;v(this.depMaps,u(this,function(a,b){var c,f;if("string"===typeof a){a=p(a,this.map.isDefine?this.map:this.map.parentMap,!1,!this.skipMap);this.depMaps[b]=a;if(c=m(L,a.id)){this.depExports[b]=c(this);return}this.depCount+=1;q(a,"defined",u(this,function(a){this.defineDep(b,
a);this.check()}));this.errback&&q(a,"error",u(this,this.errback))}c=a.id;f=h[c];!t(L,c)&&(f&&!f.enabled)&&i.enable(a,this)}));B(this.pluginMaps,u(this,function(a){var b=m(h,a.id);b&&!b.enabled&&i.enable(a,this)}));this.enabling=!1;this.check()},on:function(a,b){var c=this.events[a];c||(c=this.events[a]=[]);c.push(b)},emit:function(a,b){v(this.events[a],function(a){a(b)});"error"===a&&delete this.events[a]}};i={config:j,contextName:b,registry:h,defined:r,urlFetched:S,defQueue:A,Module:Z,makeModuleMap:p,
nextTick:g.nextTick,onError:w,configure:function(a){a.baseUrl&&"/"!==a.baseUrl.charAt(a.baseUrl.length-1)&&(a.baseUrl+="/");var b=j.shim,c={paths:!0,bundles:!0,config:!0,map:!0};B(a,function(a,b){c[b]?(j[b]||(j[b]={}),U(j[b],a,!0,!0)):j[b]=a});a.bundles&&B(a.bundles,function(a,b){v(a,function(a){a!==b&&(aa[a]=b)})});a.shim&&(B(a.shim,function(a,c){H(a)&&(a={deps:a});if((a.exports||a.init)&&!a.exportsFn)a.exportsFn=i.makeShimExports(a);b[c]=a}),j.shim=b);a.packages&&v(a.packages,function(a){var b,
a="string"===typeof a?{name:a}:a;b=a.name;a.location&&(j.paths[b]=a.location);j.pkgs[b]=a.name+"/"+(a.main||"main").replace(ia,"").replace(Q,"")});B(h,function(a,b){!a.inited&&!a.map.unnormalized&&(a.map=p(b))});if(a.deps||a.callback)i.require(a.deps||[],a.callback)},makeShimExports:function(a){return function(){var b;a.init&&(b=a.init.apply(ba,arguments));return b||a.exports&&da(a.exports)}},makeRequire:function(a,e){function j(c,d,m){var n,q;e.enableBuildCallback&&(d&&G(d))&&(d.__requireJsBuild=
!0);if("string"===typeof c){if(G(d))return w(C("requireargs","Invalid require call"),m);if(a&&t(L,c))return L[c](h[a.id]);if(g.get)return g.get(i,c,a,j);n=p(c,a,!1,!0);n=n.id;return!t(r,n)?w(C("notloaded",'Module name "'+n+'" has not been loaded yet for context: '+b+(a?"":". Use require([])"))):r[n]}J();i.nextTick(function(){J();q=s(p(null,a));q.skipMap=e.skipMap;q.init(c,d,m,{enabled:!0});D()});return j}e=e||{};U(j,{isBrowser:z,toUrl:function(b){var d,e=b.lastIndexOf("."),k=b.split("/")[0];if(-1!==
e&&(!("."===k||".."===k)||1<e))d=b.substring(e,b.length),b=b.substring(0,e);return i.nameToUrl(c(b,a&&a.id,!0),d,!0)},defined:function(b){return t(r,p(b,a,!1,!0).id)},specified:function(b){b=p(b,a,!1,!0).id;return t(r,b)||t(h,b)}});a||(j.undef=function(b){x();var c=p(b,a,!0),e=m(h,b);d(b);delete r[b];delete S[c.url];delete $[b];T(A,function(a,c){a[0]===b&&A.splice(c,1)});e&&(e.events.defined&&($[b]=e.events),y(b))});return j},enable:function(a){m(h,a.id)&&s(a).enable()},completeLoad:function(a){var b,
c,d=m(j.shim,a)||{},g=d.exports;for(x();A.length;){c=A.shift();if(null===c[0]){c[0]=a;if(b)break;b=!0}else c[0]===a&&(b=!0);E(c)}c=m(h,a);if(!b&&!t(r,a)&&c&&!c.inited){if(j.enforceDefine&&(!g||!da(g)))return e(a)?void 0:w(C("nodefine","No define call for "+a,null,[a]));E([a,d.deps||[],d.exportsFn])}D()},nameToUrl:function(a,b,c){var d,e,h;(d=m(j.pkgs,a))&&(a=d);if(d=m(aa,a))return i.nameToUrl(d,b,c);if(g.jsExtRegExp.test(a))d=a+(b||"");else{d=j.paths;a=a.split("/");for(e=a.length;0<e;e-=1)if(h=a.slice(0,
e).join("/"),h=m(d,h)){H(h)&&(h=h[0]);a.splice(0,e,h);break}d=a.join("/");d+=b||(/^data\:|\?/.test(d)||c?"":".js");d=("/"===d.charAt(0)||d.match(/^[\w\+\.\-]+:/)?"":j.baseUrl)+d}if(!j.urlArgs){return d}if(typeof j.urlArgs === 'string'){return d+((- 1 === d.indexOf("?") ? "?":"&")+j.urlArgs)}else if(typeof j.urlArgs === 'function'){var urlArgs;try{urlArgs = j.urlArgs(a.join("/"),b,c,d)}catch(e){urlArgs = ""}return d+((- 1 === d.indexOf("?") ? "?":"&")+urlArgs)}return j.urlArgs?d+((-1===d.indexOf("?")?"?":"&")+j.urlArgs):d},load:function(a,b){g.load(i,a,b)},execCb:function(a,b,c,d){return b.apply(d,c)},onScriptLoad:function(a){if("load"===a.type||ja.test((a.currentTarget||a.srcElement).readyState))N=null,a=I(a),i.completeLoad(a.id)},onScriptError:function(a){var b=I(a);if(!e(b.id))return w(C("scripterror",
"Script error for: "+b.id,a,[b.id]))}};i.require=i.makeRequire();return i}var g,x,y,D,I,E,N,J,s,O,ka=/(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/mg,la=/[^.]\s*require\s*\(\s*["']([^'"\s]+)["']\s*\)/g,Q=/\.js$/,ia=/^\.\//;x=Object.prototype;var K=x.toString,fa=x.hasOwnProperty,ha=Array.prototype.splice,z=!!("undefined"!==typeof window&&"undefined"!==typeof navigator&&window.document),ea=!z&&"undefined"!==typeof importScripts,ja=z&&"PLAYSTATION 3"===navigator.platform?/^complete$/:/^(complete|loaded)$/,
Y="undefined"!==typeof opera&&"[object Opera]"===opera.toString(),F={},q={},R=[],M=!1;if("undefined"===typeof define){if("undefined"!==typeof requirejs){if(G(requirejs))return;q=requirejs;requirejs=void 0}"undefined"!==typeof require&&!G(require)&&(q=require,require=void 0);g=requirejs=function(b,c,d,e){var n,p="_";!H(b)&&"string"!==typeof b&&(n=b,H(c)?(b=c,c=d,d=e):b=[]);n&&n.context&&(p=n.context);(e=m(F,p))||(e=F[p]=g.s.newContext(p));n&&e.configure(n);return e.require(b,c,d)};g.config=function(b){return g(b)};
g.nextTick="undefined"!==typeof setTimeout?function(b){setTimeout(b,4)}:function(b){b()};require||(require=g);g.version="2.1.15";g.jsExtRegExp=/^\/|:|\?|\.js$/;g.isBrowser=z;x=g.s={contexts:F,newContext:ga};g({});v(["toUrl","undef","defined","specified"],function(b){g[b]=function(){var c=F._;return c.require[b].apply(c,arguments)}});if(z&&(y=x.head=document.getElementsByTagName("head")[0],D=document.getElementsByTagName("base")[0]))y=x.head=D.parentNode;g.onError=ca;g.createNode=function(b){var c=
b.xhtml?document.createElementNS("http://www.w3.org/1999/xhtml","html:script"):document.createElement("script");c.type=b.scriptType||"text/javascript";c.charset="utf-8";c.async=!0;return c};g.load=function(b,c,d){var e=b&&b.config||{};if(z)return e=g.createNode(e,c,d),e.setAttribute("data-requirecontext",b.contextName),e.setAttribute("data-requiremodule",c),e.attachEvent&&!(e.attachEvent.toString&&0>e.attachEvent.toString().indexOf("[native code"))&&!Y?(M=!0,e.attachEvent("onreadystatechange",b.onScriptLoad)):
(e.addEventListener("load",b.onScriptLoad,!1),e.addEventListener("error",b.onScriptError,!1)),e.src=d,J=e,D?y.insertBefore(e,D):y.appendChild(e),J=null,e;if(ea)try{importScripts(d),b.completeLoad(c)}catch(m){b.onError(C("importscripts","importScripts failed for "+c+" at "+d,m,[c]))}};z&&!q.skipDataMain&&T(document.getElementsByTagName("script"),function(b){y||(y=b.parentNode);if(I=b.getAttribute("data-main"))return s=I,q.baseUrl||(E=s.split("/"),s=E.pop(),O=E.length?E.join("/")+"/":"./",q.baseUrl=
O),s=s.replace(Q,""),g.jsExtRegExp.test(s)&&(s=I),q.deps=q.deps?q.deps.concat(s):[s],!0});define=function(b,c,d){var e,g;"string"!==typeof b&&(d=c,c=b,b=null);H(c)||(d=c,c=null);!c&&G(d)&&(c=[],d.length&&(d.toString().replace(ka,"").replace(la,function(b,d){c.push(d)}),c=(1===d.length?["require"]:["require","exports","module"]).concat(c)));if(M){if(!(e=J))N&&"interactive"===N.readyState||T(document.getElementsByTagName("script"),function(b){if("interactive"===b.readyState)return N=b}),e=N;e&&(b||
(b=e.getAttribute("data-requiremodule")),g=F[e.getAttribute("data-requirecontext")])}(g?g.defQueue:R).push([b,c,d])};define.amd={jQuery:!0};g.exec=function(b){return eval(b)};g(q)}})(this);

/**
 * require config
 * ------------------------------------------------------------------------------------ */
(function () {
    var paths = {
            'jquery': 'lib/jquery/jquery-1.11.1.min',
            'docs': 'demo/docs.min',
            "jquery-ui": "lib/jquery/jquery-ui-1.11.0",
            "jquery-pin": "lib/jquery/jquery.pin.min",
            "utils": "lib/base/base", // 工具类
            "messenger": "lib/messenger/messenger.min", // 消息控件
            'placeholder': 'lib/plugins/jquery-placeholder',
            'unveil': 'lib/plugins/jquery.unveil',
            'echarts': 'lib/echarts/echarts3',
            'dataTool': 'lib/echarts/dataTool.min',
            'toastr': 'lib/bootstrap/toastr.min',
            'bootstrap': 'lib/bootstrap/bootstrap.min',
            'template': 'lib/base/baiduTemplate', // baidu 模板引擎
            'underscore': 'lib/underscore/underscore',	//便捷下划线工具类
            'validate': 'lib/jquery-validation/1.11.1/jquery.validate.min',	//表单验证
            'validateCH': 'lib/jquery-validation/1.11.1/messages_bs_zh',	//表单验证中文语言包
            'form': 'lib/jquery-form/jquery.form.min',		//jqueryForm
            'navMenu': 'lib/widgets/navMenu',//导航菜单
            'searchBox': 'lib/widgets/searchBox',//筛选条件
            'searchBox2': 'lib/widgets/searchBox2',//筛选条件
            'searchBox3': 'lib/widgets/searchBox3',//筛选条件
            'selectItem': 'lib/widgets/selectItem',    //标签选择器
            'moment': 'lib/moment/moment',
            'timeLine': 'lib/widgets/timeLine',//时间轴
            'timeLine2': 'lib/widgets/timeLine2',//时间轴
            'message': 'lib/widgets/message',//消息控件
            'vernierCursor': 'lib/widgets/vernierCursor',//游标尺
            "jgGrid": 'lib/jqGrid/jquery.jqGrid.min',//表格组件
            "jgGrid/base": 'lib/jqGrid/grid.base',		//表格基础包
            "jgGrid/language": 'lib/jqGrid/i18n/grid.locale-cn',//表格语言组件
            "zrw-elements": 'lib/base/zrw-elements',//zrw框架核心对象封装
            "tree": 'lib/widgets/jquery.tree',//树组件
            "datetimepicker": "lib/datetime/bootstrap-datetimepicker",//日期组件
            "tooltip": "lib/bootstrap/bootstrap-tooltip",//提示提示
            "confirmation": "lib/bootstrap/bootstrap-confirmation",//确认框
            "ztree": "lib/ztree/jquery.ztree.all.min", //ztree组件
            "jQdropdown": "lib/widgets/jquery.dropdown",//下拉组件
            "organTreeSelector": "lib/widgets/organTreeSelector",//机构树
            "webuploader": "lib/webuploader/webuploader",
            "zrw": "lib/base/zrw",	//基本核心方法
            "ace": "lib/base/ace.min",
            "ace-elements": "lib/base/ace-elements.min",
            "zrender": "lib/zrender/zrender",
            'zrender/Layer': 'zrender/Layer',
            'zrender/tool/color': "lib/zrender/zrender",
            'zrender/tool/guid': "lib/zrender/zrender",
            'zrender/shape/util/smoothSpline': "lib/zrender/zrender",
            'zrender/shape/Circle': "lib/zrender/zrender",
            'zrender/shape/Polyline': "lib/zrender/zrender",
            'zrender/shape/Polygon': "lib/zrender/zrender",
            'zrender/shape/Text': "lib/zrender/zrender",
            'zrender/shape/Line': "lib/zrender/zrender",
            'zrender/shape/Path': "lib/zrender/zrender",
            'zrender/shape/Ellipse': "lib/zrender/zrender",
            'riskTree': "lib/widgets/riskTree", //
            'riskTree2': "lib/widgets/riskTree2", //
            'riskList': "lib/widgets/riskList", //
            "talent-map": 'lib/widgets/talent-map', // 人才地图
            "talent-map2": 'lib/widgets/talent-map2', // 人才地图
            "tooltipZrw": 'lib/widgets/tooltipZrw', // 弹出层自定义嵌套div
            "resize": 'lib/base/resize', // 弹出层自定义嵌套div
            "select2": 'lib/select2/select2.full',  //输入框
            "bottomStock": 'lib/widgets/jquery.bottomStock', //人才盘点布局-底部
            "scrollTo": 'lib/scrollto/jquery.scrollTo.min', //滚动
            "jquery-drag": "lib/jquery-drag/jquery.drag",//jqueryDrag
            "jquery-mCustomScrollBar": "lib/jquery-mCustomScrollbar/jquery.mCustomScrollbar.concat.min",//jqueryMCustomScrollbar
            'echarts/chart/gauge': 'lib/echarts/chart/gauge',
            "zepto": '../mobile/js/lib/zepto/zepto',
            'slide': '../mobile/js/lib/slide',
            'appBase': '../mobile/js/lib/appBase',
            'screenZoom': '../mobile/js/lib/screenZoom',
            'chartTooltip': '../mobile/js/lib/chartTooltip',
            'press': '../mobile/js/lib/press',
            'datetime': '../mobile/js/lib/datetime/datetime',
            //'iscroll' : '../mobile/js/lib/datetime/iscroll',
//        "jquery-mobile":'../mobile/js/lib/jquery-mobile/jquery.mobile-1.4.5.min',
//        "touchSwipe":'../mobile/js/lib/jquery.touchSwipe.min',
            "iscroll": '../mobile/js/lib/iscroll',
            'selection': "lib/widgets/selection", //时间人群弹出框
            'gaugeAuxiliary': '../mobile/js/lib/gaugeAuxiliary',
            'mapGrid': "lib/widgets/mapGrid", //人才地图列表
            'inventoryReport': "lib/widgets/inventoryReport", //人才地图盘点报告
            'keyTalentsAdd': "lib/widgets/keyTalentsAdd", //人才地图盘点报告-添加关键人才
            'jBootstrapPage': "lib/widgets/jBootstrapPage", //分页
            'swal': '../mobile/js/lib/sweetalert.min',  //移动端弹出提示框
            'cardTabel': 'lib/widgets/cardTabel',
        },

        shim = {
            "jquery-ui": {
                exports: '$',
                deps: ['jquery']
            },
            "jquery-pin": {deps: ["jquery"]},
            "jquery-drag": {deps: ["jquery"]},
            "validate": {deps: ["jquery"]},
            "validateCH": {deps: ["validate"]},
            "placeholder": {deps: ["jquery"]},
            "unveil": {deps: ['jquery']},
            "messenger": {deps: ["jquery"]},
            "bootstrap": {deps: ["jquery"]},
            "docs": {deps: ["jquery"]},
            "navMenu": {deps: ["jquery"]},
            "searchBox": {deps: ["jquery"]},
            "searchBox2": {deps: ["jquery"]},
            "searchBox3": {deps: ["jquery", "datetimepicker", "organTreeSelector"]},
            "selectItem": {deps: ["jquery"]},
            "jgGrid/language": {deps: ["jquery"]},
            "jgGrid": {deps: ["jgGrid/language"]},
            "tree": {deps: ["jquery"]},
            "zrw-elements": {deps: ["tree"]},
            "datetimepicker": {deps: ["jquery"]},
            "tooltip": {deps: ["jquery"]},
            "confirmation": {deps: ["tooltip"]},
            "utils": {deps: ['jquery']},
            "ztree": {deps: ["jquery"]},
            "zrw": {deps: ["jquery"]},
            //"organTreeSelector":{deps:["dropdown"]},
            "timeLine": {deps: ["bootstrap"]},
            "message": {deps: ["bootstrap"]},
            "ace-elements": {deps: ["jquery"]},
            "ace": {deps: ["ace-elements"]},
            "riskTree": {deps: ["jquery", "zrender", 'zrender/shape/Line']},
//		"riskTree2" : {deps:['jquery',"zrender",'echarts','echarts/chart/scatter','bootstrap','messenger']},
            "riskTree2": {deps: ['jquery', "zrender", 'zrender/shape/Line']},
            'riskList': {deps:['jquery', "zrender", 'zrender/shape/Line']},
            "tooltipZrw": {deps: ["jquery"]},
            "resize": {deps: ["jquery"]},
            "select2": {deps: ["jquery"]},
            "bottomStock": {deps: ["jquery-ui"]},
            "scrollTo": {deps: ["jquery"]},
            "jquery-drag": {deps: ["jquery"]},
            "jquery-mCustomScrollBar": {deps: ["jquery"]},
            "slide": {deps: ["zepto"]},
//        "jquery-mobile":{deps:["jquery"]},
//        "touchSwipe":{deps:["jquery"]},

            "chartTooltip": {deps: ["jquery"]},
            "press": {deps: ["jquery"]},
            "screenZoom": {deps: ["jquery", "press"]},
            "appBase": {deps: ["zepto", "screenZoom"]},
            "gaugeAuxiliary": {deps: ["jquery"]},
            "datetime": {deps: ["jquery", "iscroll"]},

            "jBootstrapPage": {deps: ["jquery"]},

            "cardTabel": {deps: ["jquery"]},
        },

        baseUrl = function () {
            function getAbsoluteSrc(node) {
                return node.hasAttribute ? // non-IE6/7
                    node.src : node.getAttribute("src", 4);
            }

            var script = document.getElementById("require-config");
            if (!script) {
                var scripts = document.getElementsByTagName("script");
                var regex = /require.js/;
                for (var i = 0, j = scripts.length; i < j; i++) {
                    if (regex.test(getAbsoluteSrc(scripts[i]))) {
                        script = scripts[i];
                        break;
                    }
                }
                ;
            }
            var main = script.getAttribute('js-main');
            requirejs.config({deps: [main]});
            return getAbsoluteSrc(script).replace("require.js", '');
        }(),

        isDevMode = function () {
                var regex = new RegExp("(^|&)dev=([^&]*)(&|$)");
                var arg = window.location.search.substring(1).match(regex);
                return !!(arg && unescape(arg[2]) === 'true');
            }() || true;

    //isDevMode && requirejs.config({
    //	urlArgs : 'v=' + (new Date()).getTime()
    //});

    //paths对象 键值反转
    var mapping = {};
    for (var i in paths) {
        mapping[paths[i]] = i;
    }
    requirejs.config({
        baseUrl: baseUrl,
        paths: paths,
        urlArgs: function (a, b, c, d) {
            var key = mapping[a];
            if (key == undefined) {
                return "";
            }

            if (version[key] == undefined) {
                return 'v=' + version.version;
            }
            return 'v=' + version[key];
        },
        shim: shim
    });
})();