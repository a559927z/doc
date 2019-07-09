import request from '../utils/request';
import { stringify } from 'qs';

export async function getParamInfoList(params) {
	return request('/pub/paramInfo/getParamInfoList', {
		method: 'POST',
		body: params,
	});
}

export async function addParamInfo(params) {
	return request('/pub/paramInfo/addParamInfo', {
		method: 'POST',
		body: params,
	});
}

export async function editParamInfo(params) {
	return request('/pub/paramInfo/editParamInfo', {
		method: 'POST',
		body: params,
	});
}

export async function deleteParamInfo(params) {
	return request(`/pub/paramInfo/deleteParamInfo?${stringify(params)}`);
}

export async function getOneParamInfo(params) {
	return request(`/pub/paramInfo/getOneParamInfo?${stringify(params)}`);
}