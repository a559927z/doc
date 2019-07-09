/**
 * Created by wqcai on 15/5/12.
 */
require(['jquery', 'timeLine'], function() {
	var options = {
		title:'管理建议与备忘',
		titleSuffix:'条未读',
		data:[
			{id:'10101',userName:'Cathyli',userNameCh:'李峰',isRead:0,hasDelete:0,content:'通过分析发现，因工作负荷太大,导致员工离职率增高',createTime:'2015-07-03 15:13:03'},
			{id:'10102',userName:'Mztcc',userNameCh:'常林',isRead:0,hasDelete:0,content:'可以试试另外的思路',createTime:'2015-07-03 15:12:03'},
			{id:'10103',userName:'Cathyli',userNameCh:'李峰1',isRead:0,hasDelete:0,content:'1212 \n 1、定期召开技术分享会，知识分享会；\n 2、加强团队建设及精神鼓励；\n 3、加强团队建设及精神鼓励；',createTime:'2015-07-03 12:13:12'},
			{id:'10104',userName:'Mztcc',userNameCh:'常林1',isRead:0,hasDelete:0,content:'可以试试另外的思路',createTime:'2015-07-03 12:12:12'},
			{id:'10105',userName:'Cathyli',userNameCh:'李峰2',isRead:0,hasDelete:1,content:'通过分析发现，因工作负荷太大,导致员工离职率增高',createTime:'2015-07-03 11:13:28'},
			{id:'10106',userName:'Mztcc',userNameCh:'常林2',isRead:0,hasDelete:0,content:'可以试试另外的思路',createTime:'2015-07-03 11:12:28'},
			{id:'10107',userName:'Cathyli',userNameCh:'李峰3',isRead:0,hasDelete:1,content:'1212 \n 1、定期召开技术分享会，知识分享会；\n 2、加强团队建设及精神鼓励；',createTime:'2015-07-02 15:13:40'},
			{id:'10108',userName:'Mztcc',userNameCh:'常林3',isRead:1,hasDelete:0,content:'可以试试另外的思路',createTime:'2015-07-02 15:10:40'},
			{id:'10109',userName:'Cathyli',userNameCh:'李峰4',isRead:1,hasDelete:1,content:'通过分析发现，因工作负荷太大,导致员工离职率增高',createTime:'2015-06-05 15:13:53'},
			{id:'10110',userName:'Mztcc',userNameCh:'常林4',isRead:1,hasDelete:1,content:'可以试试另外的思路',createTime:'2015-06-05 15:12:53'},
			{id:'10111',userName:'Cathyli',userNameCh:'李峰5',isRead:1,hasDelete:1,content:'通过分析发现，因工作负荷太大,导致员工离职率增高',createTime:'2014-06-05 15:14:08'},
			{id:'10112',userName:'Mztcc',userNameCh:'常林6',isRead:1,hasDelete:1,content:'可以试试另外的思路',createTime:'2014-06-05 15:10:08'}
		],
		onSubmit: function(text){
			console.log(text);
		}
	}
	$('#new').timeLine(options);
});