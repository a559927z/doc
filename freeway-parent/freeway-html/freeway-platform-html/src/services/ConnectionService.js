import request from '../utils/request';

export async function getAllIniiativeMap(params) {
	return request('/data/contact/iniiativeMap/getAllIniiativeMap', {
		method: 'POST',
		body: params,
	});
}

export async function addIniiativeMaps(params) {
	return request('/data/contact/iniiativeMap/addIniiativeMaps', {
		method: 'POST',
		body: params,
	});
}

export async function delIniiativeMap(params) {
	return request('/data/contact/iniiativeMap/delIniiativeMap', {
		method: 'POST',
		body: params,
	});
}

export async function modifyIniiativeMap(params) {
	return request('/data/contact/iniiativeMap/modifyIniiativeMap', {
		method: 'POST',
		body: params,
	});
}
