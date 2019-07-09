import request from '@/utils/request';

export async function queryUserList() {
	return request('/data/user/queryUserList');
}

export async function queryCurrent() {
	return request('/data/user/currentUser', {
		method: 'POST',
	});
}
