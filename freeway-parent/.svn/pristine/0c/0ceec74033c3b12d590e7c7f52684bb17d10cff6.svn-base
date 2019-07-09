import { stringify } from 'qs';
import request from '../utils/request';

// 通过标签类型查询标签列表
export async function getTagByType(params) {
	return request(`/data/contact/tag/getTagByType?${stringify(params)}`);
}

// 添加库标签
export async function addLibraryTag(params) {
	return request('/data/contact/tag/addLibraryTag', {
		method: 'POST',
		body: params,
	});
}

// 添加枚举标签
export async function addEnumTag(params) {
	return request('/data/contact/tag/addEnumTag', {
		method: 'POST',
		body: params,
	});
}
// 获取库标签
export async function getAllLibraryTag() {
	return request('/data/contact/tag/getAllLibraryTag');
}
// 删除库标签
export async function delTag(params) {
	return request(`/data/contact/tag/delTag?${stringify(params)}`);
}
// 更新库标签
export async function updateTag(params) {
	return request('/data/contact/tag/updateTag', {
		method: 'POST',
		body: params,
	});
}
