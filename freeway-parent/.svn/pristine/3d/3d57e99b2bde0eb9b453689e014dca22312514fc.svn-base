import { stringify } from 'qs';
import request from '../utils/request';

export async function selectByPage(params) {
	return request('/data/contact/contractRecord/selectByPage', {
		method: 'POST',
		body: params,
	});
}

export async function selectAllRecord(params) {
	return request('/data/contact/contractRecord/selectAllRecord', {
		method: 'POST',
		body: params,
	});
}

export async function save(params) {
	return request('/data/contact/contractRecord/save', {
		method: 'POST',
		body: params,
	});
}

export async function saveRecordList(params) {
	return request('/data/contact/contractRecord/saveContactRecordList', {
		method: 'POST',
		body: params,
	});
}

export async function del(params) {
	return request(`/data/contact/contractRecord/del?${stringify(params)}`);
}

export async function transform(params) {
	return request(`/data/contact/contractRecord/transferToRecord?${stringify(params)}`);
}

export async function viewDetial(params) {
	return request(`/data/contact/contractRecord/viewDetial?${stringify(params)}`);
}

export async function ecportExcel(params) {
	return request(`/data/contact/export/exportContactRecord?${stringify(params)}`);
}

export async function calculateRecord(params) {
	return request(`/data/contact/contractRecord/calculateRecord?${stringify(params)}`);
}

export async function calculateDatePlan(params) {
	return request(`/data/contact/contractRecord/calculateDatePlan?${stringify(params)}`);
}

export async function selectDatePlanPage(params) {
	return request('/data/contact/contractRecord/selectDatePlanPage', {
		method: 'POST',
		body: params,
	});
}

export async function noRemid(params) {
	return request(`/data/contact/contractRecord/noRemid?${stringify(params)}`);
}
